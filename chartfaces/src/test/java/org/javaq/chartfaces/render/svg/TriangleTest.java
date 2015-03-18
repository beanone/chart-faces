package org.javaq.chartfaces.render.svg;

import org.junit.Assert;
import org.junit.Test;



public class TriangleTest {

	@Test
	public void testGetBaseAngle() {
		final Triangle triangle = new Triangle(1);
		Assert.assertEquals(0.0, triangle.getBaseAngle(), 0.001);
		triangle.getPaths();
		Assert.assertEquals(0.0, triangle.getBaseAngle(), 0.001);
	}

	@Test
	public void testGetPathsSetBaseAngle() {
		final Triangle t = new Triangle(1);
		Paths paths = t.getPaths();
		final Paths paths1 = PathsUtil.createRegularPolygonPaths(3, 0.0, 0.0, 1);
		Assert.assertArrayEquals(paths1.getXArray(), paths.getXArray(), 0.001);
		Assert.assertArrayEquals(paths1.getYArray(), paths.getYArray(), 0.001);

		t.setBaseAngle(45);
		paths = t.getPaths();
		Assert.assertArrayEquals(new double[] { 0, -1, 1 }, paths.getXArray(),
				0.001);
		Assert.assertArrayEquals(new double[] { 1, 0, 0 }, paths.getYArray(),
				0.001);
	}

	@Test
	public void testGetPathsSetTopAngle() {
		final Triangle t = new Triangle(1);
		Paths paths = t.getPaths();
		final Paths paths1 = PathsUtil.createRegularPolygonPaths(3, 0.0, 0.0, 1);
		Assert.assertArrayEquals(paths1.getXArray(), paths.getXArray(), 0.001);
		Assert.assertArrayEquals(paths1.getYArray(), paths.getYArray(), 0.001);

		t.setTopAngle(90);
		paths = t.getPaths();
		Assert.assertArrayEquals(new double[] { 0, -1, 1 }, paths.getXArray(),
				0.001);
		Assert.assertArrayEquals(new double[] { 1, 0, 0 }, paths.getYArray(),
				0.001);

		t.setTopAngle(90);
		t.setBaseAngle(60);
		paths = t.getPaths();
		Assert.assertArrayEquals(new double[] { 0.5, -1, 1 },
				paths
						.getXArray(),
				0.001);
		Assert.assertArrayEquals(new double[] { 0.866, 0, 0 }, paths
				.getYArray(),
				0.001);

		t.setBaseAngle(30);
		paths = t.getPaths();
		Assert.assertArrayEquals(new double[] { -0.5, 1, -1 },
				paths.getXArray(), 0.001);
		Assert.assertArrayEquals(new double[] { 0.866, 0, 0 }, paths
				.getYArray(), 0.001);
	}

	@Test
	public void testGetRadius() {
		final Triangle triangle = new Triangle(1);
		Assert.assertEquals(1.0, triangle.getRadius(), 0.001);
		triangle.getPaths();
		Assert.assertEquals(1.0, triangle.getRadius(), 0.001);
	}

	@Test
	public void testGetTopAngle() {
		final Triangle triangle = new Triangle(1);
		Assert.assertEquals(0.0, triangle.getTopAngle(), 0.001);
		triangle.getPaths();
		Assert.assertEquals(0.0, triangle.getTopAngle(), 0.001);
	}

	@Test
	public void testTriangle() {
		new Triangle(1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testTriangleRadius0() {
		new Triangle(0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testTriangleRadiusNegative() {
		new Triangle(-1);
	}
}
