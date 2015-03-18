package org.javaq.chartfaces.dataspec.impl;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;

import org.javaq.chartfaces.api.IChartAxis;
import org.javaq.chartfaces.api.IChartComponent;
import org.javaq.chartfaces.dataspec.IAxisRangeProcessor;
import org.javaq.chartfaces.dataspec.IRangeCalculator;
import org.javaq.chartfaces.dataspec.PartFinder;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


/**
 * Implementation of {@link IRangeCalculator} for Cartesian coordinate.
 * 
 * @author Hongyan Li
 * 
 */
@Component("cartesianRangeCalculator")
@Scope("singleton")
public class CartesianRangeCalculator implements IRangeCalculator<UIComponent> {
	@Override
	public void calculateRanges(final UIComponent chart) throws IOException {
		final Collection<IAxisRangeProcessor> rangeProcessors = createAxisRangeProcessors(chart);
		for (final IAxisRangeProcessor processor : rangeProcessors) {
			processor.process();
		}
	}

	// Constructs the collection of axis range processor - groups the components
	// with their governing axis and coordinates the range calculation.
	private Collection<IAxisRangeProcessor>
			createAxisRangeProcessors(final UIComponent chart) {
		final Map<Object, IAxisRangeProcessor> processorMap = new HashMap<Object, IAxisRangeProcessor>();
		final String defaultX = "Default X";
		final String defaultY = "Default Y";
		final List<UIComponent> children = chart.getChildren();
		IChartComponent cc;
		Object key;
		IChartAxis axis;
		for (final UIComponent child : children) {
			if (child instanceof IChartComponent) {
				cc = (IChartComponent) child;

				// the x-axis range
				axis = PartFinder.findXAxis(cc);
				if (axis == null) {
					key = defaultX;
				} else {
					key = axis;
				}

				IAxisRangeProcessor processor = processorMap.get(key);
				if (processor == null) {
					processor = AxisRangeProcessorMaker
							.makeXAxisRangeProcessor(axis);
					processorMap.put(key, processor);
				}
				processor.addComponent(cc);

				// the y-axis range
				axis = PartFinder.findYAxis(cc);
				if (axis == null) {
					key = defaultY;
				} else {
					key = axis;
				}

				processor = processorMap.get(key);
				if (processor == null) {
					processor = AxisRangeProcessorMaker
							.makeYAxisRangeProcessor(axis);
					processorMap.put(key, processor);
				}
				processor.addComponent(cc);
			}
		}
		return processorMap.values();
	}
}
