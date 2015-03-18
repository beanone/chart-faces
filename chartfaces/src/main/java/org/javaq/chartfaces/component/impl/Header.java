package org.javaq.chartfaces.component.impl;

import javax.faces.component.FacesComponent;

import org.javaq.chartfaces.constants.Constants;
import org.javaq.chartfaces.constants.EnumPart;
import org.javaq.chartfaces.constants.EnumPosition;


/**
 * A {@link Header} defines a header for a chart.
 * 
 * @author Hongyan Li
 * @since 1.0
 */
@FacesComponent(value = Constants.COMPONENT_TYPE_HEADER)
public class Header extends AbstractChartPart {
	protected enum PropertyKeys {
		textanchor;
	}

	public Header() {
		setPartType(EnumPart.header);
	}

	@Override
	public EnumPosition getEnumPosition() {
		return EnumPosition.header;
	}

	@Override
	public String getRendererType() {
		return null;
	}

	public String getTextanchor() {
		return (String) getStateHelper().eval(
				PropertyKeys.textanchor,
				getChartSettings().getHeaderTextAnchorDefault());
	}

	public void setTextanchor(final String anchor) {
		getStateHelper().put(PropertyKeys.textanchor, anchor);
	}

	@Override
	protected Object getHeightDefault() {
		return ParserUtil.getFontSize(this) + 2 * getPadding();
	}

	@Override
	protected EnumPosition getPositionDefault() {
		return EnumPosition.header;
	}

	@Override
	protected String getStyleDefault() {
		return getChartSettings().getHeaderStyle();
	}

	@Override
	protected Object getWidthDefault() {
		return null;
	}
}