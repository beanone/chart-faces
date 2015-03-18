package org.javaq.chartfaces.iterable;

/**
 * Implement this interface to convert any object into an iterable of specified
 * type.
 * 
 * @author Hongyan Li
 * 
 * @param <TObject>
 *            the type of object that the data values is read from.
 * @param <TData>
 *            the type data values that this is going to work with.
 * @param <TReturn>
 *            an {@link Iterable} of <TData> that <TObject> will be converted
 *            into.
 */
public interface IIterableConverter<TObject, TData, TReturn extends Iterable<TData>> {
	/**
	 * @return convert the passed in value of type <TObject> into that of type
	 *         <TReturn>.
	 */
	TReturn convert(TObject target);
}
