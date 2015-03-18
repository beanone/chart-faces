package org.javaq.chartfaces.converter;

/**
 * Further modifies the values transformed by
 * {@link TranslateScaleToDoubleIterable} and the compliment of the transformed
 * values, where the base is the newRange.
 * 
 * @author Hongyan Li
 * 
 */
public class TranslateScaleComplimentDoubleIterable extends
		TranslateScaleToDoubleIterable<Double> {
	private final int newRange;

	/**
	 * 
	 * @param offset
	 * @param scale
	 * @param newOrigin
	 * @param newRange
	 *            the new data range which will be used as the base to calculate
	 *            the complimentary.
	 */
	public TranslateScaleComplimentDoubleIterable(final double offset,
			final double scale,
			final int newOrigin, final int newRange) {
		super(offset, scale, newOrigin);
		this.newRange = newRange;
	}

	@Override
	protected Double transform(final Double target) {
		final Double returns = super.transform(target);
		return returns == null ? null : this.newRange - returns;
	}
}