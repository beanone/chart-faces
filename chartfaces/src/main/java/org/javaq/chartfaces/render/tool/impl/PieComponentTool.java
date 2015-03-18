package org.javaq.chartfaces.render.tool.impl;

import java.util.List;
import java.util.Map;

import org.javaq.chartfaces.api.IChartComponent;
import org.javaq.chartfaces.api.IChartPart;
import org.javaq.chartfaces.document.IDataElement;
import org.javaq.chartfaces.document.IElement;
import org.javaq.chartfaces.render.tool.IChartToolFactory;


public class PieComponentTool extends AbstractComponentTool {

	PieComponentTool(final IChartToolFactory factory, final IChartPart part,
			final Map<Object, Object> state) {
		super(factory, part, state);
	}

	@Override
	public void synchToolState(final Map<Object, Object> state) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected List<IDataElement> createDataElementList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void fillLayoutElement(final IElement element) {
		// do nothing
	}

	@Override
	protected boolean isValid(final IChartPart part) {
		return part instanceof IChartComponent;
	}

	@Override
	protected void prepareData() {
		// TODO fetch the data values from the component
		// TODO apply the scales to the data values according to the part
		// dimensions.
	}
}
