package org.javaq.chartfaces.viewspec.impl;

import junit.framework.Assert;

import org.javaq.chartfaces.api.Box;
import org.javaq.chartfaces.api.IChartPart;
import org.javaq.chartfaces.component.impl.UIChart;
import org.javaq.chartfaces.viewspec.IPartSpecCalculator;
import org.junit.Test;

public class LegendSpecCalculatorTest extends SpecCalculatorTestBase {
	private static final String LEGEND_ID = "alegend";
	private static final String LEGEND_WIDTH = "100px";
	private static final String LEGEND_HEIGHT = "60px";

	@Test
	public void testComputeViewBox() throws Exception {
		final UIChart chart = createChart();
		final IChartPart part = createPart();
		final LegendSpecCalculator creator = (LegendSpecCalculator) createSpecCreator(
				chart, part);
		final Box box = creator.computeViewBox(part);
		Assert.assertNotNull(box);
		// for position = "top" (the default for a legend), width is calculated
		Assert.assertEquals(1540, box.getWidth());
		Assert.assertEquals(60, box.getHeight());
	}

	@Override
	protected IChartPart createPart() {
		return createLegend(LegendSpecCalculatorTest.LEGEND_ID,
				LegendSpecCalculatorTest.LEGEND_WIDTH,
				LegendSpecCalculatorTest.LEGEND_HEIGHT);
	}

	@Override
	protected IPartSpecCalculator createSpecCreator(final SpecHelper helper) {
		return new LegendSpecCalculator(helper);
	}

}
