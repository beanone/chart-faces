package org.javaq.chartfaces.render.tool.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.javaq.chartfaces.api.IChartPart;
import org.javaq.chartfaces.constants.EnumPart;
import org.javaq.chartfaces.document.IDataElement;
import org.javaq.chartfaces.document.IElement;
import org.javaq.chartfaces.render.tool.IChartToolFactory;


public class AngularAxisTool extends AbstractChartTool {
	private boolean boxed;

	AngularAxisTool(final IChartToolFactory factory, final IChartPart part,
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
	protected IElement createLayoutElement() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void doProcess() throws IOException {
		if (this.boxed) {
			throw new IllegalStateException(
					"Only one axis can be boxed in one axis box!");
		}

		super.doProcess();

		this.boxed = true;
	}

	@Override
	protected ChartDocumentType getDocumentType() {
		return ChartDocumentType.agular;
	}

	@Override
	protected boolean isValid(final IChartPart part) {
		return part.getPartType()==EnumPart.angular;
	}

	@Override
	protected void prepareData() {
		// TODO Auto-generated method stub

	}
}
