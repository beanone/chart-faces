package org.javaq.chartfaces.part.layout;

import java.io.IOException;

import org.javaq.chartfaces.api.Box;



/**
 * An abstraction of a handler whose responsibility is to coordinate among the
 * IChartParts to manage part dimensions and layout specifications. The
 * {@link #register(Object)} method registers the passed in as participant
 * and the {@link #execute()} method should be called after all the participants
 * have been registered. In essence, the {@link IPartCoordinator} facilitates
 * coordination among the parts while calculating their dimensions, and holds
 * the states during the process.
 * 
 * @author Hongyan Li
 * 
 */
public interface IPartCoordinator<T> {
	/**
	 * Performs the negotiation among all the participants.
	 * 
	 * @throws IOException
	 *             if there is any error.
	 */
	void execute() throws IOException;

	/**
	 * @return the {@link Box} for the charting area of the chart.
	 */
	Box getChartingAreaViewBox();

	/**
	 * @return the layout manager.
	 */
	ILayoutManager getLayoutManager();

	/**
	 * Registers the passed in participant.
	 * 
	 * @param participant
	 */
	void register(T participant);
}
