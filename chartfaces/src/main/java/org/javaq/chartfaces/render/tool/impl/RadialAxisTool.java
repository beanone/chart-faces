package org.javaq.chartfaces.render.tool.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.javaq.chartfaces.api.IChartPart;
import org.javaq.chartfaces.document.IDataElement;
import org.javaq.chartfaces.part.axis.RadialAxis;
import org.javaq.chartfaces.render.tool.IChartToolFactory;


public class RadialAxisTool extends AbstractChartTool {
	private boolean boxed;

	RadialAxisTool(final IChartToolFactory factory, final IChartPart part,
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
		return ChartDocumentType.radial;
	}

	@Override
	protected boolean isValid(final IChartPart part) {
		return part instanceof RadialAxis;
	}

	@Override
	protected void prepareData() {
		// TODO Auto-generated method stub

	}
}
