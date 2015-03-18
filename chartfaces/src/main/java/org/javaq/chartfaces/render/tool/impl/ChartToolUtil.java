package org.javaq.chartfaces.render.tool.impl;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import org.javaq.chartfaces.constants.SVGConstants;
import org.javaq.chartfaces.converter.TranslateScaleToDoubleIterable;
import org.javaq.chartfaces.document.IElement;
import org.javaq.chartfaces.document.impl.Element;
import org.javaq.chartfaces.iterable.ISizeAwareIterable;
import org.javaq.chartfaces.render.svg.PathsUtil;
import org.javaq.chartfaces.util.IStringBuilder;
import org.javaq.chartfaces.util.IStringBuilderFactory;
import org.javaq.chartfaces.util.NumberRoundingStringBuilder;
import org.javaq.chartfaces.util.NumberUtils;

import com.sun.faces.facelets.el.ELText;

public final class ChartToolUtil {
	
	private static final class NumberRoundingStringBuilderFactory implements
			IStringBuilderFactory {
		@Override
		public IStringBuilder newBuilder() {
			return new NumberRoundingStringBuilder();
		}
	}

	private ChartToolUtil() {}

	public static String eveluateContent(String content) {
		FacesContext context = FacesContext.getCurrentInstance();
		ELContext elContext = context.getELContext();
		Application application = context.getApplication();
		ExpressionFactory factory = application.getExpressionFactory();
		ELText text = ELText.parse(factory, elContext, content);
		return text.toString(elContext);
	}

	private static final PathsUtil pathsUtil = 
			new PathsUtil(new NumberRoundingStringBuilderFactory());

	/**
	 * Create a SVG rotate property value String.
	 * 
	 * @param angle
	 *            the angle to rotate
	 * @param x
	 *            the x coordinate of the center of rotation
	 * @param y
	 *            the y coordinate of the center of rotation
	 * @return an SVG rotate command string.
	 */
	public static String makeRotateString(
			final int angle, final double x, final double y) {
		double cx = NumberUtils.truncate(x, -1);
		double cy = NumberUtils.truncate(y, -1);
		return "rotate(" + angle + "," + cx + "," + cy + ")";
	}

	/**
	 * Create a SVG translate property value String.
	 * 
	 * @param x
	 *            the translation along the x-axis.
	 * @param y
	 *            the translation along the y-axis.
	 * @return a SVG translate command string.
	 */
	public static String makeTranslateString(final double x, final double y) {
		double dx = NumberUtils.truncate(x, -1);
		double dy = NumberUtils.truncate(y, -1);
		return "translate(" + dx + "," + dy + ")";
	}

	/**
	 * Create a SVG path command string.
	 * 
	 * @param xIterable an Iterable for all the x coordinates. Not null.
	 * @param yIterable an Iterable for all the y coordinates. Not null.
	 * @param closed a flag that indicates whether the path is closed or not.
	 * @return a SVG path command string.
	 */
	public static String renderAbsolutePaths(
			final ISizeAwareIterable<Double> xIterable,
			final ISizeAwareIterable<Double> yIterable, final boolean closed) {
		return ChartToolUtil.pathsUtil.renderAbsolutePaths(xIterable,
				yIterable, closed);
	}

	/**
	 * Scales and translates all the values of the passed in {@link Iterable} using the passed
	 * in scale factor, offset and new origin.
	 * 
	 * @param values an {@link Iterable} of values to be scaled and translated. Not null.
	 * @param factor a scale factor.
	 * @param newMin an offset to be applied to the values before scaling.
	 * @param originalMin the new origin of the values (used for translation after scaling).
	 * @return the resulting iterable of values.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static ISizeAwareIterable<Double> scaleTranslate(
			final Iterable<? extends Number> values,
			final double factor,
			final int newMin, final double originalMin) {

		return new TranslateScaleToDoubleIterable(
						originalMin, factor, newMin).convert(values);
	}
	
	/**
	 * Creates an element that is exactly boxed in the passed in {@link BoxModel}. The passed in element
	 * will be used as the return if it is not null. Otherwise, a new element is created.
	 * 
	 * @param boxModel the {@link BoxModel} the to be returned is exactly boxed in. Not null.
	 * @param element an {@link IElement}. Null accepted.
	 * @return an {@link IElement} that is exactly boxed in the passed in {@link BoxModel}.
	 */
	public static IElement createLayoutBox(final BoxModel boxModel, IElement element) {
		if(element == null) {
			element = Element.newInstance(SVGConstants.SVG_G_NS);
		}
		element.addProperty(SVGConstants.SVG_X, String.valueOf(boxModel.getOriginX()));
		element.addProperty(SVGConstants.SVG_Y, String.valueOf(boxModel.getOriginY()));
		element.addProperty(SVGConstants.SVG_WIDTH, String.valueOf(boxModel.getWidth()));
		element.addProperty(SVGConstants.SVG_HEIGHT, String.valueOf(boxModel.getHeight()));
		if (boxModel.getOriginX() != 0 || boxModel.getOriginY() != 0) {
			element.addProperty(SVGConstants.SVG_TRANSFORM, createTransformString(boxModel));
		}
		
		return element;
	}

	private static Object createTransformString(BoxModel boxModel) {
		return new StringBuilder()
			.append(SVGConstants.SVG_TRANSLATE).append("(")
			.append(boxModel.getOriginX()).append(",")
			.append(boxModel.getOriginY()).append(")").toString();
	}
}
