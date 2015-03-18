package org.javaq.chartfaces.render.svg;

import java.util.Iterator;

import org.javaq.chartfaces.iterable.ISizeAwareIterable;
import org.javaq.chartfaces.util.IStringBuilder;
import org.javaq.chartfaces.util.IStringBuilderFactory;
import org.javaq.chartfaces.util.NumberUtils;
import org.javaq.chartfaces.util.StringBuilderWraper;


/**
 * Groups a collections of utility methods that calculate paths for regular
 * shapes.
 * 
 * @author Hongyan Li
 * 
 */
public class PathsUtil {
	private static final PathsUtil DEFAULT_INSTANCE = new PathsUtil();
	private static final char h_COMMAND = 'h';
	private static final char H_COMMAND = 'H';
	private static final char l_COMMAND = 'l';
	private static final char L_COMMAND = 'L';
	private static final char m_COMMAND = 'm';
	private static final char M_COMMAND = 'M';
	private static final double UNIT_RADIAN = Math.PI / 180.0;
	private static final char v_COMMAND = 'v';
	private static final char V_COMMAND = 'V';

	private static final char z_COMMAND = 'z';

	/**
	 * Centers the passed in {@link Paths} to their respective centers.
	 * 
	 * @param pathsArray
	 *            an array of {@link Paths}.
	 */
	public static void center(final Paths... pathsArray) {
		for (final Paths paths : pathsArray) {
			if (paths != null) {
				final double x = PathsUtil.average(paths.getXArray());
				final double y = PathsUtil.average(paths.getYArray());
				PathsUtil.translate(-x, -y, paths);
			}
		}
	}

	/**
	 * Create the paths for a regular polygon. The resulting polygon, if having
	 * odd number vertices, one of its vertices will be on the Y coordinate, if
	 * even number however, the center of two of the sides will be on the Y
	 * coordinate.
	 * 
	 * @param sides
	 *            the number of sides of the polygon. Must be greater than 2.
	 * @param x
	 *            the X coordinate of the center of the polygon.
	 * @param y
	 *            the Y coordinate of the center of the polygon.
	 * @param radius
	 *            radius of the smallest circle enclosing the polygon. Must be
	 *            greater than 0.
	 * @return a {@link Paths} object that contains the x and y coordinates.
	 *         Never null.
	 */
	public static Paths createRegularPolygonPaths(final int sides,
			final double x,
			final double y, final double radius) {
		if (sides < 3) {
			throw new IllegalArgumentException(
					"Invalid number of sides for polygon. sides=" + sides);
		}
		if (radius <= 0) {
			throw new IllegalArgumentException(
					"Invalid radius for polygon. radius=" + radius);
		}
		final double deltaAngle = 2 * Math.PI / sides;
		double angle = Math.PI / 2;
		if (sides == sides / 2 * 2) {
			angle -= deltaAngle / 2;
		}
		final double[] xArray = new double[sides];
		final double[] yArray = new double[sides];

		for (int i = 0; i < sides; i++) {
			xArray[i] = x + radius * Math.cos(angle);
			yArray[i] = y + radius * Math.sin(angle);
			angle += deltaAngle;
		}

		return Paths.newPaths(xArray, yArray);
	}

	/**
	 * Create the paths for a regular p/q star polygon.
	 * 
	 * @param p
	 *            the number of sides of a polygon.
	 * @param q
	 *            an integer that is greater than 1, smaller than p and not
	 *            equal to p/2.
	 * @param x
	 *            the X coordinate of the center of the polygon.
	 * @param y
	 *            the Y coordinate of the center of the polygon.
	 * @param radius
	 *            radius of the smallest circle enclosing the polygon. Must be
	 *            greater than 0.
	 * @return a {@link Paths} object that contains the x and y coordinates.
	 *         Never null.
	 */
	public static Paths createRegularStarPath(final int p, final int q,
			final double x, final double y,
			final double radius) {
		if (q * 2 == p || q >= p || q == 1) {
			throw new IllegalArgumentException("Invalid q for given p. p=" + p
					+ " q=" + q);
		}
		
		int newQ = q;
		if (q > p / 2) {
			newQ = p - q;
		}
		final Paths polygon = PathsUtil.createRegularPolygonPaths(p, x, y,
				radius);
		return Paths.newStarPaths(polygon, newQ);
	}

