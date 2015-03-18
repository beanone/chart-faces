package org.javaq.chartfaces.viewspec.impl;

import java.io.IOException;

import org.javaq.chartfaces.api.Box;
import org.javaq.chartfaces.api.IChartPart;
import org.javaq.chartfaces.api.IPartSpec;
import org.javaq.chartfaces.viewspec.IPartSpecCalculator;


/**
 * An abstract implementation of the {@link IPartSpecCalculator} interface.
 * 
 * @author Hongyan Li
 * 
 */
public abstract class AbstractPartSpecCalculator implements IPartSpecCalculator {
	private final SpecHelper specHelper;

	protected AbstractPartSpecCalculator(final SpecHelper specHelper) {
		this.specHelper = specHelper;
	}

	@Override
	public IPartSpec calculate(final IChartPart part) throws IOException {
		final Box viewBox = computeViewBox(part);
		part.getPartSpec().setViewBox(viewBox);
		return part.getPartSpec();
	}

	/**
	 * Compute the view box of the IChartPart this is creating the specs for.
	 * Note that since this is determined by the space available for graphing,
	 * the addition or removal of a IChartPart will affect the result of this if
	 * that IChartPart requires exclusive ownership of a piece of the graphing
	 * space.
	 * 
	 * TODO: Need to reflect this in a more clear way in the implementation!!!
	 * 
	 * @param part
	 * @return the view Box for the passed in chart part.
	 * @throws IOException
	 */
	protected abstract Box computeViewBox(final IChartPart part)
			throws IOException;

	/**
	 * @return the specHelper
	 */
	protected SpecHelper getSpecHelper() {
		return this.specHelper;
	}

}
