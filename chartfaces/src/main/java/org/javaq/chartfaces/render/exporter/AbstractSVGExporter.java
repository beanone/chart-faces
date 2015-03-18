package org.javaq.chartfaces.render.exporter;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.context.PartialResponseWriter;

import org.javaq.chartfaces.component.impl.UIChart;
import org.javaq.chartfaces.document.IChartDocument;


public abstract class AbstractSVGExporter extends AbstractExporter {
	@Override
	protected void handleAjax(final FacesContext context,
			final IChartDocument chartDoc, UIChart chart) throws IOException {
		// handle ajax polling update
		final PartialResponseWriter rw = context.getPartialViewContext()
				.getPartialResponseWriter();
		final String jsan = getRenderUtils().toJSan(chartDoc
				.getDataElementList()).toString();
		rw.write(packageJsonPayload(jsan, chart));
	}
}
