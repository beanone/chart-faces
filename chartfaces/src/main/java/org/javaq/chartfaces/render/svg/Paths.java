package org.javaq.chartfaces.render.svg;

import java.util.HashMap;
import java.util.Map;

import org.javaq.chartfaces.util.IStringBuilder;
import org.javaq.chartfaces.util.IStringBuilderFactory;
import org.javaq.chartfaces.util.NumberRoundingStringBuilder;
import org.javaq.chartfaces.util.NumberUtils;


/**
 * A simple bean that holds the x, y coordinate pairs as double arrays.
 * 
 * @author Hongyan Li
 * 
 */
public class Paths {
	private static final class NumberRoundingStringBuilderFactory implements
			IStringBuilderFactory {
		private final int sigDigit;

		private NumberRoundingStringBuilderFactory(int sigDigit) {
			this.sigDigit = sigDigit;
		}

		@Override
		public IStringBuilder newBuilder() {
			return new NumberRoundingStringBuilder(sigDigit);
		}
	}

	private double[][] box;
	private double[] xArray;
	private double[] yArray;
	
	/**
	 * Construct a <code>Paths</code> from the passed in pair of coordinate
	 * arrays. The two arrays must have the exact same length.
	 * 
	 * @param x
	 *            the x-coordinates of the paths. Cannot be null.
	 * @param y
	 *            the y-coordinates of the paths. Cannot be null.
	 * @throws NullPointerException
	 *             if either of the passed in is null.
	 * @throws IllegalArgumentException
	 *             if the passed in do not have the same array length.
	 */
	public static Paths newPaths(final double[] x, final double[] y) {
		return new Paths(x,y);
	}
	
	public static Paths newStarPaths(final Paths polygon1, final Paths polygon2) {
		return new RegularStarPath0(polygon1, polygon2);
	}
	
	public static Paths newStarPaths(final Paths polygon, final int q) {
		return new RegularStarPath1(polygon, q);
	}

	private Paths(final double[] x, final double[] y) {
		if (x.length != y.length) {
			throw new IllegalArgumentException(
					"xArray and yArray lengths are different!");
		}
		this.xArray = x.clone();
		this.yArray = y.clone();
	}

	/**
	 * Construct an empty paths. Internal use only.
	 */
	protected Paths() {
		// internal use only
	}

	/**
	 * Lazily find the imaginary rectangular box that encloses the paths passed
	 * in.
	 * 
	 * @return the {@link Paths} of a rectangle that encloses the passed in.
	 *         Note: the xArray and yArray of the returned <code>Paths</code> is
	 *         ordered (smaller value first).
	 */
	public double[][] boxMe() {
		if (this.box == null) {
			this.box = NumberUtils.boxMe(getXArray(), getYArray());
		}
		return this.box.clone();
	}

	/**
	 * Box the box of this so that all coordinates in this are boxed-in even
	 * after truncation using the passed in toleranceHint.
	 * 
	 * @param toleranceHint
	 *            a hint for truncation of the coordinates. The actual tolerance
	 *            will be such that as if the passed in was the tolerance when
	 *            the range of the number was 0 to 1.
	 * @return a double[][] that contains {xMin, xMax, xSigNum} and {yMin, yMax,
	 *         ySigNum} where xMin and yMin are the lower bound of the box, xMax
	 *         and yMax are the upper. xSigNum and ySigNum are the numbers that
	 *         indicate the position of the most significant number (1 indicates
	 *         10 is the most significant, -1, the first decimal point).
	 */
	public double[][] boxMe(final double toleranceHint) {
		final double[][] theBox = boxMe();
		return NumberUtils.boxMe(theBox, toleranceHint);
	}

	double[] getXArray() {
		return this.xArray;
	}

	double[] getYArray() {
		return this.yArray;
	}

	/**
	 * Renders this, treating the paths coordinates as absolute coordinates. The
	 * coordinates will be truncated before rendering. The passed in
	 * toleranceHint will be used to determine how the numbers will be
	 * truncated.
	 * 
	 * @param toleranceHint
	 *            a hint for truncation of the coordinates. The actual tolerance
	 *            will be such that as if the passed in was the tolerance when
	 *            the range of the number was 0 to 1.
	 * @param closed
	 *            whether the path should rendered as closed.
	 * @return the rendering result.
	 */
	public String renderAbsolute(final double toleranceHint,
			final boolean closed) {
		final double[][] myBox = boxMe(toleranceHint);
		PathsUtil.truncateDecimals(myBox[0][2], myBox[1][2], new Paths(
				getXArray().clone(), getYArray().clone()));
		return PathsUtil.getDefaultInstance()
				.renderAbsolutePaths(getXArray(), getYArray(), closed);
	}

