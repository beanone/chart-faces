package org.javaq.chartfaces.part.axis;

import javax.faces.component.FacesComponent;

import org.javaq.chartfaces.component.impl.AbstractChartAxis;
import org.javaq.chartfaces.constants.Constants;
import org.javaq.chartfaces.constants.EnumPart;
import org.javaq.chartfaces.constants.EnumPosition;


/**
 * A {@link AngularAxis} defines an Angular axis of a chart - the data binding,
 * the position, and the style, etc..
 * 
 * @author Hongyan Li
 * @since 1.0
 */
@FacesComponent(value = Constants.COMPONENT_TYPE_AXIS_ANGULAR)
public class AngularAxis extends AbstractChartAxis {
	protected enum PropertyKeys {
		radius;

		private String value;

		PropertyKeys() {
		}

		PropertyKeys(final String toString) {
			this.setValue(toString);
		}

		@Override
		public String toString() {
			return ((this.getValue() != null) ? this.getValue() : super.toString());
		}

		String getValue() {
			return value;
		}

		void setValue(String value) {
			this.value = value;
		}
	}

	public AngularAxis() {
		setPartType(EnumPart.angular);
	}

	@Override
	public Double getMax() {
		return 360.;
	}

	@Override
	public Double getMin() {
		return 0.;
	}

	public Double getRadius() {
		return (Double) getStateHelper().eval(PropertyKeys.radius, null);
	}

	public void seRadius(final Double radius) {
		getStateHelper().put(PropertyKeys.radius, radius);
	}

	@Override
	public void setMax(final Double max) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setMin(final Double min) {
		throw new UnsupportedOperationException();
	}

	@Override
	protected Object getHeightDefault() {
		return null;
	}

	@Override
	protected EnumPosition getPositionDefault() {
		return EnumPosition.middle;
	}

	@Override
	protected Object getWidthDefault() {
		return null;
	}
}
