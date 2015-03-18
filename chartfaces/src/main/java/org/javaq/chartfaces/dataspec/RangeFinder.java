package org.javaq.chartfaces.dataspec;

import org.javaq.chartfaces.iterable.ArrayIterable;
import org.javaq.chartfaces.iterable.SortedIterable;
import org.javaq.chartfaces.util.NumberRange;

/**
 * Utility class that helps to find the range of data in a iterable object.
 * 
 * @author Hongyan Li
 * 
 */
public final class RangeFinder {
	
	private RangeFinder() {}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static NumberRange findRange(final Object values) {
		final NumberRange nr = new NumberRange();
		if (values == null) {
			throw new IllegalArgumentException("The passed in values object is null.");
		}
		if (values instanceof Iterable) {
			final SortedIterable iterable = new SortedIterable((Iterable) values);
			RangeFinder.mergeToRange(nr, iterable.getFirst());
			if (iterable.getSize() > 1) {
				RangeFinder.mergeToRange(nr, iterable.getLast());
			}
		} else if (values.getClass().isArray()) {
			final ArrayIterable iterable = new ArrayIterable(values).getSorted(false);
			RangeFinder.mergeToRange(nr, iterable.getFirst());
			if (iterable.size() > 1) {
				RangeFinder.mergeToRange(nr, iterable.getLast());
			}
		}

		return nr;
	}

	private static void mergeToRange(final NumberRange nr, final Object bound) {
		final Double d = RangeFinder.toDouble(bound);
		if (d != null) {
			nr.mergeBound(d);
		}
	}

	private static Double toDouble(final Object val) {
		Double d = null;
		if (val != null) {
			if (val instanceof Number) {
				d = ((Number) val).doubleValue();
			} else {
				d = Double.valueOf(val.toString());
			}
		}
		return d;
	}
}
