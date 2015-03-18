package org.javaq.chartfaces.part.layout.impl;

import java.io.IOException;

import junit.framework.Assert;

import org.javaq.chartfaces.ChartFacesManager;
import org.javaq.chartfaces.DefaultChartSettings;
import org.javaq.chartfaces.api.Box;
import org.javaq.chartfaces.api.Coordinate;
import org.javaq.chartfaces.api.IChartPart;
import org.javaq.chartfaces.component.impl.UIChart;
import org.javaq.chartfaces.part.axis.XAxis;
import org.javaq.chartfaces.part.axis.YAxis;
import org.javaq.chartfaces.part.component.ChartComponent;
import org.javaq.chartfaces.part.layout.ILayoutManager;
import org.javaq.chartfaces.part.legend.ChartLegend;
import org.junit.Before;
import org.junit.Test;


public class LayoutCreatorTest {

	@Before
	public void setUp() {
		ChartFacesManager.getInstance().setDefaultChartSettings(
				new DefaultChartSettings());
	}

	@Test
	public void testGetChartAfterRegister() throws Exception {
		final LayoutManager manager = new LayoutManager();
		manager.register(new UIChart());
		Assert.assertNotNull(manager.getChart());
	}

	@Test(expected = NullPointerException.class)
	public void testGetOriginAfterInvokeLayoutAndWithChartNoViewBox()
			throws Exception {
		final ILayoutManager creator = getLayoutManager();
		creator.register(new UIChart());
		final IChartPart part = new XAxis();
		creator.register(part);
		creator.layout();
	}

	@Test
	public void testGetOriginAfterInvokeLayoutAndWithChartWithViewBox()
			throws Exception {
		final ILayoutManager manager = getLayoutManager();
		final UIChart chart = new UIChart();
		chart.getPartSpec().setViewBox(new Box(1600, 1000));
		manager.register(chart);
		final IChartPart part = new YAxis();
		part.getPartSpec().setViewBox(new Box(1450, 120));
		manager.register(part);
		manager.layout();
		Assert.assertNotNull(manager.getOrigin(part));
	}

	protected ILayoutManager getLayoutManager() {
		final LayoutManager manager = new LayoutManager();
		manager.setHorizontalOrderStrategy(new HorizontalOrderStrategy());
		manager.setVerticalOrderStrategy(new VerticalOrderStrategy());
		return manager;
	}

	@Test(expected = NullPointerException.class)
	public void testGetOriginAfterInvokeLayoutButNoChart() throws Exception {
		final ILayoutManager creator = getLayoutManager();
		final IChartPart part = new XAxis();
		creator.register(part);
		creator.layout();
	}

	@Test(expected = IllegalStateException.class)
	public void testGetOriginBeforeInvokeLayout() {
		final ILayoutManager creator = getLayoutManager();
		final IChartPart part = new XAxis();
		creator.register(part);
		creator.getOrigin(part);
	}

	@Test(expected = IOException.class)
	public void testLayoutNotEnoughHorizontalSpace()
			throws Exception {
		final ILayoutManager manager = getLayoutManager();
		final UIChart chart = new UIChart();
		chart.getPartSpec().setViewBox(new Box(1600, 1000));
		manager.register(chart);
		final IChartPart part = new YAxis();
		part.getPartSpec().setViewBox(new Box(1595, 120));
		manager.register(part);
		manager.layout();
	}

	@Test(expected = IOException.class)
	public void testLayoutNotEnoughVerticalSpace()
			throws Exception {
		final ILayoutManager manager = getLayoutManager();
		final UIChart chart = new UIChart();
		chart.getPartSpec().setViewBox(new Box(1600, 1000));
		manager.register(chart);
		final IChartPart part = new XAxis();
		part.getPartSpec().setViewBox(new Box(120, 995));
		manager.register(part);
		manager.layout();
	}

