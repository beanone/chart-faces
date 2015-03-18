package org.javaq.chartfaces.event;

import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;

/**
 * Fired when the client request for an update for a progressively rendering chart.
 *
 * @author Hongyan Li
 *
 */
public class ChartProgressiveRenderEvent extends ActionEvent {
	private static final long serialVersionUID = 4915117119075279088L;
	private final int index;

	public ChartProgressiveRenderEvent(final UIComponent source, int index) {
		super(source);
		this.index = index;
	}

	public int getIndex() {
		return index;
	}
}