package org.javaq.chartfaces.api;


/**
 * An interface that converts user data into {@link IChartingDataXYView}.
 * 
 * @author Hongyan Li
 * 
 */
public interface IChartingDataConverter {
	/**
	 * Best effort converting the passed in object to {@link IChartingDataXYView}.
	 * 
	 * @param data
	 * @return an {@link IChartingDataXYView} for the passed in data object.
	 */
	IChartingDataXYView toChartingDateXYView(Object data);
}
