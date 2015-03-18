package org.javaq.chartfaces.part.layout.impl;

import java.io.Serializable;
import java.util.Comparator;

import org.javaq.chartfaces.api.IChartPart;
import org.javaq.chartfaces.constants.EnumPart;


public abstract class AbstractPartsComparator implements
		Comparator<IChartPart>, Serializable {
	private static final long serialVersionUID = -6417848813790463843L;

	@Override
	public int compare(final IChartPart part1, final IChartPart part2) {
		final Integer order1 = getOrder(part1);
		final Integer order2 = getOrder(part2);
		return (order1 == null || order2 == null) ? 0 : order1 - order2;
	}

	protected Integer getOrder(final IChartPart part) {
		Integer order = getPositionOrder(part);
		if (order != null && part.getPartType() == EnumPart.legend) {
			// legend is always outside the axes
			order *= 2;
		}
		return order;
	}

	protected abstract Integer getPositionOrder(IChartPart part);
}
