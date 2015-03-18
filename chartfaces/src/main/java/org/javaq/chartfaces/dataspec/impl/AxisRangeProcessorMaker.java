package org.javaq.chartfaces.dataspec.impl;

import org.javaq.chartfaces.api.IChartAxis;
import org.javaq.chartfaces.api.IChartComponent;
import org.javaq.chartfaces.api.IChartingDataXYView;
import org.javaq.chartfaces.dataspec.IAxisRangeProcessor;
import org.javaq.chartfaces.util.NumberRange;

/**
 * The factory class for making {@link IAxisRangeProcessor} instances.
 * 
 * @author Hongyan Li
 * 
 */
final class AxisRangeProcessorMaker {
	
	private AxisRangeProcessorMaker() {}
	
	private static class XAxisRangeProcessor extends AxisRangeProcessor {
		public XAxisRangeProcessor(final IChartAxis axis) {
			super(axis);
		}

		@SuppressWarnings("rawtypes")
		@Override
		protected Iterable getIterableDataValues(final IChartComponent component) {
			final IChartingDataXYView cdxy = component.getChartingDataXYView();
			return (cdxy == null) ? null : cdxy.getXValues();
		}

		@Override
		protected void storeAxisRange(
				final IChartComponent component, final NumberRange range) {
			component.setXRange(range);
		}
	}

	private static class YAxisRangeProcessor extends AxisRangeProcessor {
		public YAxisRangeProcessor(final IChartAxis axis) {
			super(axis);
		}

		@SuppressWarnings("rawtypes")
		@Override
		protected Iterable getIterableDataValues(final IChartComponent component) {
			final IChartingDataXYView cdxy = component.getChartingDataXYView();
			return (cdxy == null) ? null : cdxy.getYValues();
		}

		@Override
		protected void storeAxisRange(
				final IChartComponent component, final NumberRange range) {
			component.setYRange(range);
		}
	}

	static IAxisRangeProcessor makeXAxisRangeProcessor(final IChartAxis axis) {
		return new XAxisRangeProcessor(axis);
	}

	static IAxisRangeProcessor makeYAxisRangeProcessor(final IChartAxis axis) {
		return new YAxisRangeProcessor(axis);
	}
}
