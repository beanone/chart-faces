package org.javaq.chartfaces.render.tool.impl;

import java.util.Map;

import org.javaq.chartfaces.api.Box;
import org.javaq.chartfaces.api.IChartPart;
import org.javaq.chartfaces.api.IPartSpec;
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


public class TopAxisTool extends CartesianAxisTool {
	private static final class TranslationRotationIterable extends
			AbstractIterableToIterable<Double, String> {
		private final int angle;
		private final double yShift;
		private final double y;
		private final double xShift;

		private TranslationRotationIterable(int angle, double yShift, double y,
				double xShift) {
			this.angle = angle;
			this.yShift = yShift;
			this.y = y;
			this.xShift = xShift;
		}

		@Override
		protected String transform(final Double value) {
			return ChartToolUtil.makeRotateString(angle,
					value, y)
					+ " "
					+ ChartToolUtil.makeTranslateString(
							xShift, yShift);
		}
	}

	private ISizeAwareIterable<Double> computedXLabelCoordinates;

	private int xLabelShiftHint;

	TopAxisTool(final IChartToolFactory factory, final IChartPart part,
			final Map<Object, Object> state) {
		super(factory, part, state);
	}

	@Override
	protected void addLabels(final IDataElement element) {
		final ISizeAwareIterable<Double> labelsCoords = getLabelCoordinates();
		final int labelFontSize =
				ParserUtil.getAxisLabelFontSize(getAxisPart());
		final IPartSpec specs = getPartSpecs();
		final Box viewBox = specs.getViewBox();
		final double y = viewBox.getHeight() - getAxisPart().getTickHeight()
				- getChart().getPadding();
		final IDataElement label = DataElement.newInstance(SVGConstants.SVG_TEXT,
				getDataElementContainer());
		final Iterable<Object> labelsIterable = getLabelIterable();
		if (labelsIterable != null) {
			label.addProperty(SVGConstants.SVG_Y, String.valueOf(y));
			label.addCustomProperty(SVGConstants.SVG_X, labelsCoords);
			label.addCustomProperty(SVGConstants.SVG_INNER_TEXT, labelsIterable);
			final int angle = getAxisPart().getLabelAngle();
			if (angle != 0) {
				final double xShift;
				if (angle < 0) {
					xShift = this.xLabelShiftHint / NEGATIVE_ANGLE_LABEL_X_SHIFT;
				} else {
					xShift = this.xLabelShiftHint / POSITIVE_ANGLE_LABEL_X_SHIFT;
				}
				final double yShift;
				if (angle < 0) {
					yShift = -labelFontSize / LABEL_Y_SHIFT;
				} else {
					yShift = labelFontSize / LABEL_Y_SHIFT;
				}
				final ISizeAwareIterable<String> transforms =
						new TranslationRotationIterable(angle, yShift, y, xShift).convert(labelsCoords);
				label.addCustomProperty(SVGConstants.SVG_TRANSFORM, transforms);
			}
			element.addChildren(label);
		}
	}

	@Override
	protected IStringBuilder createAxisLinePath() {
		final IPartSpec specs = getPartSpecs();
		final Box viewBox = specs.getViewBox();
		final IStringBuilder sb = new NumberRoundingStringBuilder()
				.append('M').append("0 ").append(viewBox.getHeight())
				.append('h').append(viewBox.getWidth());
		return sb;
	}

	@Override
	protected IElement createCaptionTextElement(final IElement captionElement) {
		final IPartSpec specs = getPartSpecs();
		final Box viewBox = specs.getViewBox();
		// final int y = viewBox.getHeight() - getAxisPart().getTickHeight()
		// - ParserUtil.getAxisLabelFontSize(getAxisPart())
		// - getChart().getPadding() * 3;
		final int y = getChart().getPadding()
				+ ParserUtil.getCaptionFontSize(getAxisPart()) / 2;
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
			final double y = 0;
			final double lineLength = viewBox.getHeight();
			double xLast = -1;
			double xFirst = -1;
			double dx = -1;
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
						addVerticalLine(sb, xLast, y, lineLength);
					}
				}
				xLast = x;
				addVerticalLine(sb, x, y, lineLength);
			}
			if (dx > 0) {
				// lines in between the axis-min and the first label
				for (double x = xFirst - dx; x > 0; x -= dx) {
					addVerticalLine(sb, x, y, lineLength);
				}
				// lines in between the last label and the axis-max
				for (double x = xLast + dx; x < viewBox.getWidth(); x += dx) {
					addVerticalLine(sb, x, y, lineLength);
				}
			}
		}
		return sb.toString();
	}

	private void addVerticalLine(final IStringBuilder sb, double x,
			final double y, final double lineLength) {
		sb.append('M').append(x).append(' ').append(y)
				.append('v').append(lineLength);
	}

	@Override
	protected String createTickPaths() {
		final double tickSize = -getAxisPart().getTickHeight();
		final IStringBuilder sb = new NumberRoundingStringBuilder();
		if (tickSize != 0) {
			final ISizeAwareIterable<Double> labelsCoords = getLabelCoordinates();
			if (labelsCoords.size() > 0) {
				final IPartSpec specs = getPartSpecs();
				final Box viewBox = specs.getViewBox();
				final double y = viewBox.getHeight();
				for (final double x : labelsCoords) {
					addVerticalLine(sb, x, y, tickSize);
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
			return SVGConstants.SVG_END;
		} else if (getAxisPart().getLabelAngle() < 0) {
			return SVGConstants.SVG_START;
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