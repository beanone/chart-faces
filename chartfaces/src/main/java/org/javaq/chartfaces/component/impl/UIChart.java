package org.javaq.chartfaces.component.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.el.MethodExpression;
import javax.faces.component.FacesComponent;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.FacesContext;
import javax.faces.event.FacesEvent;

import org.javaq.chartfaces.api.Box;
import org.javaq.chartfaces.constants.Constants;
import org.javaq.chartfaces.constants.EnumOrientation;
import org.javaq.chartfaces.constants.EnumOutputType;
import org.javaq.chartfaces.constants.EnumPart;
import org.javaq.chartfaces.constants.EnumPosition;


/**
 * The abstract super class for all the chart components.
 * <p/>
 * Note: behavioral attributes are purposefully left out of this component and
 * is expected to be handled by facelet tags. Such design decision should be
 * respected by the subclasses for the sake of consistency.
 *
 *
 * @author Hongyan Li
 * @since 1.0
 */
@FacesComponent(value = Constants.COMPONENT_TYPE_CHART)
public class UIChart extends UIChartBase implements ClientBehaviorHolder {
	public static enum PropertyKeys {
		borderStyle,
		bottomPadding,
		bounds,
		cancel,
		footer,
		footerStyle,
		header,
		headerStyle,
		leftPadding,
		legendOff,
		liveListener("live"),
		progressiveRenderListener("progressive"),
		progress,
		margin,
		orientation,
		output,
		panListener("move"),
		refreshInterval,
		resetListener("reset"),
		rightPadding,
		settings,
		start,
		topPadding,
		zoomListener("zoom"),
		wheelListener("wheel");

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

	private static final Map<String, PropertyKeys> EVENT_MAP = new HashMap<String, PropertyKeys>();
	static {
		UIChart.addEventEntry(PropertyKeys.liveListener);
		UIChart.addEventEntry(PropertyKeys.progressiveRenderListener);
		UIChart.addEventEntry(PropertyKeys.panListener);
		UIChart.addEventEntry(PropertyKeys.resetListener);
		UIChart.addEventEntry(PropertyKeys.zoomListener);
		UIChart.addEventEntry(PropertyKeys.wheelListener);
	}

	private static void addEventEntry(final PropertyKeys key) {
		UIChart.EVENT_MAP.put(key.getValue(), key);
	}

	public UIChart() {
		setPartType(EnumPart.chart);
	}

	@Override
	public void broadcast(final FacesEvent event) {
		super.broadcast(event);

		final FacesContext facesContext = FacesContext.getCurrentInstance();

		final String eventName = getChartFacesManager().getChartEventFactory()
				.getEventName(event.getClass());
		if (eventName != null) {
			final PropertyKeys key = UIChart.EVENT_MAP.get(eventName);
			if (key != null) {
				final MethodExpression me =
						(MethodExpression) getStateHelper().eval(key, null);
				if (me != null) {
					me.invoke(facesContext.getELContext(),
							new Object[] { event });
				}
			}
		}
	}

	public String getBorderStyle() {
		return (String) getStateHelper().eval(PropertyKeys.borderStyle,
				getChartSettings().getBorderStyle());
	}

	public int getBottomPadding() {
		return (Integer) getStateHelper().eval(PropertyKeys.bottomPadding,
				getChartSettings().getPadding());
	}

	public Box getChartingAreaBounds() {
		return (Box) getStateHelper().get(PropertyKeys.bounds);
	}

	public boolean isCancel() {
		return (Boolean) getStateHelper().eval(PropertyKeys.cancel, Boolean.FALSE);
	}

	public boolean isStart() {
		return (Boolean) getStateHelper().eval(PropertyKeys.start, Boolean.TRUE);
	}

	@Override
	public String getDefaultEventName() {
		return "refresh";
	}

	@Override
	public EnumPosition getEnumPosition() {
		// no position for the chart
		return null;
	}

	@Override
	public Collection<String> getEventNames() {
		// TODO: investigate what is needed and add them here.
		return Arrays.asList("refresh", "click", "mouseover");
	}

	@Override
	public String getFamily() {
		return Constants.COMPONENT_FAMILY_CHART;
	}

	public String getFooter() {
		return (String) getStateHelper().eval(PropertyKeys.footer, null);
	}

	public String getFooterStyle() {
		return (String) getStateHelper().eval(PropertyKeys.footerStyle,
				getChartSettings().getFooterStyle());
	}

	public String getHeader() {
		return (String) getStateHelper().eval(PropertyKeys.header, null);
	}

	public String getHeaderStyle() {
		return (String) getStateHelper().eval(PropertyKeys.headerStyle,
				getChartSettings().getHeaderStyle());
	}

	public int getLeftPadding() {
		return (Integer) getStateHelper().eval(PropertyKeys.leftPadding,
				getChartSettings().getPadding());
	}

	/**
	 * By default, a chart has a legend even if one is not specified. Set this
	 * to true to turn it off. *
	 *
	 * @return true if legend is turned off.
	 */
	public boolean getLegendOff() {
		return (Boolean) getStateHelper().eval(PropertyKeys.legendOff,
				Boolean.FALSE);
	}

	public MethodExpression getLiveListener() {
		return (MethodExpression) getStateHelper().eval(
				PropertyKeys.liveListener, null);
	}

	public MethodExpression getProgressiveRenderListener() {
		return (MethodExpression) getStateHelper().eval(
				PropertyKeys.progressiveRenderListener, null);
	}

