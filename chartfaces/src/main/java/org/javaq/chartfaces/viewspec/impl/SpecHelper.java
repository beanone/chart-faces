package org.javaq.chartfaces.viewspec.impl;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.faces.component.UIComponent;

import org.javaq.chartfaces.api.Box;
import org.javaq.chartfaces.api.IChartLegend;
import org.javaq.chartfaces.api.IChartPart;
import org.javaq.chartfaces.boxcalculator.BoxCalculator;
import org.javaq.chartfaces.boxcalculator.BoxCalculatorFactory;
import org.javaq.chartfaces.component.impl.ParserUtil;
import org.javaq.chartfaces.component.impl.UIChart;
import org.javaq.chartfaces.constants.EnumPart;
import org.javaq.chartfaces.constants.EnumPosition;
import org.javaq.chartfaces.part.component.ChartComponent;
import org.javaq.chartfaces.part.layout.ILayoutManager;


/**
 * A {@link SpecHelper} keeps reference to all parts and provides convenient
 * methods to help figure out the dimension (view specification) of each part in
 * the view box.
 *
 * @author Hongyan Li
 *
 */
public final class SpecHelper {

	private final class ShadowAllButWidthViewBox extends Box {
		private final Integer originY;
		private final IChartPart part;
		private static final long serialVersionUID = -4992196419779683330L;

		private ShadowAllButWidthViewBox(int w, int h, Integer originY, IChartPart part) {
			super(w, h);
			this.originY = originY;
			this.part = part;
		}

		// Shadows all but the width
		@Override
		public int getHeight() {
			int addHeight = originY == null ? 0
					: (getLayoutManager().getOrigin(part).getY() - originY) * 2;
			return SpecHelper.this.getChartingArea().getHeight() + addHeight;
		}

		@Override
		public int getOriginX() {
			return getLayoutManager().getOrigin(part).getX();
		}

		@Override
		public int getOriginY() {
			return originY == null ? getLayoutManager().getOrigin(part)
					.getY() : originY;
		}
	}

	private final class ShadowAllButHeightViewBox extends Box {
		private final IChartPart part;
		private final Integer originX;
		private static final long serialVersionUID = 4920687989271641907L;

		private ShadowAllButHeightViewBox(int w, int h, IChartPart part, Integer originX) {
			super(w, h);
			this.part = part;
			this.originX = originX;
		}

		@Override
		public int getOriginX() {
			return originX == null ? getLayoutManager().getOrigin(part)
					.getX() : originX;
		}

		@Override
		public int getOriginY() {
			return getLayoutManager().getOrigin(part).getY();
		}

		// Shadows all but the height
		@Override
		public int getWidth() {
			int addWidth = originX == null ? 0
					: (getLayoutManager().getOrigin(part).getX() - originX) * 2;
			return SpecHelper.this.getChartingArea().getWidth() + addWidth;
		}
	}

	private final class ShadowAllViewBox extends Box {
		private static final long serialVersionUID = -3389105885456999350L;

		private ShadowAllViewBox(int w, int h) {
			super(w, h);
		}

		// Shadows all attributes
		@Override
		public int getHeight() {
			return SpecHelper.this.getChartingArea().getHeight();
		}

		@Override
		public int getOriginX() {
			return getLayoutManager().getOrigin(null).getX();
		}

		@Override
		public int getOriginY() {
			return getLayoutManager().getOrigin(null).getY();
		}

		@Override
		public int getWidth() {
			return SpecHelper.this.getChartingArea().getWidth();
		}
	}

	private static final class FlexBox {
		private int height;
		private int width;

		private FlexBox(final Box box) {
			this.width = box.getWidth();
			this.height = box.getHeight();
		}

		int getHeight() {
			return this.height;
		}

		int getWidth() {
			return this.width;
		}

		int minusHeight(final int taken) {
			this.height -= taken;
			return this.height;
		}

		int minusWidth(final int taken) {
			this.width -= taken;
			return this.width;
		}
	}


	private BoxCalculatorFactory boxCalculatorFactory;
	private transient final UIChart chart;
	private transient FlexBox chartingArea;
	private transient final ILayoutManager layoutManager;
	private transient final Map<String, IChartPart> partsMap = new LinkedHashMap<String, IChartPart>();

