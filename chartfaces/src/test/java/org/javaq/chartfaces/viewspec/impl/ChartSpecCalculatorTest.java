package org.javaq.chartfaces.viewspec.impl;

import junit.framework.Assert;

import org.javaq.chartfaces.api.IChartPart;
import org.javaq.chartfaces.api.IPartSpec;
import org.javaq.chartfaces.viewspec.IPartSpecCalculator;


public class ChartSpecCalculatorTest extends SpecCalculatorTestBase {

	@Override
	protected IChartPart createPart() {
		return getChart();
	}

	@Override
	protected IPartSpecCalculator createSpecCreator(final SpecHelper helper) {
		return new ChartSpecCalculator(helper);
	}

	@Override
	protected void customAssertions(final IPartSpec specs) {
		Assert.assertNotNull(specs.getProperties().get("width"));
		Assert.assertNotNull(specs.getProperties().get("height"));
	}
}
