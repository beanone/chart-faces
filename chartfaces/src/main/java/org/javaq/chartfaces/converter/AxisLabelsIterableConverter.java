package org.javaq.chartfaces.converter;

import org.javaq.chartfaces.api.IChartAxis;
import org.javaq.chartfaces.iterable.IIterableConverter;
import org.javaq.chartfaces.iterable.ISizeAwareIterable;
import org.javaq.chartfaces.iterable.IterableUtility;
import org.javaq.chartfaces.iterable.ReadonlySizeAwareIterable;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


/**
 * Accesses axis labels. If not defined, default to the values.
 * 
 * @author Hongyan Li
 * 
 */
@Component("defaultAxisLabelAccessor")
@Scope("singleton")
public class AxisLabelsIterableConverter implements
		IIterableConverter<IChartAxis, Object, ISizeAwareIterable<Object>> {
	@Override
	public ISizeAwareIterable<Object> convert(final IChartAxis axis) {
		Iterable<Object> labelsIterable;
		final Object labelsObject = axis.getLabel();
		if (labelsObject == null) {
			final Object valueObject = axis.getValue();
			labelsIterable = IterableUtility.toIterable(valueObject);
		} else {
			labelsIterable = IterableUtility.toIterable(labelsObject);
		}
		return labelsIterable == null ? null
				: new ReadonlySizeAwareIterable<Object>(labelsIterable);
	}
}
