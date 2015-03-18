package org.javaq.chartfaces.part.layout.impl;

import org.javaq.chartfaces.api.Coordinate;
import org.javaq.chartfaces.api.IChartPart;
import org.javaq.chartfaces.component.impl.UIChart;

/**
 * Calculates the horizontal layout of the chart parts.
 * 
 * @author Hongyan Li
 * 
 */
public class HorizontalLayoutCalculator extends AbstractLayoutCalculator {
	private final UIChart chart;

	protected HorizontalLayoutCalculator(final Iterable<IChartPart> targets,
			final LayoutManager layoutManager, final UIChart chart) {
		super(targets, layoutManager);
		this.chart = chart;
	}

	/**
	 * @return the chart
	 */
	private UIChart getChart() {
		return this.chart;
	}

	@Override
	protected int getAbsolutePosition(final IChartPart part) {
		return part.getX();
	}

	@Override
	protected int getDimension() {
		return getChart().getViewBox().getWidth();
	}

	@Override
	protected int getEndPadding() {
		return getChart().getRightPadding();
	}

	@Override
	protected int getPositionMiddleOrigin(final IChartPart part) {
		return (int) ((getChart().getViewBox().getWidth()
				- getViewBox(part).getWidth() - 0.5) / 2.);
	}

	@Override
	protected int getStartPadding() {
		return getChart().getLeftPadding();
	}

	@Override
	protected int incrementPosition(final int currentValue,
			final IChartPart part) {
		// padding has already included in the viewBox calculations in
		// the SpecHelper
		return currentValue + getViewBox(part).getWidth();
	}

	@Override
	protected void setOrigin(final Coordinate origin, final int p) {
		origin.setX(p);
	}

}