	/**
	 * Create the paths for a regular star shaped polygon of the given number of
	 * convex vertices. Note: "star shaped polygon" is different from
	 * "star polygon". A star shaped polygon does not self-insect!
	 * 
	 * @param sides
	 *            number of convex vertices. Must be greater than 2.
	 * @param x
	 *            the X coordinate of the center of the polygon.
	 * @param y
	 *            the Y coordinate of the center of the polygon.
	 * @param r1
	 *            radius of a circle enclosing either all the polygon's convex
	 *            vertices or the concave vertices. Must be greater than 0.
	 * @param r2
	 *            radius of a circle enclosing either all the polygon's convex
	 *            vertices or the concave vertices. Must be greater than 0.
	 * @return a {@link Paths} object that contains the x and y coordinates.
	 *         Never null.
	 */
	public static Paths createRegularStarPaths(final int sides, final double x,
			final double y,
			final double r1, final double r2) {
		double outerR = r1;
		double innerR = r2;
		if (r2 > r1) {
			outerR = r2;
			innerR = r1;
		}

		Paths result = null;
		final Paths innerPolygon = PathsUtil.createRegularPolygonPaths(sides,
				x, y,
				innerR);
		if (r1 == r2) {
			result = innerPolygon;
		} else {
			PathsUtil.rotate(x, y, 180.0 / sides, innerPolygon);
			final Paths outerPolygon = PathsUtil.createRegularPolygonPaths(
					sides, x,
					y, outerR);
			result = Paths.newStarPaths(innerPolygon, outerPolygon);
		}
		return result;
	}

	/**
	 * Create the paths for a square.
	 * 
	 * @param x
	 *            the X coordinate of the center of the square.
	 * @param y
	 *            the Y coordinate of the center of the square.
	 * @param size
	 *            the length of the sides. Must be greater than 0.
	 * @return a {@link Paths} object that contains the x and y coordinates.
	 *         Never null.
	 */
	public static Paths createSquarePaths(final double x, final double y,
			final double size) {
		if (size <= 0) {
			throw new IllegalArgumentException("Invalid size for square! size="
					+ size);
		}
		final double[] xArray = new double[4];
		final double[] yArray = new double[4];
		xArray[3] = x - size / 2.0;
		xArray[0] = xArray[3];
		yArray[1] = y - size / 2.0;
		yArray[0] = yArray[1];
		xArray[2] = x + size / 2.0;
		xArray[1] = xArray[2];
		yArray[3] = y + size / 2.0;
		yArray[2] = yArray[3];
		return Paths.newPaths(xArray, yArray);
	}

	/**
	 * Create the paths for a star plot from the passed in double array of
	 * radiuses of the vertexes centered around the passed in center
	 * coordinates.
	 * 
	 * @param x0
	 *            the x-coordinator for the center of the star plot.
	 * @param y0
	 *            the y-coordinator for the center of the star plot.
	 * @param radiuses
	 *            an array of radiuses for the vertexes of the star plot. Cannot
	 *            be null and length must be greater than 2.
	 * 
	 * @return the {@link Paths} for the Start plot.
	 * @throws NullPointerException
	 *             if the passed in is null.
	 * @throws IllegalArgumentException
	 *             if the length of the passed in is less than 2.
	 */
	public static Paths createStarPlotPaths(final double x0, final double y0,
			final double[] radiuses) {
		final int size = radiuses.length;
		if (size < 3) {
			throw new IllegalArgumentException(
					"Too few points in the star plot! Requires more than 2.");
		}
		final double unitAngle = 360. / size * PathsUtil.UNIT_RADIAN;
		final double[] xArray = new double[size];
		final double[] yArray = new double[size];

		double angle;
		for (int i = 0; i < radiuses.length; i++) {
			angle = unitAngle * i;
			xArray[i] = radiuses[i] * Math.cos(angle) + x0;
			yArray[i] = radiuses[i] * Math.sin(angle) + y0;
		}
		return Paths.newPaths(xArray, yArray);
	}

