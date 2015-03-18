package org.javaq.chartfaces.render.tool.impl;

import java.util.Map;

import org.javaq.chartfaces.api.Box;
import org.javaq.chartfaces.api.IChartPart;
import org.javaq.chartfaces.component.impl.ParserUtil;
import org.javaq.chartfaces.constants.EnumPart;
import org.javaq.chartfaces.constants.SVGConstants;
import org.javaq.chartfaces.converter.AbstractIterableToIterable;
import org.javaq.chartfaces.converter.TranslateScaleToDoubleIterable;
import org.javaq.chartfaces.document.IDataElement;
import org.javaq.chartfaces.document.IElement;
import org.javaq.chartfaces.document.impl.DataElement;
import org.javaq.chartfaces.document.impl.Element;
import org.javaq.chartfaces.iterable.ISizeAwareIterable;
import org.javaq.chartfaces.render.tool.IChartToolFactory;
import org.javaq.chartfaces.util.IStringBuilder;
import org.javaq.chartfaces.util.NumberRoundingStringBuilder;


public class BottomAxisTool extends CartesianAxisTool {
	private static final class TranslationRotationIterable extends
			AbstractIterableToIterable<Double, String> {
		private final int yShift;
		private final double y;
		private final int xShift;
		private final int angle;

		private TranslationRotationIterable(int yShift, double y,
				int xShift, int angle) {
			this.yShift = yShift;
			this.y = y;
			this.xShift = xShift;
			this.angle = angle;
		}

		@Override
		protected String transform(final Double value) {
			return ChartToolUtil.makeRotateString(
					angle, value, y)
					+ " "
					+ ChartToolUtil.makeTranslateString(
							xShift, yShift);
		}
	}

	private ISizeAwareIterable<Double> computedXLabelCoordinates;

	private int xLabelShiftHint;

	BottomAxisTool(final IChartToolFactory factory, final IChartPart part,
			final Map<Object, Object> state) {
		super(factory, part, state);
	}

	@Override
	protected void addLabels(final IDataElement element) {
		final ISizeAwareIterable<Double> labelsCoords = getLabelCoordinates();
		final int labelFontSize =
				ParserUtil.getAxisLabelFontSize(getAxisPart());
		final int padding = getChart().getPadding();
		final double y = getAxisPart().getTickHeight()
				+ labelFontSize + padding;
		final IDataElement label = DataElement.newInstance(SVGConstants.SVG_TEXT,
				getDataElementContainer());
		final Iterable<Object> labelsIterable = getLabelIterable();
		if (labelsIterable != null) {
			label.addProperty(SVGConstants.SVG_Y, y);
			label.addCustomProperty(SVGConstants.SVG_X, labelsCoords);
			label.addCustomProperty(SVGConstants.SVG_INNER_TEXT, labelsIterable);
			final int angle = getAxisPart().getLabelAngle();
			if (angle != 0) {
				final int xShift;
				if (angle < 0) {
					xShift = (int) (this.xLabelShiftHint / NEGATIVE_ANGLE_LABEL_X_SHIFT);
				} else {
					xShift = (int) (this.xLabelShiftHint / POSITIVE_ANGLE_LABEL_X_SHIFT);
				}
				final int yShift;
				if (angle < 0) {
					yShift = -(int) (labelFontSize / LABEL_Y_SHIFT);
				} else {
					yShift = (int) (labelFontSize / LABEL_Y_SHIFT);
				}
				final ISizeAwareIterable<String> transforms =
						new TranslationRotationIterable(yShift, y, xShift, angle).convert(labelsCoords);
				label.addCustomProperty(SVGConstants.SVG_TRANSFORM, transforms);
			}
			element.addChildren(label);
		}
	}

	@Override
	protected IStringBuilder createAxisLinePath() {
		final Box viewBox = getPartSpecs().getViewBox();
		final IStringBuilder sb = new NumberRoundingStringBuilder()
				.append('M').append("0 0h").append(viewBox.getWidth());
		return sb;
	}

