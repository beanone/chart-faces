package org.javaq.chartfaces.render;

import javax.faces.render.FacesRenderer;

import org.javaq.chartfaces.constants.Constants;
import org.javaq.chartfaces.render.base.CartesianChartRenderer;


@FacesRenderer(rendererType = Constants.RENDERER_SCATTER,
		renderKitId = Constants.RENDERKIT_ID,
		componentFamily = Constants.COMPONENT_FAMILY_CHART)
public class ScatterChartRenderer extends CartesianChartRenderer {
}