	/**
	 * Flips all the coordinate pairs in the passed in arrays of {@link Paths}
	 * about a line with the given slope and Y-intersect.
	 * 
	 * @param slope
	 *            the slope of a line.
	 * @param yIntersect
	 *            the Y-intersect of the line.
	 * @param pathsArray
	 *            the {@link Paths} to be worked on.
	 */
	public static void flip(final double slope, final double yIntersect,
			final Paths... pathsArray) {
		if (slope == 0.0) {
			// if the line is parallel to the X coordinate.
			for (final Paths paths : pathsArray) {
				if (paths != null) {
					final double[] ys = paths.getYArray();
					for (int i = 0; i < ys.length; i++) {
						ys[i] = 2.0 * yIntersect - ys[i];
					}
				}
			}
		} else {
			final double dividsor = slope * slope + 1;
			final double factor = slope * slope - 1;
			double temp;
			for (final Paths paths : pathsArray) {
				if (paths != null) {
					final double[] xs = paths.getXArray();
					final double[] ys = paths.getYArray();
					for (int i = 0; i < xs.length; i++) {
						temp = (2.0 * slope * (ys[i] - yIntersect) - factor
								* xs[i])
								/ dividsor;
						ys[i] = (2.0 * (slope * xs[i] + yIntersect) + factor
								* ys[i])
								/ dividsor;
						xs[i] = temp;
					}
				}
			}
		}
	}

	/**
	 * Flips all the coordinate pairs in the passed in arrays of {@link Paths}
	 * about a line that is parallel to the Y-axis and intersects with the
	 * X-axis at the given X-intersect.
	 * 
	 * @param xIntersect
	 *            the X-intersect of a vertical line.
	 * @param pathsArray
	 *            the {@link Paths} to be worked on.
	 */
	public static void flip(final double xIntersect, final Paths... pathsArray) {
		for (final Paths paths : pathsArray) {
			if (paths != null) {
				final double[] xs = paths.getXArray();
				for (int i = 0; i < xs.length; i++) {
					xs[i] = 2.0 * xIntersect - xs[i];
				}
			}
		}
	}

	public static PathsUtil getDefaultInstance() {
		return PathsUtil.DEFAULT_INSTANCE;
	}

	/**
	 * Rotates the passed in {@link Paths} with the angle passed in.
	 * 
	 * @param x
	 *            the X coordinate of the center of rotation.
	 * @param y
	 *            the Y coordinate of the center of rotation.
	 * @param degree
	 *            the number of degrees to rotate anti-clockwise.
	 * @param pathsArray
	 *            the array of {@link Paths} to be rotated.
	 */
	public static void rotate(final double x, final double y,
			final double degree,
			final Paths... pathsArray) {
		double[] xA, yA;
		double deltaX, deltaY; // relative to (x,y)
		final double angle = degree * PathsUtil.UNIT_RADIAN;
		for (final Paths paths : pathsArray) {
			if (paths != null) {
				xA = paths.getXArray();
				yA = paths.getYArray();
				for (int i = 0; i < xA.length; i++) {
					deltaX = xA[i] - x;
					deltaY = yA[i] - y;
					xA[i] = x + deltaX * Math.cos(angle) -
							deltaY * Math.sin(angle);
					yA[i] = y + deltaX * Math.sin(angle) +
							deltaY * Math.cos(angle);
				}
			}
		}
	}

	/**
	 * Scales the passed in {@link Paths} with the passed in scale factor.
	 * 
	 * @param xFactor
	 *            the scale factor to apply to the X coordinate.
	 * @param yFactor
	 *            the scale factor to apply to the Y coordinate.
	 * @param pathsArray
	 *            the array of {@link Paths} to be rotated.
	 */
	public static void scale(final double xFactor, final double yFactor,
			final Paths... pathsArray) {
		double[] xA, yA;
		for (final Paths paths : pathsArray) {
			xA = paths.getXArray();
			yA = paths.getYArray();
			for (int i = 0; i < xA.length; i++) {
				xA[i] *= xFactor;
				yA[i] *= yFactor;
			}
		}
	}