	private SpecHelper(final UIChart chart, final ILayoutManager layoutManager) {
		if (chart == null) {
			throw new IllegalArgumentException("The passed in chart object is null.");
		}
		this.chart = chart;
		this.layoutManager = layoutManager;

		this.layoutManager.register(this.chart);
		IChartPart part;
		// we want the chart at the top.
		this.partsMap.put(this.chart.getPartKey(), this.chart);
		for (final UIComponent comp : this.chart.getChildren()) {
			if (comp instanceof IChartPart) {
				part = (IChartPart) comp;
				this.partsMap.put(part.getPartKey(), part);
				if (EnumPosition.absolute != part.getEnumPosition()
						&& EnumPart.component != part.getPartType()
								.getBaseType()) {
					this.layoutManager.register(part);
				}
			}
		}
	}

	/**
	 * Create a new instance of this.
	 *
	 * @param chart
	 */
	public static final SpecHelper newInstance(final UIChart chart, final ILayoutManager layoutManager) {
		SpecHelper returns = new SpecHelper(chart, layoutManager);
		returns.layoutManager.register(returns.createPlaceHolderComponent());
		return returns;
	}

	/**
	 * Encapsulates an algorithm that helps create the view {@link Box} for a
	 * part that is horizontally oriented, e.g., a x-axis, header, footer, or a
	 * legend.
	 *
	 * @param part
	 *            the part whose view box is worked on.
	 * @param originX
	 *            the preferred x coordinate of the origin of the box.
	 * @return a {@link Box} that contains size information for the legend in
	 *         view-port coordinate.
	 */
	public Box computeHorizontalViewBox(final IChartPart part, Integer originX)
			throws IOException {
		if (part.getEnumPosition().isLeftRight()) {
			throw new IOException("Invalid position. Vertical ones expected!");
		}
		final int height = computeHorizontalBoxHeight(part);
		return shadowAllButHeight(part, height, originX);
	}

	/**
	 * Encapsulates an algorithm that helps create the view {@link Box} for a
	 * legend.
	 *
	 * @param legend
	 * @return a {@link Box} that contains size information for the legend in
	 *         view-port coordinate.
	 */
	public Box computeLegendViewBox(final IChartLegend legend)
			throws IOException {
		if (legend.getEnumPosition().isLeftRight()) {
			// if left-right positioned, only width can be specified
			return computeVerticalViewBox(legend, null);
		} else if (legend.getEnumPosition().isTopBottom()) {
			// if top-bottom positioned, only height can be specified
			return computeHorizontalViewBox(legend, null);
		}

		BoxCalculator calculator = boxCalculatorFactory.getCalculator(legend.getEnumPosition());
		Box box = calculator.calculate(getChart().getViewBox(), legend);
		return box;
	}

	/**
	 * Encapsulates an algorithm that helps create the view {@link Box} for a
	 * part that is vertically oriented, e.g., a y-axis, or a legend.
	 *
	 * @param part
	 * @param originY
	 *            the preferred y coordinate of the origin of the box.
	 * @return a {@link Box} that contains size information for the y-axis.
	 */
	public Box computeVerticalViewBox(final IChartPart part, Integer originY)
			throws IOException {
		if (part.getEnumPosition().isTopBottom()) {
			throw new IOException("Invalid position. Horizontal ones expected!");
		}
		final int width = computeVerticalBoxWidth(part);
		return shadowAllButWidth(part, width, originY);
	}

	public Box getChartingAreaViewBox() {
		// both the height and the width are flex'd with the charting area.
		return new ShadowAllViewBox(1, 1);
	}

	public ILayoutManager getLayoutManager() {
		return this.layoutManager;
	}

	public IChartPart getPart(final String key) {
		return this.partsMap.get(key);
	}

	public Collection<IChartPart> getParts() {
		return this.partsMap.values();
	}

	public void setBoxCalculatorFactory(BoxCalculatorFactory factory) {
		this.boxCalculatorFactory = factory;
	}

	private int computeHorizontalBoxHeight(final IChartPart part) {
		// int labelExists = axis.getValue() == null ? 0 : 1;
		final int height = ParserUtil.getInt(part.getHeight(), -1);
		// TODO: Research on SVGOMTextElement and SVGOMTextPositioningElement in
		// batik on how to calculate text size. For now just use the default
		// height which is set in the DefaultChartSetting!!!
		if (height <= 0) {
			throw new IllegalArgumentException(
					"Height of " + part.getPartType() + " must be given!");
		}
		// height = ParserUtil.getAxisLabelFontSize(axis) * labelExists
		// + getCaptionFontSize(axis) + axis.getTickHeight()
		// + getChart().getPadding() * 3 + axis.getTopPadding()
		// + axis.getBottomPadding();
		return height;
	}

