package org.javaq.chartfaces.boxcalculator.impl;

import org.javaq.chartfaces.api.Box;
import org.javaq.chartfaces.api.IChartPart;
import org.javaq.chartfaces.boxcalculator.BoxCalculator;
import org.javaq.chartfaces.component.impl.ParserUtil;
import org.javaq.chartfaces.constants.EnumPosition;
import org.springframework.stereotype.Component;

@Component
public class BottomLeftBoxCalculatorImpl implements BoxCalculator {
	@Override
	public Box calculate(Box containerBox, IChartPart part) {
		int x = part.getAnchorX();
		int y = containerBox.getHeight() - part.getAnchorY();
		final int width = ParserUtil.getInt(part.getWidth(), 0);
		final int height = ParserUtil.getInt(part.getHeight(), 0);
		return new Box(x, y, width, height);
	}

	@Override
	public Object[] getIds() {
		return new Object[] {EnumPosition.bottomLeft};
	}

}
