package org.javaq.chartfaces.iterable;

import java.util.Iterator;

/**
 * A read-only wrapper of the enclosing {@link Iterable}.
 * 
 * @author Hongyan Li
 * 
 * @param <T>
 */
public class ReadonlyIterable<T> implements Iterable<T> {
	private class ReadonlyIterator implements Iterator<T> {
		private final Iterator<T> wrapped;

		ReadonlyIterator(final Iterator<T> iter) {
			this.wrapped = iter;
		}

		@Override
		public boolean hasNext() {
			return this.wrapped.hasNext();
		}

		@Override
		public T next() {
			return this.wrapped.next();
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	private final Iterable<T> wrapped;

	public ReadonlyIterable(final Iterable<T> wrapped) {
		this.wrapped = wrapped;
	}

	@Override
	public Iterator<T> iterator() {
		return new ReadonlyIterator(this.wrapped.iterator());
	}
}
