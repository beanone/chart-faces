package org.javaq.chartfaces.converter;

import java.util.Iterator;

import org.javaq.chartfaces.iterable.IIterableConverter;
import org.javaq.chartfaces.iterable.ISizeAwareIterable;
import org.javaq.chartfaces.iterable.IterableUtility;


/**
 * Converts Iterable of one data type to that of another.
 * 
 * @author Hongyan Li
 * 
 */
public abstract class AbstractIterableToIterable<TIn, TData> implements
		IIterableConverter<Iterable<TIn>, TData, ISizeAwareIterable<TData>> {

	private final class DataTransformIterator implements Iterator<TData> {
		private final Iterator<TIn> delegate;

		private DataTransformIterator(final Iterator<TIn> delegate) {
			this.delegate = delegate;
		}

		@Override
		public boolean hasNext() {
			return this.delegate.hasNext();
		}

		@Override
		public TData next() {
			return transform(this.delegate.next());
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	@Override
	public ISizeAwareIterable<TData> convert(final Iterable<TIn> target) {
		return new ISizeAwareIterable<TData>() {

			@Override
			public Iterator<TData> iterator() {
				return target == null ? null : new DataTransformIterator(
						target.iterator());
			}

			@Override
			public int size() {
				return target == null ? 0 : IterableUtility.getSize(target);
			}
		};
	}

	/**
	 * Transforms the passed in data to the returned.
	 * 
	 * @param target
	 * @return an Object if type TData that the passed in is transformed to.
	 */
	protected abstract TData transform(TIn target);
}