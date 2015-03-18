package org.javaq.chartfaces.render.svg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import org.javaq.chartfaces.document.IContainerElement;
import org.javaq.chartfaces.document.IDataElement;
import org.javaq.chartfaces.document.IElement;
import org.javaq.chartfaces.document.impl.ContainerElement;
import org.javaq.chartfaces.document.impl.DataElement;
import org.javaq.chartfaces.document.impl.Element;
import org.junit.Test;


public class RenderUtilsTest {
	private final RenderUtils utils = new RenderUtils();

	@Test
	public void testToJSan() {
		IContainerElement container = ContainerElement.newInstance("c", "parent");
		final DataElement e1 = DataElement.newInstance("e1", container);
		String json = utils.toJSan(e1).toString();
		// rendering empty element
		Assert.assertEquals("{\"e1.parent.0\":{}}", json);
		e1.addProperty("k3", "v3")
				.addProperty("k1", "v1")
				.addProperty("k2", "v2"); // ordered
		// rendering
		json = utils.toJSan(e1).toString();
		Assert.assertEquals(
				"{\"e1.parent.0\":{\"k1\":\"v1\",\"k2\":\"v2\",\"k3\":\"v3\"}}",
				json);

		// now test rendering of custom arrays property of simple data types
		e1.addCustomProperty("a1", new ArrayList<Object>(Arrays
				.asList(new Object[] { 0, 1 })));
		e1.addCustomProperty("a2", new ArrayList<Object>(Arrays
						.asList(new Object[] { Character.valueOf((char) 65),
								Character.valueOf((char) 66) })));
		json = utils.toJSan(e1).toString();
		Assert.assertEquals(
				"{\"e1.parent.0\":{\"k1\":\"v1\",\"k2\":\"v2\",\"k3\":\"v3\",\"a1\":[\"0\",\"1\"],\"a2\":[\"A\",\"B\"]}}",
				json);

		// now test rendering children elements
		final IElement e2 = Element.newInstance("e2");
		e2.addProperty("k1", "v1");
		e1.addChildren(e2);
		final IElement e3 = Element.newInstance("e3");
		e3.addProperty("k1", "v1").addProperty("k2", "v2");
		e1.addChildren(e3);
		json = utils.toJSan(e1).toString();
		Assert.assertEquals(
				"{\"e1.parent.0\":{\"k1\":\"v1\",\"k2\":\"v2\",\"k3\":\"v3\",\"e2.0\":{\"k1\":\"v1\"},\"e3.0\":{\"k1\":\"v1\",\"k2\":\"v2\"},\"a1\":[\"0\",\"1\"],\"a2\":[\"A\",\"B\"]}}",
				json);

		// now test rendering children element embed multiple layers
		final IElement e4 = Element.newInstance("e4");
		e4.addProperty("k4", "v4");
		e2.addChildren(e4);
		json = utils.toJSan(e1).toString();
		Assert.assertEquals(
				"{\"e1.parent.0\":{\"k1\":\"v1\",\"k2\":\"v2\",\"k3\":\"v3\",\"e2.0\":{\"k1\":\"v1\",\"e4.0\":{\"k4\":\"v4\"}},\"e3.0\":{\"k1\":\"v1\",\"k2\":\"v2\"},\"a1\":[\"0\",\"1\"],\"a2\":[\"A\",\"B\"]}}",
				json);
	}

	@Test
	public void testToJSan2Elements() {
		IContainerElement container = ContainerElement.newInstance("c", "parent");
		final DataElement e1 = DataElement.newInstance("e1", container);
		final IDataElement e2 = DataElement.newInstance("e2", container);
		final String json = utils.toJSan(e1, e2).toString();
		Assert.assertEquals("{\"e1.parent.0\":{},\"e2.parent.1\":{}}", json);
	}

	@Test
	public void testToJSan2ElementList() {
		IContainerElement container = ContainerElement.newInstance("c", "parent");
		final IDataElement e1 = DataElement.newInstance("e1", container);
		final IDataElement e2 = DataElement.newInstance("e2", container);
		final List<IDataElement> elementList = new ArrayList<IDataElement>();
		elementList.add(e1);
		elementList.add(e2);
		final String json = utils.toJSan(elementList).toString();
		Assert.assertEquals("{\"e1.parent.0\":{},\"e2.parent.1\":{}}", json);
	}

