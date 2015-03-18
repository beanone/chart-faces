package org.javaq.chartfaces.render.exporter;

import java.util.HashMap;
import java.util.Map;

import org.javaq.chartfaces.api.IExporter;
import org.javaq.chartfaces.api.IExporterFactory;
import org.javaq.chartfaces.constants.EnumOutputType;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component("exporterFactory")
@Scope("singleton")
public class ExporterFactory implements IExporterFactory {
	private final IExporter defaultExporter;
	private final Map<String, IExporter> exporters = new HashMap<String, IExporter>();

	public ExporterFactory() {
		this.defaultExporter = new SVGExporter();
		this.exporters.put(EnumOutputType.svg.toString(), this.defaultExporter);
		this.exporters.put(EnumOutputType.puresvg.toString(), new PureSVGExporter());
		this.exporters.put(EnumOutputType.jpg.toString(), new JPGExporter());
		this.exporters.put(EnumOutputType.png.toString(), new PNGExporter());
		this.exporters.put(EnumOutputType.tiff.toString(), new TIFFExporter());
	}

	@Override
	public IExporter getExporter(final String format) {
		final IExporter exporter = this.exporters.get(format);
		return exporter == null ? getDefaultExporter() : exporter;
	}

	private IExporter getDefaultExporter() {
		return this.defaultExporter;
	}
}
