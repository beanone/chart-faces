package org.javaq.chartfaces.render.tool.impl;

import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;

import org.javaq.chartfaces.api.IChartPart;
import org.javaq.chartfaces.component.impl.UIChartBase;
import org.javaq.chartfaces.constants.EnumPart;
import org.javaq.chartfaces.constants.SVGConstants;
import org.javaq.chartfaces.document.IDataElement;
import org.javaq.chartfaces.document.IElement;
import org.javaq.chartfaces.document.impl.Element;
import org.javaq.chartfaces.render.tool.IChartToolFactory;


public class HeaderTool extends AbstractChartTool {
	HeaderTool(final IChartToolFactory factory, final IChartPart part,
			final Map<Object, Object> state) {
		super(factory, part, state);
	}

	private void addAttributes(final IElement element) {
		ChartToolUtil.createLayoutBox(getBoxModel(), element);
	}

	private String getTextanchor() {
		UIComponent comp = (UIComponent) getChartPart();
		return (String) comp.getAttributes().get("textanchor");
	}

	@Override
	protected List<IDataElement> createDataElementList() {
		// we don't need to have any ajax support for headers
		return null;
	}

	@Override
	protected final IElement createLayoutElement() {
		UIChartBase part = (UIChartBase) getChartPart();
		final Object headerObj = part.getValue();

		final IElement headerOuter = Element.newInstance(SVGConstants.SVG_G_NS);
		addAttributes(headerOuter);

		final IElement header = Element.newInstance(SVGConstants.SVG_G_NS);
		header.addProperty(SVGConstants.SVG_STYLE, getChartPart().getStyle());
		header.addProperty(SVGConstants.SVG_TEXT_ANCHOR, getTextanchor());
		if (headerObj == null) {
			if (part.getChildCount() > 0) {
				String content = part.getChildren().get(0).toString();
				String innerText = ChartToolUtil.eveluateContent(content);
				header.setInnerText(innerText);
			}
		} else {
			final IElement headerTextElement = createTextElement(headerObj);
			header.addChildren(headerTextElement);
		}

		headerOuter.addChildren(header);

		return headerOuter;
	}

	protected IElement createTextElement(final Object headerObj) {
		final IElement headerTextElement = Element.newInstance(SVGConstants.SVG_TEXT_NS);
		headerTextElement.addProperty(SVGConstants.SVG_X, getViewBox()
				.getWidth() / 2);
		headerTextElement.addProperty(SVGConstants.SVG_Y, getViewBox()
				.getHeight() / 2);
		headerTextElement.setInnerText(headerObj.toString());
		return headerTextElement;
	}

	@Override
	protected ChartDocumentType getDocumentType() {
		return ChartDocumentType.header;
	}

	@Override
	protected boolean hasContainerElementForData() {
		return false;
	}

	@Override
	protected boolean isValid(final IChartPart part) {
		return part.getPartType() == EnumPart.header;
	}

	@Override
	protected void prepareData() {
		// do nothing
	}

	@Override
	protected void synchToolState(final Map<Object, Object> state) {
		// do nothing
	}

}