	/**
	 * Scales the passed in {@link Paths} with the passed in scale factor.
	 * 
	 * @param factor
	 *            the scale factor to apply to both X and Y coordinates.
	 * @param pathsArray
	 *            the array of {@link Paths} to be rotated.
	 */
	public static void scale(final double factor, final Paths... pathsArray) {
		PathsUtil.scale(factor, factor, pathsArray);
	}

	/**
	 * Translates the passed in {@link Paths} with the passed in delta.
	 * 
	 * @param deltaX
	 *            the delta X to translate.
	 * @param deltaY
	 *            the delta Y to translate.
	 * @param pathsArray
	 *            the array of {@link Paths} to be rotated.
	 */
	public static void translate(final double deltaX, final double deltaY,
			final Paths... pathsArray) {
		double[] xA, yA;
		for (final Paths paths : pathsArray) {
			xA = paths.getXArray();
			yA = paths.getYArray();
			for (int i = 0; i < xA.length; i++) {
				xA[i] += deltaX;
				yA[i] += deltaY;
			}
		}
	}

	/**
	 * Truncate the passed in path values according to the given accuracy or
	 * tolerance so that when rendering path string from these paths, less bytes
	 * are generated.
	 * 
	 * @param xTolerance
	 *            accuracy or tolerance for the x-coordinator values.
	 * @param yTolerance
	 *            accuracy or tolerance for the y-coordinator values.
	 * @param paths
	 *            the array of {@link Paths} to be truncated.
	 */
	public static void truncateDecimals(final double xTolerance,
			final double yTolerance,
			final Paths... paths) {
		for (final Paths path : paths) {
			final double[] xArray = path.getXArray();
			final double[] yArray = path.getYArray();
			for (int j = 0; j < xArray.length; j++) {
				xArray[j] = Math.round(xArray[j] / xTolerance) * xTolerance;
				yArray[j] = Math.round(yArray[j] / yTolerance) * yTolerance;
			}
		}
	}

	/*
	 * Averages the passed in values.
	 */
	private static double average(final double[] values) {
		double average = 0;
		for (final double value : values) {
			average += value;
		}
		final int divisor = values.length == 0 ? 1 : values.length;
		return average / divisor;
	}

	private final IStringBuilderFactory stringBuilderFactory;

	public PathsUtil() {
		this(new IStringBuilderFactory() {
			@Override
			public IStringBuilder newBuilder() {
				return new StringBuilderWraper(new StringBuilder());
			}
		});
	}

	public PathsUtil(final IStringBuilderFactory stringBuilderFactory) {
		this.stringBuilderFactory = stringBuilderFactory;
	}

	/**
	 * Render a path string from the passed in coordinate pairs. All coordinate
	 * pairs are treated as absolute coordinates.
	 * 
	 * @param xArray
	 *            an array of X coordinates. Must have the same length as
	 *            yArray.
	 * @param yArray
	 *            and array of Y coordinates. Must have the same length as
	 *            xArray.
	 * @param closed
	 *            true if renders this as a closed path.
	 * @return a SVG string that has the drawing instructions for the paths of
	 *         the paths. Never null.
	 */
	public String renderAbsolutePaths(final double[] xArray,
			final double[] yArray,
			final boolean closed) {
		return renderPaths(xArray, yArray, PathsUtil.M_COMMAND,
				PathsUtil.L_COMMAND, closed);
	}

