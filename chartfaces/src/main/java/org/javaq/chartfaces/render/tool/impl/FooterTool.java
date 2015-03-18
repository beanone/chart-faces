package org.javaq.chartfaces.render.tool.impl;

import java.util.Map;

import org.javaq.chartfaces.api.IChartPart;
import org.javaq.chartfaces.constants.EnumPart;
import org.javaq.chartfaces.render.tool.IChartToolFactory;


public class FooterTool extends HeaderTool {
	FooterTool(final IChartToolFactory factory, final IChartPart part,
			final Map<Object, Object> state) {
		super(factory, part, state);
	}

	@Override
	protected ChartDocumentType getDocumentType() {
		return ChartDocumentType.footer;
	}

	@Override
	protected boolean isValid(final IChartPart part) {
		return part.getPartType() == EnumPart.footer;
	}
}