	/**
	 * Compute the width of a the y-axis. If the width of the box is specified,
	 * then use it. If it is not, then calculate it from the font size.
	 *
	 * @param part
	 * @return
	 */
	private int computeVerticalBoxWidth(final IChartPart part) {
		// final Iterable<?> values =
		// IterableUtility.toIterable(axis.getValue());
		final int width = ParserUtil.getInt(part.getWidth(), -1);
		// TODO: Research on SVGOMTextElement and SVGOMTextPositioningElement in
		// batik on how to calculate text size. For now just use the default
		// width which is set in the DefaultChartSetting!!!
		if (width <= 0) {
			throw new IllegalArgumentException(
					"Width of " + part.getPartType() + " must be given!");
		}
		// if (width == -1) {
		// width = getMaxStringLength(values)
		// * ParserUtil.getAxisLabelFontSize(axis)
		// + getCaptionFontSize(axis) + axis.getTickHeight()
		// + getChart().getPadding() * 3 + axis.getLeftPadding()
		// + axis.getRightPadding();
		// }
		return width;
	}

	// This component is a place holder to reserve the charting area so that the
	// chart still has a charting area even when no charting component has been
	// specified by the user
	private IChartPart createPlaceHolderComponent() {
		final ChartComponent comp = new ChartComponent();
		comp.getPartSpec().setViewBox(getChartingAreaViewBox());
		return comp;
	}

	/**
	 * @return the chart
	 */
	private UIChart getChart() {
		return this.chart;
	}

	private FlexBox getChartingArea() {
		if (this.chartingArea == null) {
			// Should only be called after the ChartSpecHandler has been called
			// to handle the chart specs.
			this.chartingArea =
					new FlexBox(getChart().getPartSpec().getViewBox());
			this.chartingArea.minusWidth(getChart().getLeftPadding());
			this.chartingArea.minusWidth(getChart().getRightPadding());

			// TODO: Find how to configure the various paddings
			this.chartingArea.minusHeight(getChart().getTopPadding());
			this.chartingArea.minusHeight(getChart().getBottomPadding());
			if (getChart().getHeader() != null) {
				this.chartingArea.minusHeight(
						ParserUtil.getHeaderFontSize(getChart()));
				// TODO: use a different configurable padding???
				this.chartingArea.minusHeight(getChart().getTopPadding());
			}
			if (getChart().getFooter() != null) {
				this.chartingArea.minusHeight(
						ParserUtil.getFooterFontSize(getChart()));
				// TODO: use a different configurable padding???
				this.chartingArea.minusHeight(getChart().getBottomPadding());
			}
		}
		return this.chartingArea;
	}

	/**
	 * Create a ViewBox that shadows all but the height attribute which is
	 * predetermined from user input or a default value for the relevant part.
	 * The shadowed attributes are determined later by further calculations.
	 *
	 * @param part
	 * @param height
	 * @param originX
	 *            the preferred x coordinate of the origin of the box.
	 * @return
	 * @throws IOException
	 */
	private Box shadowAllButHeight(final IChartPart part, final int height,
			final Integer originX) throws IOException {
		if (this.getChartingArea().getHeight() > height) {
			this.getChartingArea().minusHeight(height);
		} else {
			throw new IOException(
					"Not enough vertical charting space for the specified dimensions!");
		}
		// it is assumed that the height is one that include the paddings
		return new ShadowAllButHeightViewBox(1, height, part, originX);
	}

	/**
	 * Create a ViewBox that shadows all but the width attribute which is
	 * predetermined from user input or a default value for the relevant part.
	 * The shadowed attributes are determined later by further calculations.
	 *
	 * @param part
	 * @param originY
	 *            the preferred y coordinate of the origin of the box.
	 * @param height
	 * @return
	 * @throws IOException
	 */
	private Box shadowAllButWidth(final IChartPart part, final int width,
			final Integer originY) throws IOException {
		if (this.getChartingArea().getWidth() > width) {
			this.getChartingArea().minusWidth(width);
		} else {
			throw new IOException(
					"Not enough vertical charting space for the specified dimensions!");
		}
		// it is assumed that the width is one that include the paddings
		return new ShadowAllButWidthViewBox(width, 1, originY, part);
	}
}