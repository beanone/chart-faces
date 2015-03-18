package org.javaq.chartfaces.event;

import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;

/**
 * Fired when the client request for zooming on a chart.
 * 
 * @author Hongyan Li
 * 
 */
public class ChartZoomEvent extends ActionEvent {
	private static final long serialVersionUID = 4384332708485953058L;

	private final double endX;

	private final double endY;

	private final double startX;

	private final double startY;

	public ChartZoomEvent(final UIComponent source, final double startX,
			final double startY, final double endX, final double endY) {
		super(source);
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
	}

	/**
	 * @return the endX
	 */
	public double getEndX() {
		return this.endX;
	}

	/**
	 * @return the endY
	 */
	public double getEndY() {
		return this.endY;
	}

	/**
	 * @return the startX
	 */
	public double getStartX() {
		return this.startX;
	}

	/**
	 * @return the startY
	 */
	public double getStartY() {
		return this.startY;
	}
}