package org.javaq.chartfaces.render.tool.impl;

import java.util.List;
import java.util.Map;

import org.javaq.chartfaces.api.IChartComponent;
import org.javaq.chartfaces.api.IChartLegend;
import org.javaq.chartfaces.api.IChartPart;
import org.javaq.chartfaces.api.ILegendHandler;
import org.javaq.chartfaces.component.impl.UIChart;
import org.javaq.chartfaces.document.IDataElement;
import org.javaq.chartfaces.document.IElement;
import org.javaq.chartfaces.part.component.ChartComponent;
import org.javaq.chartfaces.render.tool.IChartToolFactory;


public class LegendTool extends AbstractChartTool {
	private boolean boxed;
	private IChartComponent forComponent;

	LegendTool(final IChartToolFactory factory, final IChartPart part,
			final Map<Object, Object> state) {
		super(factory, part, state);
	}

	@Override
	public void synchToolState(final Map<Object, Object> state) {
		// TODO Auto-generated method stub
		// restore attributes if needed
	}

	@Override
	protected List<IDataElement> createDataElementList() {
		// no data element list for legends
		return null;
	}

	@Override
	protected IElement createLayoutElement() {
		IElement layoutElement = ChartToolUtil.createLayoutBox(getBoxModel(), null);

		IChartComponent forComponent = getForComponent();
		ILegendHandler legendHandler = getMainChartTool().getLegendHandler(forComponent);
		IElement legendElement = legendHandler.createElement();

		layoutElement.addChildren(legendElement);

		return layoutElement;
	}

	@Override
	protected void doProcess() {
		if (this.boxed) {
			throw new IllegalStateException(
					"Only one legend can be boxed in one legend box!");
		}
	}

	@Override
	protected ChartDocumentType getDocumentType() {
		return ChartDocumentType.lengend;
	}

	@Override
	protected boolean isValid(final IChartPart part) {
		return part instanceof IChartLegend;
	}

	@Override
	protected void prepareData() {
		// TODO Auto-generated method stub
		// scale data if needed
	}
	
	private IChartComponent getForComponent() {
		if(this.forComponent==null) {
			this.forComponent = resolveForComponent();
		}
		
		return this.forComponent;
	}

	private ChartComponent resolveForComponent() {
		IChartLegend legendComponent = (IChartLegend) getChartPart();
		String forComponentId = legendComponent.getFor();
		UIChart chart = getChart();
		return (ChartComponent) chart.findComponent(forComponentId);
	}
}
