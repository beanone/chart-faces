package org.javaq.chartfaces.viewspec.impl;

import java.io.IOException;

import org.javaq.chartfaces.api.Box;
import org.javaq.chartfaces.api.IChartPart;


public class HeaderFooterSpecCalculator extends AbstractPartSpecCalculator {
	protected HeaderFooterSpecCalculator(final SpecHelper specHelper) {
		super(specHelper);
	}

	@Override
	protected Box computeViewBox(final IChartPart part) throws IOException {
		return getSpecHelper().computeHorizontalViewBox(part, 0);
	}
}
