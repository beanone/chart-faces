package org.javaq.chartfaces.viewspec.impl;

import java.io.IOException;
import java.util.Collection;

import org.javaq.chartfaces.api.Box;
import org.javaq.chartfaces.api.IChartPart;
import org.javaq.chartfaces.boxcalculator.BoxCalculatorFactory;
import org.javaq.chartfaces.component.impl.UIChart;
import org.javaq.chartfaces.part.layout.ILayoutManager;
import org.javaq.chartfaces.part.layout.IPartCoordinator;
import org.javaq.chartfaces.viewspec.IPartSpecCalculator;
import org.javaq.chartfaces.viewspec.IPartSpecCalculatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


/**
 * Pre-processes all the parts to give each a dimension specification.
 *
 * @author Hongyan Li
 *
 */
@Component("chartPartCoordinator")
@Scope("request")
public class PartCoordinator implements IPartCoordinator<IChartPart> {
	private UIChart chart;
	private Box chartingAreaViewBox;

	@Autowired
	@Qualifier(value = "layoutManager")
	private ILayoutManager layoutManager;

	@Autowired
	@Qualifier(value = "partSpecCalculatorFactory")
	private IPartSpecCalculatorFactory<?> partSpecCalculatorFactory;

	@Autowired
	@Qualifier(value = "boxCalculatorFactory")
	private BoxCalculatorFactory boxCalculatorFactory;

	@Override
	public void execute() throws IOException {
		@SuppressWarnings("unchecked")
		final IPartSpecCalculatorFactory<SpecHelper> factory =
				(IPartSpecCalculatorFactory<SpecHelper>) getPartSpecCalculatorFactory();
		// first calculates the part specs
		final SpecHelper helper = SpecHelper.newInstance(this.chart, getLayoutManager());
		helper.setBoxCalculatorFactory(boxCalculatorFactory);
		final Collection<IChartPart> parts = helper.getParts();
		for (final IChartPart part : parts) {
			final IPartSpecCalculator specCalculator =
					factory.getCalculator(part, helper);
			specCalculator.calculate(part);
		}
		// now lay the parts out according to the specs
		getLayoutManager().layout();
		this.chartingAreaViewBox = helper.getChartingAreaViewBox();
	}

	@Override
	public Box getChartingAreaViewBox() {
		return this.chartingAreaViewBox;
	}

	@Override
	public ILayoutManager getLayoutManager() {
		return this.layoutManager;
	}

	/**
	 * @return the partSpecHandlerFactory
	 */
	public IPartSpecCalculatorFactory<?> getPartSpecCalculatorFactory() {
		return this.partSpecCalculatorFactory;
	}

	@Override
	public void register(final IChartPart chart) {
		if(chart instanceof UIChart) {
			this.chart = (UIChart) chart;
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * @param layoutManager
	 *            the layoutManager to set
	 */
	public void setLayoutManager(final ILayoutManager layoutManager) {
		this.layoutManager = layoutManager;
	}

	/**
	 * @param partSpecCalculatorFactory
	 *            the partSpecHandlerFactory to set
	 */
	public void setPartSpecCalculatorFactory(
			final IPartSpecCalculatorFactory<?> partSpecCalculatorFactory) {
		this.partSpecCalculatorFactory = partSpecCalculatorFactory;
	}

	public void setBoxCalculatorFactory(BoxCalculatorFactory factory) {
		this.boxCalculatorFactory = factory;
	}
}