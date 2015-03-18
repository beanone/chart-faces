package org.javaq.chartfaces.converter;

import org.javaq.chartfaces.api.IChartAxis;
import org.javaq.chartfaces.iterable.IIterableConverter;
import org.javaq.chartfaces.iterable.ISizeAwareIterable;
import org.javaq.chartfaces.iterable.IterableUtility;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


/**
 * Accesses the axis values. If not defined, default to the index of the labels.
 * 
 * @author Hongyan Li
 * 
 */
@Component("defaultAxisValueAccessor")
@Scope("singleton")
public class AxisValuesIterableConverter implements
		IIterableConverter<IChartAxis, Double, ISizeAwareIterable<Double>> {

	@Override
	public ISizeAwareIterable<Double> convert(final IChartAxis axis) {
		Iterable<? extends Number> valuesIterable;
		final Object valueObject = axis.getValue();
		if (valueObject == null) {
			final Object labelObject = axis.getLabel();
			valuesIterable = IterableUtility.toIterableIndices(labelObject);
		} else {
			valuesIterable = IterableUtility.toIterable(valueObject);
		}
		return valuesIterable == null ? null
				: IterableUtility.toIterableOfDoubleValues(valuesIterable);
	}
}
