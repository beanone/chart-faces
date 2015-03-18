package org.javaq.chartfaces.api;

import java.util.Map;





/**
 * Specification about a chart part.
 * 
 * @author Hongyan Li
 * 
 */
public interface IPartSpec {
	/**
	 * @return additional properties about the view
	 */
	Map<String, String> getProperties();

	/**
	 * @return the viewBox
	 */
	Box getViewBox();

	/**
	 * @param viewBox
	 *            the viewBox to set
	 */
	void setViewBox(final Box viewBox);

}