package org.javaq.chartfaces.component.impl;

import java.util.HashMap;
import java.util.Map;

import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;

import org.javaq.chartfaces.ChartFacesManager;
import org.javaq.chartfaces.IChartSettings;
import org.javaq.chartfaces.api.Box;
import org.javaq.chartfaces.api.IChartPart;
import org.javaq.chartfaces.api.IPartSpec;
import org.javaq.chartfaces.constants.Constants;
import org.javaq.chartfaces.constants.EnumPart;



public abstract class UIChartBase extends UICommand implements IChartPart {
	private final class ChartBasePartSpec implements IPartSpec {
		@Override
		public Map<String, String> getProperties() {
			HashMap<String, String> props =
					(HashMap<String, String>) UIChartBase.this
							.getViewProperties();
			if (props == null) {
				props = new HashMap<String, String>();
				UIChartBase.this.setViewProperties(props);
			}
			return props;
		}

		@Override
		public Box getViewBox() {
			return UIChartBase.this.getViewBox();
		}

		@Override
		public void setViewBox(final Box viewBox) {
			UIChartBase.this.setViewBox(viewBox);
		}
	}

	protected enum PropertyKeys {
		height,
		name,
		padding,
		style,
		svgContainerId,
		value,
		viewBox,
		viewProperties,
		width,
		x,
		y;
	}

	private IChartSettings chartSettings;

	private final IPartSpec partSpecs = new ChartBasePartSpec();

	private EnumPart partType;

	public UIChartBase() {
		super();
	}

	@Override
	public IChartPart getParentPart() {
		UIComponent parent = getParent();
		return (parent instanceof IChartPart)? (IChartPart) parent : null;
	}

	@Override
	public String getHeight() {
		final Object value = getStateHelper().eval(
				UIChartBase.PropertyKeys.height,
				getHeightDefault());
		return value == null ? null : String.valueOf(value);
	}

	@Override
	public String getName() {
		return (String) getStateHelper().eval(PropertyKeys.name, null);
	}

	@Override
	public int getPadding() {
		return (Integer) getStateHelper().eval(PropertyKeys.padding,
				getChartSettings().getInternalPadding());
	}

	@Override
	public String getPartKey() {
		final String id = getId();
		return id == null ? getClientId() : id;
	}

	@Override
	public IPartSpec getPartSpec() {
		return this.partSpecs;
	}

	@Override
	public EnumPart getPartType() {
		return this.partType;
	}

	@Override
	public String getStyle() {
		return (String) getStateHelper().eval(PropertyKeys.style,
				getStyleDefault());
	}

	@Override
	public String getSvgContainerId() {
		return (String) getStateHelper().get(PropertyKeys.svgContainerId);
	}

	@Override
	public Object getValue() {
		return getStateHelper().eval(PropertyKeys.value, null);
	}

	public Box getViewBox() {
		return (Box) getStateHelper().get(PropertyKeys.viewBox);
	}

	@SuppressWarnings("unchecked")
	public Map<String, String> getViewProperties() {
		return (Map<String, String>) getStateHelper().get(
				PropertyKeys.viewProperties);
	}

	@Override
	public String getWidth() {
		final Object value = getStateHelper().eval(
				UIChartBase.PropertyKeys.width,
				getWidthDefault());
		return value == null ? null : String.valueOf(value);
	}

	@Override
	public int getX() {
		return (Integer) getStateHelper().eval(PropertyKeys.x, -1);
	}

	@Override
	public int getY() {
		return (Integer) getStateHelper().eval(PropertyKeys.y, -1);
	}

	public void setHeight(final java.lang.String height) {
		getStateHelper().put(PropertyKeys.height, height);
	}

	public void setName(final String name) {
		getStateHelper().put(PropertyKeys.name, name);
	}

	public void setPadding(final String padding) {
		getStateHelper().put(PropertyKeys.padding, padding);
	}

	public void setStyle(final String style) {
		getStateHelper().put(PropertyKeys.style, style);
	}

	@Override
	public void setSvgContainerId(final String id) {
		getStateHelper().put(PropertyKeys.svgContainerId, id);
	}

	@Override
	public void setValue(final Object value) {
		getStateHelper().put(PropertyKeys.value, value);
	}

	public void setViewBox(final Box box) {
		getStateHelper().put(PropertyKeys.viewBox, box);
	}

	public void setViewProperties(final Map<String, String> props) {
		getStateHelper().put(PropertyKeys.viewProperties, props);
	}

	public void setWidth(final String width) {
		getStateHelper().put(PropertyKeys.width, width);
	}

	public void setX(final int x) {
		getStateHelper().put(PropertyKeys.x, x);
	}

	public void setY(final int y) {
		getStateHelper().put(PropertyKeys.y, y);
	}

	protected UIChart getChart() {
		IChartPart part = this;
		while (!(part instanceof UIChart) && part != null) {
			part = (IChartPart) getParent();
		}
		return (UIChart) part;
	}

	protected ChartFacesManager getChartFacesManager() {
		return ChartFacesManager.getInstance();
	}

	protected IChartSettings getChartSettings() {
		if (this.chartSettings == null) {
			final Object settings =
					getChart() == null ? null : getChart().getSettings();
			if (settings instanceof IChartSettings) {
				this.chartSettings = (IChartSettings) settings;
			} else {
				this.chartSettings =
						getChartFacesManager().getDefaultChartSettings();
			}
		}
		return this.chartSettings;
	}

	protected abstract Object getHeightDefault();

	protected String getStyleDefault() {
		return null;
	}

	protected abstract Object getWidthDefault();

	protected boolean isHorizontal() {
		return Constants.ORIENTATION_HORIZONTAL.equals(
				getChart().getOrientation());
	}

	protected void setPartType(final EnumPart aType) {
		this.partType = aType;
	}

	@Override
	public int getAnchorX() {
		return 0;
	}

	@Override
	public int getAnchorY() {
		return 0;
	}
}