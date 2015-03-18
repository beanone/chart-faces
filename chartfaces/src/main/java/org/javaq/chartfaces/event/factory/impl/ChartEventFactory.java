package org.javaq.chartfaces.event.factory.impl;

import java.util.HashMap;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.FacesEvent;

import org.javaq.chartfaces.event.factory.IChartEventCreator;
import org.javaq.chartfaces.event.factory.IChartEventFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component("chartEventFactory")
@Scope("singleton")
public class ChartEventFactory implements IChartEventFactory {
	private final Map<String, IChartEventCreator> eventCreatorMap = new HashMap<String, IChartEventCreator>();
	private final Map<Class<? extends FacesEvent>, String> supportedEventNames =
			new HashMap<Class<? extends FacesEvent>, String>();

	public ChartEventFactory() {
		addEventCreator(new ProgressiveRenderEventCreator());
		addEventCreator(new LiveEventCreator());
		addEventCreator(new ResetEventCreator());
		addEventCreator(new PanEventCreator());
		addEventCreator(new ZoomEventCreator());
		addEventCreator(new WheelEventCreator());
	}

	@Override
	public FacesEvent createEvent(final FacesContext context, final UIComponent component) {
		final ExternalContext external = context.getExternalContext();
		final Map<String, String> params = external.getRequestParameterMap();
		final String eventType = params.get("event.type");
		final IChartEventCreator creator = this.eventCreatorMap.get(eventType);
		if (creator != null) {
			return creator.createEvent(context, component);
		} else {
			return null;
		}
	}

	@Override
	public String getEventName(final Class<? extends FacesEvent> eventClass) {
		return this.supportedEventNames.get(eventClass);
	}

	private void addEventCreator(final IChartEventCreator creator) {
		this.eventCreatorMap.put(creator.getSupportedEventName(), creator);
		this.supportedEventNames.put(creator.getSuportedEventClass(),
				creator.getSupportedEventName());
	}
}
