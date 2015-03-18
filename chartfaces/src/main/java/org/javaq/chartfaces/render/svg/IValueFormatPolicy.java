package org.javaq.chartfaces.render.svg;

/**
 * Holds policy on how values should be formatted.
 * 
 * @author Hongyan Li
 * 
 */
public interface IValueFormatPolicy {
	Object format(Object key, Object value);
}
