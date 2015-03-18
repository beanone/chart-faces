package org.javaq.chartfaces.viewspec.impl;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import org.javaq.chartfaces.api.Box;
import org.javaq.chartfaces.api.IChartPart;
import org.javaq.chartfaces.api.IPartSpec;
import org.javaq.chartfaces.constants.Constants;
import org.javaq.chartfaces.viewspec.IPartSpecCalculator;


/**
 * The handler for the main UIChart view specification.
 * 
 * @author Hongyan Li
 * 
 */
public class ChartSpecCalculator implements IPartSpecCalculator {
	public ChartSpecCalculator(final SpecHelper specHelper) {
		// no need to do any thing since we don't use specHelper here;
	}

	@Override
	public IPartSpec calculate(final IChartPart part)
			throws IOException {
		final IChartPart chart = part;
		String widthString = chart.getWidth();
		String heightString = chart.getHeight();
		final int width = 1600;

		int w = 0;
		int h = 0;
		if (widthString == null || widthString.trim().length() == 0) {
			widthString = "100%";
		} else {
			w = widthString.endsWith("%") ? 0 : parseInteger(widthString);
		}
		if (heightString == null || heightString.trim().length() == 0) {
			heightString = "100%";
		} else {
			h = heightString.endsWith("%") ? 0 : parseInteger(heightString);
		}

		part.getPartSpec().getProperties().put(Constants.WIDTH, widthString);
		part.getPartSpec().getProperties().put(Constants.HEIGHT, heightString);

		final int height = w * h == 0 ? 1000 : (int) ((1600. / w) * h);
		part.getPartSpec().setViewBox(new Box(width, height));
		return part.getPartSpec();
	}

	private int parseInteger(final String dim) throws IOException {
		final NumberFormat nf = NumberFormat.getIntegerInstance(Locale.ENGLISH);
		try {
			return nf.parse(dim).intValue();
		} catch (final ParseException e) {
			throw new IOException(e);
		}
	}
}
