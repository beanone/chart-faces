package org.javaq.chartfaces.dataspec.impl;

import junit.framework.Assert;

import org.javaq.chartfaces.api.IChartPart;
import org.javaq.chartfaces.component.impl.UIChart;
import org.javaq.chartfaces.constants.EnumPart;
import org.javaq.chartfaces.dataspec.PartFinder;
import org.javaq.chartfaces.part.axis.XAxis;
import org.javaq.chartfaces.part.axis.YAxis;
import org.javaq.chartfaces.part.component.ChartComponent;
import org.junit.Test;


public class PartFinderTest {

	@Test
	public void testFindPartById() {
		final UIChart chart = new UIChart();
		final XAxis xaxis = new XAxis();
		xaxis.setId("xaxis");
		chart.getChildren().add(xaxis);
		final YAxis yaxis = new YAxis();
		yaxis.setId("yaxis");
		chart.getChildren().add(yaxis);
		IChartPart part = PartFinder.findPartById(chart, "xaxis");
		Assert.assertSame(xaxis, part);
		part = PartFinder.findPartById(chart, "yaxis");
		Assert.assertSame(yaxis, part);
		part = PartFinder.findPartById(chart, "nosuchid");
		Assert.assertNull(part);
	}

	@Test
	public void testFindXAxis() {
		final UIChart chart = new UIChart();
		final XAxis xaxis = new XAxis();
		xaxis.setId("xaxis");
		chart.getChildren().add(xaxis);
		final XAxis xaxis1 = new XAxis();
		xaxis1.setId("xaxis1");
		chart.getChildren().add(xaxis1);
		final ChartComponent component = new ChartComponent();
		chart.getChildren().add(component);
		final ChartComponent component1 = new ChartComponent();
		component1.setAxesIds("xaxis1,yaxis1");
		chart.getChildren().add(component1);

		IChartPart part = PartFinder.findXAxis(component);
		Assert.assertSame(xaxis, part);
		part = PartFinder.findXAxis(component1);
		Assert.assertSame(xaxis1, part);
	}

	@Test
	public void testFindYAxis() {
		final UIChart chart = new UIChart();
		final YAxis yaxis = new YAxis();
		yaxis.setId("yaxis");
		chart.getChildren().add(yaxis);
		final YAxis yaxis1 = new YAxis();
		yaxis1.setId("yaxis1");
		chart.getChildren().add(yaxis1);
		final ChartComponent component = new ChartComponent();
		chart.getChildren().add(component);
		final ChartComponent component1 = new ChartComponent();
		component1.setAxesIds("xaxis1,yaxis1");
		chart.getChildren().add(component1);

		IChartPart part = PartFinder.findYAxis(component);
		Assert.assertSame(yaxis, part);
		part = PartFinder.findYAxis(component1);
		Assert.assertSame(yaxis1, part);
	}

	@Test
	public void testGetChart() {
		final UIChart chart = new UIChart();
		final XAxis axis = new XAxis();
		chart.getChildren().add(axis);
		IChartPart part = PartFinder.getChart(chart);
		Assert.assertSame(chart, part);
		part = PartFinder.getChart(axis);
		Assert.assertSame(chart, part);
	}

	@Test
	public void testGetFirstByType() {
		final UIChart chart = new UIChart();
		final XAxis xaxis = new XAxis();
		xaxis.setId("xaxis");
		chart.getChildren().add(xaxis);
		final XAxis xaxis1 = new XAxis();
		xaxis1.setId("xaxis1");
		chart.getChildren().add(xaxis1);
		final YAxis yaxis = new YAxis();
		yaxis.setId("yaxis");
		chart.getChildren().add(yaxis);
		final YAxis yaxis1 = new YAxis();
		yaxis1.setId("yaxis1");
		chart.getChildren().add(yaxis1);
		final ChartComponent component = new ChartComponent();
		chart.getChildren().add(component);
		final ChartComponent component1 = new ChartComponent();
		component1.setAxesIds("xaxis1,yaxis1");
		chart.getChildren().add(component1);

		IChartPart part = PartFinder.getFirstByType(chart, EnumPart.xaxis);
		Assert.assertSame(xaxis, part);
		part = PartFinder.getFirstByType(chart, EnumPart.yaxis);
		Assert.assertSame(yaxis, part);
		part = PartFinder.getFirstByType(chart, EnumPart.component);
		Assert.assertSame(component, part);
	}

}
