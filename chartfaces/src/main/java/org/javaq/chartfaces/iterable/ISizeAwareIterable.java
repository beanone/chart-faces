package org.javaq.chartfaces.iterable;

/**
 * Abstraction of an {@link Iterable} that knows its size.
 * 
 * @author Hongyan Li
 * 
 * @param <T>
 */
public interface ISizeAwareIterable<T> extends Iterable<T> {
	/**
	 * Note: the size of this does NOT have to be the exact size of the
	 * iterated, although it usually should is the case. This is because that
	 * one of the purposes of an {@link Iterable} is that it can handle allows
	 * one to represent a data source that has an unlimited size, such as the
	 * case when it represents stream data.
	 * 
	 * @return the size of this iterable.
	 */
	int size();
}
