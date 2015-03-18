package org.javaq.chartfaces.render.tool.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import junit.framework.Assert;

import org.javaq.chartfaces.ChartFacesManager;
import org.javaq.chartfaces.DefaultChartSettings;
import org.javaq.chartfaces.api.Box;
import org.javaq.chartfaces.api.IChartComponent;
import org.javaq.chartfaces.api.ILegendHandler;
import org.javaq.chartfaces.component.impl.UIChart;
import org.javaq.chartfaces.constants.Constants;
import org.javaq.chartfaces.converter.AxisLabelsIterableConverter;
import org.javaq.chartfaces.converter.AxisValuesIterableConverter;
import org.javaq.chartfaces.dataspec.impl.DefaultChartingDataConverter;
import org.javaq.chartfaces.document.IChartDocument;
import org.javaq.chartfaces.document.IElement;
import org.javaq.chartfaces.iterable.ISizeAwareIterable;
import org.javaq.chartfaces.iterable.IterableUtility;
import org.javaq.chartfaces.iterable.SizeAwareIterable;
import org.javaq.chartfaces.part.axis.XAxis;
import org.javaq.chartfaces.part.axis.YAxis;
import org.javaq.chartfaces.part.component.ChartComponent;
import org.javaq.chartfaces.part.layout.impl.HorizontalOrderStrategy;
import org.javaq.chartfaces.part.layout.impl.LayoutManager;
import org.javaq.chartfaces.part.layout.impl.VerticalOrderStrategy;
import org.javaq.chartfaces.part.legend.ChartLegend;
import org.javaq.chartfaces.render.tool.IChartTool;
import org.javaq.chartfaces.render.tool.IChartToolFactory;
import org.javaq.chartfaces.viewspec.impl.PartCoordinator;
import org.javaq.chartfaces.viewspec.impl.PartSpecCalculatorFactory;
import org.junit.Before;
import org.junit.Test;


public class MainChartToolTest {
	private static final String VIEW_BOX_WITH_PADDING = "0 0 1540 940";
	private static IChartToolFactory factory = new ChartToolFactory();

	@Before
	public void setUp() {
		final DefaultChartSettings settings = new DefaultChartSettings();
		ChartFacesManager.getInstance().setDefaultAxisLabelAccessor(
				new AxisLabelsIterableConverter());
		ChartFacesManager.getInstance().setDefaultAxisValueAccessor(
				new AxisValuesIterableConverter());
		ChartFacesManager.getInstance().setDefaultDataConverter(
				new DefaultChartingDataConverter());
		ChartFacesManager.getInstance().setDefaultChartSettings(settings);
	}

	@Test
	public void testAddGetLegendHandler() throws Exception {
		final UIChart chart = new UIChart();
		chart.setId("anId");
		final MainChartTool ct = (MainChartTool) MainChartToolTest.factory
				.getTool(chart, new HashMap<Object, Object>());
		IChartComponent chartComponent = new ChartComponent();
		ILegendHandler legendHandler = new ILegendHandler() {
			@Override
			public IElement createElement() {
				return null;
			}
		};
		ct.registerLegendHandler(chartComponent, legendHandler);
		Assert.assertEquals(legendHandler, ct.getLegendHandler(chartComponent));
		IChartComponent chartComponent1 = new ChartComponent();
		legendHandler = new ILegendHandler() {
			@Override
			public IElement createElement() {
				return null;
			}
		};
		ct.registerLegendHandler(chartComponent1, legendHandler);
		Assert.assertEquals(legendHandler, ct.getLegendHandler(chartComponent1));
	}

	@Test
	public void testGetChartComponentCount() throws Exception {
		final UIChart chart = new UIChart();
		chart.setRendererType(Constants.RENDERER_BAR);
		chart.getPartSpec().setViewBox(new Box(0, 0, 100, 100));
		chart.setLeftPadding(1);
		chart.setRightPadding(1);
		chart.setTopPadding(1);
		chart.setBottomPadding(1);
		chart.getPartSpec().setViewBox(new Box(0, 0, 100, 100));
		chart.setId("anId");
		final ChartComponent component = new ChartComponent();
		component.getPartSpec().setViewBox(new Box(10, 8, 85, 88));
		chart.getChildren().add(component);
		final ChartComponent component1 = new ChartComponent();
		component1.getPartSpec().setViewBox(new Box(10, 8, 85, 88));
		chart.getChildren().add(component1);
		final ChartComponent component2 = new ChartComponent();
		component2.getPartSpec().setViewBox(new Box(10, 8, 85, 88));
		chart.getChildren().add(component2);
		final MainChartTool ct = (MainChartTool) MainChartToolTest.factory
				.getTool(chart, new HashMap<Object, Object>());
		ct.initBoxModel((BoxModel) null);
		Assert.assertEquals(3, ct.getChartComponentCount());
	}

