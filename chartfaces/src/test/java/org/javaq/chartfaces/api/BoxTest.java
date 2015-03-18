package org.javaq.chartfaces.api;

import org.junit.Test;





public class BoxTest {

	@Test
	public void testBoxIntInt() {
		new Box(1, 1);
	}

	@Test
	public void testBoxIntIntIntInt() {
		new Box(1, 1, 1, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBoxIntIntIntIntNegativeHeight() {
		new Box(1, 1, 1, -1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBoxIntIntIntIntNegativeOriginX() {
		new Box(-1, 1, 1, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBoxIntIntIntIntNegativeOriginY() {
		new Box(-1, 1, 1, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBoxIntIntIntIntNegativeWidth() {
		new Box(1, 1, -1, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBoxIntIntIntIntZeroHeight() {
		new Box(1, 1, 1, 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBoxIntIntIntIntZeroWidth() {
		new Box(1, 1, 0, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBoxIntIntNegativeHeight() {
		new Box(1, -1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBoxIntIntNegativeWidth() {
		new Box(-1, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBoxIntIntZeroHeight() {
		new Box(1, 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBoxIntIntZeroWidth() {
		new Box(0, 1);
	}
}
