package org.javaq.chartfaces.part.axis;

import javax.faces.component.FacesComponent;

import org.javaq.chartfaces.component.impl.AbstractCartesianAxis;
import org.javaq.chartfaces.constants.Constants;
import org.javaq.chartfaces.constants.EnumPart;
import org.javaq.chartfaces.constants.EnumPosition;


/**
 * A {@link YAxis} defines a y axis of a chart - its data binding, position, and
 * style, etc..
 * 
 * @author Hongyan Li
 * @since 1.0
 */
@FacesComponent(value = Constants.COMPONENT_TYPE_AXIS_Y)
public class YAxis extends AbstractCartesianAxis {

	public YAxis() {
		setPartType(EnumPart.yaxis);
	}

	@Override
	protected Object getHeightDefault() {
		return null;
	}

	@Override
	protected EnumPosition getPositionDefault() {
		return getChartSettings().getYAxisPosition();
	}

	@Override
	protected Object getWidthDefault() {
		return getChartSettings().getYAxisWidth();
	}
}
