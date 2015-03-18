package org.javaq.chartfaces.converter;

/**
 * Scales and translate the values and returns the result as Iterable of Double.
 * 
 * @author Hongyan Li
 * 
 */
public class TranslateScaleToDoubleIterable<T extends Number> extends
		AbstractIterableToIterable<T, Double> {
	private final int newOrigin;
	private final double offset;
	private final double scale;

	public TranslateScaleToDoubleIterable(final double offset,
			final double scale,
			final int newOrigin) {
		this.offset = offset;
		this.scale = scale;
		this.newOrigin = newOrigin;
	}

	@Override
	protected Double transform(final T target) {
		if (target == null) {
			// a null value corresponding to an undefined value
			return null;
		}

		final Number num = target;
		return (num.doubleValue() - this.offset) * this.scale
				+ this.newOrigin;
	}

}
