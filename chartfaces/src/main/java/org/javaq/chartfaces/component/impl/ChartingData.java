package org.javaq.chartfaces.component.impl;

import java.util.ArrayList;

import org.javaq.chartfaces.api.IChartingData;
import org.javaq.chartfaces.iterable.IterableUtility;

public class ChartingData implements IChartingData {
	private final Iterable<Double> depIterable;
	private final Iterable<Double> indepIterable;

	public ChartingData(final Iterable<? extends Number> indepIterable,
			final Iterable<? extends Number> depIterable) {
		this.indepIterable =
				IterableUtility.toIterableOfDoubleValues(indepIterable);
		this.depIterable =
				IterableUtility.toIterableOfDoubleValues(depIterable);
	}

	@Override
	public Iterable<Double> getDependentValues() {
		return this.depIterable;
	}

	@Override
	public Iterable<Double> getIndependentValues() {
		return this.indepIterable;
	}

	public static final ChartingData EMPTY = new ChartingData(new ArrayList<Number>(), new ArrayList<Number>());
}
