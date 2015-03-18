package org.javaq.chartfaces.render.tool.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.javaq.chartfaces.api.IChartComponent;
import org.javaq.chartfaces.api.IChartPart;
import org.javaq.chartfaces.api.IChartingDataXYView;
import org.javaq.chartfaces.api.ILegendHandler;
import org.javaq.chartfaces.constants.EnumPartZIndex;
import org.javaq.chartfaces.constants.SVGConstants;
import org.javaq.chartfaces.converter.AbstractIterableToIterable;
import org.javaq.chartfaces.converter.ToStringIterableDataConverter;
import org.javaq.chartfaces.document.IContainerElement;
import org.javaq.chartfaces.document.IDataElement;
import org.javaq.chartfaces.document.IElement;
import org.javaq.chartfaces.document.impl.DataElement;
import org.javaq.chartfaces.document.impl.Element;
import org.javaq.chartfaces.iterable.ISizeAwareIterable;
import org.javaq.chartfaces.iterable.IterableUtility;
import org.javaq.chartfaces.part.component.ScatterComponent;
import org.javaq.chartfaces.render.svg.SymbolTemplate;
import org.javaq.chartfaces.render.tool.IChartToolFactory;


public class ScatterComponentTool extends AbstractComponentTool {
	private static final class VerticalTranslationIterable extends
			AbstractIterableToIterable<Double, Double> {
		private final int yAxisLen;
		private final int shift;

		private VerticalTranslationIterable(int yAxisLen, int shift) {
			this.yAxisLen = yAxisLen;
			this.shift = shift;
		}

		@Override
		protected Double transform(final Double target) {
			// shift to the center from the top-left of the symbol
			return yAxisLen - target - shift;
		}
	}

	private static final class HorizontalTranslationIterable extends
			AbstractIterableToIterable<Double, Double> {
		private final int shift;

		private HorizontalTranslationIterable(int shift) {
			this.shift = shift;
		}

		@Override
		protected Double transform(final Double target) {
			// shift to the center from the top-left of the symbol
			return target - shift;
		}
	}

	private ISizeAwareIterable<Double> xValues;
	private ISizeAwareIterable<Double> yValues;

	ScatterComponentTool(final IChartToolFactory factory,
			final IChartPart part, final Map<Object, Object> state) {
		super(factory, part, state);
	}

	@Override
	public void synchToolState(final Map<Object, Object> state) {
		// TODO Auto-generated method stub
		
	}

	private ISizeAwareIterable<Double> computeCoordsX(final int shift) {
		final Iterable<Double> valuesArray = this.getXValues();
		return new HorizontalTranslationIterable(shift).convert(valuesArray);
	}

	private ISizeAwareIterable<Double> computeCoordsY(final int shift) {
		final Iterable<Double> valuesArray = this.getYValues();
		final int yAxisLen = getViewBox().getHeight();
		return new VerticalTranslationIterable(yAxisLen, shift).convert(valuesArray);
	}

	private ILegendHandler createLegendHandler() {
		return new ILegendHandler() {

			@Override
			public IElement createElement() {
				final IElement element = Element.newInstance(SVGConstants.SVG_USE_NS);
				element.addProperty(SVGConstants.SVG_XLINK_HREF, "#" + getSymbolId());
				// TODO: there is still the x, and y
				return element;
			}
		};
	}

	private void fillPathProperties(final IDataElement pathTemplate) {
		final String paths = ChartToolUtil.renderAbsolutePaths(
				getXValues(), computeCoordsY(0), false);
		pathTemplate.addProperty(
				SVGConstants.SVG_STYLE, getScatterPart().getLineStyle());
		pathTemplate.addProperty(SVGConstants.SVG_D, paths);
	}

	private ScatterComponent getScatterPart() {
		return (ScatterComponent) getChartComponent();
	}

	private boolean isDrawLine() {
		return getScatterPart().isShowLine() && (getXValues() != null)
				&& (getYValues() != null);
	}

