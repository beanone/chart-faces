package org.javaq.chartfaces.svg.document.impl;

import org.javaq.chartfaces.document.IElement;
import org.javaq.chartfaces.document.impl.Element;
import org.javaq.chartfaces.document.impl.ElementMaker;
import org.javaq.chartfaces.render.svg.RenderUtils;
import org.junit.Assert;
import org.junit.Test;


public class ElementTest {
	private final RenderUtils utils = new RenderUtils();

	@Test(expected = NullPointerException.class)
	public void testAddChildrenNull() {
		final IElement e = ElementMaker.newRectangle();
		e.addChildren((IElement[]) null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddPropertiesNull() {
		final IElement e = ElementMaker.newRectangle();
		e.addProperty(null, "");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddPropertyKeyNull() {
		final IElement e = ElementMaker.newRectangle();
		e.addProperty(null, "1");
	}

	@Test
	public void testAddRemoveChildren() {
		final IElement e = ElementMaker.newRectangle();
		final IElement a = Element.newInstance("a");
		final IElement b = Element.newInstance("b");
		final IElement c = Element.newInstance("c");
		e.addChildren(a, b, c);
		Assert.assertEquals("<svg:rect><a></a><b></b><c></c></svg:rect>",
				utils.toXML(e).toString());
		e.removeChildren(b, c);
		Assert.assertEquals("<svg:rect><a></a></svg:rect>",
				utils.toXML(e).toString());
	}

	@Test
	public void testAddRemoveProperties() {
		final IElement e = ElementMaker.newRectangle();
		e.addProperty("k1", "1");
		Assert.assertEquals("<svg:rect k1=\"1\"></svg:rect>",
				utils.toXML(e).toString());
		e.addProperty("k2", "2").addProperty("k3", "3");
		Assert.assertEquals("<svg:rect k1=\"1\" k2=\"2\" k3=\"3\"></svg:rect>",
				utils.toXML(e).toString());
		e.removeProperties("k1").addProperty("k2", "v2")
				.addProperty("k3", "v3");
		Assert.assertEquals("<svg:rect k2=\"v2\" k3=\"v3\"></svg:rect>",
				utils.toXML(e).toString());
		e.removeProperties("k1", "k2");
		Assert.assertEquals("<svg:rect k3=\"v3\"></svg:rect>",
				utils.toXML(e).toString());
	}

	@Test
	public void testAddRemoveProperty() {
		final IElement e = ElementMaker.newRectangle();
		e.addProperty("k1", "1");
		Assert.assertEquals("<svg:rect k1=\"1\"></svg:rect>",
				utils.toXML(e).toString());
		e.addProperty("k1", null);
		Assert.assertEquals("<svg:rect></svg:rect>", utils.toXML(e)
				.toString());
	}

	@Test
	public void testElement() {
		final String tegName = "svg:rect";
		final IElement e = ElementMaker.newRectangle();
		Assert.assertEquals(tegName, e.getTagName());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testElementTagNameNull() {
		Element.newInstance(null);
	}

	@Test(expected = NullPointerException.class)
	public void testRemoveChildrenNull() {
		final IElement e = ElementMaker.newRectangle();
		e.addChildren(Element.newInstance("a"));
		e.removeChildren((IElement[]) null);
	}

	@Test(expected = NullPointerException.class)
	public void testRemovePropertiesNull() {
		final IElement e = ElementMaker.newRectangle();
		e.addProperty("k1", "1").addProperty("k2", "2");
		e.removeProperties((String[]) null);
	}
}
