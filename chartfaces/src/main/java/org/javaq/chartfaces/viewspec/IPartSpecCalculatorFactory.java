package org.javaq.chartfaces.viewspec;

import java.io.IOException;

import org.javaq.chartfaces.api.IChartPart;


/**
 * An abstraction of a factory that creates a {@link IPartSpecCalculator}
 * instance for a given {@link IChartPart}. The actual implementation should be
 * determined by the type of chart that is rendered.
 * 
 * @author Hongyan Li
 * 
 */
public interface IPartSpecCalculatorFactory<T> {

	/**
	 * @param part
	 *            the pertaining chart part.
	 * @param state
	 *            the state object that will hold the intermediary data during
	 *            the calculations of chart part specification.
	 * @return a {@link IPartSpecCalculator}.
	 * @throws IOException
	 *             if error occurred during the calculation.
	 */
	IPartSpecCalculator getCalculator(IChartPart part, T state)
			throws IOException;

}