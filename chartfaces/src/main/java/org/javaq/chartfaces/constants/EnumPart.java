package org.javaq.chartfaces.constants;

/**
 * Enumeration of type of parts of typical charts.
 * 
 * @author Hongyan Li
 * @since 1.0
 */
public enum EnumPart {
	/**
	 * for an angular axis.
	 */
	angular,
	/**
	 * for an the whole chart.
	 */
	chart,
	/**
	 * for a chart data series.
	 */
	component(true),
	/**
	 * for a chart legend.
	 */
	legend,
	/**
	 * for a radial axis.
	 */
	radial,
	/**
	 * for a chart data series per to 3-varaible scatter points.
	 */
	scatter3v(true),
	/**
	 * for a x-axis.
	 */
	xaxis,
	/**
	 * for a y-axis.
	 */
	yaxis,
	/**
	 * for a header.
	 */
	header,
	/**
	 * for a footer.
	 */
	footer;

	private final boolean isComponent;

	private EnumPart() {
		this(false);
	}

	private EnumPart(final boolean isComponent) {
		this.isComponent = isComponent;
	}

	/**
	 * @return the base type is like a super class, e.g., a
	 *         <code>scatter3v</code> is a component.
	 */
	public EnumPart getBaseType() {
		return this.isComponent ? component : this;
	}
}