	/**
	 * Render a path string from the passed in coordinate pairs. All coordinate
	 * pairs are treated as absolute coordinates.
	 * 
	 * @param xIterable
	 *            an {@link Iterable} of X coordinates. Must have the same
	 *            length as yIterable.
	 * @param yIterable
	 *            and {@link Iterable} of Y coordinates. Must have the same
	 *            length as xIterable.
	 * @param closed
	 *            true if renders this as a closed path.
	 * @return a SVG string that has the drawing instructions for the paths of
	 *         the paths. Never null.
	 */
	public String renderAbsolutePaths(
			final ISizeAwareIterable<? extends Number> xIterable,
			final ISizeAwareIterable<? extends Number> yIterable,
			final boolean closed) {
		return renderPaths(xIterable, yIterable, PathsUtil.M_COMMAND,
				PathsUtil.L_COMMAND, closed);
	}
	/**
	 * Render a series of rays (or array-less vectors), each pointing from the
	 * passed in origin coordinates as center to a point given in the passed
	 * arrays of coordinates.
	 * 
	 * @param x0
	 *            the absolute x-coordinator of the origin.
	 * @param y0
	 *            the absolute y-coordinator of the origin.
	 * @param xArray
	 *            the array of x-coordinator values for the end points of the
	 *            rays.
	 * @param yArray
	 *            the array of y-coordinator values for the end points of the
	 *            rays.
	 * @return a SVG string that has the drawing instructions for the paths of
	 *         the rays. Never null.
	 * @throws NullPointerException
	 *             if either of the passed in arrays is null.
	 * @throws IllegalArgumentException
	 *             if the lengths of the passed in arrays are not the same.
	 */
	public String renderRaysFromOrigin(final double x0, final double y0,
			final double[] xArray, final double[] yArray) {
		if (xArray.length != yArray.length) {
			throw new IllegalArgumentException(
					"Number of X-coordinates different from that of Y-coordinates!");
		}

		final IStringBuilder sb = getStringBuilderFactory().newBuilder();
		for (int i = 0; i < xArray.length; i++) {
			if (NumberUtils.equals(xArray[i], x0) && NumberUtils.equals(yArray[i], y0)) {
				continue;
			}

			sb.append(PathsUtil.M_COMMAND).append(x0).append(' ').append(y0);
			if (NumberUtils.equals(xArray[i], x0)) {
				sb.append(PathsUtil.V_COMMAND).append(yArray[i]);
			} else if (NumberUtils.equals(yArray[i], y0)) {
				sb.append(PathsUtil.H_COMMAND).append(xArray[i]);
			} else {
				sb.append(PathsUtil.L_COMMAND).append(xArray[i]).append(' ')
						.append(yArray[i]);
			}
		}
		return sb.toString();
	}

	/**
	 * Render a path string from the passed in coordinate pairs. All coordinate
	 * pairs except the first one are treated as relative coordinates.
	 * 
	 * @param xArray
	 *            an array of X coordinates. Must have the same length as
	 *            yArray.
	 * @param yArray
	 *            and array of Y coordinates. Must have the same length as
	 *            xArray.
	 * @param relativeMove
	 *            if true, use relative move as the first command.
	 * @param closed
	 *            renders this as a closed path.
	 * @return a SVG string that has the drawing instructions for the paths of
	 *         the paths. Never null.
	 */
	public String renderRelativePaths(final double[] xArray,
			final double[] yArray,
			final boolean relativeMove, final boolean closed) {
		char mCommand = PathsUtil.M_COMMAND;
		if (relativeMove) {
			mCommand = PathsUtil.m_COMMAND;
		}
		return renderPaths(xArray, yArray, mCommand,
				PathsUtil.l_COMMAND, closed);
	}

	private IStringBuilderFactory getStringBuilderFactory() {
		return this.stringBuilderFactory;
	}

