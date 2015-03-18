package org.javaq.chartfaces.viewspec.impl;

import java.io.IOException;

import org.javaq.chartfaces.api.Box;
import org.javaq.chartfaces.api.IChartLegend;
import org.javaq.chartfaces.api.IChartPart;


public class LegendSpecCalculator extends AbstractPartSpecCalculator {
	protected LegendSpecCalculator(final SpecHelper specHelper) {
		super(specHelper);
	}

	@Override
	protected Box computeViewBox(final IChartPart part) throws IOException {
		return getSpecHelper().computeLegendViewBox((IChartLegend) part);
	}
}
