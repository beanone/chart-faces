package org.javaq.chartfaces.iterable;

import java.util.Iterator;

public class SizeAwareIterable<T> implements ISizeAwareIterable<T> {
	private final Iterable<T> wrapped;

	public SizeAwareIterable(final Iterable<T> wrapped) {
		if (wrapped == null) {
			throw new IllegalArgumentException();
		}
		this.wrapped = wrapped;

	}

	@Override
	public Iterator<T> iterator() {
		return this.wrapped.iterator();
	}

	@Override
	public int size() {
		return IterableUtility.getSize(this.wrapped);
	}
}
