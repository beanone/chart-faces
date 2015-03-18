package org.javaq.chartfaces.dataspec.impl;

import javax.faces.component.UIComponent;

import junit.framework.Assert;

import org.javaq.chartfaces.ChartFacesManager;
import org.javaq.chartfaces.DefaultChartSettings;
import org.javaq.chartfaces.component.impl.UIChart;
import org.javaq.chartfaces.constants.EnumOrientation;
import org.javaq.chartfaces.converter.AxisLabelsIterableConverter;
import org.javaq.chartfaces.converter.AxisValuesIterableConverter;
import org.javaq.chartfaces.dataspec.IRangeCalculator;
import org.javaq.chartfaces.part.axis.XAxis;
import org.javaq.chartfaces.part.axis.YAxis;
import org.javaq.chartfaces.part.component.ChartComponent;
import org.javaq.chartfaces.util.NumberRange;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class CartesianRangeCalculatorTest {
	private final IRangeCalculator<UIComponent> calculator;
	private ChartFacesManager manager;

	public CartesianRangeCalculatorTest() {
		this.calculator = new CartesianRangeCalculator();
	}

	@Before
	public void setUp() {
		final DefaultChartSettings settings = new DefaultChartSettings();
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("chartfaces-context.xml");
		manager = ChartFacesManager.getInstance();
		manager.setDefaultAxisLabelAccessor(new AxisLabelsIterableConverter());
		manager.setDefaultDataConverter(
				new DefaultChartingDataConverter());
		manager.setDefaultAxisValueAccessor(
				new AxisValuesIterableConverter());
		manager.setDefaultChartSettings(settings);
	}

	@Test
	public void testCalculateRangesNoXAxis() throws Exception {
		final UIChart chart = new UIChart();
		final YAxis yaxis = createYAxis(1.0, 2.0);
		chart.getChildren().add(yaxis);
		final ChartComponent component = new ChartComponent();
		chart.getChildren().add(component);
		component.setValue(new int[] { 4, 3, 3 });
		this.calculator.calculateRanges(chart);
		final NumberRange range = yaxis.getRange();
		Assert.assertEquals(1.0, range.getMin());
		Assert.assertEquals(2.0, range.getMax());
		
		// x-axis range not defined
		Assert.assertNull(component.getXRange().getMin());
		Assert.assertNull(component.getXRange().getMax());
		Assert.assertEquals(1.0, component.getYRange().getMin());
		Assert.assertEquals(2.0, component.getYRange().getMax());
	}

	@Test
	public void testCalculateRangesNoXAxisHorizontalOriented()
			throws Exception {
		final UIChart chart = new UIChart();
		chart.setOrientation(EnumOrientation.horizontal.toString());
		final YAxis yaxis = createYAxis(1.0, 2.0);
		chart.getChildren().add(yaxis);
		final ChartComponent component = new ChartComponent();
		chart.getChildren().add(component);
		component.setValue(new int[] { 4, 3, 3 });
		this.calculator.calculateRanges(chart);
		final NumberRange range = yaxis.getRange();
		Assert.assertEquals(1.0, range.getMin());
		Assert.assertEquals(2.0, range.getMax());

		// x-axis range uses the range from the data values
		Assert.assertEquals(3.0, component.getXRange().getMin());
		Assert.assertEquals(4.0, component.getXRange().getMax());
		Assert.assertEquals(1.0, component.getYRange().getMin());
		Assert.assertEquals(2.0, component.getYRange().getMax());
	}

	@Test
	public void testCalculateRangesNoYAxis() throws Exception {
		// ..........x-axis with full range
		final UIChart chart = new UIChart();
		final XAxis xaxis = createXAxis(1.0, 2.0);
		chart.getChildren().add(xaxis);
		final ChartComponent component = new ChartComponent();
		chart.getChildren().add(component);
		component.setValue(new int[] { 4, 3, 3 });
		this.calculator.calculateRanges(chart);
		final NumberRange range = xaxis.getRange();
		Assert.assertEquals(1.0, range.getMin());
		Assert.assertEquals(2.0, range.getMax());

		Assert.assertEquals(1.0, component.getXRange().getMin());
		Assert.assertEquals(2.0, component.getXRange().getMax());
		// y-axis range uses the range from the data values
		Assert.assertEquals(3.0, component.getYRange().getMin());
		Assert.assertEquals(4.0, component.getYRange().getMax());
	}

	@Test
	public void testCalculateRangesNoYAxisHorizontalOriented()
			throws Exception {
		final UIChart chart = new UIChart();
		chart.setOrientation(EnumOrientation.horizontal.toString());
		final XAxis xaxis = createXAxis(1.0, 2.0);
		chart.getChildren().add(xaxis);
		final ChartComponent component = new ChartComponent();
		chart.getChildren().add(component);
		component.setValue(new int[] { 4, 3, 3 });
		this.calculator.calculateRanges(chart);
		final NumberRange range = xaxis.getRange();
		Assert.assertEquals(1.0, range.getMin());
		Assert.assertEquals(2.0, range.getMax());

		Assert.assertEquals(1.0, component.getXRange().getMin());
		Assert.assertEquals(2.0, component.getXRange().getMax());
		// y-axis no range defined
		Assert.assertNull(component.getYRange().getMin());
		Assert.assertNull(component.getYRange().getMax());
	}

	@Test
	public void testCalculateRangesXAxisNoMaxNoYAxis() throws Exception {
		// ..........x-axis with no max
		final UIChart chart = new UIChart();
		final XAxis xaxis = createXAxis(1.0, null);
		chart.getChildren().add(xaxis);
		final ChartComponent component = new ChartComponent();
		chart.getChildren().add(component);
		component.setValue(new int[] { 4, 3, 3, 3 });
		this.calculator.calculateRanges(chart);
		final NumberRange range = xaxis.getRange();
		// x-axis range uses its own min and the max of the component index
		Assert.assertNull(range.getMin());
		Assert.assertNull(range.getMax());

		Assert.assertNull(component.getXRange().getMin());
		Assert.assertNull(component.getXRange().getMax());
		// y-axis range uses the range from the data values
		Assert.assertEquals(3.0, component.getYRange().getMin());
		Assert.assertEquals(4.0, component.getYRange().getMax());
	}

	@Test
	public void testCalculateRangesXAxisNoMaxNoYAxisHorizontalOriented()
			throws Exception {
		// ..........x-axis with no max
		final UIChart chart = new UIChart();
		chart.setOrientation(EnumOrientation.horizontal.toString());
		final XAxis xaxis = createXAxis(0.0, null);
		chart.getChildren().add(xaxis);
		final ChartComponent component = new ChartComponent();
		chart.getChildren().add(component);
		component.setValue(new int[] { 4, 3, 3, 3 });
		this.calculator.calculateRanges(chart);
		final NumberRange range = xaxis.getRange();
		// x-axis range uses its own min and the max of the data values
		Assert.assertEquals(0.0, range.getMin());
		Assert.assertEquals(4.0, range.getMax());

		Assert.assertEquals(0.0, component.getXRange().getMin());
		Assert.assertEquals(4.0, component.getXRange().getMax());
		// y-axis range not defined
		Assert.assertNull(component.getYRange().getMin());
		Assert.assertNull(component.getYRange().getMax());
	}

	@Test
	public void testCalculateRangesXAxisNoMinMaxNoYAxis() throws Exception {
		// ..........x-axis with no max
		final UIChart chart = new UIChart();
		final XAxis xaxis = createXAxis(null, null);
		chart.getChildren().add(xaxis);
		final ChartComponent component = new ChartComponent();
		chart.getChildren().add(component);
		component.setValue(new int[] { 4, 3, 3, 3 });
		this.calculator.calculateRanges(chart);
		final NumberRange range = xaxis.getRange();
		// x-axis range not defined
		Assert.assertNull(range.getMin());
		Assert.assertNull(range.getMax());

		Assert.assertNull(component.getXRange().getMin());
		Assert.assertNull(component.getXRange().getMax());
		// y-axis range uses the range from the data values
		Assert.assertEquals(3.0, component.getYRange().getMin());
		Assert.assertEquals(4.0, component.getYRange().getMax());
	}

	@Test
	public void testCalculateRangesXAxisNoMinMaxNoYAxisHorizontalOriented()
			throws Exception {
		// ..........x-axis with no max
		final UIChart chart = new UIChart();
		chart.setOrientation(EnumOrientation.horizontal.toString());
		final XAxis xaxis = createXAxis(null, null);
		chart.getChildren().add(xaxis);
		final ChartComponent component = new ChartComponent();
		chart.getChildren().add(component);
		component.setValue(new int[] { 4, 3, 3, 3 });
		this.calculator.calculateRanges(chart);
		final NumberRange range = xaxis.getRange();
		// x-axis range uses the min and max of the data values
		Assert.assertEquals(3.0, range.getMin());
		Assert.assertEquals(4.0, range.getMax());

		// x-axis range uses the range from the x-axis and the data values max
		Assert.assertEquals(3.0, component.getXRange().getMin());
		Assert.assertEquals(4.0, component.getXRange().getMax());
		// y-axis range not defined
		Assert.assertNull(component.getYRange().getMin());
		Assert.assertNull(component.getYRange().getMax());
	}

	@Test
	public void testCalculateRangesXAxisNoMinNoYAxis() throws Exception {
		// ..........x-axis with no max
		final UIChart chart = new UIChart();
		final XAxis xaxis = createXAxis(null, 7.0);
		chart.getChildren().add(xaxis);
		final ChartComponent component = new ChartComponent();
		chart.getChildren().add(component);
		component.setValue(new int[] { 4, 3, 3, 3 });
		this.calculator.calculateRanges(chart);
		final NumberRange range = xaxis.getRange();
		// x-axis range not defined
		Assert.assertNull(range.getMin());
		Assert.assertNull(range.getMax());

		Assert.assertNull(component.getXRange().getMin());
		Assert.assertNull(component.getXRange().getMax());
		// y-axis range uses the range from the data values
		Assert.assertEquals(3.0, component.getYRange().getMin());
		Assert.assertEquals(4.0, component.getYRange().getMax());
	}

	@Test
	public void testCalculateRangesXAxisNoMinNoYAxisHorizontalOriented()
			throws Exception {
		// ..........x-axis with no max
		final UIChart chart = new UIChart();
		chart.setOrientation(EnumOrientation.horizontal.toString());
		final XAxis xaxis = createXAxis(null, 7.0);
		chart.getChildren().add(xaxis);
		final ChartComponent component = new ChartComponent();
		chart.getChildren().add(component);
		component.setValue(new int[] { 4, 3, 3, 3 });
		this.calculator.calculateRanges(chart);
		final NumberRange range = xaxis.getRange();
		// x-axis range uses its own max and the min of the data values
		Assert.assertEquals(3.0, range.getMin());
		Assert.assertEquals(7.0, range.getMax());

		// x-axis range uses the range from the x-axis and the data values max
		Assert.assertEquals(3.0, component.getXRange().getMin());
		Assert.assertEquals(7.0, component.getXRange().getMax());
		// y-axis range not defined
		Assert.assertNull(component.getYRange().getMin());
		Assert.assertNull(component.getYRange().getMax());
	}

	@Test
	public void testCalculateRangesYAxisNoMaxNoXAxis() throws Exception {
		// ..........x-axis with no max
		final UIChart chart = new UIChart();
		final YAxis yaxis = createYAxis(1.0, null);
		chart.getChildren().add(yaxis);
		final ChartComponent component = new ChartComponent();
		chart.getChildren().add(component);
		component.setValue(new int[] { 4, 3, 3, 3 });
		this.calculator.calculateRanges(chart);
		final NumberRange range = yaxis.getRange();
		// x-axis range uses its own min and the max of the component index
		Assert.assertEquals(1.0, range.getMin());
		Assert.assertEquals(4.0, range.getMax());

		// x-axis range not defined
		Assert.assertNull(component.getXRange().getMin());
		Assert.assertNull(component.getXRange().getMax());
		Assert.assertEquals(1.0, component.getYRange().getMin());
		Assert.assertEquals(4.0, component.getYRange().getMax());
	}

	@Test
	public void testCalculateRangesYAxisNoMaxNoXAxisHorizontalOriented()
			throws Exception {
		// ..........x-axis with no max
		final UIChart chart = new UIChart();
		chart.setOrientation(EnumOrientation.horizontal.toString());
		final YAxis yaxis = createYAxis(0.0, null);
		chart.getChildren().add(yaxis);
		final ChartComponent component = new ChartComponent();
		chart.getChildren().add(component);
		component.setValue(new int[] { 4, 3, 3, 3 });
		this.calculator.calculateRanges(chart);
		final NumberRange range = yaxis.getRange();
		// x-axis range not defined
		Assert.assertNull(range.getMin());
		Assert.assertNull(range.getMax());

		// x-axis range uses the range from the x-axis and the data values max
		Assert.assertEquals(3.0, component.getXRange().getMin());
		Assert.assertEquals(4.0, component.getXRange().getMax());
		Assert.assertNull(component.getYRange().getMin());
		Assert.assertNull(component.getYRange().getMax());
	}

	@Test
	public void testCalculateRangesYAxisNoMinMaxNoXAxis() throws Exception {
		// ..........x-axis with no max
		final UIChart chart = new UIChart();
		final YAxis yaxis = createYAxis(null, null);
		chart.getChildren().add(yaxis);
		final ChartComponent component = new ChartComponent();
		chart.getChildren().add(component);
		component.setValue(new int[] { 4, 3, 3, 3 });
		this.calculator.calculateRanges(chart);
		final NumberRange range = yaxis.getRange();
		// x-axis range uses min and max of the data values
		Assert.assertEquals(3.0, range.getMin());
		Assert.assertEquals(4.0, range.getMax());

		// x-axis range not defined
		Assert.assertNull(component.getXRange().getMin());
		Assert.assertNull(component.getXRange().getMax());
		Assert.assertEquals(3.0, component.getYRange().getMin());
		Assert.assertEquals(4.0, component.getYRange().getMax());
	}

	@Test
	public void testCalculateRangesYAxisNoMinMaxNoXAxisHorizontalOriented()
			throws Exception {
		// ..........x-axis with no max
		final UIChart chart = new UIChart();
		chart.setOrientation(EnumOrientation.horizontal.toString());
		final YAxis yaxis = createYAxis(null, null);
		chart.getChildren().add(yaxis);
		final ChartComponent component = new ChartComponent();
		chart.getChildren().add(component);
		component.setValue(new int[] { 4, 3, 3, 3 });
		this.calculator.calculateRanges(chart);
		final NumberRange range = yaxis.getRange();
		// x-axis range not defined
		Assert.assertNull(range.getMin());
		Assert.assertNull(range.getMax());

		// x-axis range uses the range from the x-axis and the data values max
		Assert.assertEquals(3.0, component.getXRange().getMin());
		Assert.assertEquals(4.0, component.getXRange().getMax());
		Assert.assertNull(component.getYRange().getMin());
		Assert.assertNull(component.getYRange().getMax());
	}

	@Test
	public void testCalculateRangesYAxisNoMinNoXAxis() throws Exception {
		// ..........x-axis with no max
		final UIChart chart = new UIChart();
		final YAxis yaxis = createYAxis(null, 7.0);
		chart.getChildren().add(yaxis);
		final ChartComponent component = new ChartComponent();
		chart.getChildren().add(component);
		component.setValue(new int[] { 4, 3, 3, 3 });
		this.calculator.calculateRanges(chart);
		final NumberRange range = yaxis.getRange();
		// x-axis range uses its own max and the min of the data values
		Assert.assertEquals(3.0, range.getMin());
		Assert.assertEquals(7.0, range.getMax());

		// x-axis range not defined
		Assert.assertNull(component.getXRange().getMin());
		Assert.assertNull(component.getXRange().getMax());
		Assert.assertEquals(3.0, component.getYRange().getMin());
		Assert.assertEquals(7.0, component.getYRange().getMax());
	}

	@Test
	public void testCalculateRangesYAxisNoMinNoXAxisHorizontalOriented()
			throws Exception {
		// ..........x-axis with no max
		final UIChart chart = new UIChart();
		chart.setOrientation(EnumOrientation.horizontal.toString());
		final YAxis yaxis = createYAxis(null, 7.0);
		chart.getChildren().add(yaxis);
		final ChartComponent component = new ChartComponent();
		chart.getChildren().add(component);
		component.setValue(new int[] { 4, 3, 3, 3 });
		this.calculator.calculateRanges(chart);
		final NumberRange range = yaxis.getRange();
		// x-axis range not defined
		Assert.assertNull(range.getMin());
		Assert.assertNull(range.getMax());

		// x-axis range uses the range from the x-axis and the data values max
		Assert.assertEquals(3.0, component.getXRange().getMin());
		Assert.assertEquals(4.0, component.getXRange().getMax());
		Assert.assertNull(component.getYRange().getMin());
		Assert.assertNull(component.getYRange().getMax());
	}

	private XAxis createXAxis(final Double min, final Double max) {
		final XAxis xaxis = new XAxis();
		xaxis.setMin(min);
		xaxis.setMax(max);
		return xaxis;
	}

	private YAxis createYAxis(final Double min, final Double max) {
		final YAxis yaxis = new YAxis();
		yaxis.setMin(min);
		yaxis.setMax(max);
		return yaxis;
	}
}
