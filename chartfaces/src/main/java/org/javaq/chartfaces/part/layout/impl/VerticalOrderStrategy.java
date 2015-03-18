package org.javaq.chartfaces.part.layout.impl;

import java.util.Comparator;

import org.javaq.chartfaces.api.IChartPart;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


/**
 * The strategy that orders the parts that participate in the vertical layout of
 * the chart.
 * 
 * @author Hongyan Li
 * 
 */
@Component("verticalOrderStrategy")
@Scope("singleton")
public class VerticalOrderStrategy extends AbstractOrderStrategy {
	@Override
	public boolean accept(final IChartPart part) {
		boolean accept = false;
		switch (part.getPartType()) {
		case xaxis:
		case legend:
		case header:
		case footer:
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
		return part.getEnumPosition().getVerticalOrder();
	}

	@Override
	public Comparator<IChartPart> getPartComparator() {
		return new AbstractPartsComparator() {
			private static final long serialVersionUID = -6658003103260559048L;

			@Override
			public Integer getPositionOrder(final IChartPart part) {
				return VerticalOrderStrategy.getPositionOrder(part);
			}
		};
	}
}
