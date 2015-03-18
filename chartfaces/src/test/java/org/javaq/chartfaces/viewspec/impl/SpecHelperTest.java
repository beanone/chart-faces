package org.javaq.chartfaces.viewspec.impl;

import java.io.IOException;

import junit.framework.Assert;

import org.javaq.chartfaces.ChartFacesManager;
import org.javaq.chartfaces.DefaultChartSettings;
import org.javaq.chartfaces.api.Box;
import org.javaq.chartfaces.boxcalculator.BoxCalculatorFactory;
import org.javaq.chartfaces.component.impl.UIChart;
import org.javaq.chartfaces.part.axis.XAxis;
import org.javaq.chartfaces.part.axis.YAxis;
import org.javaq.chartfaces.part.layout.ILayoutManager;
import org.javaq.chartfaces.part.layout.impl.HorizontalOrderStrategy;
import org.javaq.chartfaces.part.layout.impl.LayoutManager;
import org.javaq.chartfaces.part.layout.impl.VerticalOrderStrategy;
import org.javaq.chartfaces.part.legend.ChartLegend;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:chartfaces-context.xml"})
public class SpecHelperTest {
	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private BoxCalculatorFactory boxCalculatorFactory;

	public void setApplicationContext(ApplicationContext context) {
		this.applicationContext = context;
	}

	public void setBoxCalculatorFactory(BoxCalculatorFactory factory) {
		this.boxCalculatorFactory = factory;
	}

	private static final int HEIGHT_WITH_PADDING = 1140;
	private static final int WIDTH_WITH_PADDING = 1540;

	@Before
	public void setUp() {
		ChartFacesManager.getInstance().setDefaultChartSettings(
				new DefaultChartSettings());
	}

	@Test
	public void testComputeLegendDefaultPositionViewBox() throws Exception {
		final ChartLegend legend = new ChartLegend();
		legend.setId("myLegend");
		final String hString = legend.getHeight();
		final String wString = legend.getWidth();
		Assert.assertNotNull(hString);
		Assert.assertNull(wString);
		final UIChart chart = createChart("myChart");
		chart.getChildren().add(legend);
		final SpecHelper helper = newSpecHelper(chart);
		new ChartSpecCalculator(helper).calculate(chart);
		final Box viewBox = helper.computeLegendViewBox(legend);
		Assert.assertEquals(hString, "" + viewBox.getHeight());
		Assert.assertEquals(SpecHelperTest.WIDTH_WITH_PADDING,
				viewBox.getWidth());
	}

	private SpecHelper newSpecHelper(final UIChart chart) {
		final SpecHelper helper = SpecHelper.newInstance(chart, getLayoutManager());
		helper.setBoxCalculatorFactory(boxCalculatorFactory);
		return helper;
	}

	@Test
	public void testComputeLegendPositionAbsoluteViewBox() throws Exception {
		final ChartLegend legend = new ChartLegend();
		legend.setPosition("absolute");
		legend.setId("myLegend");
		final String hString = legend.getHeight();
		final String wString = legend.getWidth();
		legend.setX(1000);
		legend.setY(300);
		Assert.assertNotNull(hString);
		Assert.assertNotNull(wString);
		final UIChart chart = createChart("myChart");
		chart.getChildren().add(legend);
		final SpecHelper helper = newSpecHelper(chart);
		BoxCalculatorFactory factory = applicationContext.getBean(BoxCalculatorFactory.class);
		helper.setBoxCalculatorFactory(factory);
		new ChartSpecCalculator(helper).calculate(chart);
		final Box viewBox = helper.computeLegendViewBox(legend);
		Assert.assertEquals(hString, "" + viewBox.getHeight());
		Assert.assertEquals(wString, "" + viewBox.getWidth());
		legend.getPartSpec().setViewBox(viewBox);
		final ILayoutManager creator = helper.getLayoutManager();
		creator.layout();
		Assert.assertEquals(1000, viewBox.getOriginX());
		Assert.assertEquals(300, viewBox.getOriginY());
	}

	protected LayoutManager getLayoutManager() {
		LayoutManager manager = new LayoutManager();
		manager.setHorizontalOrderStrategy(new HorizontalOrderStrategy());
		manager.setVerticalOrderStrategy(new VerticalOrderStrategy());
		return manager;
	}

	@Test
	public void testComputeLegendPositionBottomViewBox() throws Exception {
		final ChartLegend legend = new ChartLegend();
		legend.setPosition("bottom");
		legend.setId("myLegend");
		final String hString = legend.getHeight();
		final String wString = legend.getWidth();
		Assert.assertNotNull(hString);
		Assert.assertNull(wString);
		final UIChart chart = createChart("myChart");
		chart.getChildren().add(legend);
		final SpecHelper helper = newSpecHelper(chart);
		new ChartSpecCalculator(helper).calculate(chart);
		final Box viewBox = helper.computeLegendViewBox(legend);
		Assert.assertEquals(hString, "" + viewBox.getHeight());
		Assert.assertEquals(SpecHelperTest.WIDTH_WITH_PADDING,
				viewBox.getWidth());
	}

