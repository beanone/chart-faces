package org.javaq.chartfaces.render.tool;

import java.io.IOException;
import java.util.Map;

import org.javaq.chartfaces.api.IChartPart;


/**
 * A factory interface for making {@link IChartTool} from a javax.faces.component.UIComponent.
 * This makes it possible to inject a new factory using a framework like JSF or
 * Spring.
 * 
 * @author Hongyan Li
 */
public interface IChartToolFactory {
	/**
	 * Make a {@link IChartTool} for the passed in javax.faces.component.UIComponent.
	 * 
	 * @param part an {@link IChartPart} whose {@link IChartTool} is to be fetched.
	 * @param state
	 *            a {@link Map} that holds the request scope rendering state
	 *            data. Basically this is a bucket for all the state data that
	 *            is shared across all the {@link IChartTool} instances.
	 * @return the {@link IChartTool} for the passed in {@link IChartPart}.
	 * @throws IOException
	 *             if the IChartTool can't be instantiated.
	 */
	IChartTool getTool(IChartPart part, Map<Object, Object> state)
			throws IOException;
}
