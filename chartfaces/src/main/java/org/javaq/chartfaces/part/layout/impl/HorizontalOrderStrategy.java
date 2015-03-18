package org.javaq.chartfaces.part.layout.impl;

import java.util.Comparator;

import org.javaq.chartfaces.api.IChartPart;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


/**
 * The strategy that orders the parts that participate in the horizontal layout
 * of the chart.
 * 
 * @author Hongyan Li
 * 
 */
@Component("horizontalOrderStrategy")
@Scope("singleton")
public class HorizontalOrderStrategy extends AbstractOrderStrategy {
	@Override
	protected boolean accept(final IChartPart part) {
		boolean accept = false;
		switch (part.getPartType()) {
		case yaxis:
		case legend:
		case component:
			if (getPositionOrder(part) != null) {
				accept = true;
			}
			break;
		default:
			// do nothing
			break;
		}

		return accept;
	}

	private static Integer getPositionOrder(final IChartPart part) {
		return part.getEnumPosition().getHorizontalOrder();
	}

	@Override
	public Comparator<IChartPart> getPartComparator() {
		return new AbstractPartsComparator() {
			private static final long serialVersionUID = -1092939532322200598L;

			@Override
			protected Integer getPositionOrder(final IChartPart part) {
				return HorizontalOrderStrategy.getPositionOrder(part);
			}
		};
	}
}
