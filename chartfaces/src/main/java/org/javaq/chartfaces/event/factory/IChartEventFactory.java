package org.javaq.chartfaces.event.factory;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.FacesEvent;

/**
 * Abstraction of a factory that generates chart events.
 * 
 * @author Hongyan Li
 * 
 */
public interface IChartEventFactory {
	/**
	 * Create a {@link FacesEvent} from the passed in.
	 * 
	 * @param context a {@link FacesContext}
	 * @param component a {@link UIComponent}
	 * @return a {@link FacesEvent}.
	 */
	FacesEvent createEvent(FacesContext context, UIComponent component);

	/**
	 * @return the name of the event if it is supported. Otherwise return null.
	 */
	String getEventName(Class<? extends FacesEvent> eventClass);
}
