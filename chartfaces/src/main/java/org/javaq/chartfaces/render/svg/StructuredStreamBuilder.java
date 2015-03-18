package org.javaq.chartfaces.render.svg;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class StructuredStreamBuilder extends StructuredStringBuilder {
	private final OutputStream out;

	public StructuredStreamBuilder(final OutputStream out) {
		this.out = new BufferedOutputStream(out);
	}

	/**
	 * Append the passed in.
	 * 
	 * @param target
	 *            the to be appended. Cannot be null.
	 * @return this for chaining of calls.
	 * @throws NullPointerException
	 *             if the passed in is null.
	 */
	@Override
	public StructuredStringBuilder append(final Object target) {

		try {
			this.out.write(target.toString().getBytes("UTF-8"));
		} catch (final IOException ioe) {
			throw new ChartfacesStreamError(ioe);
		}
		return this;
	}
}
