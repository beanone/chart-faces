package org.javaq.chartfaces.render.exporter;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.batik.transcoder.Transcoder;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.log4j.Logger;
import org.javaq.chartfaces.constants.EnumSVG;
import org.javaq.chartfaces.document.IChartDocument;
import org.javaq.chartfaces.document.IDataElement;
import org.javaq.chartfaces.render.svg.IStructuredStringBuilder;
import org.javaq.chartfaces.render.svg.IStructuredStringBuilderFactory;
import org.javaq.chartfaces.render.svg.RenderUtils;
import org.javaq.chartfaces.render.svg.StructuredStringBuilder;
import org.javaq.chartfaces.util.NumberRoundingStringBuilder;


public final class ChartDocumentUtil {
	private static int count;
	private static Object initLock = new Object();
	private static Object lock = new Object();
	private static final Logger LOGGER =
			Logger.getLogger(ChartDocumentUtil.class.getName());
	private static final String TEMP_FOLDER_NAME = "webapps/chartfaces/temp/";

	private static Boolean token;
	
	private ChartDocumentUtil() {}

	private static RenderUtils utils = new RenderUtils(
			new IStructuredStringBuilderFactory() {
				@Override
				public IStructuredStringBuilder newBuilder() {
					return new StructuredStringBuilder(
							new NumberRoundingStringBuilder());
				}
			});

	public static String saveAsXML(final IChartDocument chartDoc,
			final String contentType, final Transcoder transcoder)
			throws IOException {
		ChartDocumentUtil.checkInit();
		// InputStream stream = new BufferedInputStream(toXMLStream(chartDoc));
		final InputStream stream = new BufferedInputStream(ChartDocumentUtil.toInputStream(chartDoc));

		// Create the transcoder input.
		final TranscoderInput input = new TranscoderInput(stream);

		File tempFile = null;
		while (true) {
			tempFile = new File(
					ChartDocumentUtil.TEMP_FOLDER_NAME + "image" + ChartDocumentUtil.nextNumber() + ".svg");
			if (!tempFile.exists() && tempFile.createNewFile()) {
				tempFile.deleteOnExit();
				break;
			}
		}

		// Create the transcoder output.
		final OutputStream ostream = new FileOutputStream(tempFile);
		final TranscoderOutput output = new TranscoderOutput(ostream);

		try {
			// Save the image.
			transcoder.transcode(input, output);
		} catch (final TranscoderException e) {
			ChartDocumentUtil.LOGGER.error(e.getMessage(), e);
			throw new IOException(e);
		}

		// Flush and close the stream.
		ostream.flush();
		ostream.close();

		return tempFile.getPath();
	}

	public static InputStream toInputStream(final IChartDocument chartDoc)
			throws UnsupportedEncodingException {
		final String xml = ChartDocumentUtil.toXML(chartDoc);
		return new ByteArrayInputStream(xml.getBytes("UTF-8"));
	}

	public static String toXML(final IChartDocument chartDoc) {
		final List<IDataElement> dataElementList = chartDoc.getDataElementList();
		for (final IDataElement t : dataElementList) {
			t.setNameSpacePrefix(EnumSVG.getNamespacePrefix());
			t.getContainerElement().addChildren(t);
		}

		final String xml = ChartDocumentUtil.getRenderUtils().toXML(
				chartDoc.getLayoutElement()).toString();
		return xml;
	}

	public static InputStream toXMLStream(final IChartDocument chartDoc)
			throws IOException {
		final List<IDataElement> dataElementList = chartDoc.getDataElementList();
		for (final IDataElement t : dataElementList) {
			t.setNameSpacePrefix(EnumSVG.getNamespacePrefix());
			t.getContainerElement().addChildren(t);
		}

		return ChartDocumentUtil.getRenderUtils().toXMLStream(chartDoc.getLayoutElement());
	}
	private static boolean checkInit() {
		if (ChartDocumentUtil.token == null) {
			synchronized (ChartDocumentUtil.initLock) {
				final File directory = new File(ChartDocumentUtil.TEMP_FOLDER_NAME);
				if (directory.exists()) {
					ChartDocumentUtil.token = Boolean.TRUE;
				} else {
					if (!directory.mkdirs()) {
						ChartDocumentUtil.LOGGER.warn("Failed to create folder "
								+ ChartDocumentUtil.TEMP_FOLDER_NAME
								+ ". Any request to generate images will fail!");
						ChartDocumentUtil.token = Boolean.FALSE;
					} else {
						ChartDocumentUtil.token = Boolean.TRUE;
					}
				}
			}
		}
		return ChartDocumentUtil.token;
	}

	private static RenderUtils getRenderUtils() {
		return ChartDocumentUtil.utils;
	}

	private static int nextNumber() {
		int returns;
		synchronized (ChartDocumentUtil.lock) {
			returns = ChartDocumentUtil.count++;
		}
		return returns;
	}
}
