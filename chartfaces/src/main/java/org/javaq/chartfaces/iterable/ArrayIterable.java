/**
 *
 */
package org.javaq.chartfaces.iterable;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * An {@link Iterable} implementation for array of all types of java objects and
 * primitives.
 * 
 * @author Hongyan Li
 * 
 */
@SuppressWarnings("rawtypes")
public class ArrayIterable implements ISizeAwareIterable {
	private static class ArrayIterator implements Iterator {
		private final Object array;
		private int index = 0;
		private final int size;

		public ArrayIterator(final Object array, final int len) {
			this.array = array;
			this.size = len;
		}

		@Override
		public boolean hasNext() {
			return this.index < this.size;
		}

		@Override
		public Object next() {
			if (hasNext()) {
				return Array.get(this.array, this.index++);
			}
			throw new NoSuchElementException();
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	private final Object array;
	private final int size;

	/**
	 * Construct a new instance.
	 * 
	 * @param array
	 *            an array of Objects or primitive values. Cannot be null.
	 * @throws NullPointerException
	 *             if the passed in is null.
	 */
	public ArrayIterable(final Object array) {
		if (!array.getClass().isArray()) {
			throw new IllegalArgumentException("The passed in is not an array!");
		}
		this.array = array;
		this.size = Array.getLength(array);
	}

	public Object getFirst() {
		return this.size > 0 ? Array.get(this.array, 0) : null;
	}

	public Object getLast() {
		return this.size > 0 ? Array.get(this.array, this.size - 1) : null;
	}

	/**
	 * Sort the array in this. Alter the contained array if inPlace is true,
	 * otherwise, the array will be copied the only the copy is sorted.
	 * 
	 * @param inPlace
	 * @return an sorted Iteratorable . If inPlace is true, this will return .
	 *         itself.
	 */
	public ArrayIterable getSorted(final boolean inPlace) {
		return inPlace? inPlaceSort() : sort();
	}
	
	private ArrayIterable sort() {
		Object sorted = null;
		if (this.array instanceof int[]) {
			sorted = Arrays.copyOf((int[]) this.array, this.size);
			Arrays.sort((int[])sorted);
		} else if (this.array instanceof double[]) {
			sorted = Arrays.copyOf((double[]) this.array, this.size);
			Arrays.sort((double[])sorted);
		} else if (this.array instanceof short[]) {
			sorted = Arrays.copyOf((short[]) this.array, this.size);
			Arrays.sort((short[])sorted);
		} else if (this.array instanceof long[]) {
			sorted = Arrays.copyOf((long[]) this.array, this.size);
			Arrays.sort((long[])sorted);
		} else if (this.array instanceof float[]) {
			sorted = Arrays.copyOf((float[]) this.array, this.size);
			Arrays.sort((float[])sorted);
		} else if (this.array instanceof char[]) {
			sorted = Arrays.copyOf((char[]) this.array, this.size);
			Arrays.sort((char[])sorted);
		} else if (this.array instanceof byte[]) {
			sorted = Arrays.copyOf((byte[]) this.array, this.size);
			Arrays.sort((byte[])sorted);
		} else if (this.array instanceof boolean[]) {
			throw new UnsupportedOperationException();
		} else {
			Object[] oSorted = Arrays.copyOf((Object[]) this.array, this.size);
			Arrays.sort(oSorted);
			sorted = oSorted;
		}
		return new ArrayIterable(sorted);
	}
	
	private ArrayIterable inPlaceSort() {
		Object sorted = this.array;
		if (this.array instanceof int[]) {
			Arrays.sort((int[]) sorted);
		} else if (this.array instanceof double[]) {
			Arrays.sort((double[]) sorted);
		} else if (this.array instanceof short[]) {
			Arrays.sort((short[]) sorted);
		} else if (this.array instanceof long[]) {
			Arrays.sort((long[]) sorted);
		} else if (this.array instanceof float[]) {
			Arrays.sort((float[]) sorted);
		} else if (this.array instanceof char[]) {
			Arrays.sort((char[]) sorted);
		} else if (this.array instanceof byte[]) {
			Arrays.sort((byte[]) sorted);
		} else if (this.array instanceof boolean[]) {
			throw new UnsupportedOperationException();
		} else {
			Object[] oSorted = (Object[]) this.array;
			Arrays.sort(oSorted);
		}
		return this;
	}

	@Override
	public Iterator<?> iterator() {
		return new ArrayIterator(this.array, this.size);
	}

	@Override
	public int size() {
		return this.size;
	}
}