	@Test
	public void testComputeLegendPositionLeftViewBox() throws Exception {
		final ChartLegend legend = new ChartLegend();
		legend.setPosition("left");
		legend.setId("myLegend");
		final String hString = legend.getHeight();
		final String wString = legend.getWidth();
		Assert.assertNull(hString);
		Assert.assertNotNull(wString);
		final UIChart chart = createChart("myChart");
		chart.getChildren().add(legend);
		final SpecHelper helper = newSpecHelper(chart);
		new ChartSpecCalculator(helper).calculate(chart);
		final Box viewBox = helper.computeLegendViewBox(legend);
		Assert.assertEquals(SpecHelperTest.HEIGHT_WITH_PADDING,
				viewBox.getHeight());
		Assert.assertEquals(65, viewBox.getWidth());
	}

	@Test
	public void testComputeLegendPositionRightViewBox() throws Exception {
		final ChartLegend legend = new ChartLegend();
		legend.setPosition("right");
		legend.setId("myLegend");
		final String hString = legend.getHeight();
		final String wString = legend.getWidth();
		Assert.assertNull(hString);
		Assert.assertNotNull(wString);
		final UIChart chart = createChart("myChart");
		chart.getChildren().add(legend);
		final SpecHelper helper = newSpecHelper(chart);
		new ChartSpecCalculator(helper).calculate(chart);
		final Box viewBox = helper.computeLegendViewBox(legend);
		Assert.assertEquals(SpecHelperTest.HEIGHT_WITH_PADDING,
				viewBox.getHeight());
		Assert.assertEquals(65, viewBox.getWidth());
	}

	@Test
	public void testComputeXAxisViewBox() throws Exception {
		final XAxis xaxis = new XAxis();
		xaxis.setId("myAxis");
		final String hString = xaxis.getHeight();
		Assert.assertNotNull(hString);
		final UIChart chart = createChart("myChart");
		chart.getChildren().add(xaxis);
		final SpecHelper helper = newSpecHelper(chart);
		new ChartSpecCalculator(helper).calculate(chart);
		final Box viewBox = helper.computeHorizontalViewBox(xaxis, null);
		Assert.assertEquals(hString, "" + viewBox.getHeight());
		Assert.assertEquals(SpecHelperTest.WIDTH_WITH_PADDING,
				viewBox.getWidth());
		xaxis.getPartSpec().setViewBox(viewBox);
		final ILayoutManager creator = helper.getLayoutManager();
		creator.layout();
		Assert.assertEquals(0, viewBox.getOriginX());
		Assert.assertEquals(990, viewBox.getOriginY());
	}

	@Test(expected = IOException.class)
	public void testComputeXAxisViewBoxInvalidPosition() throws Exception {
		final XAxis xaxis = new XAxis();
		xaxis.setId("myAxis");
		final String hString = xaxis.getHeight();
		Assert.assertNotNull(hString);
		final UIChart chart = createChart("myChart");
		chart.getChildren().add(xaxis);
		final SpecHelper helper = newSpecHelper(chart);
		new ChartSpecCalculator(helper).calculate(chart);
		xaxis.setPosition("left");
		helper.computeHorizontalViewBox(xaxis, null);
	}

