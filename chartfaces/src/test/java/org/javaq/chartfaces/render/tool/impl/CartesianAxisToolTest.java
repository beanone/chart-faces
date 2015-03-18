package org.javaq.chartfaces.render.tool.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.javaq.chartfaces.api.Box;
import org.javaq.chartfaces.api.IChartPart;
import org.javaq.chartfaces.constants.EnumPart;
import org.javaq.chartfaces.constants.EnumSVG;
import org.javaq.chartfaces.converter.TranslateScaleToDoubleIterable;
import org.javaq.chartfaces.document.IDataElement;
import org.javaq.chartfaces.document.IElement;
import org.javaq.chartfaces.document.impl.Element;
import org.javaq.chartfaces.iterable.ISizeAwareIterable;
import org.javaq.chartfaces.part.axis.AngularAxis;
import org.javaq.chartfaces.part.axis.XAxis;
import org.javaq.chartfaces.render.tool.IChartToolFactory;
import org.javaq.chartfaces.util.IStringBuilder;
import org.javaq.chartfaces.util.NumberRoundingStringBuilder;
import org.junit.Test;


public class CartesianAxisToolTest extends AxisToolTestBase {
	static class MockAxisTool extends CartesianAxisTool {
		public MockAxisTool(final IChartToolFactory factory,
				final IChartPart part, Map<Object, Object> state) {
			super(factory, part, state);
		}

		@Override
		protected void addLabels(final IDataElement element) {
			element.addChildren(Element.newInstance(EnumSVG.text.toQualifiedString()));
		}

		@Override
		protected IStringBuilder createAxisLinePath() {
			return new NumberRoundingStringBuilder().append("M0 0h100");
		}

		@Override
		protected IElement createCaptionTextElement(
				final IElement captionElement) {
			final IElement element = Element.newInstance(
					EnumSVG.text.toQualifiedString());
			element.addProperty(EnumSVG.x.toString(), "50");
			element.addProperty(EnumSVG.y.toString(), "85");
			element.addProperty(EnumSVG.textAnchor.toString(), "middle");
			return element;
		}

		@Override
		protected String createGridLinesPaths(final int density) {
			return "M0 0v100";
		}

		@Override
		protected String createTickPaths() {
			return "M0 10h-1";
		}

		@Override
		protected int getAxisLength(final Box box) {
			return 10;
		}

		@Override
		protected EnumPart getAxisType() {
			return EnumPart.xaxis;
		}

		@Override
		protected ISizeAwareIterable<Double> getComponentComputedLabelCoordinates() {
			return null;
		}

		@Override
		protected ChartDocumentType getDocumentType() {
			return ChartDocumentType.xaxis;
		}

		@Override
		protected String getLabelAnchor() {
			return "right";
		}

		@Override
		protected ISizeAwareIterable<Double> scaleCoordinates(
				final Iterable<Double> values,
				final double minValue, final double scale, final int axisRange) {
			return new TranslateScaleToDoubleIterable<Double>(
					minValue, scale, 0).convert(values);
		}

		@Override
		protected void synchToolState(Map<Object, Object> state) {
			// TODO Auto-generated method stub

		}
	}

	@Override
	@Test
	public void testAddLabels() {
		// no need to have this test here so pass.
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCartesianAxisToolIChartPartTypeWrong() {
		new MockAxisTool(null, new AngularAxis(), new HashMap<Object, Object>());
	}

	@Test
	public void testCreateElement() throws Exception {
		final CartesianAxisTool tool = createTool();
		tool.initBoxModel(new BoxModel(1000, 1000));
		tool.getAxisPart().setGridLineDensity(1);
		final IElement element = tool.createLayoutElement();
		Assert.assertNotNull(element);
		Assert.assertEquals("1", element.getProperties().get("x"));
		Assert.assertEquals("2", element.getProperties().get("y"));
		Assert.assertEquals("100", element.getProperties().get("width"));
		Assert.assertEquals("100", element.getProperties().get("height"));
		Assert.assertTrue(element.getChildren().size() > 0);
	}

	@Test
	public void testCreateGridLinesElement() throws Exception {
		final CartesianAxisTool tool = createTool();
		tool.initBoxModel(new BoxModel(1000, 1000));
		final IElement element = tool.createGridLinesElement(1);
		Assert.assertNotNull(element);
		Assert.assertEquals(EnumSVG.path.toString(),
				element.getTagName());
		Assert.assertNotNull(element.getProperties().get("d"));
	}

	@Test
	public void testCreateLabelTextElements() throws Exception {
		final CartesianAxisTool tool = createTool();
		tool.initBoxModel(new BoxModel(1000, 1000));
		final IElement element = tool.createLabelTextElements();
		Assert.assertNotNull(element);
		Assert.assertEquals(EnumSVG.g.toString(),
				element.getTagName());
		Assert.assertNotNull(element.getProperties().get("style"));
		Assert.assertNotNull(element.getProperties().get("text-anchor"));
		Assert.assertTrue(element.getChildren().size() == 1);
	}

	@Test
	public void testCreateTemplateDataList() throws Exception {
		final CartesianAxisTool tool = createTool();
		tool.initBoxModel(new BoxModel(1000, 1000));
		final List<IDataElement> templableList = tool.createDataElementList();
		Assert.assertNotNull(templableList);
	}

	@Test
	public void testCreateTicksElement() throws Exception {
		final CartesianAxisTool tool = createTool();
		tool.initBoxModel(new BoxModel(1000, 1000));
		final IElement element = tool.createTicksElement();
		Assert.assertNotNull(element);
		Assert.assertEquals(EnumSVG.path.toString(),
				element.getTagName());
		Assert.assertNotNull(element.getProperties().get("d"));
	}

	@Override
	protected CartesianAxisTool doCreateTool() {
		final XAxis axis = new XAxis();
		axis.setId("xaxis");
		axis.getPartSpec().setViewBox(new Box(1, 2, 100, 100));
		return new MockAxisTool(null, axis, new HashMap<Object, Object>());
	}

}
