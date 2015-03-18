package org.javaq.chartfaces.part.layout.impl;

import org.javaq.chartfaces.api.Coordinate;
import org.javaq.chartfaces.api.IChartPart;
import org.javaq.chartfaces.component.impl.UIChart;

/**
 * Calculates the the vertical layout of the chart parts.
 * 
 * @author Hongyan Li
 * 
 */
public class VerticalLayoutCalculator extends AbstractLayoutCalculator {
	private final UIChart chart;

	protected VerticalLayoutCalculator(final Iterable<IChartPart> targets,
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
		return part.getY();
	}

	@Override
	protected int getDimension() {
		return getChart().getViewBox().getHeight();
	}

	@Override
	protected int getEndPadding() {
		return getChart().getBottomPadding();
	}

	@Override
	protected int getPositionMiddleOrigin(final IChartPart part) {
		return (int) ((getChart().getViewBox().getHeight()
				- getViewBox(part).getHeight() - 0.5) / 2.);
	}

	@Override
	protected int getStartPadding() {
		return getChart().getTopPadding();
	}

	@Override
	protected int incrementPosition(final int currentValue,
			final IChartPart part) {
		// padding has already included in the viewBox calculations in
		// the SpecHelper
		return currentValue + getViewBox(part).getHeight();
	}

	@Override
	protected void setOrigin(final Coordinate origin, final int p) {
		origin.setY(p);
	}

}
