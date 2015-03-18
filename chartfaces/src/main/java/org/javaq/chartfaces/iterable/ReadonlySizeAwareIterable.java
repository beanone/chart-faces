package org.javaq.chartfaces.iterable;

import java.util.Iterator;



/**
 * A read-only version of the {@link SizeAwareIterable} which besides been
 * read-only, it also caches the size and thus is more efficient.
 * 
 * @author Hongyan Li
 * 
 * @param <T>
 */
public class ReadonlySizeAwareIterable<T> implements ISizeAwareIterable<T> {
	private final int size;
	private final Iterable<T> wrapped;

	public ReadonlySizeAwareIterable(final Iterable<T> wrapped) {
		if (wrapped == null) {
			throw new IllegalArgumentException();
		}
		this.wrapped = new ReadonlyIterable<T>(wrapped);
		this.size = IterableUtility.getSize(wrapped);
	}

	@Override
	public Iterator<T> iterator() {
		return this.wrapped.iterator();
	}

	@Override
	public int size() {
		return this.size;
	}
}
