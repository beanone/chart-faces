package org.javaq.chartfaces.api;

/**
 * An abstraction of an image definition.
 * 
 * @author Hongyan Li
 * 
 */
public interface IImageDefinition {
	/**
	 * @return the image content type.
	 */
	String getContentType();

	/**
	 * @return the path to the image.
	 */
	String getFilePath();
}
