package org.javaq.chartfaces.part.legend;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponent;

import org.javaq.chartfaces.api.IChartLegend;
import org.javaq.chartfaces.component.impl.AbstractChartPart;
import org.javaq.chartfaces.constants.Constants;
import org.javaq.chartfaces.constants.EnumLayout;
import org.javaq.chartfaces.constants.EnumPart;
import org.javaq.chartfaces.constants.EnumPosition;


/**
 * A {@link ChartLegend} defines a legend of a chart - the data binding, the
 * position, and the style, etc..
 *
 * @author Hongyan Li
 * @since 1.0
 */
@FacesComponent(value = Constants.COMPONENT_TYPE_LEGEND)
public class ChartLegend extends AbstractChartPart implements IChartLegend {
	protected enum PropertyKeys {
		anchorX, // default 0
		anchorY, // default 0
		layout,
		forValue("for");

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

		void setValue(String aValue) {
			this.value = aValue;
		}
	}

	public ChartLegend() {
		setPartType(EnumPart.legend);
	}

	@Override
	public EnumLayout getLayout() {
		return (EnumLayout) getStateHelper().eval(PropertyKeys.layout,
				getChartSettings().getLegendLayout());
	}

	public void setLayout(final EnumLayout layout) {
		getStateHelper().put(PropertyKeys.layout, layout);
	}

	private boolean isLeftRight() {
		final EnumPosition position = getEnumPosition();
		return position.isLeftRight();
	}

	private boolean isTopBottom() {
		final EnumPosition position = getEnumPosition();
		return position.isTopBottom();
	}

	@Override
	protected Object getHeightDefault() {
		String defaultHeight = "" + getChartSettings().getLegendHeight();
		if (isLeftRight()) {
			defaultHeight = null;
		} else if (isTopBottom()) {
			defaultHeight = "" + (getChartSettings().getLegendHeight() / 2);
		}
		return defaultHeight;
	}

	@Override
	protected EnumPosition getPositionDefault() {
		return getChartSettings().getLegendPosition();
	}

	@Override
	protected Object getWidthDefault() {
		String defaultWidth = "" + getChartSettings().getLegendWidth();
		if (isTopBottom()) {
			defaultWidth = null;
		} else if (isLeftRight()) {
			defaultWidth = "" + (getChartSettings().getLegendHeight() / 2);
		}

		return defaultWidth;
	}

    /**
     * Return the Identifier of the chart part for which to render legend.
     * If this component is within the same NamingContainer as the target
     * component, this must be the component identifier. Otherwise, it
     * must be an absolute component identifier (starting with ":"). See
     * the {@link UIComponent#findComponent} for more
     * information.
     */
	@Override
	public String getFor() {
        return (String) getStateHelper().eval(PropertyKeys.forValue);
	}

    /**
     * Set the identifier of the chart part for which this legend is for.
     * This property required.
     *
     * @param newFor The new client id
     */
    public void setFor(String newFor) {
        getStateHelper().put(PropertyKeys.forValue, newFor);
    }

	@Override
	public int getAnchorX() {
		return (Integer) getStateHelper().eval(PropertyKeys.anchorX, 0);
	}

	@Override
	public int getAnchorY() {
		return (Integer) getStateHelper().eval(PropertyKeys.anchorY, 0);
	}

	public void setAnchorX(final int x) {
		getStateHelper().put(PropertyKeys.anchorX, x);
	}

	public void setAnchorY(final int y) {
		getStateHelper().put(PropertyKeys.anchorY, y);
	}
}