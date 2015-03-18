package org.javaq.chartfaces.iterable;

import java.util.Iterator;


public class IndexIterable implements ISizeAwareIterable<Integer> {
	private static class IndexIterator implements Iterator<Integer> {
		private int index = 0;
		private final int n;

		IndexIterator(final int n) {
			this.n = n;
		}

		@Override
		public boolean hasNext() {
			return this.index < this.n;
		}

		@Override
		public Integer next() {
			return this.index++;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	private final int n;

	IndexIterable(final int n) {
		this.n = n;
	}

	@Override
	public Iterator<Integer> iterator() {
		return new IndexIterator(this.n);
	}

	@Override
	public int size() {
		return this.n;
	}

}
