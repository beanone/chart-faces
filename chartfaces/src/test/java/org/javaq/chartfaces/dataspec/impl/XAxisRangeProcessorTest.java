package org.javaq.chartfaces.dataspec.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.javaq.chartfaces.ChartFacesManager;
import org.javaq.chartfaces.DefaultChartSettings;
import org.javaq.chartfaces.api.IChartComponent;
import org.javaq.chartfaces.api.IChartingData;
import org.javaq.chartfaces.component.impl.ChartingData;
import org.javaq.chartfaces.component.impl.UIChart;
import org.javaq.chartfaces.constants.EnumOrientation;
import org.javaq.chartfaces.converter.AxisLabelsIterableConverter;
import org.javaq.chartfaces.converter.AxisValuesIterableConverter;
import org.javaq.chartfaces.part.axis.XAxis;
import org.javaq.chartfaces.part.component.ChartComponent;
import org.javaq.chartfaces.util.NumberRange;
import org.junit.Before;
import org.junit.Test;


public class XAxisRangeProcessorTest {
	private final ChartFacesManager manager = ChartFacesManager.getInstance();
	@Before
	public void setUp() {
		final DefaultChartSettings settings = new DefaultChartSettings();
		manager.setDefaultDataConverter(new DefaultChartingDataConverter());
		manager.setDefaultAxisValueAccessor(new AxisValuesIterableConverter());
		manager.setDefaultChartSettings(settings);
		manager.setDefaultAxisLabelAccessor(new AxisLabelsIterableConverter());
	}

	@Test
	public void testGetIterableDataValuesFromComponentMapData() {
		final AxisRangeProcessor processor = (AxisRangeProcessor) AxisRangeProcessorMaker
				.makeXAxisRangeProcessor(null);
		final ChartComponent cc = new ChartComponent();
		final UIChart chart = new UIChart();
		chart.setOrientation(EnumOrientation.horizontal.toString());
		chart.getChildren().add(cc);
		final Map<Double, Double> dataMap = new HashMap<Double, Double>();
		dataMap.put(1.0, 10.0);
		dataMap.put(2.0, 20.0);
		cc.setValue(dataMap);
		Assert.assertNotNull(processor.getIterableDataValues(cc));
	}

	@Test
	public void testGetIterableDataValuesFromComponentWith2DArray() {
		final AxisRangeProcessor processor = (AxisRangeProcessor) AxisRangeProcessorMaker
				.makeXAxisRangeProcessor(null);
		final ChartComponent cc = new ChartComponent();
		final UIChart chart = new UIChart();
		chart.setOrientation(EnumOrientation.horizontal.toString());
		chart.getChildren().add(cc);
		final double[][] dataArray = new double[][] { new double[] { 1., 2. },
				new double[] { 10., 20. } };
		cc.setValue(dataArray);
		Assert.assertNotNull(processor.getIterableDataValues(cc));
	}

	@Test
	public void testGetIterableDataValuesFromComponentWithChartingData() {
		final AxisRangeProcessor processor = (AxisRangeProcessor) AxisRangeProcessorMaker
				.makeXAxisRangeProcessor(null);
		final ChartComponent cc = new ChartComponent();
		final UIChart chart = new UIChart();
		chart.setOrientation(EnumOrientation.horizontal.toString());
		chart.getChildren().add(cc);
		final List<Double> xData = Arrays.asList(new Double[] { 1.2, 2.3 });
		final List<Double> yData = Arrays.asList(new Double[] { 0.0, 1.1 });
		final IChartingData cd = new ChartingData(xData, yData);
		cc.setValue(cd);
		Assert.assertNotNull(processor.getIterableDataValues(cc));
	}

	@Test
	public void testGetIterableDataValuesValueForOnlyX() {
		final AxisRangeProcessor processor = (AxisRangeProcessor) AxisRangeProcessorMaker
				.makeXAxisRangeProcessor(null);
		ChartComponent cc = new ChartComponent();
		UIChart chart = new UIChart();
		chart.setOrientation(EnumOrientation.horizontal.toString());
		chart.getChildren().add(cc);
		cc.setValue(new double[] { 1.2, 2.3 });
		Assert.assertNotNull(processor.getIterableDataValues(cc));

		// we tried array values, now to be sure, try a list
		cc = new ChartComponent();
		chart = new UIChart();
		chart.setOrientation(EnumOrientation.horizontal.toString());
		chart.getChildren().add(cc);
		cc.setValue(Arrays.asList(new double[] { 1.2, 2.3 }));
		Assert.assertNotNull(processor.getIterableDataValues(cc));
	}