	@Override
	protected IElement createCaptionTextElement(final IElement captionElement) {
		final Box viewBox = getPartSpecs().getViewBox();
		final int y = viewBox.getHeight();
		final IElement element = Element.newInstance(SVGConstants.SVG_TEXT_NS);
		element.addProperty(SVGConstants.SVG_X, String.valueOf(viewBox.getWidth() / 2));
		element.addProperty(SVGConstants.SVG_Y, String.valueOf(y));
		return element;
	}

	@Override
	protected String createGridLinesPaths(final int density) {
		final IStringBuilder sb = new NumberRoundingStringBuilder();
		if (density > 0) {
			final ISizeAwareIterable<Double> labelsCoords = getLabelCoordinates();
			final Box viewBox = getChartingAreaViewBox();
			final int y = viewBox.getHeight();
			final int lineLength = -y;
			double xLast = -1;
			double xFirst = -1;
			double dx = -1;
			// lines in between and on the labeled ticks
			for (final double x : labelsCoords) {
				if (xFirst == -1) {
					xFirst = x;
				}
				if (density > 1 && xLast != -1 && x > xLast) {
					// TODO: need to handle scaled axis, e.g., one with
					// logarithmic scale. Need to abstract this away to
					// calculate with a function looked up from a function
					// library.
					dx = (x - xLast) / density;
					for (int i = 0; i < density - 1; i++) {
						xLast += dx;
						sb.append('M').append(xLast).append(' ').append(y)
								.append('v').append(lineLength);
					}
				}
				xLast = x;
				sb.append('M').append(x).append(' ').append(y).append('v')
						.append(lineLength);
			}
			if (dx > 0) {
				// lines in between the axis-min and the first label
				for (double x = xFirst - dx; x > 0; x -= dx) {
					sb.append('M').append(x).append(' ').append(y)
							.append('v').append(lineLength);
				}
				// lines in between the last label and the axis-max
				for (double x = xLast + dx; x < viewBox.getWidth(); x += dx) {
					sb.append('M').append(x).append(' ').append(y)
							.append('v').append(lineLength);
				}
			}
		}
		return sb.toString();
	}

	@Override
	protected String createTickPaths() {
		final int tickSize = getAxisPart().getTickHeight();
		final IStringBuilder sb = new NumberRoundingStringBuilder();
		if (tickSize != 0) {
			final ISizeAwareIterable<Double> labelsCoords = getLabelCoordinates();
			if (labelsCoords.size() > 0) {
				final int y = 0;
				for (final double x : labelsCoords) {
					sb.append('M').append(x).append(' ').append(y)
							.append('v').append(tickSize);
				}
			}
		}
		return sb.toString();
	}

	@Override
	protected int getAxisLength(final Box box) {
		return box.getWidth();
	}

	@Override
	protected EnumPart getAxisType() {
		return EnumPart.xaxis;
	}

	@Override
	protected ISizeAwareIterable<Double> getComponentComputedLabelCoordinates() {
		return this.computedXLabelCoordinates;
	}

	@Override
	protected ChartDocumentType getDocumentType() {
		return ChartDocumentType.xaxis;
	}

	@Override
	protected String getLabelAnchor() {
		if (getAxisPart().getLabelAngle() > 0) {
			return SVGConstants.SVG_START;
		} else if (getAxisPart().getLabelAngle() < 0) {
			return SVGConstants.SVG_END;
		} else {
			return SVGConstants.SVG_MIDDLE;
		}
	}

	@Override
	protected ISizeAwareIterable<Double> scaleCoordinates(
			final Iterable<Double> values,
			final double minValue, final double scale, final int axisRange) {
		return new TranslateScaleToDoubleIterable<Double>(
				minValue, scale, 0).convert(values);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void synchToolState(final Map<Object, Object> state) {
		super.synchToolState(state);
		final Integer shift = (Integer) state
				.get(AbstractChartTool.COMPUTED_LABEL_COORD_SHIFT);
		this.xLabelShiftHint = shift == null ? 0 : shift;
		this.computedXLabelCoordinates =
				(ISizeAwareIterable<Double>) state
						.get(AbstractChartTool.X_LABEL_COORDS);
	}
}
