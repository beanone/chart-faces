package org.javaq.chartfaces.constants;

/**
 * Enumeration of type of positions. A {@link EnumPosition} is used
 *
 * @author Hongyan Li
 * @since 1.0
 */
public enum EnumPosition {
	absolute(null, null),
	bottom(null, 1),
	bottomLeft(-1, 1),
	bottomRight(1, 1),
	footer(null, 2),
	left(-1, null),
	middle(0, 0),
	right(1, null),
	header(null, -2),
	top(null, -1),
	topLeft(-1, -1),
	topRight(1, -1);

	/**
	 * Same as {@link EnumPosition#valueOf(String)} exception that this accepts
	 * null input.
	 *
	 * @param pos
	 * @return the matching {@link EnumPosition} or null if <code>pos</code> is
	 *         null.
	 */
	public static EnumPosition from(final String pos) {
		EnumPosition returns = null;
		if (pos != null) {
			try {
				returns = EnumPosition.valueOf(pos);
			} catch (final IllegalArgumentException e) {
				// do nothing
			}
		}
		return returns;
	}

	private final Integer horizontalOrder;

	private final Integer verticalOrder;

	private EnumPosition(final Integer horizontalOrder,
			final Integer verticalOrder) {
		this.horizontalOrder = horizontalOrder;
		this.verticalOrder = verticalOrder;
	}

	public Integer getHorizontalOrder() {
		return this.horizontalOrder;
	}

	public Integer getVerticalOrder() {
		return this.verticalOrder;
	}

	public boolean isLeftRight() {
		return this == left || this == right;
	}

	public boolean isTopBottom() {
		return this == top || this == bottom;
	}
}