package org.javaq.chartfaces.render.base;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.javaq.chartfaces.ChartFacesManager;
import org.javaq.chartfaces.component.impl.UIChart;
import org.javaq.chartfaces.dataspec.IRangeCalculator;


/**
 * Abstract super class for all charts in Cartesian coordinates.
 * 
 * @author Hongyan Li
 * 
 */
public abstract class CartesianChartRenderer extends ChartRenderer {

	@Override
	protected void processDataRange(final FacesContext context, final UIChart chart)
			throws IOException {
		final ChartFacesManager manager = getChartFacesManager();
		final IRangeCalculator<UIComponent> rangeCalculator = manager.getCartesianRangeCalculator();
		rangeCalculator.calculateRanges(chart);
	}
}
