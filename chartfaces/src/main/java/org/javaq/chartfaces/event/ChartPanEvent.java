package org.javaq.chartfaces.event;

import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;

/**
 * Fired when the client request for panning on a chart. This has the exact same
 * attributes as the zooming event except that it will be interpreted
 * differently by the application.
 * 
 * @author Hongyan Li
 * 
 */
public class ChartPanEvent extends ActionEvent {
	private static final long serialVersionUID = -7311104767118007093L;
	private final String direction;
	private final double dx;

	private final double dy;

	public ChartPanEvent(final UIComponent source, final double dx,
			final double dy,
			final String direction) {
		super(source);
		this.dx = dx;
		this.dy = dy;
		this.direction = direction;
	}

	/**
	 * @return the direction
	 */
	public String getDirection() {
		return this.direction;
	}

	/**
	 * @return the dx
	 */
	public double getDx() {
		return this.dx;
	}

	/**
	 * @return the dy
	 */
	public double getDy() {
		return this.dy;
	}
}