package org.javaq.chartfaces.render.svg;

import org.junit.Assert;
import org.junit.Test;

public class PathsUtilTest {
	private final PathsUtil pathsUtil = new PathsUtil();

	@Test
	public void testCenter() {
		final Paths paths = new Triangle(1).getPaths();
		final Paths paths1 = new Triangle(1).getPaths();
		final Paths paths2 = new Triangle(1).getPaths();
		PathsUtil.translate(10.0, 2.1, paths1);
		PathsUtil.translate(5.5, -3.4, paths2);
		PathsUtil.center(paths1, paths2);
		assertPathsEquals(paths, paths1);
		assertPathsEquals(paths, paths2);
	}

	@Test
	public void testCenterAlreadyCenteredTriangle() {
		final Paths paths = new Triangle(1).getPaths();
		final Paths paths1 = new Triangle(1).getPaths();
		PathsUtil.center(paths);
		assertPathsEquals(paths, paths1);
	}

	@Test
	public void testCenterEmptyPaths() {
		final Paths paths = Paths.newPaths(new double[] {}, new double[] {});
		PathsUtil.center(paths);
	}

	@Test
	public void testCreateAbsolutePath() {
		final double[] xArray = { 1, 2, 3, 3, 5 };
		final double[] yArray = { 2, 3, 4, 5, 5 };
		final String result = pathsUtil.renderAbsolutePaths(xArray, yArray,
				true);
		Assert.assertEquals("M1.0 2.0L2.0 3.0L3.0 4.0V5.0H5.0z", result);
	}

	@Test
	public void testCreateAbsolutePathNoPoint() {
		final double[] xArray = {};
		final double[] yArray = {};
		final String result = pathsUtil.renderAbsolutePaths(xArray, yArray,
				true);
		Assert.assertEquals("", result);
	}

