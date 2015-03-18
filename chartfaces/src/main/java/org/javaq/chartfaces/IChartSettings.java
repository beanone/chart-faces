package org.javaq.chartfaces;

import org.javaq.chartfaces.api.IChartAxis;
import org.javaq.chartfaces.api.IChartingDataConverter;
import org.javaq.chartfaces.constants.EnumLayout;
import org.javaq.chartfaces.constants.EnumPart;
import org.javaq.chartfaces.constants.EnumPosition;
import org.javaq.chartfaces.constants.EnumSymbol;
import org.javaq.chartfaces.constants.EnumTickDirection;
import org.javaq.chartfaces.iterable.IIterableConverter;
import org.javaq.chartfaces.iterable.ISizeAwareIterable;

/**
 * Abstraction settings for a chart. The user can extend this and override only
 * the settings that does not meet his needs. To apply you own settings, extend
 * this and simply bind an instance of you extension to the chart tag settings
 * attribute.
 *
 * @author Hongyan Li
 *
 */
public interface IChartSettings {
	/**
	 * @return an {@link IIterableConverter} that will be used to convert the axis labels to an iterable.
	 */
	IIterableConverter<IChartAxis, Object, ISizeAwareIterable<Object>> getAxisLabelAccessor();

	/**
	 * @return the line style for the axes.
	 */
	String getAxisLineStyle();

	/**
	 * Get the distance the origin point is from the min value of the user data
	 * (NOT the axis-min). This value will be used to help determine the axis
	 * min only if the attribute is not specified by the user. The value is
	 * treated as a percent of the data value range. For example, 10 means that
	 * it is 10% of the range. The system will not try to validate whether the
	 * value is valid.
	 *
	 * @return the distance the origin point is from the min value of the user
	 *         data (NOT the axis-min).
	 */
	double getAxisOffsetPercent();

	/**
	 * Get the distance the origin point is from the max value of the user data
	 * (NOT the axis-max). This value will be used to help determine the axis
	 * max only if the attribute is not specified by the user. The value is
	 * treated as a percent of the data value range. For example, 10 means that
	 * it is 10% of the range. The system will not try to validate whether the
	 * value is valid.
	 *
	 * @return the distance from the end of the axis to the max value of the
	 *         user data (NOT the axis-max).
	 */
	double getAxisSlackPercent();

	/**
	 * @return an {@link IIterableConverter} that will be used to convert the
	 *         value attribute to an {@link ISizeAwareIterable}. The framework
	 *         will use the returned to read the user data values.
	 */
	IIterableConverter<IChartAxis, Double, ISizeAwareIterable<Double>> getAxisValueAccessor();

	/**
	 * @return the style for the border that enclosing the whole chart.
	 */
	String getBorderStyle();

	/**
	 * @return the style for the axes caption texts.
	 */
	String getCaptionStyle();

	/**
	 * @return the total height of the chart.
	 */
	String getChartHeight();

	/**
	 * @return the default line style for line graphs.
	 */
	String getChartLineStyle();

	/**
	 * @return the total width of the chart.
	 */
	String getChartWidth();

	/**
	 * @return the default charting data converter for a chart component.
	 */
	IChartingDataConverter getDataConverter();

	/**
	 * @return the style for the footer texts.
	 */
	String getFooterStyle();

	/**
	 * @return the default for text-anchor attribute of a footer tag.
	 */
	String getFooterTextAnchorDefault();

	/**
	 * @return the grid line density.
	 */
	int getGridLineDensity();

	/**
	 * @return style for the major grid lines - the lines coincide with the tick
	 *         marks.
	 */
	String getGridLineStyle();

	/**
	 * @return the style for the header texts.
	 */
	String getHeaderStyle();

	/**
	 * @return the default for text-anchor attribute of a header tag.
	 */
	String getHeaderTextAnchorDefault();

	/**
	 * @return the padding in between the chart parts, e.g., between axes labels
	 *         and the axes.
	 */
	int getInternalPadding();

	/**
	 * @return the style for the axes label texts.
	 */
	String getLabelStyle();

	/**
	 * The space between labels and ticks
	 *
	 * @return the space between the axis label and ticks.
	 */
	int getLabelTickSpacing();

	/**
	 * @return the height of a legend.
	 */
	int getLegendHeight();

	/**
	 * @return the layout of a legend.
	 * @see EnumLayout
	 */
	EnumLayout getLegendLayout();

	/**
	 * @return the position of a legend.
	 * @see EnumPosition
	 */
	EnumPosition getLegendPosition();

	/**
	 * @return the width of a legend.
	 */
	int getLegendWidth();

	/**
	 * @return the margin from the border
	 */
	int getMargin();

	/**
	 * @return the padding inside of the chart borders.
	 */
	int getPadding();

	/**
	 * The default update interval for the chart if it is live (when the live
	 * attribute is true).
	 *
	 * @return update interval in milliseconds.
	 */
	int getRefreshInterval();

	/**
	 * @return the default symbol fill color. Use null for black.
	 */
	EnumSymbol getScatterSymbol();

	/**
	 * @return the default symbol fill color. Use null for black.
	 */
	String getScatterSymbolFill();

	/**
	 * @return the default symbol opacity. 0 for totally transparent and 1 for
	 *         totally opaque.
	 */
	double getScatterSymbolOpacity();

	/**
	 * @return the default symbol size for a scatter plot.
	 */
	int getScatterSymbolSize();

	/**
	 * @return the default symbol stroke width.
	 */
	int getScatterSymbolStrokeWidth();

	/**
	 * @param partType
	 *            the part type, be an xaxis, yaxis, radial, or an angular.
	 * @param partPosition
	 *            the position of the part.
	 * @return the direction of the ticks take is valid to the part type.
	 */
	EnumTickDirection getTickDirection(EnumPart partType,
			EnumPosition partPosition);

	/**
	 * @return the height for the tick marks.
	 */
	int getTickHeight();

	/**
	 * @return the line style for the tick marks.
	 */
	String getTickStyle();

	/**
	 * @return the height of a x-axis.
	 */
	int getXAxisHeight();

	/**
	 * @return the position of a x-axis.
	 * @see EnumPosition
	 */
	EnumPosition getXAxisPosition();

	/**
	 * @return the position of a y-axis.
	 * @see EnumPosition
	 */
	EnumPosition getYAxisPosition();

	/**
	 * @return the width of a y-axis.
	 */
	int getYAxisWidth();
}