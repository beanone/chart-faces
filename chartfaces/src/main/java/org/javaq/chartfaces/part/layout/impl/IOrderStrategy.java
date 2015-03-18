package org.javaq.chartfaces.part.layout.impl;

import org.javaq.chartfaces.api.IChartPart;


/**
 * IChartPart ordering strategy.
 * 
 * @author Hongyan Li
 * 
 */
public interface IOrderStrategy {
	/**
	 * Applies order to the passed in and returns the ordered subset. The parts
	 * that are not applicable for the specific type of ordering should be
	 * removed from the returned list.
	 * 
	 * @param parts
	 *            the parts to be ordered.
	 * @return a ordered subset of the passed in.
	 */
	Iterable<IChartPart> order(Iterable<IChartPart> parts);
}
