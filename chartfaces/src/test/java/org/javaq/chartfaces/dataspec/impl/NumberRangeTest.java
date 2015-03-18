package org.javaq.chartfaces.dataspec.impl;

import junit.framework.Assert;

import org.javaq.chartfaces.util.NumberRange;
import org.junit.Test;



public class NumberRangeTest {

	@Test
	public void testGetMinV1() {
		final NumberRange nr = new NumberRange();
		nr.mergeBound(1.);
		nr.getMin();
	}

	@Test
	public void testIsValid() {
		final NumberRange nr = new NumberRange();
		Assert.assertFalse(nr.isValid());
		nr.mergeBound(1.);
		Assert.assertFalse(nr.isValid());
		nr.mergeBound(1.);
		Assert.assertFalse(nr.isValid());
		nr.mergeBound(2.);
		Assert.assertTrue(nr.isValid());
	}
}