package org.javaq.chartfaces.part.component;

import javax.faces.component.FacesComponent;

import org.javaq.chartfaces.constants.Constants;


@FacesComponent(value = Constants.COMPONENT_TYPE_SCATTER)
public class ScatterComponent extends ChartComponent {
	protected enum PropertyKeys {
		fill,
		lineStyle,
		opacity,
		showLine,
		strokeColor,
		strokeWidth,
		symbol,
		symbolSize;

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

	public String getFill() {
		return (String) getStateHelper().eval(PropertyKeys.fill, null);
	}

	public String getLineStyle() {
		return (String) getStateHelper().eval(PropertyKeys.lineStyle,
				getChartSettings().getChartLineStyle());
	}

	public double getOpacity() {
		return (Double) getStateHelper().eval(PropertyKeys.opacity,
				getChartSettings().getScatterSymbolOpacity());
	}

	public String getStrokeColor() {
		return (String) getStateHelper().eval(PropertyKeys.strokeColor, null);
	}

	public int getStrokeWidth() {
		return (Integer) getStateHelper().eval(PropertyKeys.strokeWidth, 0);
	}

	public String getSymbol() {
		return (String) getStateHelper().eval(PropertyKeys.symbol,
				getDefaultSymbol());
	}

	/**
	 * It could be an integer indicating the size of the symbol or an iterable,
	 * or array of values that indicates the size of the symbols for each data
	 * point.
	 * 
	 * @return a size value of collection of size values.
	 */
	public Object getSymbolSize() {
		return getStateHelper().eval(PropertyKeys.symbolSize,
				getChartSettings().getScatterSymbolSize());
	}

	public boolean isShowLine() {
		return (Boolean) getStateHelper().eval(PropertyKeys.showLine, false);
	}

	public void setFill(final String fill) {
		getStateHelper().put(PropertyKeys.fill, fill);
	}

	public void setLineStyle(final String lineStyle) {
		getStateHelper().put(PropertyKeys.lineStyle, lineStyle);
	}

	public void setOpacity(final double opacity) {
		getStateHelper().put(PropertyKeys.opacity, opacity);
	}

	public void setShowLine(final boolean showLine) {
		getStateHelper().put(PropertyKeys.showLine, showLine);
	}

	public void setStrokeColor(final String strokeColor) {
		getStateHelper().put(PropertyKeys.strokeColor, strokeColor);
	}

	public void setStrokeWidth(final int width) {
		getStateHelper().put(PropertyKeys.strokeWidth, width);
	}

	public void setSymbol(final String symbol) {
		getStateHelper().put(PropertyKeys.symbol, symbol);
	}

	public void setSymbolSize(final Object size) {
		getStateHelper().put(PropertyKeys.symbolSize, size);
	}

	private String getDefaultSymbol() {
		if (!isShowLine()) {
			return getChartSettings().getScatterSymbol().toString();
		}
		return null;
	}
}