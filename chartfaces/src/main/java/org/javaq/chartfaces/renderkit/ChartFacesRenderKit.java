package org.javaq.chartfaces.renderkit;

import java.io.Writer;

import javax.faces.context.ResponseWriter;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitWrapper;

/**
 * ChartFaces custom JSF2 renderkit.
 * 
 * @author Hongyan Li
 * 
 */
public class ChartFacesRenderKit extends RenderKitWrapper {
	private final RenderKit delegate;

	public ChartFacesRenderKit(final RenderKit delegate) {
		this.delegate = delegate;
	}

	@Override
	public ResponseWriter createResponseWriter(final Writer writer,
			final String contentTypeList, final String encoding) {
		final ResponseWriter rw = this.delegate.createResponseWriter(writer,
				contentTypeList, encoding);
		return new ChartFacesResponseWriter(rw);
	}

	@Override
	public RenderKit getWrapped() {
		return this.delegate;
	}
}
