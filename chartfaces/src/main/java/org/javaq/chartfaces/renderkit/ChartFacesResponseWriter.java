package org.javaq.chartfaces.renderkit;

import java.io.IOException;

import javax.faces.context.ResponseWriter;
import javax.faces.context.ResponseWriterWrapper;

import org.javaq.chartfaces.constants.Constants;


/**
 * ChartFaces custom JSF2 ResponseWriter.
 * 
 * @author Hongyan Li
 * 
 */
public class ChartFacesResponseWriter extends ResponseWriterWrapper {
	private final ResponseWriter delegate;

	public ChartFacesResponseWriter(final ResponseWriter delegate) {
		this.delegate = delegate;
	}

	@Override
	public String getContentType() {
		// TODO: This is temporary. we don't want to support only this content
		// type. We must be a good citizen and work with other renderkits well.
		return "application/xhtml+xml";
	}

	@Override
	public ResponseWriter getWrapped() {
		return this.delegate;
	}

	/**
	 * @param text
	 * @param property
	 * @throws IOException
	 * @see javax.faces.context.ResponseWriter#writeText(java.lang.Object,
	 *      java.lang.String)
	 */
	@Override
	public void writeText(final Object text, final String property) throws IOException {
		if (Constants.RENDER_SCRIPT_TEXT.equals(property)) {
			final boolean renderCData = isRenderCData();
			if (renderCData) {
				this.delegate.writeText("<![CDATA[\n", null);
			}
			this.delegate.writeText(text, null);
			if (renderCData) {
				this.delegate.writeText("\n]]>", null);
			}
		} else {
			this.delegate.writeText(text, property);
		}
	}

	private boolean isRenderCData() {
		return true;
	}

}
