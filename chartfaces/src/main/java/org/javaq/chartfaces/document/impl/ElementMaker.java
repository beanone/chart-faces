package org.javaq.chartfaces.document.impl;

import org.javaq.chartfaces.constants.EnumSVG;
import org.javaq.chartfaces.document.IContainerElement;
import org.javaq.chartfaces.document.IElement;

/**
 * Factory for Element objects.
 * 
 * @author Hongyan Li
 * 
 */
public final class ElementMaker {
	/**
	 * @return a new "circle" element.
	 */
	public static IElement newCircle() {
		return Element.newInstance(EnumSVG.circle.toQualifiedString());
	}

	/**
	 * @return a new "circle" element.
	 */
	public static IElement newDefs() {
		return Element.newInstance(EnumSVG.defs.toQualifiedString());
	}

	/**
	 * @return a new "path" element.
	 */
	public static IElement newPath(final String paths) {
		final IElement element = Element.newInstance(EnumSVG.path.toQualifiedString());
		element.addProperty(EnumSVG.d.toString(), paths);
		return element;
	}

	/**
	 * @return a new "rect" element.
	 */
	public static IElement newRectangle() {
		return Element.newInstance(EnumSVG.rect.toQualifiedString());
	}

	/**
	 * @return a new "symbol" element.
	 */
	public static IContainerElement newSymbol(final String id) {
		return ContainerElement.newInstance(EnumSVG.symbol.toQualifiedString(), id);
	}

	private ElementMaker() {
		// not intended to be instantiated.
	}
}