	@Test
	public void testCreateAbsolutePathOnePoint() {
		final double[] xArray = { 1 };
		final double[] yArray = { 2 };
		final String result = pathsUtil.renderAbsolutePaths(xArray, yArray,
				true);
		Assert.assertEquals("M1.0 2.0", result);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateAbsolutePathXandYArrayLengthDifferent() {
		final double[] xArray = { 1, 2 };
		final double[] yArray = { 2 };

		pathsUtil.renderAbsolutePaths(xArray, yArray, true);
	}

	@Test
	public void testCreateRegularPolygonPathEvenSides() {
		final Paths polygon = PathsUtil.createRegularPolygonPaths(4, 1, 0,
				1.4142);
		final Paths square = PathsUtil.createSquarePaths(1, 0, 2);
		final double[] xA = square.getXArray();
		final double[] yA = square.getYArray();
		double t = xA[0];
		xA[0] = xA[2];
		xA[2] = t;
		t = xA[1];
		xA[1] = xA[3];
		xA[3] = t;
		t = yA[0];
		yA[0] = yA[2];
		yA[2] = t;
		t = yA[1];
		yA[1] = yA[3];
		yA[3] = t;

		Assert.assertArrayEquals(xA, polygon.getXArray(), 0.01);
		Assert.assertArrayEquals(yA, polygon.getYArray(), 0.01);
	}

	@Test
	public void testCreateRegularPolygonPathOddSides() {
		final Paths polygon = PathsUtil.createRegularPolygonPaths(3, 1, 0, 1);
		final double[] xA = polygon.getXArray();
		final double[] yA = polygon.getYArray();
		Assert.assertEquals(1, xA[0], 0.01);
		Assert.assertEquals(1, yA[0], 0.01);
		Assert.assertEquals(0.134, xA[1], 0.01);
		Assert.assertEquals(-0.5, yA[1], 0.01);
		Assert.assertEquals(1.866, xA[2], 0.01);
		Assert.assertEquals(-0.5, yA[2], 0.01);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateRegularPolygonRadius0() {
		PathsUtil.createRegularPolygonPaths(3, 0, 0, 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateRegularPolygonRadiusNegative() {
		PathsUtil.createRegularPolygonPaths(3, 0, 0, -1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateRegularPolygonSides2() {
		PathsUtil.createRegularPolygonPaths(2, 0, 0, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateRegularPolygonSidesNegative() {
		PathsUtil.createRegularPolygonPaths(-1, 0, 0, 1);
	}

	@Test
	public void testCreateRegularStarPathIntDoubleDoubleDoubleDouble() {
		final Paths star = PathsUtil.createRegularStarPaths(3, 1.0, 0.0, 1.0,
				2.0);
		final double[] xA = star.getXArray();
		final double[] yA = star.getYArray();
		Assert.assertArrayEquals(xA, new double[] { 1, 0.134, -0.732, 1, 2.732,
				1.866 }, 0.01);
		Assert.assertArrayEquals(yA, new double[] { 2, 0.5, -1, -1, -1, 0.5 },
				0.01);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateRegularStarPathIntDoubleDoubleDoubleDoubleR10() {
		PathsUtil.createRegularStarPaths(3, 0.0, 0.0, 0.0, 2.0);
	}

	@Test
	public void testCreateRegularStarPathIntDoubleDoubleDoubleDoubleR1EqualR2() {
		final Paths star = PathsUtil.createRegularStarPaths(3, 1.0, 0.0, 1.0,
				1.0);
		final Paths triangle = PathsUtil.createRegularPolygonPaths(3, 1.0, 0.0,
				1.0);
		assertPathsEquals(star, triangle);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateRegularStarPathIntDoubleDoubleDoubleDoubleR20() {
		PathsUtil.createRegularStarPaths(3, 0.0, 0.0, 1.0, 0.0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateRegularStarPathIntDoubleDoubleDoubleDoubleSides2() {
		PathsUtil.createRegularStarPaths(2, 0.0, 0.0, 1.0, 2.0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateRegularStarPathIntDoubleDoubleDoubleDoubleSidesNegative() {
		PathsUtil.createRegularStarPaths(-1, 0.0, 0.0, 1.0, 2.0);
	}

	@Test
	public void testCreateRegularStarPathIntIntDoubleDoubleDouble() {
		final Paths star = PathsUtil.createRegularStarPath(5, 2, 1.0, 0.0, 1.0);
		final double[] xA = star.getXArray();
		final double[] yA = star.getYArray();
		Assert.assertArrayEquals(xA, new double[] { 1, 0.412, 1.951, 0.049,
				1.588 }, 0.01);
		Assert.assertArrayEquals(yA, new double[] { 1, -0.809, 0.309, 0.309,
				-0.809 }, 0.01);
		final Paths star1 = PathsUtil
				.createRegularStarPath(5, 3, 1.0, 0.0, 1.0);
		final double[] xA1 = star1.getXArray();
		final double[] yA1 = star1.getYArray();
		Assert.assertArrayEquals(xA, xA1, 0.01);
		Assert.assertArrayEquals(yA, yA1, 0.01);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateRegularStarPathIntIntDoubleDoubleDoubleQEqual1() {
		PathsUtil.createRegularStarPath(5, 1, 0.0, 0.0, 1.0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateRegularStarPathIntIntDoubleDoubleDoubleQEqualHalfP() {
		PathsUtil.createRegularStarPath(6, 3, 0.0, 0.0, 1.0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateRegularStarPathIntIntDoubleDoubleDoubleQEqualP() {
		PathsUtil.createRegularStarPath(5, 5, 0.0, 0.0, 1.0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateRegularStarPathIntIntDoubleDoubleDoubleRadius0() {
		PathsUtil.createRegularStarPath(5, 2, 0.0, 0.0, 0.0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateRegularStarPathIntIntDoubleDoubleDoubleRadiusNegative() {
		PathsUtil.createRegularStarPath(5, 2, 0.0, 0.0, -1.0);
	}

	@Test
	public void testCreateRelativePath() {
		final double[] xArray = { 1, 2, 3, 3, 5 };
		final double[] yArray = { 2, 3, 4, 5, 5 };
		final String result = pathsUtil.renderRelativePaths(xArray, yArray,
				false, true);
		Assert.assertEquals("M1.0 2.0l2.0 3.0l3.0 4.0v5.0h5.0z", result);
	}

	@Test
	public void testCreateRelativePathNoPoint() {
		final double[] xArray = {};
		final double[] yArray = {};
		final String result = pathsUtil.renderRelativePaths(xArray, yArray,
				false, true);
		Assert.assertEquals("", result);
	}

	@Test
	public void testCreateRelativePathOnePoint() {
		final double[] xArray = { 1 };
		final double[] yArray = { 2 };
		final String result = pathsUtil.renderRelativePaths(xArray, yArray,
				false, true);
		Assert.assertEquals("M1.0 2.0", result);
	}

	@Test
	public void testCreateRelativePathRelativeMove() {
		final double[] xArray = { 1, 2, 3, 3, 5 };
		final double[] yArray = { 2, 3, 4, 5, 5 };
		final String result = pathsUtil.renderRelativePaths(xArray, yArray,
				true, true);
		Assert.assertEquals("m1.0 2.0l2.0 3.0l3.0 4.0v5.0h5.0z", result);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateRelativePathXandYArrayLengthDifferent() {
		final double[] xArray = { 1, 2 };
		final double[] yArray = { 2 };

		pathsUtil.renderRelativePaths(xArray, yArray, false, true);
	}

	@Test
	public void testCreateSquarePath() {
		Paths square = PathsUtil.createSquarePaths(0, 0, 2);
		Assert.assertNotNull(square);
		Assert.assertEquals(4, square.getXArray().length);
		Assert.assertEquals(4, square.getYArray().length);
		Assert.assertEquals(-1.0, square.getXArray()[0], 0.01);
		Assert.assertEquals(1.0, square.getXArray()[1], 0.01);
		Assert.assertEquals(1.0, square.getXArray()[2], 0.01);
		Assert.assertEquals(-1.0, square.getXArray()[3], 0.01);
		Assert.assertEquals(-1.0, square.getYArray()[0], 0.01);
		Assert.assertEquals(-1.0, square.getYArray()[1], 0.01);
		Assert.assertEquals(1.0, square.getYArray()[2], 0.01);
		Assert.assertEquals(1.0, square.getYArray()[3], 0.01);

		square = PathsUtil.createSquarePaths(1, 1, 2);
		Assert.assertNotNull(square);
		Assert.assertEquals(4, square.getXArray().length);
		Assert.assertEquals(4, square.getYArray().length);
		Assert.assertEquals(0.0, square.getXArray()[0], 0.01);
		Assert.assertEquals(2.0, square.getXArray()[1], 0.01);
		Assert.assertEquals(2.0, square.getXArray()[2], 0.01);
		Assert.assertEquals(0.0, square.getXArray()[3], 0.01);
		Assert.assertEquals(0.0, square.getYArray()[0], 0.01);
		Assert.assertEquals(0.0, square.getYArray()[1], 0.01);
		Assert.assertEquals(2.0, square.getYArray()[2], 0.01);
		Assert.assertEquals(2.0, square.getYArray()[3], 0.01);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateSquarePathSide0() {
		PathsUtil.createSquarePaths(0, 0, 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateSquarePathSideNegative() {
		PathsUtil.createSquarePaths(0, 0, -1);
	}

	@Test
	public void testCreateStarPlotPaths() {
		final Paths paths = PathsUtil.createStarPlotPaths(1, 1, new double[] {
				1.0,
				1.0, 1.0 });
		final Paths expected = Paths.newPaths(new double[] { 2.0, 0.5, 0.5 },
				new double[] { 1.0, 1.866025, 0.133975 });
		assertPathsEquals(expected, paths);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateStarPlotPathsRadiusesLengthLessThan3() {
		PathsUtil.createStarPlotPaths(0, 0, new double[] { 1.0, 1.0 });
	}

	@Test(expected = NullPointerException.class)
	public void testCreateStarPlotPathsRadiusesNull() {
		PathsUtil.createStarPlotPaths(0, 0, null);
	}

	@Test
	public void testFlipDoubleDoublePaths() {
		final Paths square = PathsUtil.createSquarePaths(0.0, 0.0, 2);
		PathsUtil.flip(1.0, 0.0, square);
		Assert.assertArrayEquals(new double[] { -1, -1, 1, 1 },
				square.getXArray(), 0.001);
		Assert.assertArrayEquals(new double[] { -1, 1, 1, -1 },
				square.getYArray(), 0.001);
		PathsUtil.flip(-1.0, 0.0, square);
		Assert.assertArrayEquals(new double[] { 1, -1, -1, 1 },
				square.getXArray(), 0.001);
		Assert.assertArrayEquals(new double[] { 1, 1, -1, -1 },
				square.getYArray(), 0.001);
		PathsUtil.flip(1.0, 1.0, square);
		Assert.assertArrayEquals(new double[] { 0, 0, -2, -2 },
				square.getXArray(), 0.001);
		Assert.assertArrayEquals(new double[] { 2, 0, 0, 2 },
				square.getYArray(), 0.001);
	}

	@Test
	public void testFlipDoubleDoublePathsSlope0() {
		final Paths square = PathsUtil.createSquarePaths(0.0, 0.0, 2);
		PathsUtil.flip(0.0, 1.0, square);
		Assert.assertArrayEquals(new double[] { -1, 1, 1, -1 },
				square.getXArray(), 0.001);
		Assert.assertArrayEquals(new double[] { 3, 3, 1, 1 },
				square.getYArray(), 0.001);
	}

	@Test
	public void testFlipDoublePaths() {
		final Paths square = PathsUtil.createSquarePaths(0.0, 0.0, 2);
		PathsUtil.flip(0, square);
		Assert.assertArrayEquals(new double[] { 1, -1, -1, 1 },
				square.getXArray(), 0.001);
		Assert.assertArrayEquals(new double[] { -1, -1, 1, 1 },
				square.getYArray(), 0.001);
	}

	@Test
	public void testFlipDoublePathsEmptyPaths() {
		PathsUtil.flip(0);
	}

	@Test
	public void testRenderRaysFromOrigin2Test() {
		// tests
		// 1. the omitted at: (0.0, 0.0)
		// 2. the horizontal ray at: (3.0, 0.0)
		// 3. the vertical ray at: (0.0, 1.0)
		final String result = pathsUtil.renderRaysFromOrigin(0, 0,
				new double[] { 1, 0, 3, 0 }, new double[] { 2, 0, 0, 1 });
		Assert.assertEquals("M0.0 0.0L1.0 2.0M0.0 0.0H3.0M0.0 0.0V1.0", result);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRenderRaysFromOriginArraysLengthDifferentTest() {
		pathsUtil.renderRaysFromOrigin(0, 0, new double[] { 1.0 },
				new double[] { 1.0, 2.0 });
	}

	@Test
	public void testRenderRaysFromOriginTest() {
		final Paths paths = PathsUtil.createStarPlotPaths(1, 1, new double[] {
				1.0,
				1.0, 1.0 });
		PathsUtil.truncateDecimals(0.001, 0.001, paths);
		final String result = pathsUtil.renderRaysFromOrigin(1, 1,
				paths.getXArray(),
				paths.getYArray());
		Assert.assertEquals("M1.0 1.0H2.0M1.0 1.0L0.5 1.866M1.0 1.0L0.5 0.134",
				result);
	}

	@Test(expected = NullPointerException.class)
	public void testRenderRaysFromOriginXArrayNullTest() {
		pathsUtil.renderRaysFromOrigin(0, 0, null, new double[] { 1.0 });
	}

	@Test(expected = NullPointerException.class)
	public void testRenderRaysFromOriginYArrayNullTest() {
		pathsUtil.renderRaysFromOrigin(0, 0, new double[] { 1.0 }, null);
	}

	@Test
	public void testRotate() {
		final Paths triangle = PathsUtil.createRegularPolygonPaths(3, 1.0, 0.0,
				1.0);
		final Paths triangle1 = PathsUtil.createRegularPolygonPaths(3, 1.0,
				0.0, 1.0);
		PathsUtil.rotate(1.0, 0.0, 120.0, triangle);
		Assert.assertEquals(triangle.getXArray()[0], triangle1.getXArray()[1],
				0.01);
		Assert.assertEquals(triangle.getYArray()[0], triangle1.getYArray()[1],
				0.01);
		Assert.assertEquals(triangle.getXArray()[1], triangle1.getXArray()[2],
				0.01);
		Assert.assertEquals(triangle.getYArray()[1], triangle1.getYArray()[2],
				0.01);
		Assert.assertEquals(triangle.getXArray()[2], triangle1.getXArray()[0],
				0.01);
		Assert.assertEquals(triangle.getYArray()[2], triangle1.getYArray()[0],
				0.01);
	}

	@Test
	public void testRotate0Or360Degree() {
		final Paths triangle = PathsUtil.createRegularPolygonPaths(3, 1.0, 0.0,
				1.0);
		final Paths triangle1 = PathsUtil.createRegularPolygonPaths(3, 1.0,
				0.0, 1.0);
		PathsUtil.rotate(1.0, 0.0, 0.0, triangle);
		assertPathsEquals(triangle, triangle1);
		PathsUtil.rotate(1.0, 0.0, 360.0, triangle);
		assertPathsEquals(triangle, triangle1);
	}

	@Test
	public void testRotateNoPaths() {
		PathsUtil.rotate(0.0, 0.0, 30.0);
	}

	@Test
	public void testRotateTwoPaths() {
		final Paths square = PathsUtil.createSquarePaths(0.5, 0.5, 1.0);
		final Paths square1 = PathsUtil.createSquarePaths(-0.5, 0.5, 1.0);
		PathsUtil.rotate(0.0, 0.0, 90, square, square1);
		Assert.assertArrayEquals(new double[] { 0, 0, -1, -1 },
				square.getXArray(), 0.01);
		Assert.assertArrayEquals(new double[] { 0, 1, 1, 0 },
				square.getYArray(), 0.01);
		Assert.assertArrayEquals(new double[] { 0, 0, -1, -1 },
				square1.getXArray(), 0.01);
		Assert.assertArrayEquals(new double[] { -1, 0, 0, -1 },
				square1.getYArray(), 0.01);
	}

	@Test
	public void testScaleDoubleDoublePathsArray() {
		final Paths square = PathsUtil.createSquarePaths(0.5, 0.5, 1.0);
		final Paths square1 = PathsUtil.createSquarePaths(0.25, 0.25, 0.5);
		PathsUtil.scale(2, 1, square, square1);
		Assert.assertArrayEquals(new double[] { 0, 2, 2, 0 },
				square.getXArray(), 0.01);
		Assert.assertArrayEquals(new double[] { 0, 0, 1, 1 },
				square.getYArray(), 0.01);
		Assert.assertArrayEquals(new double[] { 0, 1, 1, 0 },
				square1.getXArray(), 0.01);
		Assert.assertArrayEquals(new double[] { 0, 0, 0.5, 0.5 },
				square1.getYArray(), 0.01);
	}

	@Test
	public void testScaleDoubleDoublePathsArrayNonZeroZero() {
		final Paths square = PathsUtil.createSquarePaths(0.5, 0.5, 1.0);
		final Paths square1 = PathsUtil.createSquarePaths(0.25, 0.25, 0.5);
		PathsUtil.scale(2, 0, square, square1);
		Assert.assertArrayEquals(new double[] { 0, 2, 2, 0 },
				square.getXArray(), 0.01);
		Assert.assertArrayEquals(new double[] { 0, 0, 0, 0 },
				square.getYArray(), 0.01);
		Assert.assertArrayEquals(new double[] { 0, 1, 1, 0 },
				square1.getXArray(), 0.01);
		Assert.assertArrayEquals(new double[] { 0, 0, 0, 0 },
				square1.getYArray(), 0.01);
	}

	@Test
	public void testScaleDoubleDoublePathsArrayZeroNonZero() {
		final Paths square = PathsUtil.createSquarePaths(0.5, 0.5, 1.0);
		final Paths square1 = PathsUtil.createSquarePaths(0.25, 0.25, 0.5);
		PathsUtil.scale(0, 1, square, square1);
		Assert.assertArrayEquals(new double[] { 0, 0, 0, 0 },
				square.getXArray(), 0.01);
		Assert.assertArrayEquals(new double[] { 0, 0, 1, 1 },
				square.getYArray(), 0.01);
		Assert.assertArrayEquals(new double[] { 0, 0, 0, 0 },
				square1.getXArray(), 0.01);
		Assert.assertArrayEquals(new double[] { 0, 0, 0.5, 0.5 },
				square1.getYArray(), 0.01);
	}

	@Test
	public void testScaleDoublePathsArray() {
		final Paths square = PathsUtil.createSquarePaths(0.5, 0.5, 1.0);
		final Paths square1 = PathsUtil.createSquarePaths(0.25, 0.25, 0.5);
		PathsUtil.scale(2, square, square1);
		Assert.assertArrayEquals(new double[] { 0, 2, 2, 0 },
				square.getXArray(), 0.01);
		Assert.assertArrayEquals(new double[] { 0, 0, 2, 2 },
				square.getYArray(), 0.01);
		Assert.assertArrayEquals(new double[] { 0, 1, 1, 0 },
				square1.getXArray(), 0.01);
		Assert.assertArrayEquals(new double[] { 0, 0, 1, 1 },
				square1.getYArray(), 0.01);
	}

	@Test
	public void testScaleDoublePathsArrayFactor0() {
		final Paths square = PathsUtil.createSquarePaths(0.5, 0.5, 1.0);
		final Paths square1 = PathsUtil.createSquarePaths(0.25, 0.25, 0.5);
		PathsUtil.scale(0, square, square1);
		final double[] zeros = new double[] { 0, 0, 0, 0 };
		Assert.assertArrayEquals(zeros, square.getXArray(), 0.01);
		Assert.assertArrayEquals(zeros, square.getYArray(), 0.01);
		Assert.assertArrayEquals(zeros, square1.getXArray(), 0.01);
		Assert.assertArrayEquals(zeros, square1.getYArray(), 0.01);
	}

	@Test
	public void testScaleDoublePathsArrayNoPaths() {
		PathsUtil.scale(2);
	}

	@Test
	public void testTranslate0Delta() {
		final Paths square = PathsUtil.createSquarePaths(-0.5, 0.5, 1.0);
		final Paths square1 = PathsUtil.createSquarePaths(-0.5, 0.5, 1.0);
		PathsUtil.translate(0.0, 0.0, square, square1);
		assertPathsEquals(square, square1);
	}

	@Test
	public void testTranslateNoPaths() {
		PathsUtil.translate(1.0, 1.0);
	}

	@Test
	public void testTranslateTwoPaths() {
		final Paths square = PathsUtil.createSquarePaths(-0.5, 0.5, 1.0);
		final Paths square1 = PathsUtil.createSquarePaths(-0.5, 0.5, 1.0);
		final Paths square2 = PathsUtil.createSquarePaths(0.5, 0.5, 1.0);
		PathsUtil.translate(1.0, 0.0, square, square1);
		assertPathsEquals(square, square1);
		assertPathsEquals(square2, square1);
	}

	private void assertPathsEquals(final Paths paths1, final Paths paths2) {
		Assert.assertArrayEquals(paths1.getXArray(), paths2.getXArray(), 0.001);
		Assert.assertArrayEquals(paths1.getYArray(), paths2.getYArray(), 0.001);
	}
}