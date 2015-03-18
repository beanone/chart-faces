package org.javaq.chartfaces.viewspec;

import java.io.IOException;

import org.javaq.chartfaces.api.IChartPart;
import org.javaq.chartfaces.api.IPartSpec;


/**
 * An abstraction of handlers that are responsible for calculating part
 * specifications - dimensions. Positioning of the parts in a chart is the
 * responsibility of an ILayoutManager and then the IChartTool
 * interface takes care of generating the document of the chart, and finally a
 * document renderer renders the document to XML with JSON data.
 * 
 * @author Hongyan Li
 * 
 */
public interface IPartSpecCalculator {
	/**
	 * Generates specifications for the passed in part.
	 * 
	 * @return an {@link IPartSpec} for the passed in chart part.
	 */
	IPartSpec calculate(IChartPart part)
			throws IOException;

}
