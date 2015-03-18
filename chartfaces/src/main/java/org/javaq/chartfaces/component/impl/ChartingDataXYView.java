package org.javaq.chartfaces.component.impl;

import java.util.ArrayList;

import org.javaq.chartfaces.api.IChartingDataXYView;
import org.javaq.chartfaces.iterable.IterableUtility;

public class ChartingDataXYView implements IChartingDataXYView {
	private final Iterable<Double> xIterable;
	private final Iterable<Double> yIterable;

	public ChartingDataXYView(final Iterable<? extends Number> xIterable,
			final Iterable<? extends Number> yIterable) {
		this.xIterable = IterableUtility.toIterableOfDoubleValues(xIterable);
		this.yIterable = IterableUtility.toIterableOfDoubleValues(yIterable);
	}

	@Override
	public Iterable<Double> getXValues() {
		return this.xIterable;
	}

	@Override
	public Iterable<Double> getYValues() {
		return this.yIterable;
	}

	public static final ChartingDataXYView EMPTY = new ChartingDataXYView(new ArrayList<Number>(), new ArrayList<Number>());
}
