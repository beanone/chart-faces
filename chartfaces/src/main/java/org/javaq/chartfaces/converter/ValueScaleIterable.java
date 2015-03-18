package org.javaq.chartfaces.converter;

/**
 * Scales and translate the values and returns the result as Iterable of
 * Doubles.
 * 
 * @author Hongyan Li
 * 
 */
public class ValueScaleIterable<T extends Number> extends
		AbstractIterableToIterable<T, Double> {
	private final int newOrigin;
	private final double offset;
	private final double scale;

	public ValueScaleIterable(final double offset, final double scale,
			final int newOrigin) {
		this.offset = offset;
		this.scale = scale;
		this.newOrigin = newOrigin;
	}

	@Override
	protected Double transform(final Number target) {
		if (target == null) {
			// a null value corresponding to an undefined value
			return null;
		}

		return (target.doubleValue() - this.offset) * this.scale
				+ this.newOrigin;
	}
}
