package org.javaq.chartfaces.render.tool;

import java.io.IOException;

import org.javaq.chartfaces.api.IChartPart;
import org.javaq.chartfaces.document.IChartDocument;
import org.javaq.chartfaces.part.layout.IPartCoordinator;


/**
 * Abstraction of a tool that helps making an {@link IChartDocument} from the
 * parts. A collection of {@link IChartTool} instances work hand-in-hand with a
 * collection of org.javaq.chartfaces.viewspec.IPartSpecCalculator instances. The
 * IPartSpecCalculator interface figures out the dimensions of each
 * {@link IChartPart}, an ILayoutManager lays the parts out (calculates
 * the origin points) and a collection of {@link IChartTool} objects turns the
 * parts into {@link IChartDocument}, and finally a renderer turns the document
 * into XML mark-up and JSON data.
 * 
 * @author Hongyan Li
 * 
 */
public interface IChartTool {
	double NEGATIVE_ANGLE_LABEL_X_SHIFT = 2.4;
	double POSITIVE_ANGLE_LABEL_X_SHIFT = 1.2;
	double LABEL_Y_SHIFT = 1.8;

	/**
	 * Makes a document (an {@link IChartDocument}) for the contents processed
	 * using {@link #process()}.
	 * 
	 * @return an {@link IChartDocument}.
	 */
	IChartDocument getChartDocument();

	/**
	 * Processes the contained UIComponent. The main job of this method
	 * are two fold:
	 * <p/>
	 * This method messages the UIComponent within so that a document
	 * object can be created to represent the view of the chart. The final
	 * result of such document should correspond to a valid XML document that
	 * can be translated to a page UI mark-up template, most probably in
	 * SVG.</li>
	 * 
	 * @throws IOException
	 *             if failed to instantiate a child IChartTool.
	 */
	void process() throws IOException;

	/**
	 * Sets an {@link IPartCoordinator} into this.
	 * 
	 * @param coordinator
	 */
	void setCoordinator(IPartCoordinator<IChartPart> coordinator);

	/**
	 * Sets the parent of this. By setting the parent to the children, we make
	 * it possible that the parent doesn't have to know about the detail of the
	 * children.
	 * 
	 * @param parent
	 */
	void setParent(IChartTool parent);
}