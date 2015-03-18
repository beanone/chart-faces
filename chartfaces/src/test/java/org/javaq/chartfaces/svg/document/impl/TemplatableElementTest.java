package org.javaq.chartfaces.svg.document.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import junit.framework.Assert;

import org.javaq.chartfaces.document.IDataElement;
import org.javaq.chartfaces.document.impl.ContainerElement;
import org.javaq.chartfaces.document.impl.DataElement;
import org.junit.Test;


public class TemplatableElementTest {

	@Test(expected = IllegalArgumentException.class)
	public void testAddCustomPropertySizeInconsistent() {
		final IDataElement e = DataElement.newInstance("test", ContainerElement.newInstance(
				"c", "parent"));
		e.addCustomProperty("t", new ArrayList<Object>(Arrays
						.asList(new Object[] { null, null })));
		e.addCustomProperty("t1", new ArrayList<Object>(Arrays
				.asList(new Object[] { null, null, null })));
	}

	@Test
	public void testAddRemoveCustomProperty() {
		final IDataElement e = DataElement.newInstance("test", ContainerElement.newInstance(
				"c", "parent"));
		e.addCustomProperty("t", new ArrayList<Object>(Arrays
				.asList(new Object[] { null, null })));
		Assert.assertEquals(1, e.getCustomProperties().size());
		e.addCustomProperty("t1", new ArrayList<Object>(Arrays
				.asList(new Object[] { null, null })));
		Assert.assertEquals(2, e.getCustomProperties().size());
		e.removeCustomProperty("t1");
		Assert.assertEquals(1, e.getCustomProperties().size());
	}

	@Test
	public void testGetCustomProperties() {
		final IDataElement e = DataElement.newInstance("test", ContainerElement.newInstance(
				"c", "parent"));
		e.addCustomProperty("t", new ArrayList<Object>(Arrays
				.asList(new Object[] { 0, 1 })));
		final Iterator<?> objs = e.getCustomProperties().get("t")
				.iterator();
		Assert.assertEquals(0, objs.next());
		Assert.assertEquals(1, objs.next());
	}

	@Test
	public void testTemplatableElement() {
		DataElement.newInstance("test", ContainerElement.newInstance(
				"c", "parent"));
	}
}