	@Test
	public void testGetDocument() throws Exception {
		final UIChart chart = new UIChart();
		chart.setWidth("432px");
		chart.setHeight("324px");
		final XAxis axis = new XAxis();
		axis.setMin(0.);
		axis.setMax(10.);
		axis.setValue(new String[] { "0", "5", "10" });
		axis.setId("xaxis");
		axis.setCaption("X-Axis");
		axis.setGridLineDensity(1);
		axis.setHeight("50");
		chart.getChildren().add(axis);
		final MainChartTool ct =
				(MainChartTool) MainChartToolTest.factory.getTool(chart,
						new HashMap<Object, Object>());
		ct.setCoordinator(makePartCoordinator());
		chart.setId("anId");
		ct.updateState(AbstractChartTool.X_LABEL_COORDS,
				toSizeAwareIterable(new double[] { 0, 700, 1400 }));
		ct.process();
		final IChartDocument doc = ct.getChartDocument();
		final IElement element = doc.getLayoutElement();
		Assert.assertTrue(element.getChildren().size() > 0);
	}

	@Test(expected = IllegalStateException.class)
	public void testGetDocumentBeforeProcessIsCalled() throws Exception {
		final UIChart chart = new UIChart();
		final MainChartTool ct = (MainChartTool) MainChartToolTest.factory
				.getTool(chart, new HashMap<Object, Object>());
		ct.setCoordinator(makePartCoordinator());
		chart.setId("anId");
		ct.getChartDocument();
	}

	@Test
	public void testInit() throws Exception {
		final UIChart chart = new UIChart();
		chart.setRendererType(Constants.RENDERER_BAR);
		chart.getPartSpec().setViewBox(new Box(0, 0, 100, 100));
		chart.setLeftPadding(1);
		chart.setRightPadding(1);
		chart.setTopPadding(1);
		chart.setBottomPadding(1);
		chart.getPartSpec().setViewBox(new Box(0, 0, 100, 100));
		chart.setId("anId");
		final XAxis xaxis = new XAxis();
		xaxis.getPartSpec().setViewBox(new Box(10, 93, 88, 5));
		chart.getChildren().add(xaxis);
		final YAxis yaxis = new YAxis();
		yaxis.getPartSpec().setViewBox(new Box(1, 8, 10, 90));
		chart.getChildren().add(yaxis);
		final ChartLegend legend = new ChartLegend();
		legend.getPartSpec().setViewBox(new Box(70, 12, 10, 6));
		chart.getChildren().add(legend);
		final ChartComponent component = new ChartComponent();
		component.getPartSpec().setViewBox(new Box(10, 8, 85, 88));
		chart.getChildren().add(component);
		final MainChartTool ct = (MainChartTool) MainChartToolTest.factory
				.getTool(chart, new HashMap<Object, Object>());
		ct.initBoxModel((BoxModel) null);
		BoxModel bm = ct.getBoxModel();
		Assert.assertEquals(1, bm.getOriginX());
		Assert.assertEquals(1, bm.getOriginY());
		Assert.assertEquals("0 0 98 98", bm.getViewBoxString());
		final List<IChartTool> tools = ct.getChildrenTools();
		for (final IChartTool tool : tools) {
			final AbstractChartTool t = (AbstractChartTool) tool;
			bm = t.getBoxModel();
			if (t instanceof BarComponentTool) {
				Assert.assertEquals(10, bm.getOriginX());
				Assert.assertEquals(8, bm.getOriginY());
				Assert.assertEquals("0 0 85 88", bm.getViewBoxString());
			} else if (t instanceof LeftAxisTool) {
				Assert.assertEquals(1, bm.getOriginX());
				Assert.assertEquals(8, bm.getOriginY());
				Assert.assertEquals("0 0 10 90", bm.getViewBoxString());
			} else if (t instanceof BottomAxisTool) {
				Assert.assertEquals(10, bm.getOriginX());
				Assert.assertEquals(93, bm.getOriginY());
				Assert.assertEquals("0 0 88 5", bm.getViewBoxString());
			} else if (t instanceof LegendTool) {
				Assert.assertEquals(70, bm.getOriginX());
				Assert.assertEquals(12, bm.getOriginY());
				Assert.assertEquals("0 0 10 6", bm.getViewBoxString());
			} else {
				Assert.fail("Should not come here!!!");
			}
		}
	}

	@Test
	public void testMakeBoxModel() throws Exception {
		final UIChart chart = new UIChart();
		chart.setLeftPadding(10);
		chart.setRightPadding(5);
		chart.setTopPadding(2);
		chart.setBottomPadding(7);
		chart.getPartSpec().setViewBox(new Box(0, 0, 100, 100));
		chart.setId("anId");
		final MainChartTool ct = (MainChartTool) MainChartToolTest.factory
				.getTool(chart, new HashMap<Object, Object>());
		final BoxModel boxModel = ct.makeBoxModel(null);
		Assert.assertNotNull(boxModel);
		Assert.assertEquals(10, boxModel.getOriginX());
		Assert.assertEquals(2, boxModel.getOriginY());
		Assert.assertEquals(85, boxModel.getWidth());
		Assert.assertEquals(91, boxModel.getHeight());
	}

