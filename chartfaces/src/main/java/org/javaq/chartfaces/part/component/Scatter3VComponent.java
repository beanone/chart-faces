package org.javaq.chartfaces.part.component;

import java.util.Map;

import javax.faces.component.FacesComponent;

import org.javaq.chartfaces.constants.Constants;
import org.javaq.chartfaces.constants.EnumPart;


@FacesComponent(value = Constants.COMPONENT_TYPE_SCATTER3V)
public class Scatter3VComponent extends ScatterComponent {
	protected enum PropertyKeys {
		propertyMap,
		symbolScale;

		private String value;

		private PropertyKeys() {
		}

		private PropertyKeys(final String toString) {
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

	public Scatter3VComponent() {
		setPartType(EnumPart.scatter3v);
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getPropertyMap() {
		return (Map<String, Object>) getStateHelper().eval(
				PropertyKeys.propertyMap, null);
	}

	public double getSymbolScale() {
		return ((Number) getStateHelper().eval(PropertyKeys.symbolScale, 1.))
				.doubleValue();
	}

	public void setPropertyMap(final Map<String, Object> map) {
		getStateHelper().put(PropertyKeys.propertyMap, map);
	}

	public void setSymbolScale(final double scale) {
		getStateHelper().put(PropertyKeys.symbolScale, scale);
	}
}