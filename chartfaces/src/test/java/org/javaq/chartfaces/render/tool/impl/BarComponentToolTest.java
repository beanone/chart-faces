package org.javaq.chartfaces.render.tool.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.javaq.chartfaces.ChartFacesManager;
import org.javaq.chartfaces.DefaultChartSettings;
import org.javaq.chartfaces.api.Box;
import org.javaq.chartfaces.api.IChartPart;
import org.javaq.chartfaces.component.impl.UIChart;
import org.javaq.chartfaces.constants.Constants;
import org.javaq.chartfaces.dataspec.impl.DefaultChartingDataConverter;
import org.javaq.chartfaces.part.component.ChartComponent;
import org.javaq.chartfaces.render.tool.IChartToolFactory;
import org.javaq.chartfaces.util.NumberRange;
import org.javaq.chartfaces.viewspec.impl.PartCoordinator;
import org.javaq.chartfaces.viewspec.impl.PartSpecCalculatorFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class BarComponentToolTest {
	private MainChartTool mainTool;
	private ClassPathXmlApplicationContext applicationContext;

	@Before
	public void setUp() {
		applicationContext = new ClassPathXmlApplicationContext (new String[] {"chartfaces-context.xml"});
		final DefaultChartSettings defaultChartSettings = new DefaultChartSettings();
		ChartFacesManager.getInstance()
				.setDefaultDataConverter(new DefaultChartingDataConverter());
		ChartFacesManager.getInstance().setDefaultChartSettings(
				defaultChartSettings);
	}

	@Test
	public void testCreateTemplateDataList() throws Exception {
		// BarComponentTool tool =
		// createTestChartTool(Constants.ORIENTATION_HORIZONTAL);
		// mainTool.process();
		// tool.prepareData();
		// List<ITemplatable<?>> templatableList =
		// tool.createTemplateDataList();
		// for (ITemplatable<?> t : templatableList) {
		// System.out.println(RenderUtils.toJSan((ITemplatable<Object>) t));
		// fail("Not yet finished implementation");
		// }
	}

	@Test
	public void testIsValid() {
		final Map<Object, Object> state = new HashMap<Object, Object>();
		final IChartPart part = new ChartComponent();
		final AbstractComponentTool tool =
				new BarComponentTool(new ChartToolFactory(), part, state);
		Assert.assertTrue(tool.isValid(part));
	}

	@Test
	public void testPrepareDataOrientationHorizontal() throws Exception {
		// asserting that the valueArray values are correctly scaled
		final BarComponentTool tool = createTestChartTool(Constants.ORIENTATION_HORIZONTAL);
		tool.prepareData();
		final Iterator<Double> valueArray = tool.getValuesArray().iterator();
		Assert.assertEquals(50., valueArray.next().doubleValue(), 0.00001);
		Assert.assertEquals(100., valueArray.next().doubleValue(), 0.00001);
	}

	@Test
	public void testPrepareDataOrientationVertical() throws Exception {
		// asserting that the valueArray values are correctly scaled
		final BarComponentTool tool = createTestChartTool(Constants.ORIENTATION_VERTICAL);
		tool.prepareData();
		final Iterator<Double> valueArray = tool.getValuesArray().iterator();
		Assert.assertEquals(500, valueArray.next().doubleValue(), 0.0001);
		Assert.assertEquals(1000, valueArray.next().doubleValue(), 0.0001);
	}

	private BarComponentTool createTestChartTool(final String orientation)
			throws Exception {
		final Map<Object, Object> state = new HashMap<Object, Object>();
		final UIChart chart = new UIChart();
		chart.setRendererType(Constants.RENDERER_BAR);
		chart.setId("chart");
		chart.setOrientation(orientation);
		final IChartToolFactory factory = new ChartToolFactory();
		this.mainTool = new MainChartTool(factory, chart, state);
		final PartCoordinator pp = new PartCoordinator();
		pp.setPartSpecCalculatorFactory(new PartSpecCalculatorFactory());
		this.mainTool.setCoordinator(pp);
		final ChartComponent part = new ChartComponent();
		chart.getChildren().add(part);
		part.setId("barComponent");
		part.setParent(chart);
		part.setValue(new Integer[] { 50, 100 });
		final NumberRange xrange = new NumberRange();
		xrange.mergeBound(0.);
		xrange.mergeBound(1000.);
		final NumberRange yrange = new NumberRange();
		yrange.mergeBound(0.);
		yrange.mergeBound(100.);
		part.setXRange(xrange);
		part.setYRange(yrange);
		part.getPartSpec().setViewBox(new Box(1000, 1000));
		final BarComponentTool tool = new BarComponentTool(factory, part, state);
		this.mainTool.appendChildTool(tool);
		return tool;
	}

	// @Test
	// public void testGetChartDocument() {
	// fail("Not yet implemented");
	// }
}