	@Test
	public void testGetIterableDataValuesValueForOnlyY() {
		final AxisRangeProcessor processor = (AxisRangeProcessor) AxisRangeProcessorMaker
				.makeXAxisRangeProcessor(null);
		ChartComponent cc = new ChartComponent();
		UIChart chart = new UIChart();
		chart.getChildren().add(cc);
		cc.setValue(new double[] { 1.2, 2.3 });
		Assert.assertNull(processor.getIterableDataValues(cc));

		// we tried array values, now to be sure, try a list
		cc = new ChartComponent();
		chart = new UIChart();
		chart.getChildren().add(cc);
		cc.setValue(Arrays.asList(new double[] { 1.2, 2.3 }));
		Assert.assertNull(processor.getIterableDataValues(cc));
	}

	@Test
	public void testGetIterableDataValuesValueNotSet() {
		final AxisRangeProcessor processor = (AxisRangeProcessor) AxisRangeProcessorMaker
				.makeXAxisRangeProcessor(null);
		final ChartComponent cc = new ChartComponent();
		final UIChart chart = new UIChart();
		chart.getChildren().add(cc);
		Assert.assertFalse(processor.getIterableDataValues(cc).iterator().hasNext());
	}

	@Test
	public void testHandleAxisRangeDefaultAxisNoMax() {
		final NumberRange range = new NumberRange();
		range.mergeBound(0.);
		range.mergeBound(10.);

		// if the axis has only min, assert that it takes the max of the passed
		// in NumnberRange.
		final XAxis axis = new XAxis();
		axis.setMin(5.0);
		final AxisRangeProcessor processor = (AxisRangeProcessor) AxisRangeProcessorMaker
				.makeXAxisRangeProcessor(null);
		processor.handleAxisRangeDefault(axis, range);
		final NumberRange axisRange = axis.getRange();
		Assert.assertEquals(axis.getMin(), axisRange.getMin());
		Assert.assertEquals(range.getMax(), axisRange.getMax());
	}

	@Test
	public void testHandleAxisRangeDefaultAxisNoMaxButRangeUnMergable() {
		final NumberRange range = new NumberRange();
		range.mergeBound(0.);
		range.mergeBound(4.);

		// if the axis has only min, assert that it takes the max of the passed
		// in NumnberRange.
		final XAxis axis = new XAxis();
		axis.setMin(5.0);
		final AxisRangeProcessor processor = (AxisRangeProcessor) AxisRangeProcessorMaker
				.makeXAxisRangeProcessor(null);
		processor.handleAxisRangeDefault(axis, range);
		final NumberRange axisRange = axis.getRange();
		Assert.assertNull(axisRange.getMax());
		Assert.assertNull(axisRange.getMin());
	}

	@Test
	public void testHandleAxisRangeDefaultAxisNoMin() {
		final NumberRange range = new NumberRange();
		range.mergeBound(0.);
		range.mergeBound(10.);

		// if the axis has only max, assert that it takes the min of the passed
		// in NumnberRange.
		final XAxis axis = new XAxis();
		axis.setMax(9.0);
		final AxisRangeProcessor processor = (AxisRangeProcessor) AxisRangeProcessorMaker
				.makeXAxisRangeProcessor(null);
		processor.handleAxisRangeDefault(axis, range);
		final NumberRange axisRange = axis.getRange();
		Assert.assertEquals(axis.getMax(), axisRange.getMax());
		Assert.assertEquals(range.getMin(), axisRange.getMin());
	}

	@Test
	public void testHandleAxisRangeDefaultAxisNoMinButRangeUnMergable() {
		final NumberRange range = new NumberRange();
		range.mergeBound(10.);
		range.mergeBound(13.);

		// The range is above the axis max thus no merge would happen.
		final XAxis axis = new XAxis();
		axis.setMax(9.0);
		final AxisRangeProcessor processor = (AxisRangeProcessor) AxisRangeProcessorMaker
				.makeXAxisRangeProcessor(null);
		processor.handleAxisRangeDefault(axis, range);
		final NumberRange axisRange = axis.getRange();
		Assert.assertNull(axisRange.getMax());
		Assert.assertNull(axisRange.getMin());
	}

