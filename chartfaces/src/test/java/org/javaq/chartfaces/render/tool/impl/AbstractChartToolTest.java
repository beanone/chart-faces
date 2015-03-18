package org.javaq.chartfaces.render.tool.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.javaq.chartfaces.api.IChartPart;
import org.javaq.chartfaces.component.impl.UIChart;
import org.javaq.chartfaces.document.IDataElement;
import org.javaq.chartfaces.document.IElement;
import org.javaq.chartfaces.document.impl.AbstractChartDocument;
import org.javaq.chartfaces.document.impl.Element;
import org.junit.Test;


public class AbstractChartToolTest {

	@Test
	public void testAddChildToolGetChartDocument() throws Exception {
		final Map<Object, Object> state = new HashMap<Object, Object>();
		final UIChart chart = new UIChart();
		final AbstractChartTool ct = createTestChartTool(
				createTestChartDocument("svg"), chart, state);
		final UIChart chartChild = new UIChart();
		final AbstractChartTool ctChild = createTestChartTool(
				createTestChartDocument("g"), chartChild, state);
		ct.appendChildTool(ctChild);
		ct.process();
		ctChild.process();
		final IElement doc = ct.getChartDocument().getLayoutElement();
		Assert.assertEquals("svg", doc.getTagName());
	}

	@Test
	public void testAddChildToolGetParent() {
		final Map<Object, Object> state = new HashMap<Object, Object>();
		final UIChart chart = new UIChart();
		final AbstractChartTool ct = createTestChartTool(
				createTestChartDocument("svg"), chart, state);
		final UIChart chartChild = new UIChart();
		final AbstractChartTool ctChild = createTestChartTool(
				createTestChartDocument("svg"), chartChild, state);
		ct.appendChildTool(ctChild);
		Assert.assertSame(ct, ctChild.getParent());
	}

	@Test
	public void testGetChartDocument() throws Exception {
		final Map<Object, Object> state = new HashMap<Object, Object>();
		final UIChart chart = new UIChart();
		final AbstractChartDocument cd = createTestChartDocument("svg");
		final AbstractChartTool ct = createTestChartTool(cd, chart, state);
		ct.process();
		Assert.assertSame(cd, ct.getChartDocument());
	}

	@Test(expected = IllegalStateException.class)
	public void testGetChartDocumentNotYetProcessed() {
		final Map<Object, Object> state = new HashMap<Object, Object>();
		final UIChart chart = new UIChart();
		final AbstractChartDocument cd = createTestChartDocument("svg");
		final AbstractChartTool ct = createTestChartTool(cd, chart, state);
		Assert.assertSame(cd, ct.getChartDocument());
	}

	@Test
	public void testSetGetFactory() {
		final Map<Object, Object> state = new HashMap<Object, Object>();
		final UIChart chart = new UIChart();
		final AbstractChartTool ct = createTestChartTool(
				createTestChartDocument("svg"), chart, state);
		Assert.assertNotNull(ct.getFactory());
	}

	@Test
	public void testSetGetParent() {
		final Map<Object, Object> state = new HashMap<Object, Object>();
		final UIChart chart = new UIChart();
		final AbstractChartTool ct = createTestChartTool(
				createTestChartDocument("svg"), chart, state);
		Assert.assertNull(ct.getParent());
		ct.setParent(createTestChartTool(createTestChartDocument("svg"), chart,
				state));
		Assert.assertNotNull(ct.getParent());
	}

	private AbstractChartDocument createTestChartDocument(final String tagName) {
		return new AbstractChartDocument() {
			private final List<IDataElement> dataList = new ArrayList<IDataElement>();
			private final IElement element = Element.newInstance(tagName);

			@Override
			public List<IDataElement> getDataElementList() {
				return this.dataList;
			}

			@Override
			public IElement getLayoutElement() {
				return this.element;
			}
		};
	}

	protected AbstractChartTool createTestChartTool(
			final AbstractChartDocument cd, final IChartPart part,
			final Map<Object, Object> state) {
		return new AbstractChartTool(new ChartToolFactory(), part, state) {

			@Override
			public void doProcess() throws IOException {
			}

			@Override
			protected IElement createLayoutElement() {
				return null;
			}

			@Override
			protected List<IDataElement> createDataElementList() {
				return null;
			}

			@Override
			protected ChartDocumentType getDocumentType() {
				return ChartDocumentType.component;
			}

			@Override
			protected boolean isValid(final IChartPart part) {
				return true;
			}

			@Override
			protected AbstractChartDocument createDocument() {
				return cd;
			}

			@Override
			protected void prepareData() {
			}

			@Override
			protected void synchToolState(Map<Object, Object> state) {
			}
		};
	}
}
