package org.javaq.chartfaces.event.factory.impl;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.FacesEvent;

import org.javaq.chartfaces.component.impl.UIChart;
import org.javaq.chartfaces.event.ChartProgressiveRenderEvent;
import org.javaq.chartfaces.event.factory.IChartEventCreator;


public class ProgressiveRenderEventCreator implements IChartEventCreator {

	@Override
	public FacesEvent createEvent(final FacesContext context, final UIComponent component) {
		final String clientId = component.getClientId(context);
		final Map<String, String> params = RequestUtil.getParameterMap(context);

		if (RequestUtil.isAjaxRequest(params, clientId)) {
			if(!(component instanceof UIChart)) {
				throw new IllegalArgumentException();
			}
			int index = Integer.valueOf(params.get("index"));
			return new ChartProgressiveRenderEvent(component, index);
		}

		return null;
	}

	@Override
	public Class<? extends FacesEvent> getSuportedEventClass() {
		return ChartProgressiveRenderEvent.class;
	}

	@Override
	public String getSupportedEventName() {
		return "progressive";
	}
}
