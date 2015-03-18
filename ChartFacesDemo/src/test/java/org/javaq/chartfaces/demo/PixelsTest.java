package org.javaq.chartfaces.demo;

import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import org.javaq.chartfaces.demo.Pixels;
import org.junit.Test;

public class PixelsTest {
	private final int[][] colorsArray = new int[][] {
			{ 1, 1, 1, 1, 1, 1, 1, 2, 1 },
			{ 1, 1, 1, 1, 1, 2, 2, 3, 2 },
			{ 1, 1, 1, 2, 2, 2, 1, 1, 1 },
			{ 1, 1, 2, 2, 2, 3, 2, 2, 1 },
			{ 1, 1, 2, 2, 3, 3, 2, 2, 2 },
			{ 1, 2, 2, 3, 3, 3, 2, 2, 2 },
			{ 1, 1, 1, 2, 2, 2, 2, 2, 1 },
			{ 1, 1, 1, 2, 1, 1, 1, 2, 1 }
	};

	private final List<Integer> expectedX = Arrays.asList(new Integer[] { 0, 3,
			5, 6, 7, 8, 5, 6, 7, 8, 3, 5, 6, 7, 8, 0, 2, 5, 6, 8, 2, 3, 4, 8,
			0, 1, 2, 3, 6, 8, 0, 2, 3, 4, 5, 8, 2, 3, 4, 5, 6, 7, 8 });

	private final List<Integer> expectedY = Arrays.asList(new Integer[] { 0, 0,
			0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 4, 4, 4, 4,
			5, 5, 5, 5, 5, 5, 6, 6, 6, 6, 6, 6, 7, 7, 7, 7, 7, 7, 7 });

	private final List<Integer> expectedColors = Arrays.asList(new Integer[] {
			1, 1, 1, 1, 2, 1, 2, 2, 3, 2, 2, 2, 1, 1, 1, 1, 2, 3, 2, 1, 2, 2,
			3, 2, 1, 2, 2, 3, 2, 2, 1, 1, 2, 2, 2, 1, 1, 2, 1, 1, 1, 2, 1 });

	private final List<Integer> expectedSizes = Arrays.asList(new Integer[] {
			3, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1,
			2, 1, 1, 1, 1, 1, 2, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 });

	@Test
	public void testFindSquareBottomRight() {
		Pixels pixels = new Pixels(colorsArray);
		int[] p = pixels.findSquareBottomRight(0, 0);
		Assert.assertNotNull(p);
		Assert.assertEquals(2, p[0]);
		Assert.assertEquals(2, p[1]);

		p = pixels.findSquareBottomRight(3, 0);
		Assert.assertNotNull(p);
		Assert.assertEquals(4, p[0]);
		Assert.assertEquals(1, p[1]);

		p = pixels.findSquareBottomRight(5, 0);
		Assert.assertNotNull(p);
		Assert.assertEquals(5, p[0]);
		Assert.assertEquals(0, p[1]);

		p = pixels.findSquareBottomRight(7, 0);
		Assert.assertNotNull(p);
		Assert.assertEquals(7, p[0]);
		Assert.assertEquals(0, p[1]);
	}

	@Test
	public void testCompress() {
		Pixels pixels = new Pixels(colorsArray);
		pixels.compress();
		List<Integer> xcoords = pixels.getXcoords();
		List<Integer> ycoords = pixels.getYcoords();
		List<Integer> colors = pixels.getColors();
		List<Integer> sizes = pixels.getSizes();
		assertListEquals(expectedX, xcoords);
		assertListEquals(expectedY, ycoords);
		assertListEquals(expectedColors, colors);
		assertListEquals(expectedSizes, sizes);
	}

	private void assertListEquals(List<? extends Object> l1,
			List<? extends Object> l2) {
		Assert.assertEquals(l1.size(), l2.size());
		for (int i = 0; i < l1.size(); i++) {
			Assert.assertEquals(l1.get(i), l2.get(i));
		}
	}
}