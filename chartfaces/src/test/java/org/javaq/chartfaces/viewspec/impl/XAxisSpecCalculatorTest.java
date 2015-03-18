package org.javaq.chartfaces.viewspec.impl;

import junit.framework.Assert;

import org.javaq.chartfaces.api.Box;
import org.javaq.chartfaces.api.IChartPart;
import org.javaq.chartfaces.component.impl.UIChart;
import org.javaq.chartfaces.viewspec.IPartSpecCalculator;
import org.junit.Test;


public class XAxisSpecCalculatorTest extends SpecCalculatorTestBase {
	private static final String AXIS_ID = "xaxis";
	private static final double AXIS_MIN = -5.1;
	private static final double AXIS_MAX = 55.3;

	@Test
	public void testComputeViewBox() throws Exception {
		final IChartPart axisPart = createPart();
		final UIChart chart = createChart();
		final HorizontalPartSpecCalculator creator =
				(HorizontalPartSpecCalculator) createSpecCreator(chart, axisPart);
		final Box b = creator.computeViewBox(axisPart);
		Assert.assertEquals(1540, b.getWidth());
		Assert.assertTrue(b.getHeight() > 0);
		Assert.assertTrue(b.getHeight() < 1000);
	}

	@Override
	protected IChartPart createPart() {
		return createXAxis(XAxisSpecCalculatorTest.AXIS_ID,
				XAxisSpecCalculatorTest.AXIS_MIN,
				XAxisSpecCalculatorTest.AXIS_MAX);
	}

	@Override
	protected IPartSpecCalculator createSpecCreator(final SpecHelper helper) {
		return new HorizontalPartSpecCalculator(helper);
	}

}
