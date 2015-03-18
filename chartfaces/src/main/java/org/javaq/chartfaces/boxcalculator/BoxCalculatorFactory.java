package org.javaq.chartfaces.boxcalculator;

import org.javaq.chartfaces.constants.EnumPosition;

/**
 * Factory for {@link BoxCalculator} objects.
 *
 * @author hongyan99
 *
 */
public interface BoxCalculatorFactory {
	/**
	 * Fetches the {@link BoxCalculator} for the passed in {@link EnumPosition}.
	 *
	 * @param position an {@link EnumPosition}.
	 * @return a {@link BoxCalculator}.
	 */
	BoxCalculator getCalculator(EnumPosition position);
}
