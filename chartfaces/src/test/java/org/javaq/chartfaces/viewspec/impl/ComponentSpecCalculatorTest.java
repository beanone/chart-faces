package org.javaq.chartfaces.viewspec.impl;

import junit.framework.Assert;

import org.javaq.chartfaces.api.Box;
import org.javaq.chartfaces.api.IChartPart;
import org.javaq.chartfaces.component.impl.UIChart;
import org.javaq.chartfaces.constants.Constants;
import org.javaq.chartfaces.constants.EnumPosition;
import org.javaq.chartfaces.part.axis.XAxis;
import org.javaq.chartfaces.part.axis.YAxis;
import org.javaq.chartfaces.part.component.ChartComponent;
import org.javaq.chartfaces.viewspec.IPartSpecCalculator;
import org.junit.Test;


public class ComponentSpecCalculatorTest extends SpecCalculatorTestBase {

	private static final int HEIGHT_WITH_PADDING = 940;
	private static final int WIDTH_WITH_PADDING = 1540;
	private static final double YAXIS_MAX = 10.0;
	private static final double YAXIS_MIN = 0.8;
	private static final double VALUE_MID = 1.0;
	private static final double VALUE_MAX = 2.0;
	private static final double VALUE_MIN = 0.5;
	private static final double XAXIS_MAX = 4.0;
	private static final double XAXIS_MIN = 0.0;

	@Test
	public void testComputeViewBox100PercentWidthAndHeight() throws Exception {
		final ChartComponent comp = createChartComponent(new Double[] {
				ComponentSpecCalculatorTest.VALUE_MID,
				ComponentSpecCalculatorTest.VALUE_MIN,
				ComponentSpecCalculatorTest.VALUE_MAX });
		final UIChart chart = createChart();
		chart.setWidth("100%");
		chart.setHeight("100%");
		chart.setOrientation(Constants.ORIENTATION_VERTICAL);
		final ComponentSpecCalculator creator = (ComponentSpecCalculator) createSpecCreator(
				chart, comp);
		final Box box = creator.computeViewBox(comp);
		Assert.assertEquals(ComponentSpecCalculatorTest.WIDTH_WITH_PADDING, box.getWidth());
		Assert.assertEquals(ComponentSpecCalculatorTest.HEIGHT_WITH_PADDING, box.getHeight());
	}

