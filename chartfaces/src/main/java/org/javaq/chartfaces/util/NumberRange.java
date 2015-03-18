package org.javaq.chartfaces.util;

/**
 * Abstraction of a number range with a lower and upper bound.
 * 
 * @author Hongyan Li
 * 
 */
public class NumberRange {
	private Double maxV;
	private Double minV;

	/**
	 * @return the maxV
	 */
	public Double getMax() {
		return this.maxV;
	}

	/**
	 * @return the minV
	 */
	public Double getMin() {
		return this.minV;
	}

	/**
	 * @return true if both the lower and the upper bounds are set and the two
	 *         are not euqual.
	 */
	public boolean isValid() {
		return this.minV != null && this.maxV != null && !this.minV.equals(this.maxV);
	}

	/**
	 * Merges the passed in bound with the existing bounds so that existing
	 * bounds and new bound are all enclosed within the range.
	 * 
	 * @param bound
	 */
	public void mergeBound(final Double bound) {
		if (bound != null) {
			if (this.minV == null) {
				this.minV = bound;
			} else {
				final double min = Math.min(this.minV, bound);
				double max = Math.max(this.minV, bound);
				if (this.maxV != null) {
					max = Math.max(max, this.maxV);
				}
				this.minV = min;
				this.maxV = max;
			}
		}
	}
}