	/**
	 * Render a path string from the passed in coordinate pairs.
	 * 
	 * @param xArray
	 *            an array of X coordinates. Must have the same length as
	 *            yArray.
	 * @param yArray
	 *            and array of Y coordinates. Must have the same length as
	 *            xArray.
	 * @param mCommand
	 *            the initial move command. Only 'm' or 'M'.
	 * @param lCommand
	 *            the drawing (line-to) command, either 'l' or 'L'.
	 * @param closed
	 *            renders this as a closed path.
	 * @return a SVG string that has the drawing instructions for the paths of
	 *         the paths. Never null.
	 */
	private String renderPaths(final double[] xArray,
			final double[] yArray,
			final char mCommand, final char lCommand, final boolean closed) {
		assert (mCommand == PathsUtil.M_COMMAND || mCommand == PathsUtil.m_COMMAND);
		assert (lCommand == PathsUtil.L_COMMAND || lCommand == PathsUtil.l_COMMAND);
		if (xArray.length != yArray.length) {
			throw new IllegalArgumentException(
					"Number of X-coordinates different from that of Y-coordinates!");
		}

		char hCommand = PathsUtil.h_COMMAND, vCommand = PathsUtil.v_COMMAND;
		if (lCommand == PathsUtil.L_COMMAND) {
			hCommand = PathsUtil.H_COMMAND;
			vCommand = PathsUtil.V_COMMAND;
		}
		final IStringBuilder sb = getStringBuilderFactory().newBuilder();
		if (xArray.length != 0 && yArray.length != 0) {
			sb.append(mCommand).append(xArray[0]).append(' ').append(yArray[0]);
			double xPrior = xArray[0], yPrior = yArray[0];
			if (xArray.length > 1) {
				for (int i = 1; i < xArray.length; i++) {
					if (NumberUtils.equals(xPrior, xArray[i])) {
						sb.append(vCommand).append(yArray[i]);
					} else if (NumberUtils.equals(yPrior, yArray[i])) {
						sb.append(hCommand).append(xArray[i]);
					} else {
						sb.append(lCommand).append(xArray[i]).append(' ')
								.append(yArray[i]);
					}
					xPrior = xArray[i];
					yPrior = yArray[i];
				}
				if (closed) {
					sb.append(PathsUtil.z_COMMAND);
				}
			}
		}
		return sb.toString();
	}

	/**
	 * Render a path string from the passed in coordinate pairs.
	 * 
	 * @param xIterable
	 *            an array of X coordinates. Must have the same length as
	 *            yArray.
	 * @param yITerable
	 *            and array of Y coordinates. Must have the same length as
	 *            xArray.
	 * @param mCommand
	 *            the initial move command. Only 'm' or 'M'.
	 * @param lCommand
	 *            the drawing (line-to) command, either 'l' or 'L'.
	 * @param closed
	 *            renders this as a closed path.
	 * @return a SVG string that has the drawing instructions for the paths of
	 *         the paths. Never null.
	 */
	private String renderPaths(
			final ISizeAwareIterable<? extends Number> xIterable,
			final ISizeAwareIterable<? extends Number> yITerable,
			final char mCommand, final char lCommand, final boolean closed) {
		assert (mCommand == PathsUtil.M_COMMAND || mCommand == PathsUtil.m_COMMAND);
		assert (lCommand == PathsUtil.L_COMMAND || lCommand == PathsUtil.l_COMMAND);
		if (xIterable.size() != yITerable.size()) {
			throw new IllegalArgumentException(
					"Number of X-coordinates different from that of Y-coordinates!");
		}

		char hCommand = PathsUtil.h_COMMAND, vCommand = PathsUtil.v_COMMAND;
		if (lCommand == PathsUtil.L_COMMAND) {
			hCommand = PathsUtil.H_COMMAND;
			vCommand = PathsUtil.V_COMMAND;
		}
		final IStringBuilder sb = getStringBuilderFactory().newBuilder();
		if (xIterable.size() != 0 && yITerable.size() != 0) {
			final Iterator<?> xIter = xIterable.iterator();
			final Iterator<?> yIter = yITerable.iterator();
			Object xPrior = xIter.next();
			Object yPrior = yIter.next();
			Object x = null;
			Object y = null;
			sb.append(mCommand).append(xPrior).append(' ').append(yPrior);
			while (xIter.hasNext()) {
				x = xIter.next();
				y = yIter.next();
				if (xPrior.equals(x)) {
					sb.append(vCommand).append(y);
				} else if (yPrior.equals(y)) {
					sb.append(hCommand).append(x);
				} else {
					sb.append(lCommand).append(x).append(' ').append(y);
				}
				xPrior = x;
				yPrior = y;
			}
			if (closed) {
				sb.append(PathsUtil.z_COMMAND);
			}
		}
		return sb.toString();
	}
}