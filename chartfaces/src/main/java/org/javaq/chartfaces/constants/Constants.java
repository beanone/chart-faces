package org.javaq.chartfaces.constants;

public interface Constants {
	String COMPONENT_FAMILY_CHART = "org.javaq.chartfaces.chart";
	String COMPONENT_FAMILY_PART = "org.javaq.chartfaces.chart.part";

	String COMPONENT_TYPE_AXIS = "org.javaq.chartfaces.chart.part.axis";
	String COMPONENT_TYPE_AXIS_ANGULAR = "org.javaq.chartfaces.chart.part.axis.angular";
	String COMPONENT_TYPE_AXIS_RADIAL = "org.javaq.chartfaces.chart.part.axis.radial";
	String COMPONENT_TYPE_AXIS_X = "org.javaq.chartfaces.chart.part.axis.x";
	String COMPONENT_TYPE_AXIS_Y = "org.javaq.chartfaces.chart.part.axis.y";
	String COMPONENT_TYPE_CHART = "org.javaq.chartfaces.chart";
	String COMPONENT_TYPE_ELEMENT = "org.javaq.chartfaces.chart.part.component";
	String COMPONENT_TYPE_ELEMENTSET = "org.javaq.chartfaces.chart.part.componentset";
	String COMPONENT_TYPE_LEGEND = "org.javaq.chartfaces.chart.part.legend";
	String COMPONENT_TYPE_SCATTER = "org.javaq.chartfaces.chart.part.scatterset";
	String COMPONENT_TYPE_SCATTER3V = "org.javaq.chartfaces.chart.part.scatter3vset";
	String COMPONENT_TYPE_HEADER = "org.javaq.chartfaces.chart.part.header";
	String COMPONENT_TYPE_FOOTER = "org.javaq.chartfaces.chart.part.footer";

	String HEIGHT = "height";
	String ORIENTATION_HORIZONTAL = "horizontal";
	String ORIENTATION_VERTICAL = "vertical";

	String RENDER_SCRIPT_TEXT = "Render script attribute";

	String RENDERER_BAR = "org.javaq.chartfaces.renderer.chart.bar";
	String RENDERER_PIE = "org.javaq.chartfaces.renderer.chart.pie";

	String RENDERER_SCATTER = "org.javaq.chartfaces.renderer.chart.scatter";

	// TODO: should make a different renderkit
	String RENDERKIT_ID = "HTML_BASIC";

	String WIDTH = "width";
	Integer ZEOR = Integer.valueOf(0);
}