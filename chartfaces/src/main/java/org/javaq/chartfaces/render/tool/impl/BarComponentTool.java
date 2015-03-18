package org.javaq.chartfaces.render.tool.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.javaq.chartfaces.api.IChartComponent;
import org.javaq.chartfaces.api.IChartPart;
import org.javaq.chartfaces.api.ILegendHandler;
import org.javaq.chartfaces.constants.Constants;
import org.javaq.chartfaces.constants.EnumPartZIndex;
import org.javaq.chartfaces.constants.SVGConstants;
import org.javaq.chartfaces.converter.AbstractIterableToIterable;
import org.javaq.chartfaces.document.IContainerElement;
import org.javaq.chartfaces.document.IDataElement;
import org.javaq.chartfaces.document.IElement;
import org.javaq.chartfaces.document.impl.DataElement;
import org.javaq.chartfaces.document.impl.Element;
import org.javaq.chartfaces.iterable.ISizeAwareIterable;
import org.javaq.chartfaces.iterable.IterableUtility;
import org.javaq.chartfaces.render.tool.IChartToolFactory;


/**
 * TODO: refactor this: so that state objects are calculated and saved in one
 * place. Would this be even possible???
 *
 * @author Hongyan Li
 *
 */
public class BarComponentTool extends AbstractComponentTool {

	private final class VerticalBarCreator extends BarCreator {
		private Iterable<Double> computeCoordsY() {
			final ISizeAwareIterable<Double> vArray = getValuesArray();
			final int yAxisLen = getViewBox().getHeight();
			return new AbstractIterableToIterable<Double, Double>() {

				@Override
				protected Double transform(final Double target) {
					return yAxisLen - target;
				}

			}.convert(vArray);
		}

		@Override
		protected ISizeAwareIterable<Double> calculateCoordinate(
				final double barInterval, final int groupIndex) {
			final ISizeAwareIterable<Double> values = getValuesArray();
			final Iterable<Integer> indices =
					IterableUtility.toIterableIndices(values);
			final double base = (groupIndex + 0.25) * getBarWidth();
			return new AbstractIterableToIterable<Integer, Double>() {
				@Override
				protected Double transform(final Integer target) {
					return base + barInterval * target;
				}
			}.convert(indices);
		}

		@Override
		void fillProperties(final IDataElement rect) {
			rect.addProperty(SVGConstants.SVG_WIDTH, ""
					+ (int) getBarWidth());
			rect.addCustomProperty(SVGConstants.SVG_HEIGHT,
					getValuesArray());
			rect.addCustomProperty(SVGConstants.SVG_Y,
					computeCoordsY());
			rect.addCustomProperty(SVGConstants.SVG_X,
					computeCoords());
			if(getChartComponent().isShowTooltips()) {
				rect.addCustomProperty(SVGConstants.SVG_TITLE,
						getDataValues());
			}
		}

		@Override
		int getAxisLength() {
			return getViewBox().getWidth();
		}

		@Override
		void scaleData(final Iterable<? extends Number> valuesIterable) {
			setValuesArray(scaleY(valuesIterable));
		}
	}

	private final class HorizontalBarCreator extends BarCreator {
		@Override
		protected ISizeAwareIterable<Double> calculateCoordinate(
				final double barInterval, final int groupIndex) {
			final ISizeAwareIterable<Double> values = getValuesArray();
			final Iterable<Integer> indices =
					IterableUtility.toIterableIndices(values);
			final double base = getAxisLength() -
					(groupIndex + 1.25) * getBarWidth();
			return new AbstractIterableToIterable<Integer, Double>() {
				@Override
				protected Double transform(final Integer target) {
					return base - barInterval * target;
				}
			}.convert(indices);
		}

