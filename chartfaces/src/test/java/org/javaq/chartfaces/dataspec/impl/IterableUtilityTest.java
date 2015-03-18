package org.javaq.chartfaces.dataspec.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.faces.model.ListDataModel;

import junit.framework.Assert;

import org.javaq.chartfaces.iterable.IterableUtility;
import org.junit.Test;


public class IterableUtilityTest {

	@Test
	public void testGetSize() {
		final List<String> values = new ArrayList<String>();
		Assert.assertEquals(0, IterableUtility.getSize(values));
		values.add("1");
		Assert.assertEquals(1, IterableUtility.getSize(values));
		Assert.assertEquals(2,
				IterableUtility.getSize(new String[] { "1", "2" }));
		final ListDataModel<String> lm = new ListDataModel<String>(values);
		values.add("2");
		Assert.assertEquals(2, IterableUtility.getSize(lm));
		Assert.assertEquals(2, IterableUtility.getSize(new long[] { 1, 2 }));
		Assert.assertEquals(2, IterableUtility.getSize(new short[] { 1, 2 }));
		Assert.assertEquals(2, IterableUtility.getSize(new float[] { 1f, 2f }));
	}

	@Test
	public void testGetSizeExceptionalCondition() {
		int size = IterableUtility.getSize(new Object() {
			@SuppressWarnings("unused")
			public int size() throws InvocationTargetException {
				throw new InvocationTargetException(new Exception());
			}
		});
		Assert.assertTrue(size < 0);
		size = IterableUtility.getSize(new Object() {
			@SuppressWarnings("unused")
			private int size() {
				return 10;
			}
		});
		Assert.assertTrue(size < 0);
	}

	@Test
	public void testToIterable() {
		final List<String> expected = new ArrayList<String>();
		expected.add("1");
		expected.add("2");
		expected.add("3");
		expected.add("4");
		assertIterable(expected, new String[] { "1", "2", "3", "4" });
		final Iterable<String> listData = new ListDataModel<String>(
				Arrays.asList(new String[] { "1", "2", "3", "4" }));
		assertIterable(expected, listData);
	}

	@SuppressWarnings("rawtypes")
	@Test
	public void testToIterableIndices() {
		final int[] array = new int[] { 3, 2, 5 };
		final Iterable iterable = IterableUtility.toIterableIndices(array);
		final Iterator iter = iterable.iterator();
		Assert.assertNotNull(iter);
		Assert.assertTrue(iter.hasNext());
		Assert.assertEquals(0, iter.next());
		Assert.assertTrue(iter.hasNext());
		Assert.assertEquals(1, iter.next());
		Assert.assertTrue(iter.hasNext());
		Assert.assertEquals(2, iter.next());
		Assert.assertFalse(iter.hasNext());
	}

	@SuppressWarnings("rawtypes")
	@Test(expected = UnsupportedOperationException.class)
	public void testToIterableIndicesRemoveUnsupported() {
		final int[] array = new int[] { 3, 2, 5 };
		final Iterable iterable = IterableUtility.toIterableIndices(array);
		final Iterator iter = iterable.iterator();
		try {
			iter.hasNext();
			iter.next();
		} catch (final Throwable t) {
			Assert.fail("No exception is expected here!!!");
		}
		iter.remove();
	}

	@Test
	public void testToIterablesLevel2() {
		final List<String> expected = new ArrayList<String>();
		expected.add("1");
		expected.add("2");
		expected.add("3");
		expected.add("4");
		assertIterableLevel2(expected, new Object[] {
				new String[] { "1", "2" }, new String[] {
						"3", "4" } });
		final Iterable<String> listData1 = new ListDataModel<String>(
				Arrays.asList(new String[] { "1", "2" }));
		final String[] arrayData2 = new String[] { "3", "4" };
		final List<Object> dataList = new ArrayList<Object>();
		dataList.add(listData1);
		dataList.add(arrayData2);
		assertIterableLevel2(expected, dataList);
		assertIterableLevel2(expected, expected);
	}

	@SuppressWarnings("rawtypes")
	private void assertIterable(final Collection expected, final Object values) {
		final Iterable iterable = IterableUtility.toIterable(values);
		for (final Object v : iterable) {
			Assert.assertTrue(expected.contains(v));
		}
	}

	@SuppressWarnings("rawtypes")
	private void assertIterableLevel2(final Collection expected,
			final Object values) {
		final List<Iterable> iterablesLevel2 = IterableUtility
				.toIterablesLevel2(values);
		for (final Iterable iterable : iterablesLevel2) {
			for (final Object v : iterable) {
				Assert.assertTrue(expected.contains(v));
			}
		}
	}
}
