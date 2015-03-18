package org.javaq.chartfaces.render.tool.impl;

import org.javaq.chartfaces.constants.EnumSymbol;
import org.javaq.chartfaces.part.component.ScatterComponent;
import org.javaq.chartfaces.render.svg.SymbolTemplate;

public final class SymbolMaker {
	private static final String CIRCLE = EnumSymbol.circle.toString();
	private static final String SQUARE = EnumSymbol.square.toString();
	private static final String TRIANGLE = EnumSymbol.triangle.toString();
	
	private SymbolMaker() {}

	public static SymbolTemplate makeSymbol(final String id,
			final ScatterComponent scatter) {
		SymbolTemplate template = null;
		final String symbolName = scatter.getSymbol();
		if (symbolName != null) {
			final String fill = scatter.getFill();
			final String stroke = scatter.getStrokeColor();
			final int strokeWidth = scatter.getStrokeWidth();
			double opacity = scatter.getOpacity();
			if (opacity < 0) {
				opacity = 0;
			}
			if (opacity > 1) {
				opacity = 1;
			}
			if (SymbolMaker.CIRCLE.equals(symbolName)) {
				template = SymbolTemplate.newCircle(
						id, fill, stroke, strokeWidth, opacity);
			} else if (SymbolMaker.SQUARE.equals(symbolName)) {
				template = SymbolTemplate.newSquare(
						id, fill, stroke, strokeWidth, opacity);
			} else if (SymbolMaker.TRIANGLE.equals(symbolName)) {
				template = SymbolTemplate.newEquilateralTriangle(
						id, fill, stroke, strokeWidth, opacity);
			}
		}

		return template;
	}
}