		@Override
		void fillProperties(final IDataElement rect) {
			rect.addProperty(SVGConstants.SVG_X, "0");
			rect.addProperty(SVGConstants.SVG_HEIGHT, ""
					+ (int) getBarWidth());
			rect.addCustomProperty(SVGConstants.SVG_WIDTH,
					getValuesArray());
			rect.addCustomProperty(SVGConstants.SVG_Y,
					computeCoords());
			if(getChartComponent().isShowTooltips()) {
				rect.addCustomProperty(SVGConstants.SVG_TITLE,
						getDataValues());
			}
		}

		@Override
		int getAxisLength() {
			return getViewBox().getHeight();
		}

		@Override
		void scaleData(final Iterable<? extends Number> valuesIterable) {
			setValuesArray(scaleX(valuesIterable));
		}
	}

	/**
	 * This is where the real dirty business is carried out.
	 *
	 * @author Hongyan Li
	 *
	 */
	private abstract class BarCreator {
		private double barWidth;

		// We are ignoring the calculated scale along the axis and use
		// getValuesArray().length + 0.5 instead.
		private double computeBarWidth(final int axisLength, final int count) {
			// divide by 0 is no good
			int componentCount = (count == 0) ? 1 : count;
			// 1. the margin between the bars and the end of the coordinates:
			// 1/4 of bar width
			// 2. the distance between bar groups: 1/2 of bar width
			// 3. bars from different charting components are grouped together
			// with no spaces in between
			// equation: (.25*2 + m * n + .5 * (n-1)) * w = W
			// where m = number of bars in a bar group (componentCount)
			// n=number of bars in each component (valuesArray.length)
			// W=the length of the axis (axisLength)
			// w=the bar width that we are calculating
			// to ensure accuracy, all calculations should be in double
			// precision and cast to int should be applied at the very end!
			return 2. * axisLength / (2. * componentCount + 1.)
					/ getValuesArray().size();
		}

		protected abstract ISizeAwareIterable<Double> calculateCoordinate(
				double barInterval, int groupIndex);

		protected Iterable<Double> computeCoords() {
			// TODO: redo use the data accessors
			final int componentCount = getComponentGoupCount();
			final int index = getComponentGoupIndex();
			final double width = getBarWidth();
			final double barInterval = width * (componentCount + 0.5);
			final Iterable<Double> coords =
					calculateCoordinate(barInterval, index);
			final ISizeAwareIterable<Double> labelCoords = new AbstractIterableToIterable<Double, Double>() {
				@Override
				protected Double transform(final Double target) {
					return target + width * (componentCount * 0.5 + 0.25);
				}
			}.convert(coords);

			// save the labels' coordinates to the state object so that the
			// axis for the argument (x-axis for vertical oriented, y-axis for
			// horizontal oriented) can access it.
			if (isHorizontal()) {
				updateState(AbstractChartTool.Y_LABEL_COORDS, labelCoords);
			} else {
				updateState(AbstractChartTool.X_LABEL_COORDS, labelCoords);
				updateState(AbstractChartTool.COMPUTED_LABEL_COORD_SHIFT,
						(int) (-width * componentCount));
			}
			return coords;
		}

		/**
		 * @return the barWidth
		 */
		protected double getBarWidth() {
			if (this.barWidth == 0) {
				this.barWidth = computeBarWidth(getAxisLength(),
						getComponentGoupCount());
			}
			return this.barWidth;
		}

		List<IDataElement> createDataElementList() {
			getMainChartTool().registerLegendHandler(getChartComponent(), createLegendHandler());
			// create the template data list
			final IContainerElement container =
					getMainChartTool().getChartingAreaContainer();
			final DataElement rectTemplate =
					DataElement.newInstance(SVGConstants.SVG_RECT, container);
			rectTemplate.addProperty(SVGConstants.SVG_STYLE, getBarGoupStyle());
			final List<IDataElement> rects = new ArrayList<IDataElement>();
			rects.add(rectTemplate);
			fillProperties(rectTemplate);
			rectTemplate.setOrdinal(EnumPartZIndex.datapoint);
			return rects;
		}