	@Test
	public void testToJSon() {
		final IElement e1 = Element.newInstance("e1");
		String json = utils.toJSon(e1).toString();
		Assert.assertEquals("{\"e1.0\":{}}", json); // rendering empty element

		e1.addProperty("k3", "v3")
				.addProperty("k1", "v1")
				.addProperty("k2", "v2"); // ordered rendering
		json = utils.toJSon(e1).toString();
		Assert.assertEquals(
				"{\"e1.0\":{\"k1\":\"v1\",\"k2\":\"v2\",\"k3\":\"v3\"}}", json);

		// now test rendering children elements
		final IElement e2 = Element.newInstance("e2");
		e2.addProperty("k1", "v1");
		e1.addChildren(e2);
		final IElement e3 = Element.newInstance("e3");
		e3.addProperty("k1", "v1").addProperty("k2", "v2");
		e1.addChildren(e3);
		json = utils.toJSon(e1).toString();
		Assert.assertEquals(
				"{\"e1.0\":{\"k1\":\"v1\",\"k2\":\"v2\",\"k3\":\"v3\",\"e2.0\":{\"k1\":\"v1\"},\"e3.0\":{\"k1\":\"v1\",\"k2\":\"v2\"}}}",
				json);

		// now test rendering children element embed multiple layers
		final IElement e4 = Element.newInstance("e4");
		e4.addProperty("k4", "v4");
		e2.addChildren(e4);
		json = utils.toJSon(e1).toString();
		Assert.assertEquals(
				"{\"e1.0\":{\"k1\":\"v1\",\"k2\":\"v2\",\"k3\":\"v3\",\"e2.0\":{\"k1\":\"v1\",\"e4.0\":{\"k4\":\"v4\"}},\"e3.0\":{\"k1\":\"v1\",\"k2\":\"v2\"}}}",
				json);
	}

	@Test
	public void testToXML() {
		final IElement e1 = Element.newInstance("e1");
		String xml = utils.toXML(e1).toString();
		Assert.assertEquals("<e1></e1>", xml); // rendering empty element
		e1.addProperty("k3", "v3")
				.addProperty("k1", "v1")
				.addProperty("k2", "v2"); // ordered rendering
		xml = utils.toXML(e1).toString();
		Assert.assertEquals("<e1 k1=\"v1\" k2=\"v2\" k3=\"v3\"></e1>", xml);

		final IElement e2 = Element.newInstance("e2"); // rendering of children
		e2.addProperty("k1", "v1");
		e1.addChildren(e2);
		final IElement e3 = Element.newInstance("e3");
		e3.addProperty("k1", "v1").addProperty("k2", "v2");
		e1.addChildren(e3);
		xml = utils.toXML(e1).toString();
		Assert.assertEquals(
				"<e1 k1=\"v1\" k2=\"v2\" k3=\"v3\"><e2 k1=\"v1\"></e2><e3 k1=\"v1\" k2=\"v2\"></e3></e1>",
				xml);

		final IDataElement e4 = DataElement.newInstance("e4",
				ContainerElement.newInstance("e5", "id"));
		e4.addProperty("k1", "v1");
		List<String> values = new ArrayList<String>();
		values.add("v21");
		values.add("v22");
		e4.addCustomProperty("k2", values);
		List<String> textValues = new ArrayList<String>();
		textValues.add("t1");
		textValues.add("t2");
		e4.addCustomProperty("innerText", textValues);
		e1.addChildren(e4);
		xml = utils.toXML(e1).toString();
		Assert.assertEquals(
				"<e1 k1=\"v1\" k2=\"v2\" k3=\"v3\"><e2 k1=\"v1\"></e2><e3 k1=\"v1\" k2=\"v2\"></e3><e4 k1=\"v1\" k2=\"v21\">t1</e4><e4 k1=\"v1\" k2=\"v22\">t2</e4></e1>",
				xml);
	}

	@Test
	public void testToXMLNestedDataElements() {
		final IElement e1 = Element.newInstance("e1");
		String xml = utils.toXML(e1).toString();
		Assert.assertEquals("<e1></e1>", xml); // rendering empty element
		e1.addProperty("k3", "v3")
				.addProperty("k1", "v1")
				.addProperty("k2", "v2"); // ordered rendering
		xml = utils.toXML(e1).toString();
		Assert.assertEquals("<e1 k1=\"v1\" k2=\"v2\" k3=\"v3\"></e1>", xml);

		final IDataElement e2 = DataElement.newInstance("e2", ContainerElement.newInstance(
				"e5", "id")); // rendering of children
		e2.addProperty("k1", "v1");
		e1.addChildren(e2);
		final IElement e3 = Element.newInstance("e3");
		e3.addProperty("k1", "v1").addProperty("k2", "v2");
		e1.addChildren(e3);
		xml = utils.toXML(e1).toString();
		Assert.assertEquals(
				"<e1 k1=\"v1\" k2=\"v2\" k3=\"v3\"><e2 k1=\"v1\"></e2><e3 k1=\"v1\" k2=\"v2\"></e3></e1>",
				xml);

		final IDataElement e4 = DataElement.newInstance("e4",
				ContainerElement.newInstance("e5", "id"));
		e4.addProperty("k1", "v1");
		List<String> values = new ArrayList<String>();
		values.add("v21");
		values.add("v22");
		values.add("v23");
		e4.addCustomProperty("k2", values);
		List<String> textValues = new ArrayList<String>();
		textValues.add("t1");
		textValues.add("t2");
		textValues.add("t3");
		e4.addCustomProperty("innerText", textValues);
		e2.addChildren(e4);
		xml = utils.toXML(e1).toString();
		Assert.assertEquals(
				"<e1 k1=\"v1\" k2=\"v2\" k3=\"v3\"><e2 k1=\"v1\"><e4 k1=\"v1\" k2=\"v21\">t1</e4><e4 k1=\"v1\" k2=\"v22\">t2</e4><e4 k1=\"v1\" k2=\"v23\">t3</e4></e2><e3 k1=\"v1\" k2=\"v2\"></e3></e1>",
				xml);
	}
}