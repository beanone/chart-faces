package org.javaq.chartfaces.render.tool.impl;

import junit.framework.Assert;

import org.junit.Test;





public class BoxModelTest {

	@Test(expected = IllegalArgumentException.class)
	public void testBoxModelHeightNegative() {
		new BoxModel(10, -1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBoxModelHeightZero() {
		new BoxModel(10, 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBoxModelWidthNegative() {
		new BoxModel(-1, 10);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBoxModelWidthZero() {
		new BoxModel(0, 10);
	}

	@Test
	public void testGetChildren() {
		final BoxModel bm = new BoxModel(10, 10);
		Assert.assertEquals(0, bm.getChildren().size());
		bm.newChild(1, 1, 1, 1);
		Assert.assertEquals(1, bm.getChildren().size());
		bm.newChild(2, 2, 2, 2);
		Assert.assertEquals(2, bm.getChildren().size());
	}

	@Test
	public void testGetOriginXOriginY() {
		BoxModel bm = new BoxModel(10, 10);
		Assert.assertEquals(0, bm.getOriginX());
		Assert.assertEquals(0, bm.getOriginY());
		bm = bm.newChild(1, 1, 1, 1);
		Assert.assertEquals(1, bm.getOriginX());
		Assert.assertEquals(1, bm.getOriginY());
	}

	@Test
	public void testGetParent() {
		final BoxModel bm = new BoxModel(10, 10);
		Assert.assertSame(bm, bm.newChild(1, 1, 1, 1).getParent());
	}

	@Test
	public void testGetViewBox() {
		BoxModel bm = new BoxModel(10, 10);
		Assert.assertEquals("0 0 10 10", bm.getViewBoxString());
		bm = bm.newChild(1, 1, 1, 1);
		Assert.assertEquals("0 0 8 8", bm.getViewBoxString());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNewChildBottomMarginNegative() {
		new BoxModel(10, 10).newChild(1, 1, 1, -1);
	}

	@Test
	public void testNewChildIncorrectDimension() {
		final BoxModel bm = new BoxModel(10, 10);
		Assert.assertNull(bm.newChild(10, 1, 1, 1));
		Assert.assertNull(bm.newChild(1, 10, 1, 1));
		Assert.assertNull(bm.newChild(1, 1, 10, 1));
		Assert.assertNull(bm.newChild(1, 1, 1, 10));
		Assert.assertNull(bm.newChild(5, 5, 1, 1));
		Assert.assertNull(bm.newChild(1, 1, 5, 5));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNewChildLeftMarginNegative() {
		new BoxModel(10, 10).newChild(-1, 1, 1, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNewChildRightMarginNegative() {
		new BoxModel(10, 10).newChild(1, -1, 1, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNewChildTopMarginNegative() {
		new BoxModel(10, 10).newChild(1, 1, -1, 1);
	}
}