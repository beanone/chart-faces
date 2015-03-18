package org.javaq.chartfaces.part.layout.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.javaq.chartfaces.api.Coordinate;
import org.javaq.chartfaces.api.IChartPart;
import org.javaq.chartfaces.component.impl.UIChart;
import org.javaq.chartfaces.constants.EnumPosition;
import org.javaq.chartfaces.part.layout.ILayoutManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


/**
 * A class that is responsible for laying out all the {@link IChartPart}s.
 * During the process, it consults the {@link IChartPart} instances for
 * dimension information and thus it's important that the {@link #layout()}
 * method is called only after the dimensions of all the {@link IChartPart}s are
 * finalized.
 *
 * @author Hongyan Li
 *
 */
@Component("layoutManager")
@Scope("request")
public class LayoutManager implements ILayoutManager {
	private UIChart chart;
	private IChartPart component;

	@Autowired
	@Qualifier(value = "horizontalOrderStrategy")
	private IOrderStrategy horizontalOrderStrategy;
	private boolean layedOut = false;
	private final Map<IChartPart, Coordinate> originMap = new HashMap<IChartPart, Coordinate>();

	private final Set<IChartPart> parts = new HashSet<IChartPart>();

	@Autowired
	@Qualifier(value = "verticalOrderStrategy")
	private IOrderStrategy verticalOrderStrategy;

	/**
	 * @return the horizontalOrderStrategy
	 */
	public IOrderStrategy getHorizontalOrderStrategy() {
		return this.horizontalOrderStrategy;
	}

	@Override
	public Coordinate getOrigin(final IChartPart aPart) {
		if (!this.layedOut) {
			throw new IllegalStateException(
					"Chart parts not been layed out yet!");
		}

		IChartPart part = aPart;
		if (part == null) {
			// by default, give the charting area origin
			part = this.component;
		}
		return this.originMap.get(part);
	}

	/**
	 * @return the verticalOrderStrategy
	 */
	public IOrderStrategy getVerticalOrderStrategy() {
		return this.verticalOrderStrategy;
	}

	@Override
	public void layout() throws IOException {
		// laying out horizontally
		Iterable<IChartPart> targets = getHorizontalOrderStrategy().order(this.parts);
		new HorizontalLayoutCalculator(targets, this, getChart()).execute();

		// laying out vertically
		targets = getVerticalOrderStrategy().order(this.parts);
		new VerticalLayoutCalculator(targets, this, getChart()).execute();

		this.layedOut = true;
	}

	@Override
	public void register(final IChartPart part) {
		switch (part.getPartType().getBaseType()) {
		case chart:
			// save the chart separately since it is not to be lay out
			assert(part instanceof UIChart);
			this.chart = (UIChart) part;
			break;
		case component:
			if (this.component == null) {
				this.component = part;
				this.parts.add(part);
			}
			break;
		default:
			this.parts.add(part);
			break;
		}
	}

	/**
	 * @param horizontalOrderStrategy
	 *            the horizontalOrderStrategy to set
	 */
	public void setHorizontalOrderStrategy(
			final IOrderStrategy horizontalOrderStrategy) {
		this.horizontalOrderStrategy = horizontalOrderStrategy;
	}

	/**
	 * @param verticalOrderStrategy
	 *            the verticalOrderStrategy to set
	 */
	public void setVerticalOrderStrategy(
			final IOrderStrategy verticalOrderStrategy) {
		this.verticalOrderStrategy = verticalOrderStrategy;
	}

	private int getChartingAreaOriginX() {
		return this.component == null ? 0 : getOrigin(this.component).getX();
	}

	private int getChartingAreaOriginY() {
		return this.component == null ? 0 : getOrigin(this.component).getY();
	}

	private Coordinate newOrigin(final IChartPart part) {
		Coordinate origin = null;
		switch (part.getPartType().getBaseType()) {
		case xaxis:
		case header:
		case footer:
			origin = shadowOriginX();
			break;
		case yaxis:
			origin = shadowOriginY();
			break;
		case component:
			origin = new Coordinate();
			break;
		default:
			final EnumPosition pos = part.getEnumPosition();
			if (pos.isLeftRight()) {
				origin = shadowOriginY();
			} else if (pos.isTopBottom()) {
				origin = shadowOriginX();
			} else {
				origin = new Coordinate();
			}
			break;
		}

		return origin;
	}

	private Coordinate shadowOriginX() {
		return new Coordinate() {
			@Override
			public int getX() {
				return getChartingAreaOriginX();
			}

			@Override
			public void setX(final int x) {
				throw new UnsupportedOperationException(
						"X is shadowed. No setter!");
			}
		};
	}

	private Coordinate shadowOriginY() {
		return new Coordinate() {
			@Override
			public int getY() {
				return getChartingAreaOriginY();
			}

			@Override
			public void setY(final int y) {
				throw new UnsupportedOperationException(
						"Y is shadowed. No setter!");
			}
		};
	}

	protected UIChart getChart() {
		return this.chart;
	}

	Coordinate origin(final IChartPart part) {
		Coordinate coord = this.originMap.get(part);
		if (coord == null) {
			coord = newOrigin(part);
			this.originMap.put(part, coord);
		}

		return coord;
	}
}