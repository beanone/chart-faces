package org.javaq.chartfaces.converter;

import java.util.Iterator;

import junit.framework.Assert;

import org.javaq.chartfaces.iterable.IterableUtility;
import org.junit.Test;


@SuppressWarnings({ "rawtypes", "unchecked" })
public class TranslateScaleIntegerAccessorTest {
	@Test
	public void testGetValues() {
		final TranslateScaleToDoubleIterable accessor = new TranslateScaleToDoubleIterable(
				1, 2, 3);
		final double[] data = new double[] { 1.0, 2.0 };
		final Iterable<?> dataIterable = IterableUtility.toIterable(data);
		final Iterable<Integer> result = accessor.convert(dataIterable);
		final Iterator<Integer> iter = result.iterator();
		Assert.assertEquals(3.0, iter.next());
		Assert.assertEquals(5.0, iter.next());
		Assert.assertFalse(iter.hasNext());
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testRemove() {
		final TranslateScaleToDoubleIterable accessor = new TranslateScaleToDoubleIterable(
				1, 2, 3);
		final double[] data = new double[] { 1.0, 2.0 };
		final Iterable<?> dataIterable = IterableUtility.toIterable(data);
		final Iterable<Integer> result = accessor.convert(dataIterable);
		final Iterator<Integer> iter = result.iterator();
		iter.next();
		iter.remove();
	}

	@Test
	public void testTransformObjectFromDouble() {
		final TranslateScaleToDoubleIterable accessor = new TranslateScaleToDoubleIterable(
				1, 2, 3);
		Assert.assertEquals(3.0, accessor.transform(1.0));
		Assert.assertEquals(5.0, accessor.transform(2.0));
	}

	@Test
	public void testTransformObjectFromFloat() {
		final TranslateScaleToDoubleIterable accessor = new TranslateScaleToDoubleIterable(
				1, 2, 3);
		Assert.assertEquals(3.0, accessor.transform(1.0f));
		Assert.assertEquals(5.0, accessor.transform(2.0f));
	}

	@Test
	public void testTransformObjectFromInteger() {
		final TranslateScaleToDoubleIterable accessor = new TranslateScaleToDoubleIterable(
				1, 2, 3);
		Assert.assertEquals(3.0, accessor.transform(1));
		Assert.assertEquals(5.0, accessor.transform(2));
	}

	@Test
	public void testTransformObjectFromLong() {
		final TranslateScaleToDoubleIterable accessor = new TranslateScaleToDoubleIterable(
				1, 2, 3);
		Assert.assertEquals(3.0, accessor.transform(1L));
		Assert.assertEquals(5.0, accessor.transform(2L));
	}

	@Test
	public void testTransformObjectFromShort() {
		final TranslateScaleToDoubleIterable accessor = new TranslateScaleToDoubleIterable(
				1, 2, 3);
		Assert.assertEquals(3.0, accessor.transform((short) 1));
		Assert.assertEquals(5.0, accessor.transform((short) 2));
	}
}
