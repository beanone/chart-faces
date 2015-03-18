package org.javaq.chartfaces.dataspec;

import java.util.List;
import java.util.StringTokenizer;

import javax.faces.component.UIComponent;

import org.javaq.chartfaces.api.IChartAxis;
import org.javaq.chartfaces.api.IChartComponent;
import org.javaq.chartfaces.api.IChartPart;
import org.javaq.chartfaces.constants.EnumPart;


/**
 * A utility class that helps to lookup for related {@link IChartPart}
 * 
 * @author Hongyan Li
 * 
 */
public final class PartFinder {
	
	private PartFinder() {}

	/**
	 * Finds an {@link IChartPart} by its Id.
	 * 
	 * @param chart
	 *            the UIChart that is the parent of the
	 *            {@link IChartPart}.
	 * @param id
	 *            the id of the {@link IChartPart} to be found.
	 * @return the matching {@link IChartPart} or null if not found.
	 */
	public static IChartPart findPartById(final IChartPart chart, final String id) {
		IChartPart part = null;
		final List<UIComponent> children = chart.getChildren();
		for (final UIComponent child : children) {
			if (id.equals(child.getId())) {
				if (child instanceof IChartPart) {
					part = (IChartPart) child;
				}
				break;
			}
		}
		return part;
	}

	public static IChartAxis findXAxis(final IChartComponent component) {
		return (IChartAxis) PartFinder.getAxis(component, EnumPart.xaxis);
	}

	public static IChartAxis findYAxis(final IChartComponent component) {
		return (IChartAxis) PartFinder.getAxis(component, EnumPart.yaxis);
	}

	/**
	 * Gets the UIChart component that the passed in {@link IChartPart}
	 * belongs to.
	 * 
	 * @param part
	 *            the target {@link IChartPart}
	 * @return the UIChart parent component of the passed in.
	 */
	public static IChartPart getChart(final IChartPart part) {
		IChartPart returnPart = part;
		EnumPart type = part.getPartType();
		while (!(type==EnumPart.chart) ) {
			returnPart = returnPart.getParentPart();
			type = ((IChartPart)returnPart).getPartType();
		}
		return returnPart;
	}

	public static IChartPart getFirstByType(final IChartPart chart,
			final EnumPart type) {
		IChartPart part = null;
		final List<UIComponent> children = chart.getChildren();
		for (final UIComponent child : children) {
			if (child instanceof IChartPart) {
				part = (IChartPart) child;
				if (part.getPartType() == type) {
					break;
				}
				part = null;
			}
		}
		return part;
	}

	private static IChartPart getAxis(final IChartComponent component,
			final EnumPart type) {
		final IChartPart chart = PartFinder.getChart(component);
		IChartPart part = null;
		final String idString = component.getAxesIds();
		if (idString != null) {
			final String[] idArray = PartFinder.parseAxesIds(idString);
			for (final String id : idArray) {
				part = PartFinder.findPartById(chart, id);
				if (part != null && part.getPartType() == type) {
					break;
				}
				part = null;
			}
		}

		if (part == null) {
			// handle default
			part = PartFinder.getFirstByType(chart, type);
		}
		return part;
	}

	private static String[] parseAxesIds(final String ids) {
		final StringTokenizer st = new StringTokenizer(ids, ",");
		final String[] idArray = new String[st.countTokens()];
		int i = 0;
		while (st.hasMoreTokens()) {
			idArray[i++] = st.nextToken();
		}
		return idArray;
	}

}
