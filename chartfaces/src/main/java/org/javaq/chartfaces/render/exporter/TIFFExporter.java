package org.javaq.chartfaces.render.exporter;

import org.apache.batik.transcoder.Transcoder;
import org.apache.batik.transcoder.image.TIFFTranscoder;
import org.javaq.chartfaces.constants.EnumOutputType;


public class TIFFExporter extends AbstractImageExporter {
	@Override
	public EnumOutputType getExportType() {
		return EnumOutputType.tiff;
	}

	@Override
	protected Transcoder createTranscoder() {
		return new TIFFTranscoder();
	}
}
