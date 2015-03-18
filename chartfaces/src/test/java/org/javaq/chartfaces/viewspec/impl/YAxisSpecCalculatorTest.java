package org.javaq.chartfaces.viewspec.impl;

import junit.framework.Assert;

import org.javaq.chartfaces.api.Box;
import org.javaq.chartfaces.api.IChartPart;
import org.javaq.chartfaces.component.impl.UIChart;
import org.javaq.chartfaces.viewspec.IPartSpecCalculator;
import org.junit.Test;


public class YAxisSpecCalculatorTest extends SpecCalculatorTestBase {
	private static final String AXIS_ID = "yaxis";
	private static final double AXIS_MIN = -5.1;
	private static final double AXIS_MAX = 55.3;

	@Test
	public void testComputeViewBox() throws Exception {
		final IChartPart axisPart = createPart();
		final UIChart chart = createChart();
		chart.setHeight("100%");
		final VerticalPartSpecCalculator creator =
				(VerticalPartSpecCalculator) createSpecCreator(chart, axisPart);
		final Box b = creator.computeViewBox(axisPart);
		Assert.assertEquals(940, b.getHeight());
		Assert.assertTrue(b.getWidth() > 0);
		Assert.assertTrue(b.getWidth() < 1590);
	}

	@Override
	protected IChartPart createPart() {
		return createYAxis(YAxisSpecCalculatorTest.AXIS_ID,
				YAxisSpecCalculatorTest.AXIS_MIN,
				YAxisSpecCalculatorTest.AXIS_MAX);
	}

	@Override
	protected IPartSpecCalculator createSpecCreator(final SpecHelper helper) {
		return new VerticalPartSpecCalculator(helper);
	}

}
