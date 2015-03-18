package org.javaq.chartfaces.render.tool.impl;

import java.util.Map;

import org.javaq.chartfaces.api.IChartComponent;
import org.javaq.chartfaces.api.IChartPart;
import org.javaq.chartfaces.api.IChartingData;
import org.javaq.chartfaces.document.IElement;
import org.javaq.chartfaces.document.impl.AbstractChartDocument;
import org.javaq.chartfaces.render.tool.IChartToolFactory;


public abstract class AbstractComponentTool extends AbstractChartTool {
	private static final class EmptyChartDocument extends AbstractChartDocument {
		private final IElement layoutElement;

		private EmptyChartDocument(IElement layoutElement) {
			this.layoutElement = layoutElement;
		}

		@Override
		public IElement getLayoutElement() {
			return layoutElement;
		}
	}

	protected AbstractComponentTool(final IChartToolFactory factory,
			final IChartPart part, final Map<Object, Object> state) {
		super(factory, part, state);
	}

	@Override
	protected final AbstractChartDocument createDocument() {
		final IElement layoutElement = createLayoutElement();

		// creates the chart document
		return new EmptyChartDocument(layoutElement);
	}

	@Override
	protected final IElement createLayoutElement() {
		final IElement element = ChartToolUtil.createLayoutBox(getBoxModel(), null);

		// fill our template symbols if any
		fillLayoutElement(element);
		return element;
	}

	/**
	 * Fill the layout element with content such as one or more symbol elements.
	 * 
	 * @param element
	 */
	protected abstract void fillLayoutElement(final IElement element);

	protected final IChartComponent getChartComponent() {
		return (IChartComponent) getChartPart();
	}

	protected final Iterable<? extends Number> getDataArguments() {
		// fetch the data values from the component
		final IChartingData chartingData =
				getChartComponent().getChartingData();
		final Iterable<? extends Number> valuesIterable =
				chartingData.getIndependentValues();
		return valuesIterable;
	}

	protected final Iterable<? extends Number> getDataValues() {
		// fetch the data values from the component
		final IChartingData chartingData =
				getChartComponent().getChartingData();
		final Iterable<? extends Number> valuesIterable = chartingData
				.getDependentValues();
		return valuesIterable;
	}

	@Override
	protected final ChartDocumentType getDocumentType() {
		return ChartDocumentType.component;
	}
}
