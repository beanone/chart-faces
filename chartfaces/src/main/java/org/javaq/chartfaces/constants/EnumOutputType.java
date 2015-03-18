package org.javaq.chartfaces.constants;

/**
 * Enumeration of type of chart rendering output format.
 * 
 * @author Hongyan Li
 * @since 1.0
 */
public enum EnumOutputType {
	/**
	 * for jpg output. Content type is image/jpeg and payload type is image.
	 */
	jpg("image/jpeg", "image"),
	/**
	 * for png output. Content type is image/png and payload type is image.
	 */
	png("image/png", "image"),
	/**
	 * for svg output. Content type is image/svg+xml and payload type is svg.
	 */
	puresvg("image/svg+xml", "svg"),
	/**
	 * for svg output. Content type is image/svg and payload type is svg.
	 */
	svg("image/svg+xml", "svg"),
	/**
	 * for tuff output. Content type is image/tiff and payload type is image.
	 */
	tiff("image/tiff", "image");

	private final String contentType;
	private final String payloadType;

	private EnumOutputType(final String contentType, final String payloadType) {
		this.contentType = contentType;
		this.payloadType = payloadType;
	}

	/**
	 * @return the HTML content type of the response payload.
	 */
	public String getContentType() {
		return this.contentType;
	}

	/**
	 * @return the payload type of the response. The client side Javascript will
	 *         use this to determine whether a follow up request is needed to
	 *         fetch an image content.
	 */
	public String getPayloadType() {
		return this.payloadType;
	}
}
