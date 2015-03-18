package org.javaq.chartfaces.render.tool.impl;

import java.util.Map;

import org.javaq.chartfaces.api.IChartPart;
import org.javaq.chartfaces.iterable.ISizeAwareIterable;
import org.javaq.chartfaces.render.tool.IChartToolFactory;


public class MockBarComponent extends BarComponentTool {

	MockBarComponent(final IChartToolFactory factory, final IChartPart part,
			Map<Object, Object> state) {
		super(factory, part, state);
	}

	@Override
	public void setValuesArray(final ISizeAwareIterable<Double> valuesArray) {
		super.setValuesArray(valuesArray);
	}

	@Override
	protected boolean isValid(final IChartPart part) {
		return super.isValid(part);
	}

	@Override
	protected void prepareData() {
		super.prepareData();
	}

}