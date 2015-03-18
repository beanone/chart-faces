package org.javaq.chartfaces.part.layout;

import java.io.IOException;

import org.javaq.chartfaces.api.Coordinate;
import org.javaq.chartfaces.api.IChartPart;


/**
 * An abstraction of an object that is responsible for laying out all parts of a
 * chart.
 * 
 * @author Hongyan Li
 * 
 */
public interface ILayoutManager {

	/**
	 * Fetches the origin result from the {@link #layout()} calculation.
	 * 
	 * @param part
	 *            a {@link IChartPart} registered with this.
	 * @return null if the {@link IChartPart} has never registered with this. A
	 *         non-null int array of 2 positive values if it was registered.
	 */
	Coordinate getOrigin(IChartPart part);

	/**
	 * Laying out all that has been registered.
	 * 
	 * @throws IOException
	 *             if not enough space for the requested chart parts.
	 */
	void layout() throws IOException;

	/**
	 * Registers the passed in {@link IChartPart} as one that requires exclusive
	 * ownership of a piece of view-port space.
	 * 
	 * @param part
	 */
	void register(IChartPart part);

}