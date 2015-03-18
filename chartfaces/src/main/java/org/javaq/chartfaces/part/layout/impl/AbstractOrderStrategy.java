package org.javaq.chartfaces.part.layout.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.javaq.chartfaces.api.IChartPart;


public abstract class AbstractOrderStrategy implements IOrderStrategy {
	public AbstractOrderStrategy() {
		super();
	}

	@Override
	public Iterable<IChartPart> order(final Iterable<IChartPart> parts) {
		final List<IChartPart> partsToOrder = new ArrayList<IChartPart>();
		for (final IChartPart part : parts) {
			if (accept(part)) {
				partsToOrder.add(part);
			}
		}

		Collections.sort(partsToOrder, getPartComparator());

		return partsToOrder;
	}

	/**
	 * A filtering method that determines whether an {@link IChartPart} should
	 * be included in the returned from the {@link #order(Iterable)} method.
	 * 
	 * @param part
	 * @return true if the {@link IChartPart} is accepted.
	 */
	protected abstract boolean accept(IChartPart part);

	/**
	 * The {@link Comparator} that determines the order of the
	 * {@link IChartPart} instances that are included in the returned from the
	 * {@link #order(Iterable)} method.
	 * 
	 * @return a {@link Comparator} for ordering chart parts.
	 */
	protected abstract Comparator<IChartPart> getPartComparator();
}