	public int getProgress() {
		return (Integer) getStateHelper().eval(PropertyKeys.progress, 100);
	}

	public int getMargin() {
		return (Integer) getStateHelper().eval(PropertyKeys.margin,
				getChartSettings().getMargin());
	}

	public String getOrientation() {
		return (String) getStateHelper().eval(PropertyKeys.orientation,
						EnumOrientation.vertical.toString());
	}

	public String getOutput() {
		return (String) getStateHelper().eval(PropertyKeys.output,
				EnumOutputType.svg.toString());
	}

	public MethodExpression getPanListener() {
		return (MethodExpression) getStateHelper().eval(
				PropertyKeys.panListener, null);
	}

	public int getRefreshInterval() {
		return (Integer) getStateHelper().eval(PropertyKeys.refreshInterval,
				getChartSettings().getRefreshInterval());
	}

	public MethodExpression getResetListener() {
		return (MethodExpression) getStateHelper().eval(
				PropertyKeys.resetListener, null);
	}

	public int getRightPadding() {
		return (Integer) getStateHelper().eval(PropertyKeys.rightPadding,
				getChartSettings().getPadding());
	}

	public Object getSettings() {
		return getStateHelper().eval(PropertyKeys.settings, null);
	}

	public int getTopPadding() {
		return (Integer) getStateHelper().eval(PropertyKeys.topPadding,
				getChartSettings().getPadding());
	}

	public MethodExpression getZoomListener() {
		return (MethodExpression) getStateHelper().eval(
				PropertyKeys.zoomListener, null);
	}

	public MethodExpression getWheelListener() {
		return (MethodExpression) getStateHelper().eval(
				PropertyKeys.wheelListener, null);
	}

	public boolean isLiveDataRequest(final FacesContext context) {
		return context.getExternalContext().getRequestParameterMap()
				.containsKey(this.getClientId(context) + "_dataPoll");
	}

	public void setBorderStyle(final String style) {
		getStateHelper().put(PropertyKeys.borderStyle, style);
	}

	public void setBottomPadding(final int padding) {
		getStateHelper().put(PropertyKeys.bottomPadding, padding);
	}

	/**
	 * We need to save this state so we can figure the coordinates of mouse
	 * events in the user's coordinates system.
	 *
	 * @param box
	 */
	public void setChartingAreaBounds(final Box box) {
		getStateHelper().put(PropertyKeys.bounds, box);
	}

	public void setCancel(boolean cancel) {
		getStateHelper().put(PropertyKeys.cancel, cancel);
	}

	public void setStart(boolean start) {
		getStateHelper().put(PropertyKeys.start, start);
	}

	public void setFooter(final String footer) {
		getStateHelper().put(PropertyKeys.footer, footer);
	}

	public void setFooterStyle(final String style) {
		getStateHelper().put(PropertyKeys.footerStyle, style);
	}

	public void setHeader(final String header) {
		getStateHelper().put(PropertyKeys.header, header);
	}

	public void setHeaderStyle(final String style) {
		getStateHelper().put(PropertyKeys.headerStyle, style);
	}

	public void setLeftPadding(final int padding) {
		getStateHelper().put(PropertyKeys.leftPadding, padding);
	}

	public void setLegendOff(final Boolean legendoff) {
		getStateHelper().put(PropertyKeys.legendOff, legendoff);
	}

	public void setLiveListener(final MethodExpression liveListener) {
		getStateHelper().put(PropertyKeys.liveListener, liveListener);
	}

	public void setProgressiveRenderListener(final MethodExpression liveListener) {
		getStateHelper().put(PropertyKeys.progressiveRenderListener, liveListener);
	}

	public void setProgress(int progress) {
		getStateHelper().put(PropertyKeys.progress, progress);
	}

	public void setMargin(final String margin) {
		getStateHelper().put(PropertyKeys.margin, margin);
	}

	public void setOrientation(final java.lang.String orientation) {
		getStateHelper().put(PropertyKeys.orientation, orientation);
	}

	public void setOutput(final java.lang.String output) {
		getStateHelper().put(PropertyKeys.output, output);
	}

	public void setPanListener(final MethodExpression panListener) {
		getStateHelper().put(PropertyKeys.panListener, panListener);
	}

	public void setRefreshInterval(final int refreshInterval) {
		getStateHelper().put(PropertyKeys.refreshInterval, refreshInterval);
	}

	public void setResetListener(final MethodExpression resetListener) {
		getStateHelper().put(PropertyKeys.resetListener, resetListener);
	}

	public void setRightPadding(final int padding) {
		getStateHelper().put(PropertyKeys.rightPadding, padding);
	}

	public void setSettings(final Object settings) {
		getStateHelper().put(PropertyKeys.settings, settings);
	}

	public void setTopPadding(final int padding) {
		getStateHelper().put(PropertyKeys.topPadding, padding);
	}

	public void setZoomListener(final MethodExpression zoomListener) {
		getStateHelper().put(PropertyKeys.zoomListener, zoomListener);
	}

	public void setWheelListener(final MethodExpression wheelListener) {
		getStateHelper().put(PropertyKeys.wheelListener, wheelListener);
	}

	@Override
	protected String getHeightDefault() {
		return getChartSettings().getChartHeight();
	}

	@Override
	protected String getWidthDefault() {
		return getChartSettings().getChartWidth();
	}
}