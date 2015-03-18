package org.javaq.chartfaces.render.svg;


/**
 * Factory for {@link IStructuredStringBuilder}.
 * 
 * @author Hongyan Li
 * 
 */
public interface IStructuredStringBuilderFactory {
	/**
	 * @return a new instanceo of an Object that implements IStringBuilder.
	 */
	IStructuredStringBuilder newBuilder();
}
