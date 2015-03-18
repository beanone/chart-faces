package org.javaq.chartfaces.dataspec;

import java.io.IOException;

import org.javaq.chartfaces.api.IChartComponent;



/**
 * An interface that encapsulate the range calculation logic specific to an axis
 * and its governed chart components.
 * 
 * @author Hongyan Li
 * 
 */
public interface IAxisRangeProcessor {

	/**
	 * Adds a {@link IChartComponent} that this axis governs.
	 * 
	 * @param component
	 */
	void addComponent(IChartComponent component);

	/**
	 * Processes the range.
	 * 
	 * @throws IOException
	 */
	void process() throws IOException;
}