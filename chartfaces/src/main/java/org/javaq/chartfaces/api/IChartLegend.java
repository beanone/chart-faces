package org.javaq.chartfaces.api;

import javax.faces.component.UIComponent;

import org.javaq.chartfaces.constants.EnumLayout;


/**
 * Abstraction of an {@link IChartPart} that is a chart legend.
 * 
 * @author Hongyan Li
 * @since 1.0
 */
public interface IChartLegend extends IChartPart {
	/**
	 * @return a string name of the layout.
	 * @see EnumLayout
	 */
	EnumLayout getLayout();
	
    /**
     * Return the Identifier of the chart part for which to render legend. 
     * If this component is within the same NamingContainer as the target
     * component, this must be the component identifier. Otherwise, it
     * must be an absolute component identifier (starting with ":"). See
     * the {@link UIComponent#findComponent} for more
     * information.
     * 
	 * @return the component ID of the chart part this legend is for.
	 */
	String getFor();
}