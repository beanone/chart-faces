package org.javaq.chartfaces.part.layout.impl;

import java.util.Comparator;

import junit.framework.Assert;

import org.javaq.chartfaces.ChartFacesManager;
import org.javaq.chartfaces.DefaultChartSettings;
import org.javaq.chartfaces.api.IChartPart;
import org.javaq.chartfaces.part.axis.XAxis;
import org.junit.Before;
import org.junit.Test;


public class VerticalOrderStrategyTest {
	private final ChartFacesManager manager = ChartFacesManager.getInstance();

	@Before
	public void setUp() {
		manager.setDefaultChartSettings(new DefaultChartSettings());
	}

	@Test
	public void testCompareBottomAndDefault() {
		VerticalOrderStrategy orderStrategy = new VerticalOrderStrategy();
		Comparator<IChartPart> comparator = orderStrategy.getPartComparator();
		final XAxis part1 = new XAxis();
		part1.setPosition("bottom");
		final XAxis part2 = new XAxis();
		Assert.assertTrue(comparator.compare(part1, part2) == 0);
		Assert.assertTrue(comparator.compare(part2, part2) == 0);
		Assert.assertTrue(comparator.compare(part2, part1) == 0);
	}

	@Test
	public void testCompareBottomAndMiddle() {
		VerticalOrderStrategy orderStrategy = new VerticalOrderStrategy();
		Comparator<IChartPart> comparator = orderStrategy.getPartComparator();
		final XAxis part1 = new XAxis();
		part1.setPosition("bottom");
		final XAxis part2 = new XAxis();
		part2.setPosition("middle");
		Assert.assertTrue(comparator.compare(part1, part2) > 0);
		Assert.assertTrue(comparator.compare(part2, part1) < 0);
	}

	@Test
	public void testCompareTopAndBottom() {
		VerticalOrderStrategy orderStrategy = new VerticalOrderStrategy();
		Comparator<IChartPart> comparator = orderStrategy.getPartComparator();
		final XAxis part1 = new XAxis();
		part1.setPosition("top");
		final XAxis part2 = new XAxis();
		part2.setPosition("bottom");
		Assert.assertTrue(comparator.compare(part1, part2) < 0);
		Assert.assertTrue(comparator.compare(part1, part1) == 0);
		Assert.assertTrue(comparator.compare(part2, part1) > 0);
	}

	@Test
	public void testCompareTopAndDefault() {
		VerticalOrderStrategy orderStrategy = new VerticalOrderStrategy();
		Comparator<IChartPart> comparator = orderStrategy.getPartComparator();
		final XAxis part1 = new XAxis();
		part1.setPosition("top");
		final XAxis part2 = new XAxis();
		Assert.assertTrue(comparator.compare(part1, part2) < 0);
		Assert.assertTrue(comparator.compare(part2, part2) == 0);
		Assert.assertTrue(comparator.compare(part2, part1) > 0);
	}

	@Test
	public void testCompareTopAndMiddle() {
		VerticalOrderStrategy orderStrategy = new VerticalOrderStrategy();
		Comparator<IChartPart> comparator = orderStrategy.getPartComparator();
		final XAxis part1 = new XAxis();
		part1.setPosition("top");
		final XAxis part2 = new XAxis();
		part2.setPosition("middle");
		Assert.assertTrue(comparator.compare(part1, part2) < 0);
		Assert.assertTrue(comparator.compare(part2, part2) == 0);
		Assert.assertTrue(comparator.compare(part2, part1) > 0);
	}
}