	@Test(expected = IOException.class)
	public void testComputeXAxisViewBoxNotEnoughChartingSpace()
			throws Exception {
		final XAxis xaxis = new XAxis();
		xaxis.setId("myAxis");
		xaxis.setHeight("1200");
		final UIChart chart = createChart("myChart");
		chart.getChildren().add(xaxis);
		final SpecHelper helper = newSpecHelper(chart);
		new ChartSpecCalculator(helper).calculate(chart);
		helper.computeHorizontalViewBox(xaxis, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testComputeXAxisViewBoxWithNoHeight() throws Exception {
		ChartFacesManager.getInstance().setDefaultChartSettings(
				new DefaultChartSettings() {
					@Override
					public int getXAxisHeight() {
						return 0;
					}
				});
		final XAxis xaxis = new XAxis();
		xaxis.setId("myAxis");
		final String hString = xaxis.getHeight();
		Assert.assertNotNull(hString);
		final UIChart chart = createChart("myChart");
		chart.getChildren().add(xaxis);
		final SpecHelper helper = newSpecHelper(chart);
		new ChartSpecCalculator(helper).calculate(chart);
		helper.computeHorizontalViewBox(xaxis, null);
	}

	@Test
	public void testComputeYAxisViewBox() throws Exception {
		final YAxis yaxis = new YAxis();
		yaxis.setId("myAxis");
		final String wString = yaxis.getWidth();
		Assert.assertNotNull(wString);
		final UIChart chart = createChart("myChart");
		chart.getChildren().add(yaxis);
		SpecHelper helper = SpecHelper.newInstance(chart, getLayoutManager());
		new ChartSpecCalculator(helper).calculate(chart);
		Box viewBox = helper.computeVerticalViewBox(yaxis, null);
		Assert.assertEquals(wString, "" + viewBox.getWidth());
		Assert.assertEquals(SpecHelperTest.HEIGHT_WITH_PADDING,
				viewBox.getHeight());

		yaxis.getPartSpec().setViewBox(viewBox);
		final ILayoutManager creator = helper.getLayoutManager();
		creator.layout();
		Assert.assertEquals(0, viewBox.getOriginX());
		Assert.assertEquals(0, viewBox.getOriginY());

		final int height1 = viewBox.getHeight();

		// add header to the chart, and we should have a smaller height
		chart.setHeader("My Chart");
		helper = SpecHelper.newInstance(chart, getLayoutManager());
		new ChartSpecCalculator(helper).calculate(chart);
		viewBox = helper.computeVerticalViewBox(yaxis, null);
		Assert.assertEquals(wString, "" + viewBox.getWidth());
		final int height2 = viewBox.getHeight();
		Assert.assertTrue(height2 < height1);

		// add footer to the chart, and we should have a even smaller height
		chart.setFooter("My Footer");
		helper = SpecHelper.newInstance(chart, getLayoutManager());
		new ChartSpecCalculator(helper).calculate(chart);
		viewBox = helper.computeVerticalViewBox(yaxis, null);
		Assert.assertEquals(wString, "" + viewBox.getWidth());
		final int height3 = viewBox.getHeight();
		Assert.assertTrue(height3 < height2);
	}

	@Test(expected = IOException.class)
	public void testComputeYAxisViewBoxInvalidPosition() throws Exception {
		final YAxis yaxis = new YAxis();
		yaxis.setId("myAxis");
		final String wString = yaxis.getWidth();
		Assert.assertNotNull(wString);
		final UIChart chart = createChart("myChart");
		chart.getChildren().add(yaxis);
		final SpecHelper helper = newSpecHelper(chart);
		new ChartSpecCalculator(helper).calculate(chart);
		yaxis.setPosition("top");
		helper.computeVerticalViewBox(yaxis, null);
	}

	@Test(expected = IOException.class)
	public void testComputeYAxisViewBoxNotEnoughChartingSpace()
			throws Exception {
		final YAxis yaxis = new YAxis();
		yaxis.setId("myAxis");
		yaxis.setWidth("1600");
		final UIChart chart = createChart("myChart");
		chart.getChildren().add(yaxis);
		final SpecHelper helper = newSpecHelper(chart);
		new ChartSpecCalculator(helper).calculate(chart);
		helper.computeVerticalViewBox(yaxis, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testComputeYAxisViewBoxWidthNotSet() throws Exception {
		ChartFacesManager.getInstance().setDefaultChartSettings(
				new DefaultChartSettings() {
					@Override
					public int getYAxisWidth() {
						return 0;
					}
				});
		final YAxis yaxis = new YAxis();
		yaxis.setId("myAxis");
		final String wString = yaxis.getWidth();
		Assert.assertNotNull(wString);
		final UIChart chart = createChart("myChart");
		chart.getChildren().add(yaxis);
		final SpecHelper helper = newSpecHelper(chart);
		new ChartSpecCalculator(helper).calculate(chart);
		helper.computeVerticalViewBox(yaxis, null);
	}

	@Test
	public void testGetChartingAreaViewBox() throws Exception {
		final UIChart chart = createChart("myChart");
		final SpecHelper helper = newSpecHelper(chart);
		new ChartSpecCalculator(helper).calculate(chart);
		helper.getLayoutManager().layout();
		final Box viewBox = helper.getChartingAreaViewBox();
		System.out.println(chart.getWidth());
		System.out.println(chart.getHeight());
		Assert.assertEquals(SpecHelperTest.WIDTH_WITH_PADDING,
				viewBox.getWidth());
		Assert.assertEquals(SpecHelperTest.HEIGHT_WITH_PADDING,
				viewBox.getHeight());
		Assert.assertEquals(0, viewBox.getOriginX());
		Assert.assertEquals(0, viewBox.getOriginY());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSpecHelperArgumentNull() {
		SpecHelper.newInstance(null, getLayoutManager());
	}

	private UIChart createChart(final String id) {
		final UIChart chart = new UIChart();
		chart.setId(id);
		return chart;
	}
}
