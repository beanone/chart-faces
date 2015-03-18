package org.javaq.chartfaces.util;

import java.text.NumberFormat;
import java.util.Arrays;

/**
 * A collection of utilities for number manipulation.
 *
 * @author Hongyan Li
 *
 */
public final class NumberUtils {
	public static final double TOLERANCE_FACTOR = 2000;

	private NumberUtils() {}

	public static double[][] boxMe(final double[] xArray, final double[] yArray) {
		final double[] xRange = NumberUtils.findRange(xArray);
		final double[] yRange = NumberUtils.findRange(yArray);
		return new double[][] { xRange, yRange };
	}

	/**
	 * Calculates the box surround the passed in box using the passed in
	 * tolerance hint.
	 *
	 * @param box the coordinates of a rectangle - two pairs of x, y coordinates.
	 * @param toleranceHint a tolerance hint that will be used in calculations.
	 *
	 * @return the coordinates of the new box that contains the passed in box.
	 */
	public static double[][] boxMe(final double[][] box,
			final double toleranceHint) {
		final int xSigNum = NumberUtils
				.getMostSignificantIndex((box[0][1] - box[0][0])
						* toleranceHint);
		final int ySigNum = NumberUtils
				.getMostSignificantIndex((box[1][1] - box[1][0])
						* toleranceHint);
		final double xMin = NumberUtils.floor(box[0][0], xSigNum);
		final double yMin = NumberUtils.floor(box[1][0], ySigNum);
		final double xMax = NumberUtils.ceil(box[0][1], xSigNum);
		final double yMax = NumberUtils.ceil(box[1][1], ySigNum);
		return new double[][] { { xMin, xMax, xSigNum },
				{ yMin, yMax, ySigNum } };
	}

	/**
	 * Ceiling the passed in double value according to the significant digit
	 * index passed in.
	 *
	 * @param val
	 *            the new value after been formatted.
	 * @param sigNumIndex
	 *            the index for the least significant number. 0 for the 1st
	 *            digit, 1 for the 10th digit, -1 for the first decimal, etc..
	 * @return ceiling of the passed in value.
	 */
	public static double ceil(final double val, final int sigNumIndex) {
		final double pow = Math.pow(10, sigNumIndex);
		double newVal = pow * (int) Math.ceil(val / pow);
		if (sigNumIndex < 0) {
			newVal = NumberUtils.truncate(newVal, sigNumIndex);
		}
		return newVal;
	}

	/**
	 * Finds the range of the passed in and returns the range as a double array.
	 *
	 * @param xArray
	 * @return the (min, max) range of the passed in array.
	 */
	public static double[] findRange(final double[] xArray) {
		final double[] a = xArray.clone();
		Arrays.sort(a);
		return new double[] { a[0], a[a.length - 1] };
	}

	/**
	 * Floor the passed in double value according to the significant digit index
	 * passed in.
	 *
	 * @param val
	 *            the new value after been formatted.
	 * @param sigNumIndex
	 *            the index for the least significant number. 0 for the 1st
	 *            digit, 1 for the 10th digit, -1 for the first decimal, etc..
	 * @return floor of the passed in value.
	 */
	public static double floor(final double val, final int sigNumIndex) {
		final double pow = Math.pow(10, sigNumIndex);
		double newVal = pow * (int) Math.floor(val / pow);
		if (sigNumIndex < 0) {
			newVal = NumberUtils.truncate(newVal, sigNumIndex);
		}
		return newVal;
	}

	/**
	 * Finds the most significant digit in the passed in number and returns it.
	 *
	 * @param d
	 *            the number whose most significant digit is to be found.
	 * @return 0 if the passed in is 0, or the most significant digit of the
	 *         number. e.g., the most significant digit for 0.2 is 2, for 0.021
	 *         is 2, for 51234 is 5, and for 13 is 1.
	 */
	public static int getMostSignificantDigit(final double d) {
		if (d == 0) {
			return 0;
		}
		double number = Math.abs(d);
		final int index = NumberUtils.getMostSignificantIndex(number);
		return (int) Math.floor(number / Math.pow(10, index));
	}

	/**
	 * Finds the location of the most significant digit in the passed in number
	 * and returns it.
	 *
	 * @param d
	 *            the number whose location of most significant digit is to be
	 *            found.
	 * @return 0 if the passed in is 0, or the index of the most significant
	 *         digit. Decimal point locations will produce a negative number,
	 *         e.g., 0.2 is -1, 0.02 is -2, 5 is 0, 13 is 1.
	 */
	public static int getMostSignificantIndex(final double d) {
		if (d == 0) {
			return 0;
		}
		double number = Math.abs(d);
		final double power = Math.log10(number);
		final int iPower = (int)Math.round(power);
		return (int) (equals(iPower, power) ? iPower : Math.floor(power));
	}

	/**
	 * Format the passed in double number by dropping off the less significant
	 * numbers so that the least significant number is indicated by the passed
	 * in sigNumIndex. This function is created because issues in calculation
	 * with double numbers, e.g., 3.0 * 0.1 = 0.30000000000000004!
	 *
	 * @param v
	 *            the value to be formatted.
	 * @param sigNumIndex
	 *            the index for the least significant number. 0 for the 1st
	 *            digit, 1 for the 10th digit, -1 for the first decimal, etc..
	 * @return the formatted double with the lesser significant numbers dropped.
	 */
	public static double truncate(final double v, final int sigNumIndex) {
		// if (sigNumIndex >= 0) {
		// throw new IllegalArgumentException(
		// "Invalid sigNum! Requires negative integer!");
		// }
		if (sigNumIndex >= 0) {
			return ((int) (v / Math.pow(10, sigNumIndex)))
					* Math.pow(10, sigNumIndex);
		}
		final NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setGroupingUsed(false);
		nf.setMaximumFractionDigits(Math.abs(sigNumIndex));
		return Double.parseDouble(nf.format(v));
	}

	public static boolean equals(double d1, double d2) {
		return Math.abs(d1-d2) <= d2/TOLERANCE_FACTOR;
	}
}