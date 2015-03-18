package org.javaq.chartfaces.part.layout.impl;

import java.util.Comparator;

import junit.framework.Assert;

import org.javaq.chartfaces.ChartFacesManager;
import org.javaq.chartfaces.DefaultChartSettings;
import org.javaq.chartfaces.api.IChartPart;
import org.javaq.chartfaces.part.axis.YAxis;
import org.junit.Before;
import org.junit.Test;


public class HorizontalOrderStrategyTest {

	@Before
	public void setUp() {
		ChartFacesManager.getInstance().setDefaultChartSettings(
				new DefaultChartSettings());
	}

	@Test
	public void testCompareLeftAndDefault() {
		HorizontalOrderStrategy orderStrategy = new HorizontalOrderStrategy();
		Comparator<IChartPart> comparator = orderStrategy.getPartComparator();
		final YAxis part1 = new YAxis();
		part1.setPosition("left");
		final YAxis part2 = new YAxis();
		Assert.assertTrue(comparator.compare(part1, part2) == 0);
		Assert.assertTrue(comparator.compare(part2, part2) == 0);
		Assert.assertTrue(comparator.compare(part2, part1) == 0);
	}

	@Test
	public void testCompareLeftAndMiddle() {
		HorizontalOrderStrategy orderStrategy = new HorizontalOrderStrategy();
		Comparator<IChartPart> comparator = orderStrategy.getPartComparator();
		final YAxis part1 = new YAxis();
		part1.setPosition("left");
		final YAxis part2 = new YAxis();
		part2.setPosition("middle");
		Assert.assertTrue(comparator.compare(part1, part2) < 0);
		Assert.assertTrue(comparator.compare(part2, part2) == 0);
		Assert.assertTrue(comparator.compare(part2, part1) > 0);
	}

	@Test
	public void testCompareLeftAndRight() {
		HorizontalOrderStrategy orderStrategy = new HorizontalOrderStrategy();
		Comparator<IChartPart> comparator = orderStrategy.getPartComparator();
		final YAxis part1 = new YAxis();
		part1.setPosition("left");
		final YAxis part2 = new YAxis();
		part2.setPosition("right");
		Assert.assertTrue(comparator.compare(part1, part2) < 0);
		Assert.assertTrue(comparator.compare(part1, part1) == 0);
		Assert.assertTrue(comparator.compare(part2, part1) > 0);
	}

	@Test
	public void testCompareRightAndDefault() {
		HorizontalOrderStrategy orderStrategy = new HorizontalOrderStrategy();
		Comparator<IChartPart> comparator = orderStrategy.getPartComparator();
		final YAxis part1 = new YAxis();
		part1.setPosition("right");
		final YAxis part2 = new YAxis();
		Assert.assertTrue(comparator.compare(part1, part2) > 0);
		Assert.assertTrue(comparator.compare(part2, part2) == 0);
		Assert.assertTrue(comparator.compare(part2, part1) < 0);
	}

	@Test
	public void testCompareRightAndMiddle() {
		HorizontalOrderStrategy orderStrategy = new HorizontalOrderStrategy();
		Comparator<IChartPart> comparator = orderStrategy.getPartComparator();
		final YAxis part1 = new YAxis();
		part1.setPosition("right");
		final YAxis part2 = new YAxis();
		part2.setPosition("middle");
		Assert.assertTrue(comparator.compare(part1, part2) > 0);
		Assert.assertTrue(comparator.compare(part2, part1) < 0);
	}
}