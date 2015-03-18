package org.javaq.chartfaces.render.base;

import java.io.IOException;
import java.util.HashMap;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.FacesEvent;
import javax.faces.render.Renderer;

import org.javaq.chartfaces.ChartFacesManager;
import org.javaq.chartfaces.api.IExporter;
import org.javaq.chartfaces.api.IExporterFactory;
import org.javaq.chartfaces.component.impl.UIChart;
import org.javaq.chartfaces.document.IChartDocument;
import org.javaq.chartfaces.event.factory.IChartEventFactory;
import org.javaq.chartfaces.render.tool.IChartTool;
import org.javaq.chartfaces.render.tool.IChartToolFactory;



/**
 * Abstract super class for all IChartRenderer implementations.
 * 
 * @author Hongyan Li
 * 
 */
public abstract class ChartRenderer extends Renderer {
	@Override
	public void decode(final FacesContext context, final UIComponent component) {
		super.decode(context, component);
		final ChartFacesManager manager = getChartFacesManager();
		final IChartEventFactory eventFactory = manager.getChartEventFactory();
		final FacesEvent event = eventFactory.createEvent(context, component);
		if (event != null) {
			component.queueEvent(event);
			final String clientId = component.getClientId(context);
			context.getPartialViewContext().getRenderIds().add(clientId);
		}
	}

	@Override
	public void encodeBegin(final FacesContext context,
			final UIComponent component) throws IOException {
		super.encodeBegin(context, component);

		if(!(component instanceof UIChart)) {
			throw new IllegalArgumentException();
		}
		final UIChart chart = (UIChart) component;
		processDataRange(context, chart);

		// construct the chart document using the chart tools
		final ChartFacesManager manager = getChartFacesManager();
		final IChartToolFactory chartToolFactory = manager.getChartToolFactory();

		final IChartTool chartTool = chartToolFactory.getTool(chart, new HashMap<Object, Object>());
		chartTool.setCoordinator(manager.getChartPartCoordinator());
		chartTool.process();
		final IChartDocument chartDoc = chartTool.getChartDocument();
		chartDoc.getLayoutElement().addProperty("id", chart.getClientId());

		final IExporterFactory exporterFactory = manager.getExporterFactory();
		final IExporter exporter = exporterFactory.getExporter(chart.getOutput());
		exporter.export(context, component, chartDoc);
	}

	protected ChartFacesManager getChartFacesManager() {
		return ChartFacesManager.getInstance();
	}

	protected abstract void processDataRange(FacesContext context, UIChart chart)
			throws IOException;
}
