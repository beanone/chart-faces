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

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class CometContext {
	
	public static final String CHANNEL_NAME = "primefacesCometChannel";
	public static final String CHANNEL_PATH = "/primefaces_comet/";
	public static final String PUBLISH_DATA = "primefacesPushEventMessage";
	
	private static final Logger logger = Logger.getLogger(CometContext.class.getName());
	
	public static void publish(final String channel, final Object object) {	
		if(object == null) {
			throw new IllegalArgumentException("Publish data cannot be null");
		}
		
		final FacesContext facesContext = FacesContext.getCurrentInstance();
		final HttpServletRequest request = (HttpServletRequest) facesContext.getExternalContext().getRequest();
		final HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
		
		request.setAttribute(CometContext.CHANNEL_NAME, channel);
		request.setAttribute(CometContext.PUBLISH_DATA, object);
		
		try {
			request.getRequestDispatcher(CometContext.CHANNEL_PATH + channel).forward(request, response);
		} catch (final Exception e) {
			CometContext.logger.log(Level.SEVERE, e.getMessage());
		}
	}

	private CometContext() {}
 }