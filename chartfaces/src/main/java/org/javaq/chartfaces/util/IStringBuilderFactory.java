package org.javaq.chartfaces.util;

/**
 * Factory for {@link IStringBuilder}.
 * 
 * @author Hongyan Li
 * 
 */
public interface IStringBuilderFactory {
	/**
	 * @return a new instanceo of an Object that implements
	 *         {@link IStringBuilder}.
	 */
	IStringBuilder newBuilder();
}
