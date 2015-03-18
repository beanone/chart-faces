package org.javaq.chartfaces.render.exporter;

import org.apache.batik.transcoder.Transcoder;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.javaq.chartfaces.constants.EnumOutputType;


public class PNGExporter extends AbstractImageExporter {
	@Override
	public EnumOutputType getExportType() {
		return EnumOutputType.png;
	}

	@Override
	protected Transcoder createTranscoder() {
		return new PNGTranscoder();
	}
}
