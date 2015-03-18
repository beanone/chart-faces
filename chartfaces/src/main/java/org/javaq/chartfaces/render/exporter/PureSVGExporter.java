package org.javaq.chartfaces.render.exporter;

import java.io.IOException;

import javax.faces.context.ResponseWriter;

import org.javaq.chartfaces.component.impl.UIChart;
import org.javaq.chartfaces.constants.EnumOutputType;
import org.javaq.chartfaces.document.IChartDocument;


public class PureSVGExporter extends AbstractSVGExporter {
	private static final String CHART_ID = "%chartid";
	private static final String INTERVAL = "%interval";
	private static final String SCRIPT_BLOCK_TEMPLATE =
			"$(document).ready(function(){" +
					"CF.cfInit('" + PureSVGExporter.CHART_ID + "', "
					+ PureSVGExporter.INTERVAL + ");\n" +
					"});";

	@Override
	public EnumOutputType getExportType() {
		return EnumOutputType.puresvg;
	}

	@Override
	protected String getScriptBlockTemplate(final IChartDocument chartDoc,
			final UIChart chart) {
		// render Javascript block that dynamically generates the chart content
		final String clientId = chart.getClientId();

		String refreshInterval = "''";
		if (chart.getLiveListener() != null) {
			refreshInterval = "" + chart.getRefreshInterval();
		}
		return PureSVGExporter.SCRIPT_BLOCK_TEMPLATE
				.replace(PureSVGExporter.CHART_ID, clientId)
				.replace(PureSVGExporter.INTERVAL, refreshInterval);
	}

	@Override
	protected void renderChartXml(final IChartDocument chartDoc,
			final ResponseWriter rw, final UIChart chart) throws IOException {
		rw.write(ChartDocumentUtil.toXML(chartDoc));
	}
}