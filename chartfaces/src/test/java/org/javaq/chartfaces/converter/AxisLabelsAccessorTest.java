package org.javaq.chartfaces.converter;

import java.util.Iterator;

import junit.framework.Assert;

import org.javaq.chartfaces.part.axis.XAxis;
import org.junit.Test;


public class AxisLabelsAccessorTest {

	@Test
	public void testGetValuesLabelNotNull() {
		final XAxis axis = new XAxis();
		axis.setLabel(new String[] { "a", "b" });
		final Iterable<?> labels = new AxisLabelsIterableConverter().convert(axis);
		Assert.assertNotNull(labels);
		final Iterator<?> iter = labels.iterator();
		Assert.assertEquals("a", iter.next());
		Assert.assertEquals("b", iter.next());
	}

	@Test
	public void testGetValuesLabelNullButValueNotNull() {
		final XAxis axis = new XAxis();
		axis.setValue(new double[] { 10.0, 20.0 });
		final Iterable<?> labels = new AxisLabelsIterableConverter().convert(axis);
		Assert.assertNotNull(labels);
		final Iterator<?> iter = labels.iterator();
		Assert.assertEquals(10.0, iter.next());
		Assert.assertEquals(20.0, iter.next());
	}

	@Test
	public void testGetValuesValueAndLabelAreNull() {
		final XAxis axis = new XAxis();
		Assert.assertNull(new AxisLabelsIterableConverter().convert(axis));
	}

}
