package org.javaq.chartfaces.component.impl;

import org.javaq.chartfaces.api.IScaler;
import org.javaq.chartfaces.iterable.IndexIterable;
import org.javaq.chartfaces.iterable.SortedIterable;

/**
 * A {@link AbstractCartesianAxis} defines a Cartesian coordinate axis of a
 * chart - its data binding, position, and style, etc..
 * 
 * @author Hongyan Li
 * @since 1.0
 */
public abstract class AbstractCartesianAxis extends AbstractChartAxis {

	protected enum PropertyKeys {
		max,
		min,
		scaler;

		private String value;

		PropertyKeys() {
		}

		PropertyKeys(final String toString) {
			this.setValue(toString);
		}

		@Override
		public String toString() {
			return ((this.getValue() != null) ? this.getValue() : super.toString());
		}

		String getValue() {
			return value;
		}

		void setValue(String value) {
			this.value = value;
		}
	}

	private boolean defaultComputed;
	private Double defaultMax;
	private Double defaultMin;

	@Override
	public Double getMax() {
		Double max = (Double) getStateHelper().eval(PropertyKeys.max, null);
		// only calculate the default if necessary
		max = (max == null) ? getDefaultMax() : max;
		return max;
	}

	@Override
	public Double getMin() {
		Double min = (Double) getStateHelper().eval(PropertyKeys.min, null);
		// only calculate the default if necessary
		min = (min == null) ? getDefaultMin() : min;
		return min;
	}

	/**
	 * @return an {@link IScaler} that is used to control how the axis values
	 *         are scaled.
	 */
	public IScaler getScaler() {
		return (IScaler) getStateHelper().eval(PropertyKeys.scaler,
				null);
	}

	@Override
	public void setMax(final Double max) {
		getStateHelper().put(PropertyKeys.max, max);
	}

	@Override
	public void setMin(final Double min) {
		getStateHelper().put(PropertyKeys.min, min);
	}

	public void setScaler(final IScaler scaler) {
		getStateHelper().put(PropertyKeys.scaler, scaler);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void computeDefaultMinMax() {
		if (!this.defaultComputed) {
			this.defaultComputed = true;
			final Iterable<?> values = getValueIterable();
			if (values != null) {
				final SortedIterable<?> sortedValues = new SortedIterable(values);
				if (sortedValues.getSize() > 1) {
					final double aMin = ((Number) sortedValues.getFirst()).doubleValue();
					final double aMax = ((Number) sortedValues.getLast()).doubleValue();
					final double range = aMax - aMin;
					this.defaultMin =
							aMin - getChartSettings().getAxisOffsetPercent() / 100. * range;
					this.defaultMax =
							aMax - getChartSettings().getAxisSlackPercent() / 100. * range;
					if (values instanceof IndexIterable) {
						this.defaultMax += 1;
					}
				}
			}
		}
	}

	private Double getDefaultMax() {
		computeDefaultMinMax();
		return this.defaultMax;
	}

	private Double getDefaultMin() {
		computeDefaultMinMax();
		return this.defaultMin;
	}
}
