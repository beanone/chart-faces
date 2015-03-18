package org.javaq.chartfaces.render.tool.impl;

import junit.framework.Assert;

import org.javaq.chartfaces.ChartFacesManager;
import org.javaq.chartfaces.DefaultChartSettings;
import org.javaq.chartfaces.api.Box;
import org.javaq.chartfaces.component.impl.UIChart;
import org.javaq.chartfaces.constants.EnumSVG;
import org.javaq.chartfaces.converter.AxisLabelsIterableConverter;
import org.javaq.chartfaces.converter.AxisValuesIterableConverter;
import org.javaq.chartfaces.dataspec.impl.DefaultChartingDataConverter;
import org.javaq.chartfaces.document.IDataElement;
import org.javaq.chartfaces.document.IElement;
import org.javaq.chartfaces.document.impl.ContainerElement;
import org.javaq.chartfaces.document.impl.DataElement;
import org.javaq.chartfaces.document.impl.Element;
import org.javaq.chartfaces.iterable.ISizeAwareIterable;
import org.javaq.chartfaces.iterable.IterableUtility;
import org.javaq.chartfaces.iterable.SizeAwareIterable;
import org.junit.Before;
import org.junit.Test;


public abstract class AxisToolTestBase {
	@Before
	public void setUp() {
		final ChartFacesManager manager = ChartFacesManager.getInstance();
		final DefaultChartSettings settings = new DefaultChartSettings();
		ChartFacesManager.getInstance().setDefaultAxisLabelAccessor(
				new AxisLabelsIterableConverter());
		ChartFacesManager.getInstance().setDefaultAxisValueAccessor(
				new AxisValuesIterableConverter());
		ChartFacesManager.getInstance().setDefaultDataConverter(
				new DefaultChartingDataConverter());
		manager.setDefaultChartSettings(settings);
	}

	@Test
	public void testAddLabels() {
		final IDataElement element = DataElement.newInstance(
				EnumSVG.g.toString(), ContainerElement.newInstance("g", "parent"));
		final CartesianAxisTool tool = createTool();
		tool.addLabels(element);
		Assert.assertEquals(1, element.getChildren().size());
		Assert.assertTrue(element.getChildren().get(0) instanceof IDataElement);
	}

	@Test
	public void testCreateCaptionTextElement() {
		final IElement caption = createTool().createCaptionTextElement(
				Element.newInstance(EnumSVG.g.toQualifiedString()));
		Assert.assertNotNull(caption);
		Assert.assertEquals(EnumSVG.text.toQualifiedString(),
				caption.getTagName());
		Assert.assertNotNull(caption.getProperties().get(EnumSVG.x.toString()));
		Assert.assertNotNull(caption.getProperties().get(EnumSVG.y.toString()));
	}

	@Test
	public void testGetId() {
		Assert.assertNotNull(createTool().getDataElementContainerId());
	}

	protected final CartesianAxisTool createTool() {
		final UIChart chart = new UIChart();
		final CartesianAxisTool tool = doCreateTool();
		chart.getChildren().add(tool.getAxisPart());
		tool.updateState(AbstractChartTool.CHARTING_AREA_VIEW_BOX,
				new Box(11, 2, 100, 100));
		return tool;
	}

	protected abstract CartesianAxisTool doCreateTool();

	protected int getCharCountOf(final String string, final char c) {
		int count = 0;
		int start = -1;
		while ((start = string.indexOf(c, start + 1)) > -1) {
			count++;
		}
		return count;
	}

	protected ISizeAwareIterable<Double> toSizeAwareIterable(
			final double[] doubleArray) {
		final Iterable<Double> iterable = IterableUtility
				.toIterable(doubleArray);
		return new SizeAwareIterable<Double>(iterable);
	}
}
