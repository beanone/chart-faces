package org.javaq.chartfaces.part.layout.impl;

import java.io.IOException;

import org.javaq.chartfaces.api.Box;
import org.javaq.chartfaces.api.Coordinate;
import org.javaq.chartfaces.api.IChartPart;
import org.javaq.chartfaces.constants.EnumPart;
import org.javaq.chartfaces.constants.EnumPosition;


/**
 * An abstract implementation that can be sub-classed to support positioning
 * along either the x-axis or y-axis.
 * 
 * @author Hongyan Li
 * 
 */
abstract class AbstractLayoutCalculator implements ILayoutCalculator {
	private final LayoutManager layoutCreator;
	private final Iterable<IChartPart> targets;

	/**
	 * Create a new instance.
	 * 
	 * @param targets
	 * @param layoutManager
	 */
	protected AbstractLayoutCalculator(final Iterable<IChartPart> targets,
			final LayoutManager layoutManager) {
		this.targets = targets;
		this.layoutCreator = layoutManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.javaq.chartfaces.part.layout.ILayoutStrategy#execute()
	 */
	@Override
	public void execute() throws IOException {
		// we don't count the chart padding since we are inside the padded box
		int p = 0;
		for (final IChartPart part : this.targets) {
			final Coordinate origin = this.layoutCreator.origin(part);
			final int absPos = tryGetPosition(part);
			if (absPos > -1) {
				setOrigin(origin, absPos);
			} else {
				setOrigin(origin, p);
				p = incrementPosition(p, part);
			}
		}
		// the two should be exactly the same but may be off a little because we
		// were dropping the decimals in the calculations. We may adjust the
		// calculation by absorbing the errors but that is too much effort for
		// the gain in my opinion.
		if (p > getDimension() - getStartPadding() - getEndPadding()) {
			throw new IOException("Not enough charting space!");
		}
	}

	/**
	 * Try get the position. If requested to use {@link EnumPosition#middle},
	 * the position will be calculated..
	 * 
	 * @param part
	 * @return
	 */
	private int tryGetPosition(final IChartPart part) {
		int returns = -1;
		if (part.getPartType().getBaseType() != EnumPart.component) {
			if (EnumPosition.middle == part.getEnumPosition()) {
				returns = getPositionMiddleOrigin(part);
			}
			final int requested = getAbsolutePosition(part);
			if (requested > -1) {
				returns = requested;
			}
		}
		return returns;
	}

	/**
	 * Get the ViewBox for the part. A convenience method.
	 * 
	 * @param part an {@link IChartPart} to be boxed.
	 * @return the {@link Box} that exactly enclosing the passed in chart part.
	 */
	protected Box getViewBox(final IChartPart part) {
		return part.getPartSpec().getViewBox();
	}

	/**
	 * Get the absolute position.
	 * 
	 * @param part
	 * @return the absolute position of the passed in part in the containers viewBox coordinates.
	 */
	protected abstract int getAbsolutePosition(IChartPart part);

	/**
	 * The chart viewBox width or height.
	 * 
	 * @return a dimension in the containers viewBox coordinates.
	 */
	protected abstract int getDimension();

	/**
	 * The chart right or bottom padding.
	 * 
	 * @return a padding value in the containers viewBox coordinates.
	 */
	protected abstract int getEndPadding();

	/**
	 * Compute the origin position for a part if request to be placed in the
	 * middle.
	 * 
	 * @param part
	 * @return a origin coordinate value in the containers viewBox coordinates.
	 */
	protected abstract int getPositionMiddleOrigin(IChartPart part);

	/**
	 * The chart left or top padding.
	 * 
	 * @return a padding value in the containers viewBox coordinates.
	 */
	protected abstract int getStartPadding();

	/**
	 * Increment the current position by adding the total space taken by the
	 * part.
	 * 
	 * @param currentValue
	 * @param part
	 * @return the resulting position value in the containers viewBox coordinates.
	 */
	protected abstract int incrementPosition(int currentValue,
			IChartPart part);

	/**
	 * Set the calculated origin position of the part.
	 * 
	 * @param origin
	 * @param p
	 */
	protected abstract void setOrigin(Coordinate origin, int p);
}
