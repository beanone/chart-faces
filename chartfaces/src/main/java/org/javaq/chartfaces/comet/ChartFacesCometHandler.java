/*
 * Copyright 2009 Prime Technology.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.javaq.chartfaces.comet;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.FactoryFinder;
import javax.faces.context.FacesContext;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.atmosphere.cpr.AtmosphereHandler;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResourceEvent;
import org.javaq.chartfaces.json.JSONObject;


public class ChartFacesCometHandler implements
		AtmosphereHandler<HttpServletRequest, HttpServletResponse> {

	private static final Logger LOGGER = Logger
			.getLogger(ChartFacesCometHandler.class.getName());

	@Override
	public void onRequest(
			final AtmosphereResource<HttpServletRequest, HttpServletResponse> event)
			throws IOException {
		if (ChartFacesCometHandler.LOGGER.isLoggable(Level.FINE)) {
			ChartFacesCometHandler.LOGGER.fine("Handling comet event");
		}

		final HttpServletRequest request = event.getRequest();
		final HttpServletResponse response = event
				.getResponse();

		if (request.getMethod().equals("GET")) {
			response.setContentType("text/html;charset=ISO-8859-1");
		    response.addHeader("Cache-Control", "must-revalidate");
		    response.addHeader("Cache-Control", "no-store");
		    response.addHeader("Cache-Control", "no-cache");
			response.addHeader("Cache-Control", "private");
			response.addHeader("Pragma", "no-cache");
			// some date in the past
	        response.addHeader("Expires", "Mon, 8 Aug 2006 10:00:00 GMT");


			event.suspend();

			if (ChartFacesCometHandler.LOGGER.isLoggable(Level.FINE)) {
				ChartFacesCometHandler.LOGGER.log(Level.FINE, "Client:\"{0}\" has subscribed",
						request.getRemoteAddr());
			}

		} else if (request.getMethod().equals("POST")) {
			if (ChartFacesCometHandler.LOGGER.isLoggable(Level.FINE)) {
				ChartFacesCometHandler.LOGGER.fine("Handling publish event request");
			}

			final Set<AtmosphereResource<?, ?>> channelSubscribers = new HashSet<AtmosphereResource<?, ?>>();

			final Collection<AtmosphereResource<?, ?>> broadcassterResources = event
					.getBroadcaster().getAtmosphereResources();
			for (final AtmosphereResource<?, ?> resource : broadcassterResources) {
				final HttpServletRequest suspendedRequest = (HttpServletRequest) resource
						.getRequest();
				final String subscribedChannel = suspendedRequest.getPathInfo()
						.substring(1);
				final String channelToBroadcast = (String) request
						.getAttribute(CometContext.CHANNEL_NAME);

				if (subscribedChannel.equalsIgnoreCase(channelToBroadcast)) {
					channelSubscribers.add(resource);
				}
			}

			event.getBroadcaster().broadcast(
					request.getAttribute(CometContext.PUBLISH_DATA),
					channelSubscribers);

			// Complete forwarded PrimeFaces ajax request
			final LifecycleFactory factory = (LifecycleFactory) FactoryFinder
					.getFactory(FactoryFinder.LIFECYCLE_FACTORY);
			final Lifecycle lifecycle = factory
					.getLifecycle(LifecycleFactory.DEFAULT_LIFECYCLE);
			lifecycle.render(FacesContext.getCurrentInstance());
		}
	}

	@Override
	public void onStateChange(
			final AtmosphereResourceEvent<HttpServletRequest, HttpServletResponse> event)
			throws IOException {
		if (event.getMessage() == null) {
			return;
		}

		if (event.isCancelled()) {
			if (ChartFacesCometHandler.LOGGER.isLoggable(Level.FINE)) {
				ChartFacesCometHandler.LOGGER.fine("Ignoring publishing cancelled event");
			}

			return;
		}

		if (ChartFacesCometHandler.LOGGER.isLoggable(Level.FINE)) {
			ChartFacesCometHandler.LOGGER.fine("Publishing to subsciber.");
		}

		final HttpServletRequest request = event.getResource().getRequest();
		final HttpServletResponse response = (event.getResource().getResponse());

		final Object msg = event.getMessage();
		String jsonData = null;

		jsonData = isBean(msg) ? String.format("{\"data\":%s}", JSONObject.newInstance(msg).toString()) :
			JSONObject.newInstance().put("data", msg.toString()).toString();

		final String widget = request.getParameter("widget");
		// TODO: how to avoid the risk of cross site scripting?
		final String script = String.format(
			"<script type=\"text/javascript\">window.parent.%1$s.handlePublish(%2$s);</script>", widget, jsonData);

		response.getWriter().write(script);
		response.getWriter().flush();

		if (ChartFacesCometHandler.LOGGER.isLoggable(Level.FINE)) {
			ChartFacesCometHandler.LOGGER.log(Level.FINE, "Publishing to \"{0}\" has completed",
					request.getRemoteAddr());
		}
	}

	private boolean isBean(final Object value) {
		if (value == null) {
			return false;
		}

		if (value instanceof Boolean || value instanceof String
				|| value instanceof Number) {
			return false;
		}

		return true;
	}
}