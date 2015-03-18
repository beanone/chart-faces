package org.javaq.chartfaces.render.tool.impl;

import java.util.HashMap;

import junit.framework.Assert;

import org.javaq.chartfaces.api.Box;
import org.javaq.chartfaces.constants.EnumPart;
import org.javaq.chartfaces.constants.EnumSVG;
import org.javaq.chartfaces.part.axis.XAxis;
import org.javaq.chartfaces.part.axis.YAxis;
import org.javaq.chartfaces.util.IStringBuilder;
import org.junit.Test;


public class TopAxisToolTest extends AxisToolTestBase {
	@Test
	public void testCreateAxisLinePath() {
		final IStringBuilder sb =
				createTool().createAxisLinePath();
		Assert.assertTrue(sb.toString().startsWith("M0 10h"));
	}

	@Test
	public void testCreateGridLinesPaths() {
		String paths = createTool().createGridLinesPaths(1);
		Assert.assertEquals(5, getCharCountOf(paths, 'v'));
		paths = createTool().createGridLinesPaths(2);
		Assert.assertEquals(9, getCharCountOf(paths, 'v'));
	}

	@Test
	public void testCreateTickPaths() {
		final String paths = createTool().createTickPaths();
		Assert.assertEquals(5, getCharCountOf(paths, 'v'));
	}

	@Test
	public void testGetAxisType() {
		Assert.assertSame(EnumPart.xaxis, createTool().getAxisType());
	}

	@Test
	public void testGetLabelAnchor() {
		Assert.assertSame(EnumSVG.middle.toString(), createTool()
				.getLabelAnchor());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testTopAxisTool() {
		new TopAxisTool(null, new YAxis(), new HashMap<Object, Object>());
	}

	@Override
	protected CartesianAxisTool doCreateTool() {
		final XAxis axis = new XAxis();
		axis.setLabel(new String[] { "0", "25", "50", "75", "100" });
		axis.getPartSpec().setViewBox(new Box(100, 10));
		final CartesianAxisTool tool = new TopAxisTool(null, axis,
				new HashMap<Object, Object>()) {
		};
		tool.updateState(AbstractChartTool.X_LABEL_COORDS,
				toSizeAwareIterable(new double[] { 0, 25, 50, 75, 100 }));
		return tool;
	}

}