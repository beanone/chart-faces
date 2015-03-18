package org.javaq.chartfaces.render.tool.impl;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.javaq.chartfaces.ChartFacesManager;
import org.javaq.chartfaces.DefaultChartSettings;
import org.javaq.chartfaces.component.impl.UIChart;
import org.javaq.chartfaces.constants.Constants;
import org.javaq.chartfaces.part.axis.AngularAxis;
import org.javaq.chartfaces.part.axis.RadialAxis;
import org.javaq.chartfaces.part.axis.XAxis;
import org.javaq.chartfaces.part.axis.YAxis;
import org.javaq.chartfaces.part.component.ChartComponent;
import org.javaq.chartfaces.part.legend.ChartLegend;
import org.junit.Test;


public class ChartToolFactoryTest {

	@Test
	public void testGetTool() throws Exception {
		ChartFacesManager.getInstance().setDefaultChartSettings(
				new DefaultChartSettings());
		final UIChart chart = new UIChart();
		Map<Object, Object> state = new HashMap<Object, Object>();
		final ChartToolFactory factory = new ChartToolFactory();
		factory.getTool(chart, state);
		Assert.assertTrue(factory.getTool(new XAxis(), state) instanceof BottomAxisTool);
		final XAxis xaxis = new XAxis();
		xaxis.setPosition("top");
		Assert.assertTrue(factory.getTool(xaxis, state) instanceof TopAxisTool);
		Assert.assertTrue(factory.getTool(new YAxis(), state) instanceof LeftAxisTool);
		final YAxis yaxis = new YAxis();
		yaxis.setPosition("right");
		Assert.assertTrue(factory.getTool(yaxis, state) instanceof RightAxisTool);
		Assert.assertTrue(factory.getTool(new AngularAxis(), state) instanceof AngularAxisTool);
		Assert.assertTrue(factory.getTool(new RadialAxis(), state) instanceof RadialAxisTool);
		Assert.assertTrue(factory.getTool(new ChartLegend(), state) instanceof LegendTool);
		ChartComponent cc = new ChartComponent();
		chart.setRendererType(Constants.RENDERER_BAR);
		chart.getChildren().add(cc);
		Assert.assertTrue(factory.getTool(cc, state) instanceof BarComponentTool);
		cc = new ChartComponent();
		chart.getChildren().add(cc);
		chart.setRendererType(Constants.RENDERER_PIE);
		Assert.assertTrue(factory.getTool(cc, state) instanceof PieComponentTool);
		cc = new ChartComponent();
		chart.getChildren().add(cc);
		chart.setRendererType(Constants.RENDERER_SCATTER);
		Assert.assertTrue(factory.getTool(cc, state) instanceof ScatterComponentTool);
	}
}
