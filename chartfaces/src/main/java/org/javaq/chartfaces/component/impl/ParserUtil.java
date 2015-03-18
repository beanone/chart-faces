package org.javaq.chartfaces.component.impl;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.javaq.chartfaces.api.IChartAxis;
import org.javaq.chartfaces.api.IChartPart;


/**
 * Utility method for parsing strings.
 * 
 * @author Hongyan Li
 * 
 */
public final class ParserUtil {
	
	private ParserUtil() {}
	
	private static final String FONT_SIZE = "font-size";

	public static int getAxisLabelFontSize(final IChartAxis axis) {
		return ParserUtil.getSize(axis.getLabelStyle(), ParserUtil.FONT_SIZE);
	}

	public static int getAxisLineWidth(final IChartAxis axis) {
		return ParserUtil.getSize(axis.getLineStyle(), "stroke-width");
	}

	public static int getBorderWidth(final UIChart chart) {
		return ParserUtil.getSize(chart.getBorderStyle(), "stroke-width");
	}

	public static int getCaptionFontSize(final IChartAxis axis) {
		return ParserUtil.getSize(axis.getCaptionStyle(), ParserUtil.FONT_SIZE);
	}

	public static int getFontSize(final IChartPart part) {
		return ParserUtil.getSize(part.getStyle(), ParserUtil.FONT_SIZE);
	}

	public static int getFooterFontSize(final UIChart chart) {
		return ParserUtil.getSize(chart.getFooterStyle(), ParserUtil.FONT_SIZE);
	}

	public static int getHeaderFontSize(final UIChart chart) {
		return ParserUtil.getSize(chart.getHeaderStyle(), ParserUtil.FONT_SIZE);
	}

	public static int getInt(final String str, final int defaultValue) {
		int returns = defaultValue;
		if (str != null && str.trim().length() > 0) {
			try {
				returns = NumberFormat.getIntegerInstance().parse(str)
						.intValue();
			} catch (final ParseException e) {
				// ignore, just use the default
			}
		}
		return returns;
	}

	public static int getLabelFontSize(final IChartAxis axis) {
		return ParserUtil.getSize(axis.getLabelStyle(), ParserUtil.FONT_SIZE);
	}

	public static int getSize(final String styleString, final String styleName) {
		final String fontSizeString =
				ParserUtil.getStyle(styleString, styleName);
		int fontSize = 0;
		if (fontSizeString != null) {
			try {
				fontSize = NumberFormat.getIntegerInstance().parse(
						fontSizeString).intValue();
			} catch (final ParseException e) {
				throw new IllegalArgumentException(e);
			}
		}
		return fontSize;
	}

	public static String getStyle(final String styleString,
			final String name) {
		String match = ParserUtil.matchStyle(styleString, name + ":([^;]*);");
		if (match == null) {
			match = ParserUtil.matchStyle(styleString, name + ":([^;]*)$");
		}
		return match;
	}

	public static String matchStyle(final String styleSting, final String ptn) {
		final Pattern pattern = Pattern.compile(ptn);
		final Matcher matcher = pattern.matcher(styleSting);
		String match = null;
		if (matcher.find()) {
			match = matcher.group(1);
			if (match != null && match.length() == 0) {
				match = null;
			}
		}
		return match;
	}
}
