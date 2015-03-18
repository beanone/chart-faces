package org.javaq.chartfaces.converter;

import java.util.Arrays;
import java.util.Iterator;

import org.javaq.chartfaces.iterable.IIterableConverter;
import org.javaq.chartfaces.iterable.ISizeAwareIterable;
import org.javaq.chartfaces.iterable.IterableUtility;


/**
 * Converts the original data to String values.
 * 
 * @author Hongyan Li
 * 
 */
public class ToStringIterableDataConverter implements
		IIterableConverter<Iterable<?>[], String, ISizeAwareIterable<String>> {

	private static final class SizeAwareIteratorArrayCrossSectionIterator implements
			ISizeAwareIterable<String> {
		private final Iterable<?>[] targets;

		private SizeAwareIteratorArrayCrossSectionIterator(Iterable<?>[] targets) {
			this.targets = Arrays.copyOf(targets, targets.length);
		}

		@Override
		public Iterator<String> iterator() {
			Iterator<String> returns = null;
			if (targets != null) {
				final Iterator<?>[] iters = new Iterator[targets.length];
				for (int i = 0; i < targets.length; i++) {
					iters[i] = targets[i].iterator();
				}
				returns = new IteratorArrayCrossSectionIterator(iters);
			}
			return returns;
		}

		@Override
		public int size() {
			return (targets == null || targets.length == 0) ? 0
					: IterableUtility.getSize(targets[0]);
		}
	}

	private static final class IteratorArrayCrossSectionIterator implements Iterator<String> {
		private final Iterator<?>[] delegates;

		/**
		 * Iterates an array of {@link Iterator} in parallel and during each iteration, 
		 * concatenates elements of the peer Iterators as a comma separated String.
		 * 
		 * @param iteratorArray an array of Iterators.
		 */
		private IteratorArrayCrossSectionIterator(final Iterator<?>[] iteratorArray) {
			this.delegates = Arrays.copyOf(iteratorArray, iteratorArray.length);
		}

		@Override
		public boolean hasNext() {
			return this.delegates.length > 0 && this.delegates[0].hasNext();
		}

		@Override
		public String next() {
			final StringBuilder sb = new StringBuilder();
			for (final Iterator<?> iter : this.delegates) {
				sb.append(iter.next()).append(", ");
			}
			if (sb.length() > 0) {
				sb.delete(sb.length() - 2, sb.length() - 1);
			}
			return "(" + sb.toString() + ")";
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	@Override
	public ISizeAwareIterable<String> convert(final Iterable<?>[] targets) {
		return new SizeAwareIteratorArrayCrossSectionIterator(targets);
	}
}