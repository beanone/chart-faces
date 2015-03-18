package org.javaq.chartfaces.api;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.javaq.chartfaces.constants.EnumOutputType;
import org.javaq.chartfaces.document.IChartDocument;

/**
 * An abstraction of handles that renders a JSF component into a specific type
 * of output format.
 * 
 * @author Hongyan Li
 * 
 */
public interface IExporter {
	/**
	 * Exports a {@link UIComponent} into the format specified by
	 * {@link #getExportType()}.
	 * 
	 * @param context
	 *            a {@link FacesContext}.
	 * @param component
	 *            a {@link UIComponent}.
	 * @param chartDoc
	 *            the {@link IChartDocument} generated from the
	 *            <code>component</code>.
	 * @throws IOException
	 *             if an error occurred during the operation.
	 */
	void export(FacesContext context, UIComponent component,
			IChartDocument chartDoc) throws IOException;

	/**
	 * @return the type of output format.
	 */
	EnumOutputType getExportType();
}
