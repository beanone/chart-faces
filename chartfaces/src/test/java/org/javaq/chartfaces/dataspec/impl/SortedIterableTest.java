package org.javaq.chartfaces.dataspec.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.faces.model.ListDataModel;

import junit.framework.Assert;

import org.javaq.chartfaces.iterable.SortedIterable;
import org.junit.Test;




public class SortedIterableTest {

	@Test
	public void testIterator() {
		final List<Double> list = new ArrayList<Double>();
		list.add(1.0);
		list.add(-1.2);
		list.add(1.3);
		list.add(1.1);
		final SortedIterable<Double> sortedIter = new SortedIterable<Double>(list);
		Assert.assertEquals(4, sortedIter.getSize());
		final Iterator<Double> iter = sortedIter.iterator();
		Assert.assertEquals(-1.2, iter.next());
		Assert.assertEquals(1.0, iter.next());
		Assert.assertEquals(1.1, iter.next());
		Assert.assertEquals(1.3, iter.next());
	}

	@Test
	public void testSortedIterable() {
		final List<Double> list = new ArrayList<Double>();
		list.add(1.0);
		list.add(-1.2);
		list.add(1.3);
		list.add(1.1);
		SortedIterable<Double> sortedIter = new SortedIterable<Double>(list);
		Assert.assertEquals(4, sortedIter.getSize());
		Assert.assertEquals(-1.2, sortedIter.getFirst());
		Assert.assertEquals(1.3, sortedIter.getLast());

		final ListDataModel<Double> ldm = new ListDataModel<Double>(list);
		Assert.assertEquals(4, sortedIter.getSize());
		sortedIter = new SortedIterable<Double>(ldm);
		Assert.assertEquals(-1.2, sortedIter.getFirst());
		Assert.assertEquals(1.3, sortedIter.getLast());

		final Set<Double> set = new HashSet<Double>(list);
		Assert.assertEquals(4, sortedIter.getSize());
		sortedIter = new SortedIterable<Double>(set);
		Assert.assertEquals(-1.2, sortedIter.getFirst());
		Assert.assertEquals(1.3, sortedIter.getLast());

		sortedIter = new SortedIterable<Double>(new ArrayList<Double>());
		Assert.assertEquals(0, sortedIter.getSize());
		Assert.assertNull(sortedIter.getFirst());
		Assert.assertNull(sortedIter.getLast());
	}

}
