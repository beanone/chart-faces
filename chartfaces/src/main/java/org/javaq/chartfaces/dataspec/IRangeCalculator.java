package org.javaq.chartfaces.dataspec;

import java.io.IOException;

/**
 * Abstraction of a strategy that helps to determine the ranges for a chart
 * part, e.g., the range of a x-axis, or y-axis, etc..
 * 
 * @author Hongyan Li
 * 
 */
public interface IRangeCalculator<T> {
	/**
	 * Calculates the ranges for the passed in.
	 * 
	 * @param part
	 */
	void calculateRanges(T part) throws IOException;
}
