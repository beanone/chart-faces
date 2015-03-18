package org.javaq.chartfaces.viewspec.impl;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import org.javaq.chartfaces.api.IChartPart;
import org.javaq.chartfaces.constants.EnumPart;
import org.javaq.chartfaces.viewspec.IPartSpecCalculator;
import org.javaq.chartfaces.viewspec.IPartSpecCalculatorFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


/**
 * Creates instances of {@link IPartSpecCalculator} for each {@link IChartPart}.
 * 
 * @author Hongyan Li
 * 
 */
@Component("partSpecCalculatorFactory")
@Scope("singleton")
public class PartSpecCalculatorFactory implements
		IPartSpecCalculatorFactory<SpecHelper> {
	private static final IPartSpecCalculatorFactory<SpecHelper> INSTANCE = new PartSpecCalculatorFactory();

	public static final IPartSpecCalculatorFactory<SpecHelper> getInstance() {
		return PartSpecCalculatorFactory.INSTANCE;
	}

	private final Map<EnumPart, Class<? extends IPartSpecCalculator>> handlerMap = new HashMap<EnumPart, Class<? extends IPartSpecCalculator>>();

	public PartSpecCalculatorFactory() {
		// setup the handler map
		this.handlerMap.put(EnumPart.chart, ChartSpecCalculator.class);
		this.handlerMap.put(EnumPart.xaxis, HorizontalPartSpecCalculator.class);
		this.handlerMap
				.put(EnumPart.header, HeaderFooterSpecCalculator.class);
		this.handlerMap
				.put(EnumPart.footer, HeaderFooterSpecCalculator.class);
		this.handlerMap.put(EnumPart.yaxis, VerticalPartSpecCalculator.class);
		this.handlerMap.put(EnumPart.component, ComponentSpecCalculator.class);
		this.handlerMap.put(EnumPart.legend, LegendSpecCalculator.class);
	}

	@Override
	public IPartSpecCalculator getCalculator(final IChartPart part,
			final SpecHelper helper)
			throws IOException {

		if (helper.getPart(part.getPartKey()) == null) {
			throw new IllegalArgumentException(
					"The SpecHelper was not initialized with this IChartPart!");
		}

		IPartSpecCalculator creator = null;
		final Class<? extends IPartSpecCalculator> calculatorClass =
				this.handlerMap.get(part.getPartType().getBaseType());
		if (calculatorClass != null) {
			try {
				final Constructor<? extends IPartSpecCalculator> constructor = calculatorClass
						.getDeclaredConstructor(SpecHelper.class);
				creator = constructor.newInstance(helper);
			} catch (final Exception e) {
				// this should never happen
				throw new IOException(e);
			}
		}
		return creator;
	}
}
