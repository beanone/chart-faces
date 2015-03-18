package org.javaq.chartfaces.constants;

/**
 * The single place where we keep the z-index order for all types of chart
 * parts.
 * 
 * @author Hongyan Li
 * 
 */
public enum EnumPartZIndex {
	/**
	 * z-index for an axis label.
	 */
	axislabel(9999),
	/**
	 * z-index for an axis line.
	 */
	axisline(999),
	/**
	 * z-index for an axis tick.
	 */
	axistick(99),
	/**
	 * z-index for caption.
	 */
	caption(99999),
	/**
	 * z-index for data line.
	 */
	dataline(7),
	/**
	 * z-index for data point.
	 */
	datapoint(9),
	/**
	 * z-index for grid line.
	 */
	gridline(0);

	private final int index;

	private EnumPartZIndex(final int index) {
		this.index = index;
	}

	/**
	 * @return the z-index as number.
	 */
	public int toIndex() {
		return this.index;
	}
}
