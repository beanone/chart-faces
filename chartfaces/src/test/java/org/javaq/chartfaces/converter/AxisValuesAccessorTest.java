package org.javaq.chartfaces.converter;

import java.util.Iterator;

import junit.framework.Assert;

import org.javaq.chartfaces.part.axis.XAxis;
import org.junit.Test;


public class AxisValuesAccessorTest {

	@Test
	public void testGetValuesValueAndLabelAreNull() {
		final XAxis axis = new XAxis();
		Assert.assertNull(new AxisValuesIterableConverter().convert(axis));
	}

	@Test
	public void testGetValuesValueNotNull() {
		final XAxis axis = new XAxis();
		axis.setValue(new double[] { 10.0, 20.0 });
		final Iterable<?> values = new AxisValuesIterableConverter().convert(axis);
		Assert.assertNotNull(values);
		final Iterator<?> iter = values.iterator();
		Assert.assertEquals(10.0, iter.next());
		Assert.assertEquals(20.0, iter.next());
	}

	@Test
	public void testGetValuesValueNullButLabelNotNull() {
		final XAxis axis = new XAxis();
		axis.setLabel(new String[] { "a", "b" });
		final Iterable<?> values = new AxisValuesIterableConverter().convert(axis);
		Assert.assertNotNull(values);
		final Iterator<?> iter = values.iterator();
		Assert.assertEquals(0., iter.next());
		Assert.assertEquals(1., iter.next());
	}

}
