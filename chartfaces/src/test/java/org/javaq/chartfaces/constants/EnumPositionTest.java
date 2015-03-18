package org.javaq.chartfaces.constants;

import junit.framework.Assert;

import org.junit.Test;

public class EnumPositionTest {

	@Test
	public void testFrom() {
		EnumPosition[] positions = EnumPosition.values();
		for (EnumPosition pos : positions) {
			Assert.assertSame(pos, EnumPosition.from(pos.name()));
		}
	}

	@Test
	public void testIsLeft() {
		Assert.assertTrue(EnumPosition.left.isLeftRight());
		Assert.assertTrue(EnumPosition.right.isLeftRight());
		Assert.assertFalse(EnumPosition.top.isLeftRight());
		Assert.assertFalse(EnumPosition.bottom.isLeftRight());
		Assert.assertTrue(EnumPosition.top.isTopBottom());
		Assert.assertTrue(EnumPosition.bottom.isTopBottom());
		Assert.assertFalse(EnumPosition.left.isTopBottom());
		Assert.assertFalse(EnumPosition.right.isTopBottom());
	}
}