	@Test
	public void testMakeDocument() throws Exception {
		final UIChart chart = new UIChart();
		final MainChartTool ct = (MainChartTool) MainChartToolTest.factory
				.getTool(chart, new HashMap<Object, Object>());
		ct.setCoordinator(makePartCoordinator());
		chart.setId("anId");
		ct.process();
		final IChartDocument doc = ct.createDocument();
		Assert.assertNotNull(doc);
		Assert.assertNotNull(doc.getLayoutElement());
		Assert.assertNotNull(doc.getDataElementList());
	}

	@Test
	public void testProcessGetWidthHeight() throws Exception {
		final UIChart chart = new UIChart();
		MainChartTool ct = (MainChartTool) MainChartToolTest.factory
				.getTool(chart, new HashMap<Object, Object>());
		chart.setId("anId");
		ct.setCoordinator(makePartCoordinator());
		ct.process();
		Assert.assertNotNull(ct.getWidth());
		Assert.assertNotNull(ct.getHeight());
		Assert.assertNotNull(ct.getBoxModel().getViewBoxString());

		chart.setWidth("");
		chart.setHeight("");
		ct = (MainChartTool) MainChartToolTest.factory.getTool(chart,
				new HashMap<Object, Object>());
		ct.setCoordinator(makePartCoordinator());
		ct.process();
		Assert.assertEquals("100%", ct.getWidth());
		Assert.assertEquals("100%", ct.getHeight());
		Assert.assertEquals(MainChartToolTest.VIEW_BOX_WITH_PADDING, ct
				.getBoxModel()
				.getViewBoxString());

		chart.setWidth("50%");
		chart.setHeight("100px");
		ct = (MainChartTool) MainChartToolTest.factory.getTool(chart,
				new HashMap<Object, Object>());
		ct.setCoordinator(makePartCoordinator());
		ct.process();
		Assert.assertEquals("50%", ct.getWidth());
		Assert.assertEquals("100px", ct.getHeight());
		Assert.assertEquals(MainChartToolTest.VIEW_BOX_WITH_PADDING, ct
				.getBoxModel()
				.getViewBoxString());

		chart.setWidth("50px");
		chart.setHeight("100%");
		ct = (MainChartTool) MainChartToolTest.factory.getTool(chart,
				new HashMap<Object, Object>());
		ct.setCoordinator(makePartCoordinator());
		ct.process();
		Assert.assertEquals("50px", ct.getWidth());
		Assert.assertEquals("100%", ct.getHeight());
		Assert.assertEquals(MainChartToolTest.VIEW_BOX_WITH_PADDING, ct
				.getBoxModel()
				.getViewBoxString());

		chart.setWidth("100px");
		chart.setHeight("100px");
		ct = (MainChartTool) MainChartToolTest.factory.getTool(chart,
				new HashMap<Object, Object>());
		ct.setCoordinator(makePartCoordinator());
		ct.process();
		Assert.assertEquals("100px", ct.getWidth());
		Assert.assertEquals("100px", ct.getHeight());
		Assert.assertEquals("0 0 1540 1540", ct.getBoxModel()
				.getViewBoxString());
	}

	@Test(expected = IOException.class)
	public void testProcessWidthParseFailed() throws Exception {
		final UIChart chart = new UIChart();
		final MainChartTool ct = (MainChartTool) MainChartToolTest.factory
				.getTool(chart, new HashMap<Object, Object>());
		chart.setWidth("NaN");
		ct.setCoordinator(makePartCoordinator());
		chart.setId("anId");
		ct.process();
	}

	@Test
	public void testProcessWithChildren() throws Exception {
		final UIChart chart = new UIChart();
		chart.setId("anId");
		final MainChartTool ct =
				(MainChartTool) MainChartToolTest.factory.getTool(chart,
						new HashMap<Object, Object>());
		final UIChart childChart = new UIChart();
		childChart.setId("anotherId");
		chart.getChildren().add(childChart);
		ct.setCoordinator(makePartCoordinator());
		ct.process();
		Assert.assertTrue(ct.getChartDocument().getLayoutElement()
				.getChildren().size() > 1);
	}

	private PartCoordinator makePartCoordinator() {
		final PartCoordinator pp = new PartCoordinator();
		pp.setPartSpecCalculatorFactory(new PartSpecCalculatorFactory());
		LayoutManager lm = new LayoutManager();
		lm.setHorizontalOrderStrategy(new HorizontalOrderStrategy());
		lm.setVerticalOrderStrategy(new VerticalOrderStrategy());
		pp.setLayoutManager(lm);
		return pp;
	}

	protected ISizeAwareIterable<Double> toSizeAwareIterable(
			final double[] doubleArray) {
		final Iterable<Double> iterable = IterableUtility
				.toIterable(doubleArray);
		return new SizeAwareIterable<Double>(iterable);
	}
}
