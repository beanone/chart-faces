package org.javaq.chartfaces.document.impl;

import java.util.ArrayList;
import java.util.List;

import org.javaq.chartfaces.document.IChartDocument;
import org.javaq.chartfaces.document.IDataElement;
import org.javaq.chartfaces.document.IElement;


/**
 * An abstract implementation which applies only non-padding BoxModel.
 * 
 * @author Hongyan Li
 * 
 */
public abstract class AbstractChartDocument implements IChartDocument {
	private final List<IDataElement> dataElementList = new ArrayList<IDataElement>();

	public final void addDataElementList(
			final List<IDataElement> dataElements) {
		if (dataElements != null) {
			for (final IDataElement t : dataElements) {
				getDataElementList().add(t);
			}
		}
	}

	public final void addLayoutElements(final IElement... elements) {
		getLayoutElement().addChildren(elements);
	}

	@Override
	public List<IDataElement> getDataElementList() {
		return this.dataElementList;
	}
}
