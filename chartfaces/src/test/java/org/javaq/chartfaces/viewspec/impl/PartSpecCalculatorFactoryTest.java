package org.javaq.chartfaces.viewspec.impl;

import junit.framework.Assert;

import org.javaq.chartfaces.api.IChartPart;
import org.javaq.chartfaces.component.impl.UIChart;
import org.javaq.chartfaces.part.axis.XAxis;
import org.javaq.chartfaces.part.axis.YAxis;
import org.javaq.chartfaces.part.component.ChartComponent;
import org.javaq.chartfaces.part.layout.impl.LayoutManager;
import org.javaq.chartfaces.part.legend.ChartLegend;
import org.javaq.chartfaces.viewspec.IPartSpecCalculator;
import org.javaq.chartfaces.viewspec.IPartSpecCalculatorFactory;
import org.junit.Test;


public class PartSpecCalculatorFactoryTest {

	@Test
	public void testGetCreator() throws Exception {
		final IPartSpecCalculatorFactory<SpecHelper> factory =
				PartSpecCalculatorFactory.getInstance();
		final UIChart chart = new UIChart();
		chart.setId("anId");
		// validate that ChartSpecCreator is created
		SpecHelper helper = SpecHelper.newInstance(chart, new LayoutManager());
		IPartSpecCalculator creator = factory.getCalculator(chart, helper);
		Assert.assertTrue(creator instanceof ChartSpecCalculator);
		Assert.assertNotNull(creator);
		// validate that ComponentSpecCreator is created
		final ChartComponent cc = new ChartComponent();
		cc.setId("comp");
		chart.getChildren().add(cc);
		helper = SpecHelper.newInstance(chart, new LayoutManager());
		creator = factory.getCalculator(cc, helper);
		Assert.assertTrue(creator instanceof ComponentSpecCalculator);
		Assert.assertNotNull(creator);
		// validate that XAxis is created
		final XAxis xaxis = new XAxis();
		xaxis.setId("xaxis");
		chart.getChildren().add(xaxis);
		helper = SpecHelper.newInstance(chart, new LayoutManager());
		creator = factory.getCalculator(xaxis, helper);
		Assert.assertTrue(creator instanceof HorizontalPartSpecCalculator);
		Assert.assertNotNull(creator);
		// validate that ComponentSpecCreator is created
		final YAxis yaxis = new YAxis();
		yaxis.setId("yaxis");
		chart.getChildren().add(yaxis);
		helper = SpecHelper.newInstance(chart, new LayoutManager());
		creator = factory.getCalculator(yaxis, helper);
		Assert.assertTrue(creator instanceof VerticalPartSpecCalculator);
		Assert.assertNotNull(creator);
		// validate that ComponentSpecCreator is created
		final ChartLegend legend = new ChartLegend();
		legend.setId("legend");
		chart.getChildren().add(legend);
		helper = SpecHelper.newInstance(chart, new LayoutManager());
		creator = factory.getCalculator(legend, helper);
		Assert.assertTrue(creator instanceof LegendSpecCalculator);
		Assert.assertNotNull(creator);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetCreatorPartNotAddedToSpecHelper() throws Exception {
		final IPartSpecCalculatorFactory<SpecHelper> factory =
				PartSpecCalculatorFactory.getInstance();
		final UIChart chart = new UIChart();
		chart.setId("anId");
		final SpecHelper helper = SpecHelper.newInstance(chart, new LayoutManager());
		final XAxis xaxis = new XAxis();
		xaxis.setId("anaxis");
		factory.getCalculator(xaxis, helper);
	}

	@Test(expected = NullPointerException.class)
	public void testGetCreatorPartNull() throws Exception {
		final IPartSpecCalculatorFactory<SpecHelper> factory =
				PartSpecCalculatorFactory.getInstance();
		final UIChart chart = new UIChart();
		final SpecHelper helper = SpecHelper.newInstance(chart, new LayoutManager());
		factory.getCalculator(null, helper);
	}

	@Test(expected = NullPointerException.class)
	public void testGetCreatorSpecHelperNull() throws Exception {
		final IPartSpecCalculatorFactory<SpecHelper> factory =
				PartSpecCalculatorFactory.getInstance();
		final IChartPart chart = new UIChart();
		factory.getCalculator(chart, null);
	}
}
