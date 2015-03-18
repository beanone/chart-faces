/*
 * Copyright 2010 Prime Technology.
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
package org.javaq.chartfaces.facelets;

import javax.faces.view.facelets.ComponentConfig;
import javax.faces.view.facelets.ComponentHandler;
import javax.faces.view.facelets.MetaRuleset;

import org.javaq.chartfaces.event.ChartLiveEvent;
import org.javaq.chartfaces.event.ChartPanEvent;
import org.javaq.chartfaces.event.ChartProgressiveRenderEvent;
import org.javaq.chartfaces.event.ChartResetEvent;
import org.javaq.chartfaces.event.ChartZoomEvent;
import org.javaq.chartfaces.event.ChartWheelEvent;


public class ChartEventBindingHandler extends ComponentHandler {

	public ChartEventBindingHandler(final ComponentConfig config) {
		super(config);
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected MetaRuleset createMetaRuleset(final Class type) {
		final MetaRuleset metaRuleset = super.createMetaRuleset(type);
		final Class[] resetEventClass = new Class[] { ChartResetEvent.class };
		final Class[] liveEventClass = new Class[] { ChartLiveEvent.class };
		final Class[] progressiveRenderEventClass = new Class[] { ChartProgressiveRenderEvent.class };
		final Class[] zoomEventClass = new Class[] { ChartZoomEvent.class };
		final Class[] wheelEventClass = new Class[] { ChartWheelEvent.class };
		final Class[] panEventClass = new Class[] { ChartPanEvent.class };

		metaRuleset.addRule(
				new MethodRule("resetListener", null, resetEventClass));
		metaRuleset.addRule(
				new MethodRule("liveListener", null, liveEventClass));
		metaRuleset.addRule(
				new MethodRule("progressiveRenderListener", null, progressiveRenderEventClass));
		metaRuleset.addRule(
				new MethodRule("zoomListener", null, zoomEventClass));
		metaRuleset.addRule(
				new MethodRule("wheelListener", null, wheelEventClass));
		metaRuleset.addRule(
				new MethodRule("panListener", null, panEventClass));

		return metaRuleset;
	}
}