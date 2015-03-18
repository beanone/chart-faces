package org.javaq.chartfaces.dataspec.impl;

import java.util.Arrays;

import junit.framework.Assert;

import org.javaq.chartfaces.dataspec.RangeFinder;
import org.javaq.chartfaces.util.NumberRange;
import org.junit.Test;


public class RangeFinderTest {

	@Test
	public void testFindRangeEmptyArray() {
		final NumberRange nr = RangeFinder.findRange(new int[0]);
		Assert.assertNotNull(nr);
		Assert.assertNull(nr.getMin());
		Assert.assertNull(nr.getMax());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFindRangeNullValue() {
		RangeFinder.findRange(null);
	}

	@Test
	public void testFindRangeOneValue() {
		final NumberRange nr = RangeFinder.findRange(new int[] { 1 });
		Assert.assertNotNull(nr);
		Assert.assertEquals(1.0, nr.getMin());
		Assert.assertNull(nr.getMax());
	}

	@Test
	public void testFindRangeTwoValue() {
		NumberRange nr = RangeFinder.findRange(new int[] { 2, 1 });
		Assert.assertNotNull(nr);
		Assert.assertEquals(1.0, nr.getMin());
		Assert.assertEquals(2.0, nr.getMax());
		nr = RangeFinder.findRange(Arrays.asList(new Integer[] { 2, 1 }));
		Assert.assertNotNull(nr);
		Assert.assertEquals(1.0, nr.getMin());
		Assert.assertEquals(2.0, nr.getMax());
		nr = RangeFinder.findRange(new String[] { "2", "1" });
		Assert.assertNotNull(nr);
		Assert.assertEquals(1.0, nr.getMin());
		Assert.assertEquals(2.0, nr.getMax());
	}
}
