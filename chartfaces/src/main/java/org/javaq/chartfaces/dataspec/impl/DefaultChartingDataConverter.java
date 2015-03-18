package org.javaq.chartfaces.dataspec.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.javaq.chartfaces.api.IChartingDataConverter;
import org.javaq.chartfaces.api.IChartingDataXYView;
import org.javaq.chartfaces.component.impl.ChartingDataXYView;
import org.javaq.chartfaces.iterable.IterableUtility;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component("chartingDataConverter")
@Scope("singleton")
public class DefaultChartingDataConverter implements IChartingDataConverter {

	@Override
	public IChartingDataXYView toChartingDateXYView(final Object value) {
		if (value == null) {
			return null;
		}

		final Iterable<Double> xIterable, yIterable;
		if (value instanceof IChartingDataXYView) {
			return (IChartingDataXYView) value;
		} else if (value instanceof Map) {
			@SuppressWarnings("unchecked")
			final Map<Double, Double> map = (Map<Double, Double>) value;
			// we want to make sure that xvalues and yvalues are in the same
			// order.
			List<Double> xValues, yValues;
			yValues = new ArrayList<Double>();
			xValues = new ArrayList<Double>(map.keySet());
			for (final Object key : xValues) {
				yValues.add(map.get(key));
			}

			xIterable = xValues;
			yIterable = yValues;
		} else if (value instanceof Iterable) {
			final Iterable<Double>[] iterables = getIterablePair((Iterable<?>) value);
			xIterable = iterables[0];
			yIterable = iterables[1];
		} else {
			final Iterable<Double>[] iterables = getIterablePair1(value);
			if (iterables == null) {
				throw new IllegalArgumentException(
						"Unsupported data structure!");
			} else {
				xIterable = iterables[0];
				yIterable = iterables[1];
			}
		}

		return new ChartingDataXYView(xIterable, yIterable);
	}

	private Iterable<Double>[] getIterablePair(final Iterable<?> valueIterable) {
		final Iterator<?> iter = valueIterable.iterator();
		@SuppressWarnings("unchecked")
		final Iterable<Double>[] iterables = new Iterable[2];
		Iterable<Double> iterable1;
		if (iter.hasNext()) {
			iterable1 = IterableUtility.toIterableOfDoubleValues(iter.next());
			if (iter.hasNext()) {
				iterables[0] = iterable1;
				iterables[1] = IterableUtility.toIterableOfDoubleValues(iter
						.next());
			} else {
				iterables[0] = null;
				iterables[1] = iterable1;
			}
		} else {
			iterables[0] = new ArrayList<Double>();
			iterables[1] = new ArrayList<Double>();
		}
		return iterables;
	}

	@SuppressWarnings("unchecked")
	private Iterable<Double>[] getIterablePair1(final Object value) {
		Iterable<Double>[] returns = null;
		if (value.getClass().isArray()) {
			if (value instanceof Object[]) {
				final Object[] valueArray = (Object[]) value;
				returns = new Iterable[2];
				if (valueArray[0] == null) {
					returns[1] = IterableUtility
							.toIterableOfDoubleValues(valueArray);
					returns[0] = null;
				} else if (valueArray[0].getClass().isArray()) {
					returns[0] = IterableUtility
							.toIterableOfDoubleValues(valueArray[0]);
					returns[1] = IterableUtility
							.toIterableOfDoubleValues(valueArray[1]);
				} else if (valueArray[0] instanceof Iterable) {
					returns[0] = IterableUtility
							.toIterableOfDoubleValues(valueArray[0]);
					returns[1] = IterableUtility
							.toIterableOfDoubleValues(valueArray[1]);
					// if array more than 2-D, throw away the rest
				} else {
					returns[0] = null;
					returns[1] = IterableUtility
							.toIterableOfDoubleValues(valueArray);
				}
			} else {
				returns = new Iterable[2];
				returns[0] = null;
				returns[1] = IterableUtility.toIterableOfDoubleValues(value);
			}
		}

		return returns;
	}
}
