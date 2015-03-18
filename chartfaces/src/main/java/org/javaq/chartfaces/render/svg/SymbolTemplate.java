package org.javaq.chartfaces.render.svg;

import org.javaq.chartfaces.document.IContainerElement;
import org.javaq.chartfaces.document.IElement;
import org.javaq.chartfaces.document.impl.ElementMaker;
import org.javaq.chartfaces.document.impl.ReadonlyElement;

/**
 * The template for a symbol.
 * 
 * @author Hongyan Li
 * 
 */
public final class SymbolTemplate {
	private static final String VIEW_BOX = "viewBox";

	/**
	 * Creates a new circle symbol.
	 * 
	 * @param id
	 *            the id for the symbol element. Cannot be null.
	 * @return a new instance of circle shaped {@link SymbolTemplate}.
	 * @throws NullPointerException
	 *             if the passed in is null.
	 */
	public static SymbolTemplate newCircle(final String id) {
		final SymbolTemplate template = new SymbolTemplate(id,
				ElementMaker.newCircle());
		template.updateSymbol(VIEW_BOX, "0 0 2 2");
		template.updateShape("cx", "1", "cy", "1", "r", "1");
		return template;
	}

	/**
	 * Creates a new circle symbol.
	 * 
	 * @param id
	 *            the id for the symbol element. Cannot be null.
	 * @param fill
	 *            the color fill of the symbol.
	 * @param stroke
	 *            the stroke color of the symbol.
	 * @param strokeWidth
	 *            the stroke width of the symbol.
	 * @param opacity
	 *            the opacity of the symbol
	 * @return a new instance of circle shaped {@link SymbolTemplate}.
	 * @throws NullPointerException
	 *             if the passed in is null.
	 */
	public static SymbolTemplate newCircle(final String id,
			final String fill, final String stroke, final int strokeWidth,
			final double opacity) {
		if (strokeWidth < 0 || strokeWidth > 10) {
			throw new IllegalArgumentException(
					"Invalid size, only 0 to 10 is allowed!");
		}
		final SymbolTemplate template = new SymbolTemplate(id,
				ElementMaker.newCircle());
		template.updateSymbol(VIEW_BOX, "0 0 20 20");
		template.updateShape("cx", "10", "cy", "10");
		final double r = 10 - strokeWidth / 2.;
		template.updateShape("r", "" + r);
		SymbolTemplate.fillSymbol(fill, stroke, strokeWidth, template, opacity);
		return template;
	}

	/**
	 * Creates a new equilateral triangle symbol.
	 * 
	 * @param id
	 *            the id for the symbol element. Cannot be null.
	 * @return a new instance of equilateral upward triangular shaped
	 *         {@link SymbolTemplate}.
	 * @throws NullPointerException
	 *             if the passed in is null.
	 */
	public static SymbolTemplate newEquilateralTriangle(final String id) {
		final SymbolTemplate template = new SymbolTemplate(id,
				ElementMaker.newPath("M0 0.933L0.5 0.067L1 0.933z"));
		template.updateSymbol(VIEW_BOX, "0 0 1 1");
		return template;
	}

	/**
	 * Creates a new equilateral triangle symbol.
	 * 
	 * @param id
	 *            the id for the symbol element. Cannot be null.
	 * @param fill
	 *            the color fill of the symbol.
	 * @param stroke
	 *            the stroke color of the symbol.
	 * @param strokeWidth
	 *            the stroke width of the symbol.
	 * @param opacity
	 *            the opacity fo the symbol.
	 * @return a new instance of equilateral upward triangular shaped
	 *         {@link SymbolTemplate}.
	 * @throws NullPointerException
	 *             if the passed in is null.
	 */
	public static SymbolTemplate newEquilateralTriangle(final String id,
			final String fill, final String stroke, final int strokeWidth,
			final double opacity) {
		if (strokeWidth < 0 || strokeWidth > 10) {
			throw new IllegalArgumentException(
					"Invalid size, only 0 to 10 is allowed!");
		}
		final double hShift = 86.6 * strokeWidth;
		final double vTopShift = strokeWidth;
		final double vBottomShift = strokeWidth / 2.;
		final double vBottom = 3232 - vBottomShift;
		final StringBuilder sb = new StringBuilder().append("M").append(hShift)
				.append(" ").append(vBottom).append("L1732 ")
				.append(232 + vTopShift).append("L").append(3464 - hShift)
				.append(" ").append(vBottom).append("z");
		final SymbolTemplate template = new SymbolTemplate(id,
				ElementMaker.newPath(sb.toString()));
		template.updateSymbol(VIEW_BOX, "0 0 3464 3464");
		SymbolTemplate.fillSymbol(fill, stroke, strokeWidth * 100, template,
				opacity);
		return template;
	}

	/**
	 * Creates a new symbol from path.
	 * 
	 * @param id
	 *            the id for the symbol element. Cannot be null.
	 * @param paths
	 *            paths of the symbol.
	 * @param resolution
	 *            the resolution of the path coordinates assuming that the view
	 *            port has a size of (1,1).
	 * @return a new instance of {@link SymbolTemplate} from the passed in
	 *         {@link Paths}.
	 * @throws NullPointerException
	 *             if the passed in id or paths is null.
	 */
	public static SymbolTemplate newFromPaths(final String id,
			final Paths paths, final double resolution) {
		final SymbolTemplate template = new SymbolTemplate(id,
				ElementMaker.newPath(paths.renderAbsolute(resolution, true)));
		final double[][] box = paths.boxMe(resolution);
		template.updateSymbol(VIEW_BOX, "" + box[0][0] + ' ' + box[1][0] + ' '
				+ box[0][1] + ' ' + box[1][1]);
		return template;
	}

