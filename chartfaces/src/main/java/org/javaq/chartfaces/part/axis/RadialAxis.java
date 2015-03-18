package org.javaq.chartfaces.part.axis;

import javax.faces.component.FacesComponent;

import org.javaq.chartfaces.api.IScaler;
import org.javaq.chartfaces.component.impl.AbstractChartAxis;
import org.javaq.chartfaces.constants.Constants;
import org.javaq.chartfaces.constants.EnumPart;
import org.javaq.chartfaces.constants.EnumPosition;


/**
 * A {@link RadialAxis} defines a Radial axis of a chart - the data binding, the
 * position, and the style, etc..
 *
 * @author Hongyan Li
 * @since 1.0
 */
@FacesComponent(value = Constants.COMPONENT_TYPE_AXIS_RADIAL)
public class RadialAxis extends AbstractChartAxis {
	protected enum PropertyKeys {
		angle,
		min,
		max,
		scaler;

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

	public RadialAxis() {
		setPartType(EnumPart.radial);
	}

	public Double getAngle() {
		return (Double) getStateHelper().eval(PropertyKeys.angle, 0);
	}

	public void setAngle(final double angle) {
		getStateHelper().put(PropertyKeys.angle, angle);
	}

	@Override
	public Double getMax() {
		return (Double) getStateHelper().eval(PropertyKeys.max, null);
	}

	@Override
	public Double getMin() {
		return (Double) getStateHelper().eval(PropertyKeys.min, 0);
	}

	/**
	 * @return an {@link IScaler} that is used to control how the radial axis
	 *         values are scaled.
	 */
	public IScaler getScaler() {
		return (IScaler) getStateHelper().eval(PropertyKeys.scaler,
				null);
	}

	@Override
	public void setMax(final Double max) {
		getStateHelper().put(PropertyKeys.max, max);
	}

	@Override
	public void setMin(final Double min) {
		getStateHelper().put(PropertyKeys.min, min);
	}

	public void setScaler(final IScaler scaler) {
		getStateHelper().put(PropertyKeys.scaler, scaler);
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
