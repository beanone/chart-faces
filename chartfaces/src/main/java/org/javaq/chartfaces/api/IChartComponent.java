package org.javaq.chartfaces.api;

import org.javaq.chartfaces.util.NumberRange;

/**
 * Abstraction of an {@link IChartPart} that is a chart component. A chart
 * component contains the data that corresponds to one charting data series.
 * 
 * @author Hongyan Li
 * @since 1.0
 */
public interface IChartComponent extends IChartPart {

	/**
	 * @return the IDs of the controlling axes of this. The IDs referenced must
	 *         be defined. The IDs must be separated with comma. There should be
	 *         only one or two IDs. If there are two IDs, the IDs must reference
	 *         to axes that are normal to each other. If IDs of both axes, or
	 *         either of the axes is not specified, the default axes will be
	 *         used. If there are no axes specified, the default will be
	 *         generated from all the component data values. If one or more than
	 *         one axes is specified for a type of axis, then the first one
	 *         defined in the chart tag will be used.
	 */
	String getAxesIds();

	/**
	 * @return the charting data.
	 */
	IChartingData getChartingData();

	/**
	 * @return the charting data.
	 */
	IChartingDataXYView getChartingDataXYView();

	/**
	 * TODO: define interface for instruction handler.
	 * <p/>
	 * Retrieves special instructions for the rendering of this element. This is
	 * an object because potentially we can allow the use of a binding
	 * expression which returns a special instruction handler.
	 * 
	 * @return the label of the element as an object.
	 */
	Object getInstructions();

	/**
	 * Retrieves the label of the element.
	 * 
	 * @return the label of the element as an object.
	 */
	Object getLegendLabel();

	/**
	 * @return X axis range.
	 */
	NumberRange getXRange();

	/**
	 * @return Y axis range.
	 */
	NumberRange getYRange();

	/**
	 * Sets the X axis range.
	 */
	void setXRange(NumberRange range);

	/**
	 * Sets the Y axis range.
	 */
	void setYRange(NumberRange range);

	boolean isShowTooltips();
}