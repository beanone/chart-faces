package org.javaq.chartfaces.event;

import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;

/**
 * Fired when the client request for zooming on a chart.
 *
 * @author Hongyan Li
 *
 */
public class ChartWheelEvent extends ActionEvent {
	private static final long serialVersionUID = 6642230280522917736L;
	private final double centerX;
	private final double centerY;
	private final double delta;

	public ChartWheelEvent(final UIComponent source, final double centerX,
			final double centerY, final double delta) {
		super(source);
		this.centerX = centerX;
		this.centerY = centerY;
		this.delta = delta;
	}

	/**
	 * @return the suggested x coordinate of the center of zooming
	 */
	public double getCenterX() {
		return this.centerX;
	}

	/**
	 * @return the suggested y coordinate of the center of zooming
	 */
	public double getCenterY() {
		return this.centerY;
	}

	/**
	 * @return the delta
	 */
	public double getDelta() {
		return this.delta;
	}
}