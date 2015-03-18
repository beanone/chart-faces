package org.javaq.chartfaces.render.exporter;

import java.io.IOException;

import javax.faces.context.ResponseWriter;

import org.javaq.chartfaces.component.impl.UIChart;
import org.javaq.chartfaces.constants.EnumOutputType;
import org.javaq.chartfaces.document.IChartDocument;


public class SVGExporter extends AbstractSVGExporter {
	private static final String EMPTY_JSON_STRING = "''";
	private static final String CHART_ID = "%chartid";
	private static final String INTERVAL = "%interval";
	private static final String PROGRESSIVE = "%progressive";
	private static final String PARAM_DS = "%ds";
	private static final String SCRIPT_BLOCK_TEMPLATE =
			"$(document).ready(function(){" +
					"var ds = " + SVGExporter.PARAM_DS + ";\n" +
					"CF.updateChart(ds);\n" +
					"CF.cfInit('" + SVGExporter.CHART_ID + "', "
					+ SVGExporter.INTERVAL + ", "
					+ SVGExporter.PROGRESSIVE + ");\n" +
					"});";

	@Override
	public EnumOutputType getExportType() {
		return EnumOutputType.svg;
	}

	@Override
	protected String getScriptBlockTemplate(final IChartDocument chartDoc,
			final UIChart chart) {
		// render Javascript block that dynamically generates the chart content
		final String clientId = chart.getClientId();
		final String jsan = getRenderUtils().toJSan(
				chartDoc.getDataElementList()).toString();

		String refreshInterval = EMPTY_JSON_STRING;
		String progressive = EMPTY_JSON_STRING;
		if (chart.getLiveListener() != null) {
			refreshInterval = "" + chart.getRefreshInterval();
		} else if(chart.getProgressiveRenderListener()!=null) {
			progressive = "true";
		}
		return SVGExporter.SCRIPT_BLOCK_TEMPLATE
				.replace(SVGExporter.CHART_ID, clientId)
				.replace(SVGExporter.PARAM_DS, packageJsonPayload(jsan, chart))
				.replace(SVGExporter.INTERVAL, refreshInterval)
				.replace(SVGExporter.PROGRESSIVE, progressive);
	}

	@Override
	protected void renderChartXml(final IChartDocument chartDoc,
			final ResponseWriter rw, final UIChart chart) throws IOException {
		rw.write(getRenderUtils().toXML(chartDoc.getLayoutElement()).toString());
	}
}
