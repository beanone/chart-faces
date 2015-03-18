package org.javaq.chartfaces.event.factory.impl;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.FacesEvent;

import org.javaq.chartfaces.api.Box;
import org.javaq.chartfaces.component.impl.UIChart;
import org.javaq.chartfaces.event.ChartZoomEvent;
import org.javaq.chartfaces.event.factory.IChartEventCreator;


public class ZoomEventCreator implements IChartEventCreator {

	@Override
	public FacesEvent createEvent(final FacesContext context, final UIComponent component) {
		final String clientId = component.getClientId(context);
		final Map<String, String> params = RequestUtil.getParameterMap(context);

		if (RequestUtil.isAjaxRequest(params, clientId)) {
			if(!(component instanceof UIChart)) {
				throw new IllegalArgumentException();
			}
			final double downX = Double.valueOf(params.get("down.x"));
			final double downY = Double.valueOf(params.get("down.y"));
			final double upX = Double.valueOf(params.get("up.x"));
			final double upY = Double.valueOf(params.get("up.y"));

			// coordinates as a percentage in the outer-most svg box
			double startX = Math.min(downX, upX);
			double startY = Math.min(downY, upY);
			double endX = Math.max(downX, upX);
			double endY = Math.max(downY, upY);

			// cordinates as a percentage relative to the bottom left of the
			// inner charting area box
			final UIChart chart = (UIChart) component;
			final Box innerBounds = chart.getChartingAreaBounds();
			startX = (startX - innerBounds.getOriginX())
					/ innerBounds.getWidth();
			endX = (endX - innerBounds.getOriginX())
					/ innerBounds.getWidth();
			startY = (innerBounds.getHeight() - startY +
					innerBounds.getOriginY()) / innerBounds.getHeight();
			endY = (innerBounds.getHeight() - endY +
					innerBounds.getOriginY()) / innerBounds.getHeight();

			return createEvent(component, startX, startY, endX, endY);
		}
		return null;
	}

	@Override
	public Class<? extends FacesEvent> getSuportedEventClass() {
		return ChartZoomEvent.class;
	}

	@Override
	public String getSupportedEventName() {
		return "zoom";
	}

	protected FacesEvent createEvent(final UIComponent component, final double startX,
			final double startY, final double endX, final double endY) {
		return new ChartZoomEvent(component, startX, startY, endX, endY);
	}
}
