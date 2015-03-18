package org.javaq.chartfaces.component.impl;

import org.javaq.chartfaces.constants.Constants;
import org.javaq.chartfaces.constants.EnumPosition;

/**
 * An abstract implementation of IChartPart. This can be used as a base
 * class for any IChartPart.
 * 
 * @author Hongyan Li
 * @since 1.0
 */
public abstract class AbstractChartPart extends UIChartBase {

	protected enum PropertyKeys {
		position;

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

	@Override
	public EnumPosition getEnumPosition() {
		final String positionString = getPosition();
		return EnumPosition.from(positionString);
	}

	@Override
	public String getFamily() {
		return Constants.COMPONENT_FAMILY_PART;
	}

	public String getPosition() {
		return (String) getStateHelper().eval(
				PropertyKeys.position, getPositionDefault().toString());
	}

	public void setPosition(final String pos) {
		getStateHelper().put(PropertyKeys.position, pos);
	}

	@Override
	public boolean isRendered() {
		return false;
	}

	@Override
	public boolean getRendersChildren() {
		return false;
	}

	protected abstract EnumPosition getPositionDefault();
}
