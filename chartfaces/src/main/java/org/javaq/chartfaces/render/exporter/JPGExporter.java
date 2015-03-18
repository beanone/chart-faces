package org.javaq.chartfaces.render.exporter;

import org.apache.batik.transcoder.Transcoder;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.javaq.chartfaces.constants.EnumOutputType;


public class JPGExporter extends AbstractImageExporter {
	@Override
	public EnumOutputType getExportType() {
		return EnumOutputType.jpg;
	}

	@Override
	protected Transcoder createTranscoder() {
		final Transcoder t = new JPEGTranscoder();
		t.addTranscodingHint(JPEGTranscoder.KEY_QUALITY, new Float(.8));
		return t;
	}
}