	@Test
	public void testHandleAxisRangeDefaultAxisNoMinNoMax() {
		final NumberRange range = new NumberRange();
		range.mergeBound(0.);
		range.mergeBound(10.);

		// if the axis does not have min and max, assert that it takes the min
		// and max of the passed in NumnberRange.
		final XAxis axis = new XAxis();
		final AxisRangeProcessor processor = (AxisRangeProcessor) AxisRangeProcessorMaker
				.makeXAxisRangeProcessor(null);
		processor.handleAxisRangeDefault(axis, range);
		final NumberRange axisRange = axis.getRange();
		Assert.assertEquals(range.getMax(), axisRange.getMax());
		Assert.assertEquals(range.getMin(), axisRange.getMin());
	}

	@Test
	public void testHandleAxisRangeDefaultAxisWithMinMax() {
		final NumberRange range = new NumberRange();
		range.mergeBound(0.);
		range.mergeBound(10.);

		// if the axis has both min and max, assert that it does not take any of
		// the min or max of the passed in NumnberRange.
		final XAxis axis = new XAxis();
		axis.setMin(5.0);
		axis.setMax(9.0);
		final AxisRangeProcessor processor = (AxisRangeProcessor) AxisRangeProcessorMaker
				.makeXAxisRangeProcessor(null);
		processor.handleAxisRangeDefault(axis, range);
		final NumberRange axisRange = axis.getRange();
		Assert.assertEquals(axis.getMin(), axisRange.getMin());
		Assert.assertEquals(axis.getMax(), axisRange.getMax());
	}

	@Test
	public void testIsAxisRangeUndefinedAxisNoMinMax() {
		// axis that does not have a min and max defined
		final AxisRangeProcessor processor = (AxisRangeProcessor) AxisRangeProcessorMaker
				.makeXAxisRangeProcessor(new XAxis());
		Assert.assertTrue(processor.isAxisRangeUndefined());
	}

	@Test
	public void testIsAxisRangeUndefinedAxisWithBothMinAndMax() {
		// axis has both min and max defined
		final XAxis axis = new XAxis();
		axis.setMin(0.);
		axis.setMax(0.);
		final AxisRangeProcessor processor = (AxisRangeProcessor) AxisRangeProcessorMaker
				.makeXAxisRangeProcessor(axis);
		Assert.assertFalse(processor.isAxisRangeUndefined());
	}

	@Test
	public void testIsAxisRangeUndefinedAxisWithMaxButNoMin() {
		// axis has only max defined but not the min
		XAxis axis = new XAxis();
		axis.setMax(0.);
		AxisRangeProcessor processor = (AxisRangeProcessor) AxisRangeProcessorMaker
				.makeXAxisRangeProcessor(axis);
		Assert.assertTrue(processor.isAxisRangeUndefined());

		// axis has both min and max defined
		axis = new XAxis();
		axis.setMin(0.);
		axis.setMax(0.);
		processor = (AxisRangeProcessor) AxisRangeProcessorMaker
				.makeXAxisRangeProcessor(axis);
		Assert.assertFalse(processor.isAxisRangeUndefined());
	}

	@Test
	public void testIsAxisRangeUndefinedNoAxis() {
		// no axis
		final AxisRangeProcessor processor = (AxisRangeProcessor) AxisRangeProcessorMaker
				.makeXAxisRangeProcessor(null);
		Assert.assertTrue(processor.isAxisRangeUndefined());
	}

	@Test
	public void testIsAxisRangeUndefinedWithMinButNoMax() {
		// axis has only min defined but not the max
		final XAxis axis = new XAxis();
		axis.setMin(0.);
		final AxisRangeProcessor processor = (AxisRangeProcessor) AxisRangeProcessorMaker
				.makeXAxisRangeProcessor(axis);
		Assert.assertTrue(processor.isAxisRangeUndefined());
	}

	@Test
	public void testProcessComponentRangeWithoutAxis() throws Exception {
		final ChartComponent component = new ChartComponent();
		component.setValue(new double[][] { new double[] { 1.0, 2.0, 3.0 },
				new double[] { 10.0, 2.0, 30.0 } });
		final UIChart chart = new UIChart();
		chart.getChildren().add(component);
		final AxisRangeProcessor processor = (AxisRangeProcessor) AxisRangeProcessorMaker
				.makeXAxisRangeProcessor(null);
		processor.addComponent(component);
		processor.process();
		Assert.assertEquals(1.0, component.getXRange().getMin());
		Assert.assertEquals(3.0, component.getXRange().getMax());
	}

