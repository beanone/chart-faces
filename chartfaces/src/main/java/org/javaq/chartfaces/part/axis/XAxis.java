package org.javaq.chartfaces.part.axis;

import javax.faces.component.FacesComponent;

import org.javaq.chartfaces.component.impl.AbstractCartesianAxis;
import org.javaq.chartfaces.constants.Constants;
import org.javaq.chartfaces.constants.EnumPart;
import org.javaq.chartfaces.constants.EnumPosition;


/**
 * A {@link XAxis} defines a x axis of a chart - its data binding, position, and
 * style, etc..
 * 
 * @author Hongyan Li
 * @since 1.0
 */
@FacesComponent(value = Constants.COMPONENT_TYPE_AXIS_X)
public class XAxis extends AbstractCartesianAxis {

	public XAxis() {
		setPartType(EnumPart.xaxis);
	}

	@Override
	protected Object getHeightDefault() {
		return getChartSettings().getXAxisHeight();
	}

	@Override
	protected EnumPosition getPositionDefault() {
		return getChartSettings().getXAxisPosition();
	}

	@Override
	protected Object getWidthDefault() {
		return null;
	}
}