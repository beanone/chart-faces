package org.javaq.chartfaces.render.tool.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.javaq.chartfaces.api.Box;
import org.javaq.chartfaces.api.IChartPart;
import org.javaq.chartfaces.component.impl.AbstractChartAxis;
import org.javaq.chartfaces.constants.EnumPart;
import org.javaq.chartfaces.constants.EnumPartZIndex;
import org.javaq.chartfaces.constants.SVGConstants;
import org.javaq.chartfaces.document.IContainerElement;
import org.javaq.chartfaces.document.IDataElement;
import org.javaq.chartfaces.document.IElement;
import org.javaq.chartfaces.document.impl.DataElement;
import org.javaq.chartfaces.document.impl.Element;
import org.javaq.chartfaces.iterable.ISizeAwareIterable;
import org.javaq.chartfaces.render.tool.IChartToolFactory;
import org.javaq.chartfaces.util.IStringBuilder;


public abstract class CartesianAxisTool extends AbstractChartTool {
	private boolean isGridRandered;

	private ISizeAwareIterable<Double> labelCoordinates;

	CartesianAxisTool(final IChartToolFactory factory, final IChartPart part,
			final Map<Object, Object> state) {
		super(factory, part, state);
	}

	private void addAttributes(final IElement element) {
		ChartToolUtil.createLayoutBox(getBoxModel(), element);
		element.addProperty(SVGConstants.SVG_SHAPE_RENDERING, SVGConstants.SVG_CRISP_EDGES);
	}

	private IDataElement createAxisLinePathElement() {
		final DataElement element = DataElement.newInstance(SVGConstants.SVG_PATH,
						getDataElementContainer());
		final IStringBuilder sb = createAxisLinePath();
		element.addProperty(SVGConstants.SVG_STYLE,
				getAxisPart().getLineStyle());
		element.addProperty(SVGConstants.SVG_D, sb.toString());
		element.setOrdinal(EnumPartZIndex.axisline);
		return element;
	}

	private IElement createCaptionElement() {
		final IElement element = Element.newInstance(SVGConstants.SVG_G_NS);
		final IElement captionText = createCaptionTextElement(element);
		captionText.setInnerText(getAxisPart().getCaption());
		element.addChildren(captionText);
		element.addProperty(SVGConstants.SVG_STYLE, getAxisPart()
				.getCaptionStyle());
		element.addProperty(SVGConstants.SVG_TEXT_ANCHOR,
				SVGConstants.SVG_MIDDLE);
		return element;
	}

	private IDataElement createGridLineDataListElement(final int gridLineDensity) {
		final IContainerElement container =
				getMainChartTool().getChartingAreaContainer();
		final IDataElement dataElement = DataElement.newInstance(
				SVGConstants.SVG_G, container);
		dataElement.addChildren(createGridLinesElement(gridLineDensity));
		return dataElement;
	}

	private boolean isGridRendered() {
		return this.isGridRandered;
	}

	private void setGridRendered() {
		updateState(getAxisType(), true);
	}

	protected abstract void addLabels(IDataElement element);

	protected abstract IStringBuilder createAxisLinePath();

	protected abstract IElement createCaptionTextElement(IElement captionElement);

	@Override
	protected final List<IDataElement> createDataElementList() {
		final List<IDataElement> dataList = new ArrayList<IDataElement>();
		// add the grid lines
		final int gridLineDensity = getAxisPart().getGridLineDensity();
		if (gridLineDensity > 0 && !isGridRendered()) {
			getMainChartTool().getChartingAreaDataList().add(
					createGridLineDataListElement(gridLineDensity));
			setGridRendered();
		}
		// add the axis line
		dataList.add(createAxisLinePathElement());
		// add the axis ticks
		dataList.add(createTicksElement());
		// add the axis labels
		dataList.add(createLabelTextElements());
		return dataList;
	}

	protected final IElement createGridLinesElement(final int gridLinesDensity) {
		final IElement element = Element.newInstance(SVGConstants.SVG_PATH);
		element.addProperty(SVGConstants.SVG_STYLE,
				getAxisPart().getGridLineStyle());
		element.addProperty(SVGConstants.SVG_D,
				createGridLinesPaths(gridLinesDensity));
		return element;
	}

	protected abstract String createGridLinesPaths(int density);

	protected final IDataElement createLabelTextElements() {
		final DataElement element = DataElement.newInstance(
				SVGConstants.SVG_G, getDataElementContainer());
		element.addProperty(SVGConstants.SVG_STYLE, getAxisPart()
				.getLabelStyle());
		element.addProperty(SVGConstants.SVG_TEXT_ANCHOR, getLabelAnchor());
		addLabels(element);
		element.setOrdinal(EnumPartZIndex.axislabel);
		return element;
	}

	@Override
	protected final IElement createLayoutElement() {
		final IElement element = Element.newInstance(SVGConstants.SVG_G_NS);
		addAttributes(element);
		element.addChildren(createCaptionElement());
		return element;
	}

	protected abstract String createTickPaths();

	protected final IDataElement createTicksElement() {
		final DataElement element = DataElement.newInstance(SVGConstants.SVG_PATH,
						getDataElementContainer());
		element.addProperty(SVGConstants.SVG_STYLE,
				getAxisPart().getTickStyle());
		element.addProperty(SVGConstants.SVG_D, createTickPaths());
		element.setOrdinal(EnumPartZIndex.axistick);
		return element;
	}

	protected abstract int getAxisLength(final Box box);

	protected final AbstractChartAxis getAxisPart() {
		return (AbstractChartAxis) getChartPart();
	}

	protected abstract EnumPart getAxisType();

	protected abstract ISizeAwareIterable<Double> getComponentComputedLabelCoordinates();

	protected abstract String getLabelAnchor();

	protected final ISizeAwareIterable<Double> getLabelCoordinates() {
		if (this.labelCoordinates == null) {
			this.labelCoordinates = getComponentComputedLabelCoordinates();
			if (this.labelCoordinates == null) {
				final Iterable<Double> values =
						getAxisPart().getValueIterable();
				final Box box = getViewBox();
				final double minValue = getAxisPart().getRange().getMin();
				final double maxValue = getAxisPart().getRange().getMax();
				final double valueRange = maxValue - minValue;
				if (valueRange > 0) {
					final int axisRange = getAxisLength(box);
					final double scale = axisRange / (maxValue - minValue);
					this.labelCoordinates =
							scaleCoordinates(values, minValue, scale, axisRange);
				}
			}
		}
		return this.labelCoordinates;
	}

	protected final Iterable<Object> getLabelIterable() {
		return getAxisPart().getLabelIterable();
	}

	@Override
	protected final boolean isValid(final IChartPart part) {
		return part.getPartType() == getAxisType();
	}

	@Override
	protected final void prepareData() {
		// no need to do anything
	}

	protected abstract ISizeAwareIterable<Double> scaleCoordinates(
			final Iterable<Double> values,
			final double minValue, final double scale, final int axisRange);

	@Override
	protected void synchToolState(final Map<Object, Object> state) {
		final Boolean rendered = (Boolean) state.get(getAxisType());
		this.isGridRandered = rendered == null ? false : rendered;
	}
}