	/**
	 * Renders this, treating the paths coordinates as absolute coordinates. The
	 * coordinates will be truncated before rendering. The passed in
	 * toleranceHint will be used to determine how the numbers will be
	 * truncated.
	 * 
	 * @param sigDigit
	 *            an integer indicates the significant digit of the numbers. -1
	 *            is the 10th
	 * @param closed
	 *            whether the path should rendered as closed.
	 * @return the rendering result.
	 */
	public String renderAbsolute(final int sigDigit, final boolean closed) {
		final PathsUtil util = new PathsUtil(new NumberRoundingStringBuilderFactory(sigDigit));
		return util.renderAbsolutePaths(getXArray(), getYArray(), closed);
	}

	public int size() {
		return getXArray().length;
	}

	protected void setXArray(final double[] x) {
		this.xArray = x.clone();
		this.box = null;
	}

	protected void setYArray(final double[] y) {
		this.yArray = y.clone();
		this.box = null;
	}

	/**
	 * A star-shaped polygon paths. Internal use only.
	 * 
	 * @author Hongyan Li
	 * @see <a href="http://en.wikipedia.org/wiki/Star-shaped_polygon">Star-shaped
	 *      polygon</a>.
	 */
	private static class RegularStarPath0 extends Paths {
		/**
		 * Construct this from the passed in pair of polygon {@link Paths}.
		 * 
		 * @param polygon1
		 *            the {@link Paths} of a polygon. Cannot be null and must have
		 *            the same size as <code>polygon2</code>.
		 * @param polygon2
		 *            the {@link Paths} of a polygon. Cannot be null and must have
		 *            the same size as <code>polygon1</code>.
		 * @throws NullPointerException
		 *             if either <code>polygon1</code> or <code>polygon2</code> is
		 *             null.
		 * @throws IllegalArgumentException
		 *             if the size of <code>polygon1</code> is different from that
		 *             of <code>polygon2</code>.
		 */
		public RegularStarPath0(final Paths polygon1, final Paths polygon2) {
			if (polygon1.size() != polygon2.size()) {
				throw new IllegalArgumentException(
						"The passed in two polygons do not have the same size.");
			}
			final double[] x1 = polygon1.getXArray();
			final double[] y1 = polygon1.getYArray();
			final double[] x2 = polygon2.getXArray();
			final double[] y2 = polygon2.getYArray();
	
			final int sides = x1.length;
			final double[] xArray = new double[sides * 2];
			final double[] yArray = new double[sides * 2];
			int j = 0;
			for (int i = 0; i < sides; i++) {
				j = 2 * i;
				xArray[j] = x2[i];
				xArray[j + 1] = x1[i];
				yArray[j] = y2[i];
				yArray[j + 1] = y1[i];
			}
			setXArray(xArray);
			setYArray(yArray);
		}
	}
	
	/**
	 * A p/q polygon star polygon paths. Internal use only.
	 * 
	 * @author Hongyan Li
	 * @see <a href="http://en.wikipedia.org/wiki/Star_polygon">Star polygon</a>.
	 */
	private static class RegularStarPath1 extends Paths {
		// we should not have too many of these int[] objects.
		private static Map<String, int[]> indexMaps = new HashMap<String, int[]>();
	
		/**
		 * Construct this from the passed in polygon {@link Paths} and a q value.
		 * 
		 * @param polygon
		 *            the {@link Paths} for a polygon. Size must be greater than 2.
		 * @param q
		 *            a q value. Must be greater than 0 and not greater than
		 *            floor((p-1)/2), where p is the size of the passed in
		 *            <code>polygon</code>.
		 * @throws IllegalArgumentException
		 *             if the value of q is less than 1 or greater than floor of
		 *             (p-1)/2.
		 * @throws NullPointerException
		 *             if <code>polygon</code> is null.
		 */
		public RegularStarPath1(final Paths polygon, final int q) {
			if (q < 1 || q > (polygon.size() - 1) / 2) {
				throw new IllegalArgumentException("Invalid value for p.");
			}
			final int[] indexMap = getCreateIndexMap(polygon, q);
			final double[] xA = polygon.getXArray();
			final double[] yA = polygon.getYArray();
			final double[] xArray = new double[xA.length];
			final double[] yArray = new double[yA.length];
			for (int i = 0; i < xArray.length; i++) {
				xArray[i] = xA[indexMap[i]];
				yArray[i] = yA[indexMap[i]];
			}
	
			setXArray(xArray);
			setYArray(yArray);
		}
	
		private int[] getCreateIndexMap(final Paths polygon, final int q) {
			final int len = polygon.getXArray().length;
			final String key = "" + len + '_' + q;
			int[] map = RegularStarPath1.indexMaps.get(key);
			if (map == null) {
				// create the map and add to the collection
				map = new int[len];
				int k = 0;
				for (int j = 0; j < q; j++) {
					for (int i = j; i < len; i += q) {
						map[k++] = i;
					}
				}
				RegularStarPath1.indexMaps.put(key, map);
			}
			return map;
		}
	}
}
