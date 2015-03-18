package org.javaq.chartfaces.api;

import org.javaq.chartfaces.document.IElement;

/**
 * Abstraction of a handler that is responsible for creating one element of the
 * legend.
 *
 * @author Hongyan Li
 *
 */
public interface ILegendHandler {
	/**
	 * This must return a non-null value.
	 *
	 * @return one {@link IElement} that represents one element of the legend.
	 */
	IElement createElement();
}
