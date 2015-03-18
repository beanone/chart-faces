package org.javaq.chartfaces.render.tool.impl;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.javaq.chartfaces.api.IChartPart;
import org.javaq.chartfaces.constants.SVGConstants;
import org.javaq.chartfaces.converter.AbstractIterableToIterable;
import org.javaq.chartfaces.converter.ToStringIterableDataConverter;
import org.javaq.chartfaces.document.IDataElement;
import org.javaq.chartfaces.iterable.ISizeAwareIterable;
import org.javaq.chartfaces.iterable.IterableUtility;
import org.javaq.chartfaces.part.component.Scatter3VComponent;
import org.javaq.chartfaces.render.tool.IChartToolFactory;


public class Scatter3VComponentTool extends ScatterComponentTool {
	private static final class VerticalCenterIterable extends
			AbstractIterableToIterable<Double, Double> {
		private final int yAxisLen;
		private final Iterator<Double> sizeIter;

		private VerticalCenterIterable(Iterable<Double> scaledSize, int yAxisLen) {
			this.yAxisLen = yAxisLen;
			sizeIter = scaledSize.iterator();
		}

		@Override
		protected Double transform(final Double value) {
			return yAxisLen - value - this.sizeIter.next() / 2;
		}
	}

	private static final class HorizontalCenterIterable extends
			AbstractIterableToIterable<Double, Double> {
		private final Iterator<Double> sizeIter;

		private HorizontalCenterIterable(Iterable<Double> scaledSize) {
			sizeIter = scaledSize.iterator();
		}

		@Override
		protected Double transform(final Double value) {
			return value - this.sizeIter.next() / 2;
		}
	}

	private static final class ScaleIterable extends
			AbstractIterableToIterable<Double, Double> {
		private final double scale;

		private ScaleIterable(final double scale) {
			this.scale = scale;
		}

		@Override
		protected Double transform(final Double value) {
			return value * scale;
		}
	}

	private static final class BoundedNumberIterator implements Iterator<Double> {
		private int index = 0;
		private final Object value;
		private final int size;
		
		public BoundedNumberIterator(final Object value, final int size) {
			this.value = value;
			this.size = size;
		}

		@Override
		public boolean hasNext() {
			return this.index < size;
		}

		@Override
		public Double next() {
			this.index++;
			return ((Number) value).doubleValue();
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	private static final class BoundedNumberIterable implements
			ISizeAwareIterable<Double> {

		private final Object value;
		private final int size;

		private BoundedNumberIterable(final Object value, final int size) {
			this.value = value;
			this.size = size;
		}

		@Override
		public Iterator<Double> iterator() {
			return new BoundedNumberIterator(this.value, this.size);
		}

		@Override
		public int size() {
			return size;
		}
	}

	Scatter3VComponentTool(final IChartToolFactory factory,
			final IChartPart part, final Map<Object, Object> state) {
		super(factory, part, state);
	}

	private Iterable<Double> toIterable(final Object value, final int size) {
		final Iterable<Double> values;
		if (value instanceof Number) {
			values = new BoundedNumberIterable(value, size);
		} else {
			values = IterableUtility.toIterableOfDoubleValues(value);
		}

		return values;
	}

	@Override
	protected void fillSymbolProperties(final IDataElement use) {
		// TODO: take advantage of size list next
		final Object sizeObject = getSymbolSize();
		final Scatter3VComponent scatter3v = (Scatter3VComponent) getChartComponent();
		final double scale = scatter3v.getSymbolScale();
		// every dot has different size
		use.addProperty(SVGConstants.SVG_XLINK_HREF, "#" + getSymbolId());

		final ISizeAwareIterable<Double> xvalues = getXValues();
		final ISizeAwareIterable<Double> yvalues = getYValues();

		if (sizeObject instanceof Number) {
			final String sizeDouble = ((Number) sizeObject).toString();
			use.addProperty(SVGConstants.SVG_WIDTH, sizeDouble);
			use.addProperty(SVGConstants.SVG_HEIGHT, sizeDouble);
		}

		// first scale the size
		final Iterable<Double> sizeIterable =
					toIterable(sizeObject, xvalues.size());
		final Iterable<Double> scaledSize = new ScaleIterable(scale).convert(sizeIterable);

		// now shift the x coordinates to center of the dot
		Iterable<Double> shifted = new HorizontalCenterIterable(scaledSize).convert(xvalues);
		use.addCustomProperty(SVGConstants.SVG_X, shifted);

		// and shift the y coordinates center of the dot
		final int yAxisLen = getViewBox().getHeight();
		shifted = new VerticalCenterIterable(scaledSize, yAxisLen).convert(yvalues);
		use.addCustomProperty(SVGConstants.SVG_Y, shifted);

		if (!(sizeObject instanceof Number)) {
			use.addCustomProperty(SVGConstants.SVG_WIDTH, scaledSize);
			use.addCustomProperty(SVGConstants.SVG_HEIGHT, scaledSize);
		}
		if (getChartComponent().isShowTooltips()) {
			final ISizeAwareIterable<String> coordinatePairs = new ToStringIterableDataConverter()
					.convert(new Iterable[] { getDataArguments(),
							getDataValues(), sizeIterable });
			use.addCustomProperty(SVGConstants.SVG_TITLE, coordinatePairs);
		}

		// add properties in the propertyMap
		final Map<String, Object> propertyMap = scatter3v.getPropertyMap();
		if (propertyMap != null) {
			for (final Entry<String, Object> entry : propertyMap.entrySet()) {
				use.addCustomProperty(entry.getKey(),
						IterableUtility.toIterable(entry.getValue()));
			}
		}
	}
}