	@Test
	public void testProcessEmptyComponentRangeWithAxisOnlyMinSet()
			throws Exception {
		final XAxis axis = new XAxis();
		axis.setMin(10.);
		final ChartComponent component = new ChartComponent();
		final UIChart chart = new UIChart();
		chart.getChildren().add(component);
		chart.getChildren().add(axis);
		final AxisRangeProcessor processor = (AxisRangeProcessor) AxisRangeProcessorMaker
				.makeXAxisRangeProcessor(axis);
		processor.addComponent(component);
		processor.process();
		Assert.assertNull(component.getXRange().getMin());
		Assert.assertNull(component.getXRange().getMax());
	}

	@Test
	public void testProcessEmptyComponentRangeWithAxisWithMinMax()
			throws Exception {
		final XAxis axis = new XAxis();
		axis.setMin(10.);
		axis.setMax(50.);
		final ChartComponent component = new ChartComponent();
		final UIChart chart = new UIChart();
		chart.getChildren().add(component);
		chart.getChildren().add(axis);
		final AxisRangeProcessor processor = (AxisRangeProcessor) AxisRangeProcessorMaker
				.makeXAxisRangeProcessor(axis);
		processor.addComponent(component);
		processor.process();
		Assert.assertEquals(10.0, component.getXRange().getMin());
		Assert.assertEquals(50.0, component.getXRange().getMax());
	}

	@Test
	public void testProcessEmptyComponentRangeWithoutAxis() throws Exception {
		final ChartComponent component = new ChartComponent();
		final UIChart chart = new UIChart();
		chart.getChildren().add(component);
		final AxisRangeProcessor processor = (AxisRangeProcessor) AxisRangeProcessorMaker
				.makeXAxisRangeProcessor(null);
		processor.addComponent(component);
		processor.process();
		Assert.assertNull(component.getXRange().getMin());
		Assert.assertNull(component.getXRange().getMax());
	}

	@Test
	public void testProcessNoneEmptyComponentRangeWithAxisOnlyMinSet()
			throws Exception {
		final XAxis axis = new XAxis();
		axis.setMin(10.);
		final ChartComponent component = new ChartComponent();
		component.setValue(new double[][] { new double[] { 1.0, 20.0, 30.0 },
				new double[] { 10.0, 2.0, 30.0 } });
		final UIChart chart = new UIChart();
		chart.getChildren().add(component);
		chart.getChildren().add(axis);
		final AxisRangeProcessor processor = (AxisRangeProcessor) AxisRangeProcessorMaker
				.makeXAxisRangeProcessor(axis);
		processor.addComponent(component);
		processor.process();
		Assert.assertEquals(10.0, component.getXRange().getMin());
		Assert.assertEquals(30.0, component.getXRange().getMax());
	}

	@Test
	public void testProcessTwoComponentsRangeWithoutAxis() throws Exception {
		final ChartComponent component = new ChartComponent();
		component.setValue(new double[] { 1.0, 2.0, 3.0 });
		final ChartComponent component1 = new ChartComponent();
		component1.setValue(new double[] { 10.0, 2.0, 30.0 });
		final UIChart chart = new UIChart();
		chart.setOrientation(EnumOrientation.horizontal.toString());
		chart.getChildren().add(component);
		chart.getChildren().add(component1);
		final AxisRangeProcessor processor = (AxisRangeProcessor) AxisRangeProcessorMaker
				.makeXAxisRangeProcessor(null);
		processor.addComponent(component);
		processor.addComponent(component1);
		processor.process();
		Assert.assertEquals(1.0, component.getXRange().getMin());
		Assert.assertEquals(30.0, component.getXRange().getMax());
	}

	@Test
	public void testStoreAxisRange() {
		final AxisRangeProcessor processor = (AxisRangeProcessor) AxisRangeProcessorMaker
				.makeXAxisRangeProcessor(null);
		final NumberRange range = new NumberRange();
		range.mergeBound(0.);
		range.mergeBound(10.);
		final IChartComponent component = new ChartComponent();
		processor.storeAxisRange(component, range);
		Assert.assertSame(component.getXRange(), range);
	}
}