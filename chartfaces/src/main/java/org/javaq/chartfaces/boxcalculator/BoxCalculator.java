package org.javaq.chartfaces.boxcalculator;

import org.javaq.chartfaces.api.Box;
import org.javaq.chartfaces.api.IChartPart;

/**
 * Calculates box for an {@link IChartPart}.
 *
 * @author hongyan99
 *
 */
public interface BoxCalculator {
	/**
	 * Calculates the Box for the {@link IChartPart} based on its dimension and position.
	 *
	 * @param containerBox the {@link Box} that contains the part box.
	 * @param part a chart part.
	 * @return the part box.
	 */
	Box calculate(Box containerBox, IChartPart part);

	/**
	 * The unique ID that identifies this {@link BoxCalculator}.
	 *
	 * @return array of ID value.
	 */
	Object[] getIds();
}
