package org.javaq.chartfaces.event.factory.impl;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.FacesEvent;

import org.javaq.chartfaces.api.Box;
import org.javaq.chartfaces.component.impl.UIChart;
import org.javaq.chartfaces.event.ChartPanEvent;
import org.javaq.chartfaces.event.factory.IChartEventCreator;


public class PanEventCreator implements IChartEventCreator {

	@Override
	public FacesEvent createEvent(final FacesContext context, final UIComponent component) {
		final String clientId = component.getClientId(context);
		final Map<String, String> params = RequestUtil.getParameterMap(context);

		if (RequestUtil.isAjaxRequest(params, clientId)) {
			if(!(component instanceof UIChart)) {
				throw new IllegalArgumentException();
			}
			double dx = getValue(params, "dx", 0);
			double dy = getValue(params, "dy", 0);
			final String direction = params.get("dir");

			// cordinates as a percentage relative to the bottom left of the
			// inner charting area box
			final UIChart chart = (UIChart) component;
			final Box innerBounds = chart.getChartingAreaBounds();
			dx = dx / innerBounds.getWidth();
			dy = -dy / innerBounds.getHeight();

			return new ChartPanEvent(component, dx, dy, direction);
		}
		return null;
	}

	@Override
	public Class<? extends FacesEvent> getSuportedEventClass() {
		return ChartPanEvent.class;
	}

	@Override
	public String getSupportedEventName() {
		return "move";
	}

	private double getValue(final Map<String, String> params, final String key,
			final double defaultValue) {
		final String val = params.get(key);
		if (val != null && !"null".equals(val) && !"undefined".equals(val)) {
			return Double.valueOf(val);
		}
		return defaultValue;
	}
}
