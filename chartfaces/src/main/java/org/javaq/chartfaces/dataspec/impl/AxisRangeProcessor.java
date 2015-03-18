package org.javaq.chartfaces.dataspec.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.javaq.chartfaces.api.IChartAxis;
import org.javaq.chartfaces.api.IChartComponent;
import org.javaq.chartfaces.dataspec.IAxisRangeProcessor;
import org.javaq.chartfaces.dataspec.RangeFinder;
import org.javaq.chartfaces.util.NumberRange;


/**
 * Abstract implementation of the {@link IAxisRangeProcessor} interface. Should
 * have one subclass for each type of axis.
 * 
 * @author Hongyan Li
 * 
 */
public abstract class AxisRangeProcessor implements IAxisRangeProcessor {
	private final IChartAxis axis;

	private final List<IChartComponent> components = new ArrayList<IChartComponent>();
	public AxisRangeProcessor(final IChartAxis axis) {
		this.axis = axis;
	}

	@Override
	public void addComponent(final IChartComponent component) {
		this.components.add(component);
	}

	@Override
	public void process() throws IOException {
		NumberRange unionRange = null;
		if (isAxisRangeUndefined()) {
			// we need to use the ranges of the data values of the chart
			// components only if their governing axis does NOT have a range
			// defined.
			unionRange = findComponentRangeUnion();
			if (this.axis != null) {
				handleAxisRangeDefault(this.axis, unionRange);
			}
		}
		if (this.axis != null) {
			storeAxisRangeToComponents(this.axis.getRange());
		} else {
			storeAxisRangeToComponents(unionRange);
		}
	}

	private NumberRange findComponentRangeUnion() {
		final NumberRange range = new NumberRange();
		NumberRange tempRange;
		@SuppressWarnings("rawtypes")
		Iterable iterableDataValues;
		for (final IChartComponent cc : getComponents()) {
			iterableDataValues = getIterableDataValues(cc);
			if (iterableDataValues != null) {
				tempRange = RangeFinder.findRange(iterableDataValues);
				range.mergeBound(tempRange.getMin());
				range.mergeBound(tempRange.getMax());
			}
		}
		return range;
	}

	private List<IChartComponent> getComponents() {
		return this.components;
	}

	private void storeAxisRangeToComponents(final NumberRange range) {
		if (range.getMax() == null || range.getMin() == null) {
			return;
		}
		for (final IChartComponent cc : getComponents()) {
			storeAxisRange(cc, range);
		}
	}

	@SuppressWarnings("rawtypes")
	protected abstract Iterable getIterableDataValues(IChartComponent component);

	// requires that axis is NOT null and either min or max Not defined
	protected void handleAxisRangeDefault(final IChartAxis axis,
			final NumberRange range) {
		if (range.getMin() == null || range.getMax() == null) {
			return;
		}
		if (axis.getMin() != null && axis.getMax() == null
					&& range.getMax() > axis.getMin()) {
			axis.getRange().mergeBound(axis.getMin()); // keep the min
			axis.getRange().mergeBound(range.getMax()); // take the max
		} else if (axis.getMax() != null && axis.getMin() == null
					&& range.getMin() < axis.getMax()) {
			axis.getRange().mergeBound(range.getMin()); // take the min
			axis.getRange().mergeBound(axis.getMax()); // keep the max
		} else if (axis.getMin() == null && axis.getMax() == null) {
			axis.getRange().mergeBound(range.getMin()); // take the min
			axis.getRange().mergeBound(range.getMax()); // take the max
		}
	}

	protected boolean isAxisRangeUndefined() {
		return (this.axis == null) || (this.axis.getRange().getMin() == null);
	}

	protected abstract void storeAxisRange(IChartComponent component,
			NumberRange range);
}