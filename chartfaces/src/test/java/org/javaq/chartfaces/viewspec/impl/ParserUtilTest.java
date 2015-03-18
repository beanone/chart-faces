package org.javaq.chartfaces.viewspec.impl;

import junit.framework.Assert;

import org.javaq.chartfaces.ChartFacesManager;
import org.javaq.chartfaces.DefaultChartSettings;
import org.javaq.chartfaces.component.impl.ParserUtil;
import org.javaq.chartfaces.component.impl.UIChart;
import org.javaq.chartfaces.part.axis.XAxis;
import org.junit.Before;
import org.junit.Test;


public class ParserUtilTest {
	@Before
	public void setUp() {
		ChartFacesManager.getInstance().setDefaultChartSettings(
				new DefaultChartSettings());
	}

	@Test
	public void testGetAxisLabelFontSize() {
		final XAxis axis = new XAxis();
		axis.setLabelStyle("font-family:Arial;font-size:100px;");
		final int size = ParserUtil.getAxisLabelFontSize(axis);
		Assert.assertEquals(100, size);
	}

	@Test
	public void testGetAxisLineWidth() {
		final XAxis axis = new XAxis();
		axis.setLineStyle("stroke-width:15px;stroke:#000000;fill:none;");
		final int size = ParserUtil.getAxisLineWidth(axis);
		Assert.assertEquals(15, size);
	}

	@Test
	public void testGetBorderWidth() {
		final UIChart chart = new UIChart();
		chart.setBorderStyle("stroke-width:15px;stroke:#000000;fill:none;");
		final int size = ParserUtil.getBorderWidth(chart);
		Assert.assertEquals(15, size);
	}

	@Test
	public void testGetCaptionFontSize() {
		final XAxis axis = new XAxis();
		axis.setCaptionStyle("font-family:Arial;font-size:100px;");
		final int size = ParserUtil.getCaptionFontSize(axis);
		Assert.assertEquals(100, size);
	}

	@Test
	public void testGetFooterFontSize() {
		final UIChart chart = new UIChart();
		chart.setFooterStyle("font-family:Arial;font-size:100px;");
		final int size = ParserUtil.getFooterFontSize(chart);
		Assert.assertEquals(100, size);
	}

	@Test
	public void testGetHeaderFontSize() {
		final UIChart chart = new UIChart();
		chart.setHeaderStyle("font-family:Arial;font-size:100px;");
		final int size = ParserUtil.getHeaderFontSize(chart);
		Assert.assertEquals(100, size);
	}

	@Test
	public void testGetInt() throws Exception {
		Assert.assertEquals(12, ParserUtil.getInt("12px", 0));
	}

	@Test
	public void testGetIntWithDefault() throws Exception {
		Assert.assertEquals(9, ParserUtil.getInt("somthing123", 9));
		Assert.assertEquals(9, ParserUtil.getInt("", 9));
		Assert.assertEquals(9, ParserUtil.getInt(null, 9));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetSize() throws Exception {
		ParserUtil.getSize("mystyle:a12", "mystyle");
	}

	@Test
	public void testGetStyle() throws Exception {
		final String styleString = "font-family:;font-weight:bold;font-size:55px";
		String value = ParserUtil.getStyle(styleString, "font-weight");
		Assert.assertEquals("bold", value);
		value = ParserUtil.getStyle(styleString, "font-size");
		Assert.assertEquals("55px", value);
		value = ParserUtil.getStyle(styleString, "font-family");
		Assert.assertNull(value);
	}
}