	@Test
	public void testLayoutWithXAxisYAxisAndLegend() throws Exception {
		final ILayoutManager manager = getLayoutManager();
		final UIChart chart = new UIChart();
		chart.getPartSpec().setViewBox(new Box(1600, 1000));
		manager.register(chart);
		// add one y-axis with default position
		final YAxis yaxis = new YAxis();
		yaxis.getPartSpec().setViewBox(new Box(200, 1000));
		manager.register(yaxis);
		// add another y-axis with position to right
		final YAxis yaxis1 = new YAxis();
		yaxis1.setPosition("right");
		yaxis1.getPartSpec().setViewBox(new Box(200, 1000));
		manager.register(yaxis1);
		// add another y-axis in the middle which should not take up space
		final YAxis yaxis2 = new YAxis();
		yaxis2.setPosition("middle");
		yaxis2.getPartSpec().setViewBox(new Box(200, 1000));
		manager.register(yaxis2);
		// add component
		final ChartComponent comp = new ChartComponent();
		comp.getPartSpec().setViewBox(new Box(740, 540));
		manager.register(comp);
		// add a legend at top
		final ChartLegend legend = new ChartLegend();
		legend.setPosition("top");
		// the width does not matter since it scales with the width of the chart
		// area.
		legend.getPartSpec().setViewBox(new Box(1, 200));
		manager.register(legend);
		// add a legend at bottom
		final ChartLegend legend1 = new ChartLegend();
		legend1.setPosition("bottom");
		// the width does not matter since it scales with the width the chart
		// area.
		legend1.getPartSpec().setViewBox(new Box(1, 200));
		manager.register(legend1);
		// add a legend to the left
		final ChartLegend legend2 = new ChartLegend();
		legend2.setPosition("left");
		// the height does not matter since it scales with the height the chart
		// area.
		legend2.getPartSpec().setViewBox(new Box(200, 1));
		manager.register(legend2);
		// add a legend to the right
		final ChartLegend legend3 = new ChartLegend();
		legend3.setPosition("right");
		// the height does not matter since it scales with the height the chart
		// area.
		legend3.getPartSpec().setViewBox(new Box(200, 1));
		manager.register(legend3);
		// add a legend with absolute positioning
		final ChartLegend legend4 = new ChartLegend();
		legend4.setX(1200);
		legend4.setY(300);
		// the dimensions will not be added to the calculations.
		legend4.getPartSpec().setViewBox(new Box(300, 300));
		manager.register(legend4);
		// add a x-axis in the middle which should NOT take up space
		final XAxis xaxis = new XAxis();
		xaxis.setPosition("middle");
		xaxis.getPartSpec().setViewBox(new Box(1000, 200));
		manager.register(xaxis);
		// The calculation along X-Axis:
		// Padding for the chart all around is 30
		// Padding for everything else are assumed to be taken care of by the
		// SpecHelper. So Chart-left-padding + YAxis-width + Component-width +
		// Chart-right-padding + YAxis-width = 30 + 200 + 1190 + 150 + 30 = 1600
		// Similar calculation for dimensions along Y-Axis.
		manager.layout();
		assertOriginNot00(manager.getOrigin(yaxis));
		assertOriginNot00(manager.getOrigin(yaxis1));
		assertOriginNot00(manager.getOrigin(yaxis2));
		assertOriginNot00(manager.getOrigin(comp));
		assertOrigin(manager.getOrigin(legend), 400, 0);
		assertOriginNot00(manager.getOrigin(legend1));
		assertOrigin(manager.getOrigin(legend2), 0, 200);
		assertOriginNot00(manager.getOrigin(legend3));
		assertOriginNot00(manager.getOrigin(legend4));
	}

	@Test
	public void testOrigin() {
		LayoutManager creator = new LayoutManager();
		IChartPart part = new XAxis();
		creator.register(part);
		Coordinate coord = creator.origin(part);
		Assert.assertNotNull(coord);
		Assert.assertEquals(0, coord.getX());

		creator = new LayoutManager();
		part = new YAxis();
		creator.register(part);
		coord = creator.origin(part);
		Assert.assertNotNull(coord);
		Assert.assertEquals(0, coord.getY());
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testOriginXAxisSetXNotSupported() {
		final LayoutManager creator = new LayoutManager();
		final IChartPart part = new XAxis();
		creator.register(part);
		creator.origin(part).setX(1);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testOriginYAxisSetYNotSupported() {
		final LayoutManager creator = new LayoutManager();
		final IChartPart part = new YAxis();
		creator.register(part);
		creator.origin(part).setY(1);
	}

	private void assertOrigin(final Coordinate coord, final int origX,
			final int origY) {
		Assert.assertNotNull(coord);
		Assert.assertEquals(origX, coord.getX());
		Assert.assertEquals(origY, coord.getY());
	}

	private void assertOriginNot00(final Coordinate coord) {
		Assert.assertNotNull(coord);
		Assert.assertTrue(coord.getX() > 0);
		Assert.assertTrue(coord.getY() > 0);
	}
}