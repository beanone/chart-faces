package org.javaq.chartfaces.dataspec.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import junit.framework.Assert;

import org.javaq.chartfaces.iterable.ArrayIterable;
import org.junit.Before;
import org.junit.Test;


public class ArrayIterableTest {
	private final List<Object> arraysList = new ArrayList<Object>();

	@Before
	public void setUp() {
		this.arraysList.add(new byte[] { 66, 65 });
		this.arraysList.add(new int[] { 2, 1 });
		this.arraysList.add(new short[] { 2, 1 });
		this.arraysList.add(new char[] { 66, 65 });
		this.arraysList.add(new float[] { 2.0f, 1.0f });
		this.arraysList.add(new long[] { 2, 1 });
		this.arraysList.add(new Integer[] { 2, 1 });
		this.arraysList.add(new double[] { 2., 1. });
		this.arraysList.add(new Boolean[] { true, false });
		this.arraysList.add(new String[] { "b", "a" });
		this.arraysList.add(new Object[] { new Object(), new Object() });
	}

	@Test
	public void testArrayIterable() {
		for (final Object o : this.arraysList) {
			new ArrayIterable(o);
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testArrayIterableArgumentNotArray() {
		new ArrayIterable("anything");
	}

	@Test
	public void testGetFirstLastSizeForEmptyArray() {
		final int[] array = new int[0];
		final ArrayIterable iterable = new ArrayIterable(array);
		Assert.assertNull(iterable.getFirst());
		Assert.assertNull(iterable.getLast());
		Assert.assertEquals(0, iterable.size());
	}

	@Test
	public void testGetFirstLastSizeForOneElementArray() {
		final int[] array = new int[] { 1 };
		final ArrayIterable iterable = new ArrayIterable(array);
		Assert.assertEquals(1, iterable.getFirst());
		Assert.assertEquals(1, iterable.getLast());
		Assert.assertEquals(1, iterable.size());
	}

	@Test
	public void testGetFirstLastSizeForTwoElementArray() {
		final int[] array = new int[] { 1, 2 };
		final ArrayIterable iterable = new ArrayIterable(array);
		Assert.assertEquals(1, iterable.getFirst());
		Assert.assertEquals(2, iterable.getLast());
		Assert.assertEquals(2, iterable.size());
	}

	@Test
	public void testGetSortedInPlace() {
		for (final Object o : this.arraysList) {
			if (!o.getClass().getComponentType().equals(Object.class)) {
				final ArrayIterable iterable = new ArrayIterable(o);
				Assert.assertEquals(2, iterable.size());
				Assert.assertSame(iterable, iterable.getSorted(true));
				final Object o1 = iterable.getFirst();
				final Object o2 = iterable.getLast();
				Assert.assertTrue(o1.toString().compareTo(o2.toString()) < 0);
			}
		}
	}

	@Test
	public void testGetSortedNotInPlace() {
		for (final Object o : this.arraysList) {
			if (!o.getClass().getComponentType().equals(Object.class)) {
				final ArrayIterable iterable = new ArrayIterable(o);
				Assert.assertEquals(2, iterable.size());
				final ArrayIterable sortedIterable = iterable.getSorted(false);
				Assert.assertNotSame(iterable, sortedIterable);
				Object o1 = iterable.getFirst();
				Object o2 = iterable.getLast();
				Assert.assertTrue(o1.toString().compareTo(o2.toString()) > 0);
				o1 = sortedIterable.getFirst();
				o2 = sortedIterable.getLast();
				Assert.assertTrue(o1.toString().compareTo(o2.toString()) < 0);
			}
		}
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testGetSortedNotSupportedForBooleanPrimitive() {
		final boolean[] array = new boolean[] { true, false };
		final ArrayIterable iterable = new ArrayIterable(array);
		iterable.getSorted(true);
	}

	@Test(expected = NullPointerException.class)
	public void testIterableForNull() {
		new ArrayIterable(null);
	}

	@SuppressWarnings("rawtypes")
	@Test
	public void testIterator() {
		for (final Object o : this.arraysList) {
			final Iterator iter = new ArrayIterable(o).iterator();
			Assert.assertTrue(iter.hasNext());
			Assert.assertNotNull(iter.next());
			Assert.assertTrue(iter.hasNext());
			Assert.assertNotNull(iter.next());
			Assert.assertFalse(iter.hasNext());
		}
		// test a second time
		for (final Object o : this.arraysList) {
			final Iterator iter = new ArrayIterable(o).iterator();
			Assert.assertTrue(iter.hasNext());
			Assert.assertNotNull(iter.next());
			Assert.assertTrue(iter.hasNext());
			Assert.assertNotNull(iter.next());
			Assert.assertFalse(iter.hasNext());
		}
	}

	@SuppressWarnings("rawtypes")
	@Test(expected = NoSuchElementException.class)
	public void testIteratorNosuchElementException() {
		for (final Object o : this.arraysList) {
			final Iterator iter = new ArrayIterable(o).iterator();
			iter.next();
			iter.next();
			iter.next();
		}
	}

	@SuppressWarnings("rawtypes")
	@Test(expected = UnsupportedOperationException.class)
	public void testIteratorRemoveUnsupported() {
		final int[] array = new int[] { 1, 2 };
		final ArrayIterable iterable = new ArrayIterable(array);
		final Iterator iter = iterable.iterator();
		try {
			iter.hasNext();
			iter.next();
		} catch (final Throwable t) {
			Assert.fail("No exception is expected here!!!");
		}
		iter.remove();
	}
}
