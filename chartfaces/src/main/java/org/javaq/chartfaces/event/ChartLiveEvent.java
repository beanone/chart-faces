package org.javaq.chartfaces.event;

import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;

/**
 * Fired when the client request for an update for a live chart.
 * 
 * @author Hongyan Li
 * 
 */
public class ChartLiveEvent extends ActionEvent {
	private static final long serialVersionUID = -4263946517375450323L;

	public ChartLiveEvent(final UIComponent source) {
		super(source);
	}
}