package org.javaq.chartfaces.render.exporter;

import java.io.IOException;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.javaq.chartfaces.api.IExporter;
import org.javaq.chartfaces.component.impl.UIChart;
import org.javaq.chartfaces.constants.SVGConstants;
import org.javaq.chartfaces.document.IChartDocument;
import org.javaq.chartfaces.render.base.BrowserUtil;
import org.javaq.chartfaces.render.base.ScriptUtil;
import org.javaq.chartfaces.render.svg.IStructuredStringBuilder;
import org.javaq.chartfaces.render.svg.IStructuredStringBuilderFactory;
import org.javaq.chartfaces.render.svg.IValueFormatPolicy;
import org.javaq.chartfaces.render.svg.RenderUtils;
import org.javaq.chartfaces.render.svg.StructuredStringBuilder;
import org.javaq.chartfaces.util.NumberRoundingStringBuilder;


public abstract class AbstractExporter implements IExporter {

	private static final class ValueFormatPolicy implements IValueFormatPolicy {
		@Override
		public Object format(final Object key, final Object value) {
			if (SVGConstants.SVG_TITLE.equals(key)) {
				return "" + value; // turn number to String
			}
			return value;
		}
	}

	private static final class NumberRoundingBuilderFactory implements
			IStructuredStringBuilderFactory {
		@Override
		public IStructuredStringBuilder newBuilder() {
			return new StructuredStringBuilder(
					new NumberRoundingStringBuilder());
		}
	}

	private static final String CHARTFACES_JS = "js/chartfaces.js";
	private static final String CHARTFACES_LIB = "chartfaces";
	private static final String CHARTUTIL_JS = "js/chartutil.js";
	private static final String CHARTUTIL_JS_CHROME = "js/chrome/chartutil.js";
	private static final String DRAG_JS = "js/jquery.event.drag.min.js";
	private static final String DROP_JS = "js/jquery.event.drop.min.js";
	private static final String JQUERY_MIN_JS = "js/jquery.min.js";
	private static final String JSF_JS = "js/jsf.js";
	private static final String MOUSEWHEEL_JS = "js/jquery.mousewheel.js";
	private static final String SUBSCRIBE_JS = "js/jquery.subscribe.js";

	private final RenderUtils utils;

	public AbstractExporter() {
		this.utils = new RenderUtils(
				new NumberRoundingBuilderFactory(), new ValueFormatPolicy());
	}

	@Override
	public final void export(final FacesContext context,
			final UIComponent component,
			final IChartDocument chartDoc)
			throws IOException {
		if(!(component instanceof UIChart)) {
			throw new IllegalArgumentException();
		}

		final UIChart chart = (UIChart) component;
		final String clientId = component.getClientId(context);
		if (isPartial(context, clientId)) {
			handleAjax(context, chartDoc, chart);
		} else {
			handleNonAjax(context, chartDoc, chart);
		}
	}

	private boolean isPartial(final FacesContext context, final String clientId) {
		final ExternalContext external = context.getExternalContext();
		final Map<String, String> params = external.getRequestParameterMap();
		final String behaviorSource = params.get("javax.faces.source");

		return behaviorSource != null && behaviorSource.equals(clientId);
	}

	private void renderScriptBlock(final FacesContext context,
			final IChartDocument chartDoc, final UIChart chart)
			throws IOException {
		ScriptUtil.writeScriptBlock(context,
						getScriptBlockTemplate(chartDoc, chart));
	}

	private void renderScriptFileReferences(final FacesContext context)
			throws IOException {
		// render Javascript includes
		// JSF2 resource dependency injection is just a good idea but to use it,
		// we will have to require the users to specify a certain tag in the
		// page, say, the h:head or h:body tag. But who said that we only
		// renders to HTML only in the future???
		// ScriptUtil.writeCSSFile(context, CHARTFACES_LIB, CHARTFACES_CSS);
		ScriptUtil.writeScriptFile(context, AbstractExporter.CHARTFACES_LIB,
				AbstractExporter.JSF_JS);
		ScriptUtil.writeScriptFile(context, AbstractExporter.CHARTFACES_LIB,
				AbstractExporter.JQUERY_MIN_JS);
		ScriptUtil.writeScriptFile(context, AbstractExporter.CHARTFACES_LIB,
				AbstractExporter.SUBSCRIBE_JS);
		ScriptUtil.writeScriptFile(context, AbstractExporter.CHARTFACES_LIB,
				AbstractExporter.CHARTFACES_JS);
		ScriptUtil.writeScriptFile(context, AbstractExporter.CHARTFACES_LIB,
				AbstractExporter.MOUSEWHEEL_JS);
		ScriptUtil.writeScriptFile(context, AbstractExporter.CHARTFACES_LIB,
				AbstractExporter.DRAG_JS);
		ScriptUtil.writeScriptFile(context, AbstractExporter.CHARTFACES_LIB,
				AbstractExporter.DROP_JS);
		if (BrowserUtil.isGoogleChrome()) {
			ScriptUtil.writeScriptFile(context,
					AbstractExporter.CHARTFACES_LIB,
					AbstractExporter.CHARTUTIL_JS_CHROME);
		} else {
			ScriptUtil.writeScriptFile(context,
					AbstractExporter.CHARTFACES_LIB,
					AbstractExporter.CHARTUTIL_JS);
		}
	}

	protected final RenderUtils getRenderUtils() {
		return this.utils;
	}

	protected abstract String getScriptBlockTemplate(
			final IChartDocument chartDoc, final UIChart chart);

	protected abstract void handleAjax(FacesContext context,
			IChartDocument chartDoc, UIChart chart) throws IOException;

	protected final void handleNonAjax(final FacesContext context,
			final IChartDocument chartDoc, final UIChart chart)
			throws IOException {
		final ResponseWriter rw = context.getResponseWriter();
		// render the JavaScripts
		renderScripts(context, chartDoc, chart);

		// render the chart mark-ups
		renderChartXml(chartDoc, rw, chart);
	}

	/**
	 * Package the json payload in a format that the client Javascript can
	 * recognize.
	 *
	 * @param json
	 *            the actual json payload.
	 * @param chart TODO
	 * @return json string of the packaged json object.
	 */
	protected final String packageJsonPayload(final String json, UIChart chart) {
		StringBuilder sb = new StringBuilder("{\"payloadType\":\"")
			.append(getExportType().getPayloadType()).append("\",");
		if(chart.getProgressiveRenderListener()!=null) {
			if(!chart.isStart()) {
				sb.append("\"append\":\"true\",");
			}
			if(chart.isCancel()) {
				sb.append("\"cancel\":\"true\",");
			}
			sb.append("\"progress\":").append(chart.getProgress()).append(",");
		}
		return sb.append("\"payload\":").append(json).append("}").toString();
	}

	protected abstract void renderChartXml(IChartDocument chartDoc,
			final ResponseWriter rw, UIChart chart) throws IOException;

	protected final void renderScripts(final FacesContext context,
			final IChartDocument chartDoc, final UIChart chart)
			throws IOException {
		renderScriptFileReferences(context);
		renderScriptBlock(context, chartDoc, chart);
	}
}