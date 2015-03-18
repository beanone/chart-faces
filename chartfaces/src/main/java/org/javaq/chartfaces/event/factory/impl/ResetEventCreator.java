package org.javaq.chartfaces.event.factory.impl;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.FacesEvent;

import org.javaq.chartfaces.event.ChartResetEvent;
import org.javaq.chartfaces.event.factory.IChartEventCreator;


public class ResetEventCreator implements IChartEventCreator {

	@Override
	public FacesEvent createEvent(final FacesContext context, final UIComponent component) {
		final String clientId = component.getClientId(context);
		final Map<String, String> params = RequestUtil.getParameterMap(context);

		if (RequestUtil.isAjaxRequest(params, clientId)) {
			return new ChartResetEvent(component);
		}

		return null;
	}

	@Override
	public Class<? extends FacesEvent> getSuportedEventClass() {
		return ChartResetEvent.class;
	}

	@Override
	public String getSupportedEventName() {
		return "reset";
	}
}
