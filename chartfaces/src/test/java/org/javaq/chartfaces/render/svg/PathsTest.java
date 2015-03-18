package org.javaq.chartfaces.render.svg;

import org.junit.Assert;
import org.junit.Test;



public class PathsTest {

	@Test
	public void testBoxMe() {
		final double[][] box = Paths.newPaths(new double[] { 3.31, 1.02, 2.23 },
				new double[] { 2.00, 1.10, 3.42 }).boxMe();
		Assert.assertArrayEquals(
				new double[][] { { 1.02, 3.31 }, { 1.1, 3.42 } }, box);
	}

	@Test
	public void testBoxMeDouble() {
		final double[][] box = Paths.newPaths(new double[] { 100.31, 0.31, 2.23 },
				new double[] { 12.00, 10.12, 110.12 }).boxMe(0.001);
		Assert.assertArrayEquals(new double[][] { { 0.3, 100.4, -1.0 },
				{ 10.1, 110.2, -1.0 } }, box);
	}

	@Test
	public void testPathsDoubleArrayDoubleArray() {
		final Paths paths = Paths.newPaths(new double[] { 1 }, new double[] { 2 });
		Assert.assertNotNull(paths.getXArray());
		Assert.assertNotNull(paths.getYArray());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testPathsDoubleArrayDoubleArrayDifferentSize() {
		Paths.newPaths(new double[] { 1.0 }, new double[] {});
	}

	@Test
	public void testPathsDoubleArrayDoubleArrayEmptyEmpty() {
		final Paths paths = Paths.newPaths(new double[] {}, new double[] {});
		Assert.assertNotNull(paths.getXArray());
		Assert.assertNotNull(paths.getYArray());
	}

	@Test(expected = NullPointerException.class)
	public void testPathsDoubleArrayDoubleArrayNonNullNull() {
		Paths.newPaths(new double[] {}, null);
	}

	@Test(expected = NullPointerException.class)
	public void testPathsDoubleArrayDoubleArrayNullNonNull() {
		Paths.newPaths(null, new double[] {});
	}

	@Test(expected = NullPointerException.class)
	public void testPathsDoubleArrayDoubleArrayNullNull() {
		Paths.newPaths(null, null);
	}

	@Test(expected = NullPointerException.class)
	public void testRegularStarPath0Polygon1NullTest() {
		final Paths outerPolygon = PathsUtil.createRegularPolygonPaths(5, 0, 0, 2);
		Paths.newStarPaths(null, outerPolygon);
	}

	@Test(expected = NullPointerException.class)
	public void testRegularStarPath0Polygon2NullTest() {
		final Paths innerPolygon = PathsUtil.createRegularPolygonPaths(3, 0, 0, 2);
		Paths.newStarPaths(innerPolygon, null);
	}

	@Test(expected = NullPointerException.class)
	public void testRegularStarPath0PolygonsNullTest() {
		Paths.newStarPaths(null, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRegularStarPath0PolygonsSizeDifferentTest() {
		final Paths innerPolygon = PathsUtil.createRegularPolygonPaths(3, 0, 0, 1);
		final Paths outerPolygon = PathsUtil.createRegularPolygonPaths(5, 0, 0, 2);
		Paths.newStarPaths(innerPolygon, outerPolygon);
	}

	@Test
	public void testRegularStarPath0Test() {
		final Paths innerPolygon = PathsUtil.createRegularPolygonPaths(3, 0, 0, 1);
		final Paths outerPolygon = PathsUtil.createRegularPolygonPaths(3, 0, 0, 2);
		Paths.newStarPaths(innerPolygon, outerPolygon);
	}

	@Test(expected = NullPointerException.class)
	public void testRegularStarPath1PolygonNullTest() {
		Paths.newStarPaths(null, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRegularStarPath1PTooBigTest() {
		final Paths polygon = PathsUtil.createRegularPolygonPaths(10, 0, 0, 1);
		Paths.newStarPaths(polygon, 5);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRegularStarPath1PTooSmallTest() {
		final Paths polygon = PathsUtil.createRegularPolygonPaths(3, 0, 0, 1);
		Paths.newStarPaths(polygon, 0);
	}

	@Test
	public void testRegularStarPath1Test() {
		Paths polygon = PathsUtil.createRegularPolygonPaths(3, 0, 0, 1);
		Paths.newStarPaths(polygon, 1);
		polygon = PathsUtil.createRegularPolygonPaths(4, 0, 0, 1);
		Paths.newStarPaths(polygon, 1);
		polygon = PathsUtil.createRegularPolygonPaths(10, 0, 0, 1);
		Paths.newStarPaths(polygon, 1);
		polygon = PathsUtil.createRegularPolygonPaths(10, 0, 0, 1);
		Paths.newStarPaths(polygon, 4);
	}
}