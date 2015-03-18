package org.javaq.chartfaces.api;

import java.io.Serializable;

/**
 * Abstraction of a box. A box has an originX, originY, a width, and a height.
 * All must be integers.
 *
 * @author Hongyan Li
 *
 */
public class Box implements Serializable {
	private static final long serialVersionUID = -3776464943075320567L;

	private final int height;
	private final int originX;
	private final int originY;
	private final int width;

	/**
	 * Construct a new instance of this.
	 *
	 * @param w
	 *            the width of the box. Must be greater than 0.
	 * @param h
	 *            the height of the box. Must be greater than 0.
	 */
	public Box(final int w, final int h) {
		this(0, 0, w, h);
	}

	/**
	 * Construct a new instance of this.
	 *
	 * @param ox
	 *            the origin x-coordinate. Must be 0 or positive number.
	 * @param oy
	 *            the origin y-coordinate. Must be 0 or positive number.
	 * @param w
	 *            the width of the box. Must be greater than 0.
	 * @param h
	 *            the height of the box. Must be greater than 0.
	 */
	public Box(final int ox, final int oy, final int w, final int h) {
		if (ox < 0 || oy < 0 || w <= 0 || h <= 0) {
			throw new IllegalArgumentException(
					"Invalid origin or size for Box: ox="+ox+", oy="+oy+", w="+w+", h="+h);
		}
		this.originX = ox;
		this.originY = oy;
		this.width = w;
		this.height = h;
	}

	public int getHeight() {
		return this.height;
	}

	public int getOriginX() {
		return this.originX;
	}

	public int getOriginY() {
		return this.originY;
	}

	public int getWidth() {
		return this.width;
	}
}