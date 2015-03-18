package org.javaq.chartfaces.api;

/**
 * Abstraction of the data model for a chart. A chart does not have to use this
 * data model, but instead use arrays, {@link Iterable} objects, or Maps
 * objects. These data formats will eventually be converted into an instance of
 * {@link IChartingData} internally before been consumed by the chart.
 * 
 * @author Hongyan Li
 * 
 */
public interface IChartingData {
	/**
	 * @return the dependent values.
	 */
	Iterable<Double> getDependentValues();

	/**
	 * @return the independent values.
	 */
	Iterable<Double> getIndependentValues();
}