	@Test
	public void testComputeViewBox100PercentWidthAndHeightWithAxes()
			throws Exception {
		final ChartComponent comp = createChartComponent(new Double[] {
				ComponentSpecCalculatorTest.VALUE_MID,
				ComponentSpecCalculatorTest.VALUE_MIN,
				ComponentSpecCalculatorTest.VALUE_MAX });
		UIChart chart = createChart();
		chart.setWidth("100%");
		chart.setHeight("100%");
		XAxis xAxis = (XAxis) createXAxis("xaxis",
				ComponentSpecCalculatorTest.XAXIS_MIN,
				ComponentSpecCalculatorTest.XAXIS_MAX);
		xAxis.setHeight("50px");
		YAxis yAxis = (YAxis) createYAxis("yaxis",
				ComponentSpecCalculatorTest.YAXIS_MIN,
				ComponentSpecCalculatorTest.YAXIS_MAX);
		yAxis.setWidth("50px");
		chart.setOrientation(Constants.ORIENTATION_VERTICAL);
		SpecHelper helper = createSpecHelper(chart, comp, xAxis, yAxis);
		initChartSpec(chart, helper);
		ComponentSpecCalculator creator = (ComponentSpecCalculator) createSpecCreator(helper);
		HorizontalPartSpecCalculator xAxisCreator = new HorizontalPartSpecCalculator(helper);
		xAxisCreator.calculate(xAxis);
		VerticalPartSpecCalculator yAxisCreator = new VerticalPartSpecCalculator(helper);
		yAxisCreator.calculate(yAxis);
		Box box = creator.computeViewBox(comp);
		final int boxWidth1 = box.getWidth();
		Assert.assertTrue(1600 > boxWidth1);
		// without labels
		final int boxHeight1 = box.getHeight();
		Assert.assertTrue(1000 > boxHeight1);

		// with labels
		chart = createChart();
		chart.setWidth("100%");
		chart.setHeight("100%");
		xAxis = (XAxis) createXAxis("xaxis",
				ComponentSpecCalculatorTest.XAXIS_MIN,
				ComponentSpecCalculatorTest.XAXIS_MAX);
		xAxis.setValue(new String[] { "My", "Test", "Labels" });
		xAxis.setHeight("80px");
		yAxis = (YAxis) createYAxis("yaxis",
				ComponentSpecCalculatorTest.YAXIS_MIN,
				ComponentSpecCalculatorTest.YAXIS_MAX);
		yAxis.setValue(new String[] { "My", "Test", "Labels" });
		yAxis.setWidth("80px");
		chart.setOrientation(Constants.ORIENTATION_VERTICAL);
		helper = createSpecHelper(chart, comp, xAxis, yAxis);
		initChartSpec(chart, helper);
		creator = (ComponentSpecCalculator) createSpecCreator(helper);
		xAxisCreator = new HorizontalPartSpecCalculator(helper);
		xAxisCreator.calculate(xAxis);
		yAxisCreator = new VerticalPartSpecCalculator(helper);
		yAxisCreator.calculate(yAxis);
		box = creator.computeViewBox(comp);
		final int boxWidth2 = box.getWidth();
		Assert.assertTrue(boxWidth1 > boxWidth2);
		final int boxHeight2 = box.getHeight();
		Assert.assertTrue(boxHeight1 > boxHeight2);

		// two x-axes and two y-axes
		chart = createChart();
		chart.setWidth("100%");
		chart.setHeight("100%");
		xAxis = (XAxis) createXAxis("xaxis",
				ComponentSpecCalculatorTest.XAXIS_MIN,
				ComponentSpecCalculatorTest.XAXIS_MAX);
		xAxis.setValue(new String[] { "My", "Test", "Labels" });
		yAxis = (YAxis) createYAxis("yaxis",
				ComponentSpecCalculatorTest.YAXIS_MIN,
				ComponentSpecCalculatorTest.YAXIS_MAX);
		yAxis.setValue(new String[] { "My", "Test", "Labels" });
		final XAxis xAxis1 = (XAxis) createXAxis("xaxis1",
				ComponentSpecCalculatorTest.XAXIS_MIN,
				ComponentSpecCalculatorTest.XAXIS_MAX);
		xAxis1.setPosition(EnumPosition.top.toString());
		final YAxis yAxis1 = (YAxis) createYAxis("yaxis1",
				ComponentSpecCalculatorTest.YAXIS_MIN,
				ComponentSpecCalculatorTest.YAXIS_MAX);
		yAxis1.setPosition(EnumPosition.right.toString());
		chart.setOrientation(Constants.ORIENTATION_VERTICAL);
		helper = createSpecHelper(chart, comp, xAxis, yAxis, xAxis1,
				yAxis1);
		initChartSpec(chart, helper);
		xAxisCreator = new HorizontalPartSpecCalculator(helper);
		xAxisCreator.calculate(xAxis);
		xAxisCreator.calculate(xAxis1);
		yAxisCreator = new VerticalPartSpecCalculator(helper);
		yAxisCreator.calculate(yAxis);
		yAxisCreator.calculate(yAxis1);
		creator = (ComponentSpecCalculator) createSpecCreator(helper);
		box = creator.computeViewBox(comp);
		final int boxWidth3 = box.getWidth();
		Assert.assertTrue(boxWidth2 > boxWidth3);
		final int boxHeight3 = box.getHeight();
		Assert.assertTrue(boxHeight2 > boxHeight3);
	}

	@Test
	public void testComputeViewBoxFixedWidthAndHeight() throws Exception {
		final ChartComponent comp = createChartComponent(new Double[] {
				ComponentSpecCalculatorTest.VALUE_MID,
				ComponentSpecCalculatorTest.VALUE_MIN,
				ComponentSpecCalculatorTest.VALUE_MAX });
		final UIChart chart = createChart();
		// width = height
		chart.setWidth("400px");
		chart.setHeight("400px");
		chart.setOrientation(Constants.ORIENTATION_VERTICAL);
		ComponentSpecCalculator creator = (ComponentSpecCalculator) createSpecCreator(
				chart, comp);
		Box box = creator.computeViewBox(comp);
		Assert.assertEquals(ComponentSpecCalculatorTest.WIDTH_WITH_PADDING, box.getWidth());
		Assert.assertEquals(ComponentSpecCalculatorTest.WIDTH_WITH_PADDING, box.getHeight());
		// now height = width / 2
		chart.setWidth("400px");
		chart.setHeight("200px");
		chart.setOrientation(Constants.ORIENTATION_VERTICAL);
		creator = (ComponentSpecCalculator) createSpecCreator(
				chart, comp);
		box = creator.computeViewBox(comp);
		Assert.assertEquals(ComponentSpecCalculatorTest.WIDTH_WITH_PADDING, box.getWidth());
		Assert.assertEquals(740, box.getHeight());
	}

	private ChartComponent createChartComponent(final Object values) {
		final ChartComponent comp = (ChartComponent) createPart();
		comp.setValue(values);
		return comp;
	}

	@Override
	protected IChartPart createPart() {
		final ChartComponent comp = new ChartComponent();
		comp.setId("comp");
		return comp;
	}

	@Override
	protected IPartSpecCalculator createSpecCreator(final SpecHelper helper) {
		return new ComponentSpecCalculator(helper);
	}
}
