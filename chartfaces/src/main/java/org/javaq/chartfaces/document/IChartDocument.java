package org.javaq.chartfaces.document;

import java.util.List;

/**
 * Abstraction for a chart document that holds everything for a chart. A chart
 * document has two parts: a layout document which holds the elements that
 * defines the layout of a chart and a list of data elements. The layout
 * document is represented as a top level {@link IElement} that contains some
 * children elements. The list of data elements are represented as a list of
 * {@link IDataElement}. An {@link IDataElement} is an {@link IElement} that
 * represents a list of {@link IElement}s and can be easily transfered into a
 * JSAN object.
 * 
 * @author Hongyan Li
 * @see IElement
 * @see IDataElement
 * 
 */
public interface IChartDocument {
	/**
	 * @return a list of {@link IDataElement} that will be used to generate the
	 *         dynamic portion of a chart.
	 */
	List<IDataElement> getDataElementList();

	/**
	 * @return a template document that is stored as an {@link IElement}.
	 */
	IElement getLayoutElement();
}
