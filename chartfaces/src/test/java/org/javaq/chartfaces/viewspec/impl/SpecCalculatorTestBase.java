package org.javaq.chartfaces.viewspec.impl;

import java.io.IOException;

import javax.faces.component.UIComponent;

import junit.framework.Assert;

import org.javaq.chartfaces.api.IChartPart;
import org.javaq.chartfaces.api.IPartSpec;
import org.javaq.chartfaces.boxcalculator.BoxCalculatorFactory;
import org.javaq.chartfaces.boxcalculator.impl.BoxCalculatorFactoryImpl;
import org.javaq.chartfaces.component.impl.UIChart;
import org.javaq.chartfaces.constants.Constants;
import org.javaq.chartfaces.part.axis.XAxis;
import org.javaq.chartfaces.part.axis.YAxis;
import org.javaq.chartfaces.part.layout.impl.LayoutManager;
import org.javaq.chartfaces.part.legend.ChartLegend;
import org.javaq.chartfaces.viewspec.IPartSpecCalculator;
import org.javaq.chartfaces.viewspec.IPartSpecCalculatorFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public abstract class SpecCalculatorTestBase {
	private UIChart chart;
	private final IPartSpecCalculatorFactory<SpecHelper> factory = new PartSpecCalculatorFactory();
	private ClassPathXmlApplicationContext context;

	@Before
	public void setup() throws Exception {
		context = new ClassPathXmlApplicationContext("chartfaces-context.xml");
	}

	@Test
	public void testCreateSpec() throws Exception {
		final IPartSpec specs = createSpecs();
		commonValidation(specs);
	}

	private void commonValidation(final IPartSpec specs) throws Exception {
		Assert.assertNotNull(specs);
		Assert.assertNotNull(specs.getViewBox());
		Assert.assertNotNull(specs.getProperties());
	}

	protected final UIChart createChart() {
		this.chart = new UIChart();
		this.chart.setId("a" + this.hashCode());
		this.chart.setRendererType(Constants.RENDERER_BAR);
		return this.chart;
	}

	protected IChartPart createLegend(final String id, final String width, final String height) {
		final ChartLegend legend = new ChartLegend();
		legend.setId(id);
		legend.setWidth(width);
		legend.setHeight(height);
		return legend;
	}

	protected abstract IChartPart createPart();

	protected abstract IPartSpecCalculator createSpecCreator(SpecHelper helper);

	protected final IPartSpecCalculator createSpecCreator(final UIChart chart,
			final IChartPart... chartParts) throws IOException {
		final SpecHelper helper = createSpecHelper(chart, chartParts);
		initChartSpec(chart, helper);
		return createSpecCreator(helper);
	}

	protected final SpecHelper createSpecHelper(final UIChart chart,
			final IChartPart... chartParts) {
		for (final IChartPart part : chartParts) {
			chart.getChildren().add((UIComponent) part);
		}
		final SpecHelper helper = SpecHelper.newInstance(chart, new LayoutManager());
		helper.setBoxCalculatorFactory((BoxCalculatorFactory)context.getBean("boxCalculatorFactory"));
		return helper;
	}

	protected final IPartSpec createSpecs() throws IOException {
		final UIChart chart = createChart();
		final IPartSpecCalculator creator = createSpecCreator(chart);
		final IChartPart part = createPart();
		chart.getChildren().add((UIComponent) part);
		return creator.calculate(part);
	}

	protected IChartPart createXAxis(final String id, final double min, final double max) {
		// Don't add labels, do it later!
		final XAxis xaxis = new XAxis();
		xaxis.setId(id);
		xaxis.setMin(min);
		xaxis.setMax(max);
		return xaxis;
	}

	protected IChartPart createYAxis(final String id, final double min, final double max) {
		final YAxis yaxis = new YAxis();
		yaxis.setId(id);
		yaxis.setMin(min);
		yaxis.setMax(max);
		return yaxis;
	}

	protected void customAssertions(final IPartSpec specs) {
		// empty body ad default
	}

	/**
	 * @return the chart
	 */
	protected final IChartPart getChart() {
		return this.chart;
	}

	/**
	 * @return the factory
	 */
	protected IPartSpecCalculatorFactory<SpecHelper> getFactory() {
		return this.factory;
	}

	protected void initChartSpec(final IChartPart chart, final SpecHelper helper)
			throws IOException {
		final ChartSpecCalculator creator = new ChartSpecCalculator(helper);
		creator.calculate(chart);
	}
}