		abstract void fillProperties(IDataElement rectTemplate);

		abstract int getAxisLength();

		abstract void scaleData(Iterable<? extends Number> valuesIterable);
	}

	private static class BarTracker {
		private Iterator<String> colorsIter = Arrays.asList(
				BarComponentTool.defaultColors).iterator();
		private int index = 0;

		private String nextColor() {
			if (!this.colorsIter.hasNext()) {
				this.colorsIter = Arrays.asList(
						BarComponentTool.defaultColors).iterator();
			}
			return this.colorsIter.next();
		}

		// TODO: need to refactor to ensure that this is not called twice for
		// each component
		private int nextIndex() {
			return this.index++;
		}
	}

	private static final String BAR_STATE = "bar state";

	// TODO: randomly chosen eye pleasing colors. may need to externalize it
	private static final String[] defaultColors = { "#C0C0C0", "#009999",
			"#CC9933", "#669966", "#336699", "#CC6699", "#660000", "#666600",
			"#6666CC", "#CCCC00", "#FF9900", "#330099", "#00CC00" };

	private BarCreator barCreator;

	private BarTracker barTracker;
	private Integer componentIndex;

	private ISizeAwareIterable<Double> valuesArray;

	BarComponentTool(final IChartToolFactory factory, final IChartPart part,
			final Map<Object, Object> state) {
		super(factory, part, state);
	}

	private ILegendHandler createLegendHandler() {
		return new ILegendHandler() {
			@Override
			public IElement createElement() {
				final IElement element = Element.newInstance(SVGConstants.SVG_RECT_NS);
				element.addProperty(SVGConstants.SVG_STYLE, getBarGoupStyle());
				return element;
			}
		};
	}

	private BarCreator getBarCreator() {
		if (this.barCreator == null) {
			this.barCreator = newBarCreator();
		}
		return this.barCreator;
	}

	private String getBarGoupStyle() {
		String style = this.getChartComponent().getStyle();
		if (style == null) {
			style = "fill:" + getBarTracker().nextColor();
		}
		return style;
	}

	private BarTracker getBarTracker() {
		if (this.barTracker == null) {
			this.barTracker = new BarTracker();
			updateState(BarComponentTool.BAR_STATE, this.barTracker);
		}
		return this.barTracker;
	}

	private int getComponentGoupCount() {
		return getMainChartTool().getChartComponentCount();
	}

	private int getComponentGoupIndex() {
		if (this.componentIndex == null) {
			// this makes sure that we call BarState.nextIndex() only once
			this.componentIndex = getBarTracker().nextIndex();
		}
		return this.componentIndex;
	}

	private boolean isHorizontal() {
		return Constants.ORIENTATION_HORIZONTAL.equals(
				getChart().getOrientation());
	}

	private BarCreator newBarCreator() {
		if (isHorizontal()) {
			return new HorizontalBarCreator();
		} else {
			return new VerticalBarCreator();
		}
	}

	@Override
	protected List<IDataElement> createDataElementList() {
		return this.getBarCreator().createDataElementList();
	}

	@Override
	protected void fillLayoutElement(final IElement element) {
		// do nothing
	}

	/**
	 * @return the valuesArray
	 */
	protected ISizeAwareIterable<Double> getValuesArray() {
		return this.valuesArray;
	}

	@Override
	protected boolean isValid(final IChartPart part) {
		return (part instanceof IChartComponent);
	}

	@Override
	protected void prepareData() {
		// scale to the viewBox coordinates
		this.getBarCreator().scaleData(getDataValues());
	}

	protected void setValuesArray(final ISizeAwareIterable<Double> valuesArray) {
		this.valuesArray = valuesArray;
	}

	@Override
	protected void synchToolState(final Map<Object, Object> state) {
		this.barTracker = (BarTracker) state.get(BarComponentTool.BAR_STATE);
	}
}