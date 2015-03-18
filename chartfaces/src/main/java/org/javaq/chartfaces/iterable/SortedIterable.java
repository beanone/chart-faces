package org.javaq.chartfaces.iterable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * A wrapper of Iterable<T> that takes care of sorting.
 * 
 * @author Hongyan Li
 * 
 * @param <T>
 */
public class SortedIterable<T extends Comparable<? super T>> implements
		Iterable<T> {
	private final List<T> delegate;

	public SortedIterable(final Iterable<T> it) {
		if (it instanceof Collection<?>) {
			this.delegate = new ArrayList<T>((Collection<T>) it);
		} else {
			this.delegate = new ArrayList<T>();
			for (final T v : it) {
				this.delegate.add(v);
			}
		}

		Collections.sort(this.delegate);
	}

	public Comparable<? super T> getFirst() {
		return this.delegate.size() > 0 ? this.delegate.get(0) : null;
	}

	public Comparable<? super T> getLast() {
		return this.delegate.size() > 0 ? this.delegate.get(this.delegate
				.size() - 1) : null;
	}

	public int getSize() {
		return this.delegate.size();
	}

	@Override
	public Iterator<T> iterator() {
		return this.delegate.iterator();
	}
}
