package org.javaq.chartfaces.event;

import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;

/**
 * Fired when the client request for the server to reset the chart to its
 * original state.
 * 
 * @author Hongyan Li
 * 
 */
public class ChartResetEvent extends ActionEvent {
	private static final long serialVersionUID = 8055779082805170544L;

	public ChartResetEvent(final UIComponent component) {
		super(component);
	}
}
