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


public class LeftAxisToolTest extends AxisToolTestBase {
	@Test
	public void testCreateAxisLinePath() {
		final IStringBuilder sb =
				createTool().createAxisLinePath();
		Assert.assertTrue(sb.toString().startsWith("M10 0v"));
	}

	@Test
	public void testCreateGridLinesPaths() {
		String paths = createTool().createGridLinesPaths(1);
		Assert.assertEquals(5, getCharCountOf(paths, 'h'));
		paths = createTool().createGridLinesPaths(2);
		Assert.assertEquals(9, getCharCountOf(paths, 'h'));
	}

	@Test
	public void testCreateTickPaths() {
		final String paths = createTool().createTickPaths();
		Assert.assertEquals(5, getCharCountOf(paths, 'h'));
	}

	@Test
	public void testGetAxisType() {
		Assert.assertSame(EnumPart.yaxis, createTool().getAxisType());
	}

	@Test
	public void testGetLabelAnchor() {
		Assert.assertSame(EnumSVG.end.toString(), createTool().getLabelAnchor());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testLeftAxisToolWrongPartType() {
		new LeftAxisTool(null, new XAxis(), new HashMap<Object, Object>());
	}

	@Override
	protected CartesianAxisTool doCreateTool() {
		final YAxis axis = new YAxis();
		axis.setLabel(new String[] { "0", "25", "50", "75", "100" });
		axis.getPartSpec().setViewBox(new Box(10, 100));
		final CartesianAxisTool tool = new LeftAxisTool(null, axis,
				new HashMap<Object, Object>()) {
		};
		tool.updateState(AbstractChartTool.Y_LABEL_COORDS,
				toSizeAwareIterable(new double[] { 100, 75, 50, 25, 0 }));
		return tool;
	}
}
