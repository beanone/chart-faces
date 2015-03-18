package org.javaq.chartfaces.render.exporter;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.apache.batik.transcoder.Transcoder;
import org.javaq.chartfaces.ImageStreamer;
import org.javaq.chartfaces.api.IImageDefinition;
import org.javaq.chartfaces.component.impl.UIChart;
import org.javaq.chartfaces.constants.SVGConstants;
import org.javaq.chartfaces.document.IChartDocument;
import org.javaq.chartfaces.document.IElement;
import org.javaq.chartfaces.document.impl.Element;


public abstract class AbstractImageExporter extends AbstractExporter {
	private static final String CHART_ID = "%chartid";
	private static int count = 0;

	private static final String SCRIPT_BLOCK_TEMPLATE =
			"$(document).ready(function(){" +
					"CF.cfInit('" + AbstractImageExporter.CHART_ID + "');\n" +
					"});";

	private final String key = "image" + AbstractImageExporter.incCount();

	private static int incCount() {
		return AbstractImageExporter.count++;
	}

	private IElement getSVGContainer(final IChartDocument chartDoc) {
		final IElement layout = chartDoc.getLayoutElement();
		final IElement container = Element.newInstance(layout.getTagName());
		final Map<String, Object> props = layout.getProperties();
		for (final Entry<String, Object> entry : props.entrySet()) {
			container.addProperty(entry.getKey(), entry.getValue());
		}
		return container;
	}

	private IElement makeSVGImageElement(final UIChart chart,
			final String srcUrl) {
		final IElement element = Element.newInstance(SVGConstants.SVG_IMAGE_NS);
		element.addProperty(SVGConstants.SVG_ID, chart.getClientId() + "image");
		element.addProperty(SVGConstants.SVG_WIDTH, "100%");
		element.addProperty(SVGConstants.SVG_HEIGHT, "100%");
		element.addProperty(SVGConstants.SVG_XLINK_HREF, srcUrl);
		return element;
	}

	protected String constructDynaImgUrl(final FacesContext facesContext,
			final String filePath) {
		final StringBuilder builder = new StringBuilder();
		builder.append("?").append(ImageStreamer.IMAGE_CONTENT_PARAM)
				.append("=").append(this.key);
		final FacesContext context = FacesContext.getCurrentInstance();
		final ExternalContext extContext = context.getExternalContext();
		final Map<String, Object> map = extContext.getSessionMap();
		final IImageDefinition imageDef = new IImageDefinition() {
			@Override
			public String getContentType() {
				return getExportType().getContentType();
			}

			@Override
			public String getFilePath() {
				return filePath;
			}
		};
		map.put(this.key, imageDef);
		return builder.toString();
	}

	protected abstract Transcoder createTranscoder();

	@Override
	protected String getScriptBlockTemplate(final IChartDocument chartDoc,
			final UIChart chart) {
		// render Javascript block that dynamically generates the chart content
		final String clientId = chart.getClientId();
		return AbstractImageExporter.SCRIPT_BLOCK_TEMPLATE.replace(
				AbstractImageExporter.CHART_ID, clientId);
	}

	@Override
	protected void handleAjax(final FacesContext context,
			final IChartDocument chartDoc, UIChart chart)
			throws IOException {
		final Transcoder transcoder = createTranscoder();
		final String filePath = ChartDocumentUtil.saveAsXML(
				chartDoc, getExportType().getContentType(), transcoder);
		final ResponseWriter writer = context.getResponseWriter();
		final String imgUrl = constructDynaImgUrl(context, filePath);
		writer.write(packageJsonPayload("\"" + imgUrl + "\"", chart));
	}

	@Override
	protected void renderChartXml(final IChartDocument chartDoc,
			final ResponseWriter rw,
			final UIChart chart)
			throws IOException {
		final FacesContext context = FacesContext.getCurrentInstance();

		final Transcoder transcoder = createTranscoder();
		final String filePath = ChartDocumentUtil.saveAsXML(
				chartDoc, getExportType().getContentType(), transcoder);

		final IElement container = getSVGContainer(chartDoc);
		final IElement imageElement = makeSVGImageElement(chart,
				constructDynaImgUrl(context, filePath));
		container.addChildren(imageElement);

		final ResponseWriter writer = context.getResponseWriter();
		writer.write(getRenderUtils().toXML(container).toString());
	}
}