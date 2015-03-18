package org.javaq.chartfaces.component.impl;

import org.javaq.chartfaces.api.IChartAxis;
import org.javaq.chartfaces.constants.EnumPosition;
import org.javaq.chartfaces.util.NumberRange;

/**
 * Abstract super class for all axis parts.
 * 
 * @author Hongyan Li
 * @since 1.0
 */
public abstract class AbstractChartAxis extends AbstractChartPart implements
		IChartAxis {
	protected enum PropertyKeys {
		caption,
		captionStyle,
		gridLineDensity,
		gridLineStyle,
		label,
		labelAngle,
		labelStyle,
		lineStyle,
		tickDirection,
		tickHeight,
		tickStyle,
		title;

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

	private static void mergeToRange(final NumberRange nr, final Double bound) {
		if (bound != null) {
			nr.mergeBound(bound);
		}
	}

	private boolean initialized;

	private Iterable<Object> labelIterable;

	private NumberRange range;

	private Iterable<Double> valueIterable;

	@Override
	public String getCaption() {
		return (String) getStateHelper().eval(PropertyKeys.caption, null);
	}

	@Override
	public String getCaptionStyle() {
		return (String) getStateHelper().eval(PropertyKeys.captionStyle,
				getChartSettings().getCaptionStyle());
	}

	@Override
	public int getGridLineDensity() {
		return (Integer) getStateHelper().eval(PropertyKeys.gridLineDensity,
				getChartSettings().getGridLineDensity());
	}

	@Override
	public String getGridLineStyle() {
		return (String) getStateHelper().eval(PropertyKeys.gridLineStyle,
				getChartSettings().getGridLineStyle());
	}

	@Override
	public Object getLabel() {
		return getStateHelper().eval(PropertyKeys.label, null);
	}

	@Override
	public int getLabelAngle() {
		return (Integer) getStateHelper().eval(PropertyKeys.labelAngle, 0);
	}

	@Override
	public Iterable<Object> getLabelIterable() {
		checkInit();
		return this.labelIterable;
	}

	@Override
	public String getLabelStyle() {
		return (String) getStateHelper().eval(PropertyKeys.labelStyle,
				getChartSettings().getLabelStyle());
	}

	@Override
	public int getLabelTickSpacing() {
		return getChartSettings().getLabelTickSpacing();
	}

	@Override
	public String getLineStyle() {
		return (String) getStateHelper().eval(PropertyKeys.lineStyle,
				getChartSettings().getAxisLineStyle());
	}

	@Override
	public NumberRange getRange() {
		if (this.range == null) {
			// lazy init
			this.range = new NumberRange();
			if (getMin() != null && getMax() != null) {
				AbstractChartAxis.mergeToRange(this.range, getMin());
				AbstractChartAxis.mergeToRange(this.range, getMax());
			} // otherwise calculation is needed
		}
		return this.range;
	}

	@Override
	public String getTickDirection() {
		return (String) getStateHelper().eval(PropertyKeys.tickDirection,
				getChartSettings().getTickDirection(getPartType(),
						getPositionEnum(getEnumPosition())));
	}

	@Override
	public int getTickHeight() {
		return (Integer) getStateHelper().eval(PropertyKeys.tickHeight,
				getChartSettings().getTickHeight());
	}

	@Override
	public String getTickStyle() {
		return (String) getStateHelper().eval(PropertyKeys.tickStyle,
				getChartSettings().getTickStyle());
	}

	@Override
	public String getTitle() {
		return (String) getStateHelper().eval(PropertyKeys.title, null);
	}

	@Override
	public Iterable<Double> getValueIterable() {
		checkInit();
		return this.valueIterable;
	}

	public void setCaption(final String caption) {
		getStateHelper().put(PropertyKeys.caption, caption);
	}

	public void setCaptionStyle(final String style) {
		getStateHelper().put(PropertyKeys.captionStyle, style);
	}

	public void setGridLineDensity(final int density) {
		getStateHelper().put(PropertyKeys.gridLineDensity, density);
	}

	public void setGridLineStyle(final String style) {
		getStateHelper().put(PropertyKeys.gridLineStyle, style);
	}

	public void setLabel(final Object label) {
		getStateHelper().put(PropertyKeys.label, label);
	}

	public void setLabelAngle(final int angle) {
		getStateHelper().put(PropertyKeys.labelAngle, angle);
	}

	public void setLabelStyle(final String style) {
		getStateHelper().put(PropertyKeys.labelStyle, style);
	}

	public void setLineStyle(final String style) {
		getStateHelper().put(PropertyKeys.lineStyle, style);
	}

	public void setTickDir(final String tickDir) {
		getStateHelper().put(PropertyKeys.tickHeight, tickDir);
	}

	public void setTickHeight(final int tickHeight) {
		getStateHelper().put(PropertyKeys.tickHeight, tickHeight);
	}

	public void setTickStyle(final String style) {
		getStateHelper().put(PropertyKeys.tickStyle, style);
	}

	public void setTitle(final String title) {
		getStateHelper().put(PropertyKeys.title, title);
	}

	private void checkInit() {
		if (!this.initialized) {
			this.labelIterable =
					getChartSettings().getAxisLabelAccessor().convert(this);
			this.valueIterable =
					getChartSettings().getAxisValueAccessor().convert(this);
			this.initialized = true;
		}
	}

	private EnumPosition getPositionEnum(final EnumPosition aPosition) {
		EnumPosition position = aPosition;
		if (position == null) {
			position = getPositionDefault();
		}

		return position;
	}
}
