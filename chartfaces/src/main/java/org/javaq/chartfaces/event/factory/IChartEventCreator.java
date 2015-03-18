package org.javaq.chartfaces.event.factory;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.FacesEvent;

/**
 * An interface that creates a {@link FacesEvent}. There will be only a single
 * instance of this created by the ChartEventFactory, and thus, no state
 * should be kept in the implementation of this.
 * 
 * @author Hongyan Li
 * 
 */
public interface IChartEventCreator {
	/**
	 * Create a {@link FacesEvent}.
	 * 
	 * @param context
	 * @param component
	 * @return a {@link FacesEvent}.
	 */
	FacesEvent createEvent(FacesContext context, UIComponent component);

	/**
	 * @return the event class whose instance this will be creating for.
	 */
	Class<? extends FacesEvent> getSuportedEventClass();

	/**
	 * @return the name of the event this will be creating.
	 */
	String getSupportedEventName();
}
