package org.javaq.chartfaces.api;

/**
 * An abstraction of factories that create {@link IExporter}.
 * 
 * @author Hongyan Li
 * 
 */
public interface IExporterFactory {
	/**
	 * @param format
	 *            the format of output the {@link IExporter} will generate.
	 * @return an {@link IExporter}.
	 */
	IExporter getExporter(String format);
}
