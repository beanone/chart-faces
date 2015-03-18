package org.javaq.chartfaces.api;

import org.javaq.chartfaces.util.NumberRange;

/**
 * Abstraction of an {@link IChartPart} that is a chart axis. Since SVG chart
 * elements are all position based, the "class" attribute is not supported to
 * avoid potential layout conflict situation. However, one can still use the
 * style attribute and the default setting bean binding.
 * <p/>
 * The two most important fields of an axis is its <code>value</value> field
 * and <code>label<field>. The former defines the coordinates of the ticks,
 * and the later the labels. One of the two must be specified. If the <code>value</code>
 * field is specified without the <code>label</code>, the values in the
 * <code>value</code> field will be used as the labels. If the
 * <code>label</code> is specified without the <code>value</code>, the index of
 * the labels will be used as the value.
 * 
 * @author Hongyan Li
 * @since 1.0
 */
public interface IChartAxis extends IChartPart {
	/**
	 * @return the caption of the axis.
	 */
	String getCaption();

	/**
	 * @return the style for the captions of the axis.
	 */
	String getCaptionStyle();

	/**
	 * @return the grid line density. It can be any integer. The default value
	 *         is 0. This density tells the renderer how many lines in between
	 *         the ticks. If it is 0 or negative, no grid lines will be
	 *         rendered, if 1, the grid lines will be only on the ticks. If n >
	 *         1, there will be lines on the ticks and n-1 grid lines in
	 *         between.
	 */
	int getGridLineDensity();

	/**
	 * @return the style for the grid lines that are on the tick marks (major
	 *         grid lines).
	 */
	String getGridLineStyle();

	/**
	 * Either the label or the value field must be given.
	 * <p/>
	 * The label field can be an array of strings or an {@link Iterable} of
	 * strings. If this label field is not given, the values in the value field
	 * will be used.
	 * 
	 * @return the labels of the axis.
	 */
	Object getLabel();

	/**
	 * @return the angle of the ticker labels
	 */
	int getLabelAngle();

	/**
	 * @return the ticker label values for the axis as an {@link Iterable}.
	 */
	Iterable<Object> getLabelIterable();

	/**
	 * @return the style for the labels of the axis.
	 */
	String getLabelStyle();

	/**
	 * @return the space between labels and ticks.
	 */
	int getLabelTickSpacing();

	/**
	 * @return the style for the axis line.
	 */
	String getLineStyle();

	/**
	 * TODO: investigate how to make this required.
	 * <p/>
	 * The max value of the axis.
	 * 
	 * @return the upper bound bound of the axis.
	 */
	Double getMax();

	/**
	 * The min value of the axis.
	 * 
	 * @return the lower bound of the axis.
	 */
	Double getMin();

	/**
	 * @return the axis number range.
	 */
	NumberRange getRange();

	/**
	 * @return the tick direction. The tick direction can be left, right for
	 *         y-axis ticks, top or bottom for x-axis ticks, or clockwise or
	 *         anti-clockwise for radial-axis, or inward or outward for
	 *         angular-axis, or middle for any axes.
	 */
	String getTickDirection();

	/**
	 * @return the tick height. The tick height can be specified either in
	 *         percent or absolute value. Usually 1% - 2% is a good value.
	 */
	int getTickHeight();

	/**
	 * @return the style of the ticks.
	 */
	String getTickStyle();

	/**
	 * @return title text of the axis.
	 */
	String getTitle();

	/**
	 * @return the ticker data values for the axis as an {@link Iterable}.
	 */
	Iterable<Double> getValueIterable();

	/**
	 * Sets the max value.
	 * 
	 * @param max
	 */
	void setMax(Double max);

	/**
	 * Sets the min value.
	 * 
	 * @param min
	 */
	void setMin(Double min);
}