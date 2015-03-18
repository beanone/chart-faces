package org.javaq.chartfaces.iterable;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * Utility methods that makes generic access to array like data values.
 *
 * @author Hongyan Li
 *
 */
public final class IterableUtility {

	private IterableUtility() {}

	private static final class NumberToDoubleIterable implements
			ISizeAwareIterable<Double> {
		private final Iterable<? extends Number> original;

		private NumberToDoubleIterable(Iterable<? extends Number> original) {
			this.original = original;
		}

		@Override
		public Iterator<Double> iterator() {
			final Iterator<? extends Number> iter = original.iterator();
			return new Iterator<Double>() {
				@Override
				public boolean hasNext() {
					return iter.hasNext();
				}

				@Override
				public Double next() {
					final Number num = iter.next();
					return num == null ? null : num.doubleValue();
				}

				@Override
				public void remove() {
					throw new UnsupportedOperationException();
				}
			};
		}

		@Override
		public int size() {
			return IterableUtility.getSize(original);
		}
	}

	private static Logger logger =
			Logger.getLogger(IterableUtility.class.getName());

	@SuppressWarnings("rawtypes")
	public static int getSize(final Object values) {
		if (values.getClass().isArray()) {
			return Array.getLength(values);
		}
		if (values instanceof ISizeAwareIterable) {
			return ((ISizeAwareIterable) values).size();
		}

		final Class<?> clazz = values.getClass();
		Integer length = IterableUtility.getSize(values, clazz, "size");
		length = (length < 0) ? IterableUtility.getSize(values, clazz,
				"getSize")
					: length;
		length = (length < 0) ? IterableUtility.getSize(values, clazz,
				"getLength")
					: length;
		// we don't care what exception it is.
		if (length < 0 && values instanceof Iterable) {
			length = 0;
			Iterator<?> valueIter = ((Iterable<?>) values).iterator();
			while (valueIter.hasNext()) {
				length++;
				valueIter.next();
			}
		}

		return length;
	}

	// try to convert the passed in values object to an Iterable
	// TODO: in addition to the ArrayIterable, make more Iterable wrapper for
	// other type of vector like object such as ListDataModel and provide
	// generic access to the size-like attribute.
	@SuppressWarnings("unchecked")
	public static <T> Iterable<T> toIterable(final Object values) {
		// Note: Can't iterate over primitive values
		Iterable<T> iterable = null;
		if (values != null) {
			if (values instanceof Iterable) {
				iterable = (Iterable<T>) values;
			} else if (values.getClass().isArray()) {
				iterable = new ArrayIterable(values);
			}
		}

		return iterable;
	}

	public static Iterable<Integer> toIterableIndices(final Object values) {
		if (values == null) {
			return null;
		}
		final int size = IterableUtility.getSize(values);
		return new IndexIterable(size);
	}

	public static ISizeAwareIterable<Double> toIterableOfDoubleValues(
			final Iterable<? extends Number> original) {
		return original == null ? null : new NumberToDoubleIterable(original);
	}

	public static ISizeAwareIterable<Double> toIterableOfDoubleValues(
			final Object values) {
		final Iterable<? extends Number> original = IterableUtility
				.toIterable(values);
		return IterableUtility.toIterableOfDoubleValues(original);
	}

	@SuppressWarnings("rawtypes")
	public static List<Iterable> toIterablesLevel2(final Object values) {
		final Iterable levelOne = IterableUtility.toIterable(values);
		final List<Iterable> returns = new ArrayList<Iterable>();
		if (levelOne != null) {
			Iterable levelTwo;
			for (final Object value : levelOne) {
				levelTwo = IterableUtility.toIterable(value);
				if (levelTwo == null) {
					returns.clear();
					returns.add(levelOne);
					break;
				}
				returns.add(levelTwo);
			}
		}
		return returns;
	}

	private static Integer getSize(final Object values, final Class<?> clazz,
			final String methodName) {
		Integer returns = -999;
		try {
			final Method m = clazz
					.getDeclaredMethod(methodName, (Class[]) null);
			final Object lenObj = m.invoke(values, (Object[]) null);
			if (lenObj != null) {
				returns = (Integer) lenObj;
			}
		} catch (final NoSuchMethodException e) {
			IterableUtility.logger.debug("Tried to call the size() method on "
					+ values.getClass().getName()
					+ " but failed with Exception.", e);
		} catch (final IllegalAccessException e) {
			IterableUtility.logger.debug("Tried to call the size() method on "
					+ values.getClass().getName()
					+ " but failed with Exception.", e);
			returns = -2;
		} catch (final InvocationTargetException e) {
			IterableUtility.logger.debug("Tried to call the size() method on "
					+ values.getClass().getName()
					+ " but failed with Exception.", e);
			returns = -1;
		}
		return returns;
	}
}
