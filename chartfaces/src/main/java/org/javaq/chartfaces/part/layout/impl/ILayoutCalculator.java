package org.javaq.chartfaces.part.layout.impl;

import java.io.IOException;

/**
 * Abstraction of a layout calculator.
 * 
 * @author Hongyan Li
 * 
 */
public interface ILayoutCalculator {

	/**
	 * Performs the layout operation.
	 * 
	 * @throws IOException
	 */
	void execute() throws IOException;

}