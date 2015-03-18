package org.javaq.chartfaces.part.component;

import javax.faces.component.FacesComponent;

import org.javaq.chartfaces.api.IChartComponent;
import org.javaq.chartfaces.api.IChartingData;
import org.javaq.chartfaces.api.IChartingDataXYView;
import org.javaq.chartfaces.component.impl.AbstractChartPart;
import org.javaq.chartfaces.component.impl.ChartingData;
import org.javaq.chartfaces.component.impl.ChartingDataXYView;
import org.javaq.chartfaces.constants.Constants;
import org.javaq.chartfaces.constants.EnumPart;
import org.javaq.chartfaces.constants.EnumPosition;
import org.javaq.chartfaces.util.NumberRange;


/**
 * A {@link ChartComponent} defines an element of a chart - the binding of a
 * data series for the chart, and the associated UI aspects of it.
 *
 * @author Hongyan Li
 * @since 1.0
 */
@FacesComponent(value = Constants.COMPONENT_TYPE_ELEMENT)
public class ChartComponent extends AbstractChartPart implements
		IChartComponent {
	protected enum PropertyKeys {
		axesIds,
		instructions,
		legendLabel,
		showTooltips;

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

	private IChartingData chartingData;

	private IChartingDataXYView chartingDataXYView;
	private NumberRange[] ranges;

	public ChartComponent() {
		setPartType(EnumPart.component);
	}

	@Override
	public String getAxesIds() {
		return (String) getStateHelper().eval(PropertyKeys.axesIds, null);
	}

	@Override
	public IChartingData getChartingData() {
		if (this.chartingData == null) {
			this.chartingData = createChartingData();
		}

		return this.chartingData;
	}

	@Override
	public IChartingDataXYView getChartingDataXYView() {
		if (this.chartingDataXYView == null) {
			this.chartingDataXYView = createChartingDataXYView();
		}
		return this.chartingDataXYView;
	}

	@Override
	public Object getInstructions() {
		return getStateHelper().eval(PropertyKeys.instructions, getName());
	}

	@Override
	public Object getLegendLabel() {
		return getStateHelper().eval(PropertyKeys.legendLabel, getName());
	}

	private NumberRange[] getRanges() {
		if (this.ranges == null) {
			this.ranges = new NumberRange[2];
			this.ranges[0] = new NumberRange();
			this.ranges[1] = new NumberRange();
		}
		return this.ranges;
	}

	@Override
	public boolean isShowTooltips() {
		return (Boolean) getStateHelper().eval(PropertyKeys.showTooltips, true);
	}

	public void setAxesIds(final String axesIds) {
		getStateHelper().put(PropertyKeys.axesIds, axesIds);
	}

	public void setInstructions(final Object instructions) {
		getStateHelper().put(PropertyKeys.instructions, instructions);
	}

	public void setLegendLabel(final Object label) {
		getStateHelper().put(PropertyKeys.legendLabel, label);
	}

	public void setShowTooltips(final boolean showTooltips) {
		getStateHelper().put(PropertyKeys.showTooltips, showTooltips);
	}

	private IChartingData createChartingData() {
		final Object value = getValue();
//		if(value==null) return ChartingDataXYView.EMPTY;
		IChartingData data = null;
		if (value instanceof IChartingData) {
			data = (IChartingData) value;
		} else {
			data = createChartingDataFromXYView(getChartingDataXYView());
		}

		return data;
	}

	private IChartingData createChartingDataFromXYView(
			final IChartingDataXYView value) {
		if (isHorizontal()) {
			return new ChartingData(value.getYValues(), value.getXValues());
		} else {
			return new ChartingData(value.getXValues(), value.getYValues());
		}
	}

	private IChartingDataXYView createChartingDataXYView() {
		final Object value = getValue();
		if(value==null) return ChartingDataXYView.EMPTY;
		IChartingDataXYView xyview = null;
		if (value instanceof IChartingData) {
			xyview = createChartingDataXYViewFromChartingData((IChartingData) value);
		} else {
			xyview = getChartSettings()
					.getDataConverter().toChartingDateXYView(value);
			if (xyview != null && xyview.getXValues() == null && isHorizontal()) {
				xyview = new ChartingDataXYView(xyview.getYValues(),
						xyview.getXValues());
			}
		}
		return xyview;
	}

	private IChartingDataXYView createChartingDataXYViewFromChartingData(
			final IChartingData value) {
		if (isHorizontal()) {
			return new ChartingDataXYView(value.getDependentValues(),
					value.getIndependentValues());
		} else {
			return new ChartingDataXYView(value.getIndependentValues(),
					value.getDependentValues());
		}
	}

	@Override
	protected Object getHeightDefault() {
		// a chart component has no height
		throw new UnsupportedOperationException();
	}

	@Override
	protected EnumPosition getPositionDefault() {
		return EnumPosition.middle;
	}

	@Override
	protected Object getWidthDefault() {
		throw new UnsupportedOperationException();
	}

	@Override
	public NumberRange getXRange() {
		return getRanges()[0];
	}

	@Override
	public NumberRange getYRange() {
		return getRanges()[1];
	}

	@Override
	public void setXRange(NumberRange range) {
		getRanges()[0] = range;
	}

	@Override
	public void setYRange(NumberRange range) {
		getRanges()[1] = range;
	}
}