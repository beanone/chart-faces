package org.javaq.chartfaces.render.tool.impl;

import java.io.IOException;
import java.util.Map;

import javax.faces.component.UIComponent;

import org.javaq.chartfaces.api.IChartPart;
import org.javaq.chartfaces.constants.Constants;
import org.javaq.chartfaces.constants.EnumPosition;
import org.javaq.chartfaces.render.tool.IChartTool;
import org.javaq.chartfaces.render.tool.IChartToolFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component("chartToolFactory")
@Scope("singleton")
public class ChartToolFactory implements IChartToolFactory {

	@Override
	public IChartTool getTool(final IChartPart part,
			final Map<Object, Object> state) throws IOException {
		final UIComponent component = (UIComponent) part;
		IChartTool tool = null;

		final EnumPosition position = part.getEnumPosition();
		switch (part.getPartType()) {
		case chart:
			tool = new MainChartTool(this, part, state);
			break;
		case xaxis:
			// default is bottom
			if (position != null && position == EnumPosition.top) {
				tool = new TopAxisTool(this, part, state);
			} else {
				tool = new BottomAxisTool(this, part, state);
			}
			break;
		case yaxis:
			// default is left
			if (position != null && position == EnumPosition.right) {
				tool = new RightAxisTool(this, part, state);
			} else {
				tool = new LeftAxisTool(this, part, state);
			}
			break;
		case angular:
			tool = new AngularAxisTool(this, part, state);
			break;
		case radial:
			tool = new RadialAxisTool(this, part, state);
			break;
		case legend:
			tool = new LegendTool(this, part, state);
			break;
		case scatter3v:
			tool = new Scatter3VComponentTool(this, part, state);
			break;
		case header:
			tool = new HeaderTool(this, part, state);
			break;
		case footer:
			tool = new FooterTool(this, part, state);
			break;
		case component:
			final UIComponent chart = component.getParent();
			if (chart.getRendererType().equals(Constants.RENDERER_BAR)) {
				tool = new BarComponentTool(this, part, state);
			} else if (chart.getRendererType().equals(
					Constants.RENDERER_PIE)) {
				tool = new PieComponentTool(this, part, state);
			} else if (chart.getRendererType().equals(
					Constants.RENDERER_SCATTER)) {
				tool = new ScatterComponentTool(this, part, state);
			}
			break;
		default:
			break;
		}

		return tool;
	}
}
