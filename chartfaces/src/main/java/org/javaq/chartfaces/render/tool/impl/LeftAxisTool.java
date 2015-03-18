package org.javaq.chartfaces.render.tool.impl;

import java.util.Map;

import org.javaq.chartfaces.api.Box;
import org.javaq.chartfaces.api.IChartPart;
import org.javaq.chartfaces.api.IPartSpec;
import org.javaq.chartfaces.component.impl.ParserUtil;
import org.javaq.chartfaces.constants.EnumPart;
import org.javaq.chartfaces.constants.SVGConstants;
import org.javaq.chartfaces.converter.AbstractIterableToIterable;
import org.javaq.chartfaces.converter.TranslateScaleComplimentDoubleIterable;
import org.javaq.chartfaces.document.IDataElement;
import org.javaq.chartfaces.document.IElement;
import org.javaq.chartfaces.document.impl.DataElement;
import org.javaq.chartfaces.document.impl.Element;
import org.javaq.chartfaces.iterable.ISizeAwareIterable;
import org.javaq.chartfaces.render.tool.IChartToolFactory;
import org.javaq.chartfaces.util.IStringBuilder;
import org.javaq.chartfaces.util.NumberRoundingStringBuilder;


public class LeftAxisTool extends CartesianAxisTool {
	private static final class RotationIterable extends
			AbstractIterableToIterable<Double, String> {
		private final double x;
		private final int angle;

		private RotationIterable(double x, int angle) {
			this.x = x;
			this.angle = angle;
		}

		@Override
		protected String transform(final Double value) {
			return ChartToolUtil.makeRotateString(angle, x, value);
		}
	}

	private ISizeAwareIterable<Double> computedYLabelCoordinates;

	LeftAxisTool(final IChartToolFactory factory, final IChartPart part,
			final Map<Object, Object> state) {
		super(factory, part, state);
	}

	@Override
	protected void addLabels(final IDataElement element) {
		final ISizeAwareIterable<Double> labelsCoords = getLabelCoordinates();
		final IPartSpec specs = getPartSpecs();
		final Box viewBox = specs.getViewBox();
		final double x = viewBox.getWidth()
				- getAxisPart().getTickHeight() - getChart().getPadding()
				- ParserUtil.getAxisLabelFontSize(getAxisPart()) / 2;
		final IDataElement label = DataElement.newInstance(SVGConstants.SVG_TEXT,
				getDataElementContainer());
		final Iterable<Object> labelsIterable = getLabelIterable();
		if (labelsIterable != null) {
			label.addProperty(SVGConstants.SVG_X, x);
			label.addCustomProperty(SVGConstants.SVG_Y, labelsCoords);
			label.addCustomProperty(SVGConstants.SVG_INNER_TEXT, labelsIterable);
			final int angle = getAxisPart().getLabelAngle();
			if (angle != 0) {
				final ISizeAwareIterable<String> transforms = new RotationIterable(x, angle).convert(labelsCoords);
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
				.append('M').append(viewBox.getWidth())
				.append(" 0v").append(viewBox.getHeight());
		return sb;
	}

	@Override
	protected IElement createCaptionTextElement(final IElement captionElement) {
		final IPartSpec specs = getPartSpecs();
		final Box viewBox = specs.getViewBox();
		final int x = getChart().getPadding()
				+ ParserUtil.getCaptionFontSize(getAxisPart()) / 2;
		final IElement element = Element.newInstance(SVGConstants.SVG_TEXT_NS);
		final int y = viewBox.getHeight() / 2;
		element.addProperty(SVGConstants.SVG_X, String.valueOf(x));
		element.addProperty(SVGConstants.SVG_Y, String.valueOf(y));
		captionElement.addProperty(SVGConstants.SVG_TRANSFORM,
				SVGConstants.ROTATE_MINUS_90 + x + ' ' + y + ')');
		return element;
	}

	@Override
	protected String createGridLinesPaths(final int density) {
		final IStringBuilder sb = new NumberRoundingStringBuilder();
		if (density > 0) {
			final ISizeAwareIterable<Double> labelsCoords = getLabelCoordinates();
			final Box viewBox = getChartingAreaViewBox();
			final double x = 0;
			final int lineLength = viewBox.getWidth();
			double yLast = -1;
			double yFirst = -1;
			double dy = 1; // dy should always be negative since we are going up
			for (final double y : labelsCoords) {
				if (yFirst == -1) {
					yFirst = y;
				}
				if (density > 1 && yLast != -1 && y < yLast) {
					dy = (y - yLast) / density;
					for (int i = 0; i < density - 1; i++) {
						yLast += dy;
						sb.append('M').append(x).append(' ').append(yLast)
								.append('h').append(lineLength);
					}
				}
				yLast = y;
				sb.append('M').append(x).append(' ').append(y).append('h')
						.append(lineLength);
			}
			if (dy < 0) {
				// lines in between the axis-min and the first label
				for (double y = yFirst - dy; y < viewBox.getHeight(); y -= dy) {
					sb.append('M').append(x).append(' ').append(y)
							.append('h').append(lineLength);
				}
				// lines in between the last label and the axis-max
				for (double y = yLast + dy; y > 0; y += dy) {
					sb.append('M').append(x).append(' ').append(y)
							.append('h').append(lineLength);
				}
			}
		}
		return sb.toString();
	}

	@Override
	protected String createTickPaths() {
		final int tickSize = -getAxisPart().getTickHeight();
		final IStringBuilder sb = new NumberRoundingStringBuilder();
		if (tickSize != 0) {
			final ISizeAwareIterable<Double> labelsCoords = getLabelCoordinates();
			if (labelsCoords.size() > 0) {
				final IPartSpec specs = getPartSpecs();
				final Box viewBox = specs.getViewBox();
				final int x = viewBox.getWidth();
				for (final double y : labelsCoords) {
					sb.append('M').append(x).append(' ').append(y).append('h')
							.append(tickSize);
				}
			}
		}
		return sb.toString();
	}

	@Override
	protected int getAxisLength(final Box box) {
		return box.getHeight();
	}

	@Override
	protected EnumPart getAxisType() {
		return EnumPart.yaxis;
	}

	@Override
	protected ISizeAwareIterable<Double> getComponentComputedLabelCoordinates() {
		return this.computedYLabelCoordinates;
	}

	@Override
	protected ChartDocumentType getDocumentType() {
		return ChartDocumentType.yaxis;
	}

	@Override
	protected String getLabelAnchor() {
		return SVGConstants.SVG_END;
	}

	@Override
	protected ISizeAwareIterable<Double> scaleCoordinates(
			final Iterable<Double> values,
			final double minValue, final double scale, final int axisRange) {
		return new TranslateScaleComplimentDoubleIterable(
				minValue, scale, 0, axisRange).convert(values);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void synchToolState(final Map<Object, Object> state) {
		super.synchToolState(state);
		this.computedYLabelCoordinates =
				(ISizeAwareIterable<Double>) state.get(AbstractChartTool.Y_LABEL_COORDS);
	}
}
