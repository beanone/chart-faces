package org.javaq.chartfaces.api;

/**
 * Abstraction of a function that scales a number. Typically used to scale or
 * transform a coordinate, e.g., transform a coordinate into logarithmic.
 * 
 * @author Hongyan Li
 * 
 */
public interface IScaler {
	/**
	 * Scales the passed in and returns the scaled.
	 * 
	 * @param original
	 * @return the scaled of the passed in.
	 */
	double scale(double original);
}
