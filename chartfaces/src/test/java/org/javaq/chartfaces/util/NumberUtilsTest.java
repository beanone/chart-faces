package org.javaq.chartfaces.util;

import junit.framework.Assert;

import org.junit.Test;

public class NumberUtilsTest {

	@Test
	public void testCeil() {
		Assert.assertEquals(400.0, NumberUtils.ceil(314.15926535897, 2));
		Assert.assertEquals(320.0, NumberUtils.ceil(314.15926535897, 1));
		Assert.assertEquals(315.0, NumberUtils.ceil(314.15926535897, 0));
		Assert.assertEquals(314.2, NumberUtils.ceil(314.15926535897, -1));
		Assert.assertEquals(314.16, NumberUtils.ceil(314.15926535897, -2));
		Assert.assertEquals(314.15927, NumberUtils.ceil(314.15926535897, -5));
	}

	@Test
	public void testFloor() {
		Assert.assertEquals(300.0, NumberUtils.floor(314.15926535897, 2));
		Assert.assertEquals(310.0, NumberUtils.floor(314.15926535897, 1));
		Assert.assertEquals(314.0, NumberUtils.floor(314.15926535897, 0));
		Assert.assertEquals(314.1, NumberUtils.floor(314.15926535897, -1));
		Assert.assertEquals(314.15, NumberUtils.floor(314.15926535897, -2));
		Assert.assertEquals(314.15926, NumberUtils.floor(314.15926535897, -5));
	}

	@Test
	public void testFormat() {
		Assert.assertEquals(314.2, NumberUtils.truncate(314.15926535897, -1));
		Assert.assertEquals(314.16, NumberUtils.truncate(314.15926535897, -2));
		Assert.assertEquals(314.159, NumberUtils.truncate(314.15926535897, -3));
		Assert.assertEquals(314.15927, NumberUtils.truncate(314.15926535897, -5));
	}

	public void testFormatSigNumIndex0() {
		Assert.assertEquals(314.0, NumberUtils.truncate(314.15926535897, 0));
	}

	public void testFormatSigNumIndexPositive() {
		Assert.assertEquals(300.0, NumberUtils.truncate(314.15926535897, 2));
	}

	@Test
	public void testGetMostSignificantDigit() {
		Assert.assertEquals(0, NumberUtils.getMostSignificantDigit(0));
		Assert.assertEquals(1, NumberUtils.getMostSignificantDigit(0.1415));
		Assert.assertEquals(4, NumberUtils.getMostSignificantDigit(0.0415));
		Assert.assertEquals(3, NumberUtils.getMostSignificantDigit(3.1415));
		Assert.assertEquals(3,
				NumberUtils.getMostSignificantDigit(0.000031415));
		Assert.assertEquals(5, NumberUtils.getMostSignificantDigit(-5.71428));
		Assert.assertEquals(1, NumberUtils.getMostSignificantDigit(-0.01428));
	}

	@Test
	public void testGetMostSignificantIndex() {
		// for positive numbers
		Assert.assertEquals(0, NumberUtils.getMostSignificantIndex(0));
		Assert.assertEquals(0, NumberUtils.getMostSignificantIndex(3.1415));
		Assert.assertEquals(1, NumberUtils.getMostSignificantIndex(31.415));
		Assert.assertEquals(-1, NumberUtils.getMostSignificantIndex(0.31415));
		Assert.assertEquals(-5,
				NumberUtils.getMostSignificantIndex(0.000031415));
		
		// the same is for negative numbers
		Assert.assertEquals(0, NumberUtils.getMostSignificantIndex(-5.71428));
		Assert.assertEquals(1, NumberUtils.getMostSignificantIndex(-57.1428));
		Assert.assertEquals(-1, NumberUtils.getMostSignificantIndex(-0.571428));
		Assert.assertEquals(-5,
				NumberUtils.getMostSignificantIndex(-0.0000571428));
	}

}