	/**
	 * Creates new rectangle symbol.
	 * 
	 * @param id
	 *            the id for the symbol element. Cannot be null.
	 * @return a new instance of rectangle shaped {@link SymbolTemplate}.
	 * @throws NullPointerException
	 *             if the passed in is null.
	 */
	public static SymbolTemplate newRectangle(final String id) {
		final SymbolTemplate template = new SymbolTemplate(id,
				ElementMaker.newRectangle());
		template.updateShape("x", "0", "y", "0", "width", "100%", "height",
				"100%");
		return template;
	}

	/**
	 * Creates a new square symbol.
	 * 
	 * @param id
	 *            the id for the symbol element. Cannot be null.
	 * @return a new instance of square shaped {@link SymbolTemplate}.
	 * @throws NullPointerException
	 *             if the passed in is null.
	 */
	public static SymbolTemplate newSquare(final String id) {
		final SymbolTemplate template = new SymbolTemplate(id,
				ElementMaker.newRectangle());
		template.updateSymbol(VIEW_BOX, "0 0 1 1");
		template.updateShape("x", "0", "y", "0", "width", "1", "height", "1");
		return template;
	}

	/**
	 * Creates a new square symbol.
	 * 
	 * @param id
	 *            the id for the symbol element. Cannot be null.
	 * @param fill
	 *            the color fill of the symbol.
	 * @param stroke
	 *            the stroke color of the symbol.
	 * @param strokeWidth
	 *            the stroke width of the symbol.
	 * @param opacity
	 *            the opacity of the symbol.
	 * @return a new instance of square shaped {@link SymbolTemplate}.
	 * @throws NullPointerException
	 *             if the passed in is null.
	 */
	public static SymbolTemplate newSquare(final String id,
			final String fill, final String stroke, final int strokeWidth,
			final double opacity) {
		if (strokeWidth < 0 || strokeWidth > 10) {
			throw new IllegalArgumentException(
					"Invalid size, only 0 to 10 is allowed!");
		}
		final SymbolTemplate template = new SymbolTemplate(id,
				ElementMaker.newRectangle());
		template.updateSymbol(VIEW_BOX, "0 0 20 20");
		final int size = 20 - strokeWidth;
		final double origin = strokeWidth / 2.;
		template.updateShape("x", "" + origin, "y", "" + origin,
				"width", "" + size, "height", "" + size);
		SymbolTemplate.fillSymbol(fill, stroke, strokeWidth, template, opacity);
		return template;
	}

	private static void fillSymbol(final String fill, final String stroke,
			final int strokeWidth, final SymbolTemplate template,
			final double opacity) {
		if (fill != null) {
			template.updateShape("fill", fill);
		}
		if (strokeWidth != 0) {
			template.updateShape("stroke-width", "" + strokeWidth);
		}
		if (stroke != null) {
			template.updateShape("stroke", stroke);
		}
		if (opacity > 0 && opacity <= 1) {
			template.updateShape("opacity", "" + opacity);
		}
	}

	private final String id;

	private final IElement shape;

	private final IContainerElement symbol;

	private final RenderUtils utils = new RenderUtils();

	/**
	 * Construct this from an id for the symbol and an enclosing element.
	 * 
	 * @param id
	 *            the id of the template symbol. Cannot be null.
	 * @param shape
	 *            the constituent element of the symbol. So you can only have
	 *            one element in a symbol, but whether the element has children
	 *            elements is up to you!
	 * @throws IllegalArgumentException
	 *             if the passed in is null.
	 */
	private SymbolTemplate(final String id, final IElement shape) {
		if (id == null) {
			throw new IllegalArgumentException("The id is null!");
		}
		this.id = id;
		this.shape = shape;
		this.symbol = ElementMaker.newSymbol(id);
		this.symbol.addChildren(shape);
	}

	public IElement getDefs() {
		final IElement defs = ElementMaker.newDefs();
		defs.addChildren(this.symbol);
		return new ReadonlyElement(defs);
	}

	public String getId() {
		return this.id;
	}

	/**
	 * @return the symbol
	 */
	public IElement getSymbol() {
		return new ReadonlyElement(this.symbol);
	}

	@Override
	public String toString() {
		return this.utils.toXML(this.symbol).toString();
	}

	public void updateShape(final String... keyValues) {
		for (int i = 0; i < keyValues.length - 1; i += 2) {
			this.shape.addProperty(keyValues[i], keyValues[i + 1]);
		}
	}

	/**
	 * Update the symbol with the passed in key/value pairs as the properties.
	 * 
	 * @param keyValues
	 */
	public void updateSymbol(final String... keyValues) {
		for (int i = 0; i < keyValues.length - 1; i += 2) {
			this.symbol.addProperty(keyValues[i], keyValues[i + 1]);
		}
	}

}