	/**
	 * @return the drawSymbol
	 */
	private boolean isDrawSymbol() {
		return getScatterPart().getSymbol() != null;
	}

	private void setXValues(final ISizeAwareIterable<Double> xValues) {
		this.xValues = xValues;
	}

	private void setYValues(final ISizeAwareIterable<Double> yValues) {
		this.yValues = yValues;
	}

	@Override
	protected final List<IDataElement> createDataElementList() {
		getMainChartTool().registerLegendHandler(getChartComponent(), createLegendHandler());
		final IContainerElement container = getMainChartTool()
				.getChartingAreaContainer();
		final List<IDataElement> templates = new ArrayList<IDataElement>();
		if (isDrawSymbol()) {
			// create the template data list
			final DataElement useTemplate =
					DataElement.newInstance(SVGConstants.SVG_USE, container);
			templates.add(useTemplate);
			fillSymbolProperties(useTemplate);
			// we want to be rendered last so that we are on top of others
			useTemplate.setOrdinal(EnumPartZIndex.datapoint);
		}
		if (isDrawLine()) {
			final DataElement pathTemplate = DataElement.newInstance(
					SVGConstants.SVG_PATH, container);
			templates.add(pathTemplate);
			fillPathProperties(pathTemplate);
			// we want to be rendered last so that we are on top of others
			pathTemplate.setOrdinal(EnumPartZIndex.dataline);
		}
		return templates;
	}

	@Override
	protected final void fillLayoutElement(final IElement element) {
		final ScatterComponent scatter = (ScatterComponent) getChartComponent();
		final SymbolTemplate template =
				SymbolMaker.makeSymbol(getSymbolId(), scatter);
		if (template != null) {
			final IElement defs = template.getDefs();
			element.addChildren(defs);
		}
	}

	protected void fillSymbolProperties(final IDataElement use) {
		final Object sizeObject = getSymbolSize();

		// every dot has the same size
		final int size = (int) ((Number) sizeObject).doubleValue();
		final int shift = size / 2;
		use.addProperty(SVGConstants.SVG_XLINK_HREF, "#" + getSymbolId());
		use.addProperty(SVGConstants.SVG_WIDTH, "" + size);
		use.addProperty(SVGConstants.SVG_HEIGHT, "" + size);
		use.addCustomProperty(SVGConstants.SVG_Y, computeCoordsY(shift));
		use.addCustomProperty(SVGConstants.SVG_X, computeCoordsX(shift));
		if (getChartComponent().isShowTooltips()) {
			final ISizeAwareIterable<String> coordinatePairs = new ToStringIterableDataConverter()
					.convert(new Iterable[] {
							getDataArguments(), getDataValues() });
			use.addCustomProperty(SVGConstants.SVG_TITLE, coordinatePairs);
		}
	}

	protected final String getSymbolId() {
		return getDataElementContainerId() + "symbol";
	}

	protected final Object getSymbolSize() {
		Object sizeObject = getScatterPart().getSymbolSize();
		if (sizeObject == null) {
			sizeObject = Double.valueOf(0);
		} else if (sizeObject instanceof String) {
			sizeObject = Double.valueOf((String) sizeObject);
		} else if (!(sizeObject instanceof Number)) {
			sizeObject = IterableUtility.toIterable(sizeObject);
		}
		return sizeObject;
	}

	protected final ISizeAwareIterable<Double> getXValues() {
		return this.xValues;
	}

	protected final ISizeAwareIterable<Double> getYValues() {
		return this.yValues;
	}

	@Override
	protected final boolean isValid(final IChartPart part) {
		return part instanceof IChartComponent;
	}

	@Override
	protected final void prepareData() {
		// fetch the data values from the component
		final IChartingDataXYView chartingData =
				getChartComponent().getChartingDataXYView();
		final Iterable<? extends Number> xIterable =
				chartingData.getXValues();
		this.setXValues(scaleX(xIterable));
		final Iterable<? extends Number> yIterable =
				chartingData.getYValues();
		this.setYValues(scaleY(yIterable));
	}
}