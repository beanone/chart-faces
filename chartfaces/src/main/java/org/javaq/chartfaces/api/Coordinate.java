package org.javaq.chartfaces.api;

/**
 * A bean that holds int x and y values that is 0 or positive.
 * 
 * @author Hongyan Li
 * 
 */
public class Coordinate {
	private int x;
	private int y;

	public Coordinate() {
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return this.x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return this.y;
	}

	/**
	 * @param x
	 *            the x to set
	 */
	public void setX(final int x) {
		this.x = x;
	}

	/**
	 * @param y
	 *            the y to set
	 */
	public void setY(final int y) {
		this.y = y;
	}
}
