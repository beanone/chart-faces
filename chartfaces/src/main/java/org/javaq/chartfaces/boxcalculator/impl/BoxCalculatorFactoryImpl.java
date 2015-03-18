package org.javaq.chartfaces.boxcalculator.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.javaq.chartfaces.boxcalculator.BoxCalculator;
import org.javaq.chartfaces.boxcalculator.BoxCalculatorFactory;
import org.javaq.chartfaces.constants.EnumPosition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("boxCalculatorFactory")
public class BoxCalculatorFactoryImpl implements BoxCalculatorFactory {
	private final Map<Object, BoxCalculator> boxCalculators = new HashMap<Object, BoxCalculator>();

	@Autowired
	public void setBoxCalculators(List<BoxCalculator> calculators) {
		if (calculators!=null) {
			for(BoxCalculator calculator : calculators) {
				for(Object id : calculator.getIds()) {
					boxCalculators.put(id, calculator);
				}
			}
		}
	}

	@Override
	public BoxCalculator getCalculator(EnumPosition position) {
		return boxCalculators.get(position);
	}

}
