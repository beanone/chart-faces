package org.javaq.chartfaces.event.factory.impl;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.FacesEvent;

import org.javaq.chartfaces.api.Box;
import org.javaq.chartfaces.component.impl.UIChart;
import org.javaq.chartfaces.event.ChartWheelEvent;
import org.javaq.chartfaces.event.factory.IChartEventCreator;


public class WheelEventCreator implements IChartEventCreator {

	@Override
	public FacesEvent createEvent(final FacesContext context, final UIComponent component) {
		final String clientId = component.getClientId(context);
		final Map<String, String> params = RequestUtil.getParameterMap(context);

		if (RequestUtil.isAjaxRequest(params, clientId)) {
			if(!(component instanceof UIChart)) {
				throw new IllegalArgumentException();
			}
			double centerX = Double.valueOf(params.get("at.x"));
			double centerY = Double.valueOf(params.get("at.y"));
			final double delta = Double.valueOf(params.get("delta"));

			// cordinates as a percentage relative to the bottom left of the
			// inner charting area box
			final UIChart chart = (UIChart) component;
			final Box innerBounds = chart.getChartingAreaBounds();
			centerX = (centerX - innerBounds.getOriginX())
					/ innerBounds.getWidth();
			centerY = (innerBounds.getHeight() - centerY +
					innerBounds.getOriginY()) / innerBounds.getHeight();

			return createEvent(component, centerX, centerY, delta);
		}
		return null;
	}

	@Override
	public Class<? extends FacesEvent> getSuportedEventClass() {
		return ChartWheelEvent.class;
	}

	@Override
	public String getSupportedEventName() {
		return "wheel";
	}

	protected FacesEvent createEvent(final UIComponent component, final double centerX,
			final double centerY, final double delta) {
		return new ChartWheelEvent(component, centerX, centerY, delta);
	}
}