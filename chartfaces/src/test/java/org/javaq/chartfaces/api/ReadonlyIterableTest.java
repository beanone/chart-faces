package org.javaq.chartfaces.api;

import junit.framework.Assert;

import org.javaq.chartfaces.iterable.ArrayIterable;
import org.javaq.chartfaces.iterable.ReadonlyIterable;
import org.junit.Test;


public class ReadonlyIterableTest {

	@Test
	public void testIterator() {
		final ArrayIterable ai = new ArrayIterable(new int[] { 1, 2, 3 });
		final ReadonlyIterable<Integer> ri = new ReadonlyIterable<Integer>(ai);
		int count = 0;
		for (final int i : ri) {
			count++;
		}
		Assert.assertEquals(3, count);

		// do it again
		count = 0;
		for (final int i : ri) {
			count++;
		}
		Assert.assertEquals(3, count);
	}

}
