package org.javaq.chartfaces.component.impl;

import javax.faces.component.FacesComponent;

import org.javaq.chartfaces.constants.Constants;
import org.javaq.chartfaces.constants.EnumPart;
import org.javaq.chartfaces.constants.EnumPosition;


/**
 * A {@link Footer} defines a footer for a chart.
 * 
 * @author Hongyan Li
 * @since 1.0
 */
@FacesComponent(value = Constants.COMPONENT_TYPE_FOOTER)
public class Footer extends UIChartBase {
	protected enum PropertyKeys {
		textanchor;
	}

	public Footer() {
		setPartType(EnumPart.footer);
	}

	@Override
	public EnumPosition getEnumPosition() {
		return EnumPosition.footer;
	}

	@Override
	public String getRendererType() {
		return null;
	}

	public String getTextanchor() {
		return (String) getStateHelper().eval(
				PropertyKeys.textanchor,
				getChartSettings().getFooterTextAnchorDefault());
	}

	public void setTextanchor(final String anchor) {
		getStateHelper().put(PropertyKeys.textanchor, anchor);
	}

	@Override
	protected Object getHeightDefault() {
		return ParserUtil.getFontSize(this) + 2 * getPadding();
	}

	@Override
	protected String getStyleDefault() {
		return getChartSettings().getFooterStyle();
	}

	@Override
	protected Object getWidthDefault() {
		return null;
	}
}