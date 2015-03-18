package org.javaq.chartfaces.constants;

/**
 * Enumeration of SVG tag names, and attribute names.
 * 
 * @author Hongyan Li
 * 
 */
public enum EnumSVG {
	a,
	altGlyph,
	altGlyphDef,
	altGlyphItem,
	animate,
	animateColor,
	animateMotion,
	animateTransform,
	circle,
	clipPath,
	colorProfile("color-profile"),
	crispEdges,
	cursor,
	d,
	defs,
	desc,
	ellipse,
	end,
	feBlend,
	feColorMatrix,
	feComponentTransfer,
	feComposite,
	feConvolveMatrix,
	feDiffuseLighting,
	feDisplacementMap,
	feDistantLight,
	feFlood,
	feFuncA,
	feFuncB,
	feFuncG,
	feFuncR,
	feGaussianBlur,
	feImage,
	feMerge,
	feMergeNode,
	feMorphology,
	feOffset,
	fePointLight,
	feSpecularLighting,
	feSpotLight,
	feTile,
	feTurbulence,
	filter,
	font,
	fontFace("font-face"),
	fontFaceFormat("font-face-format"),
	fontFaceName("font-face-name"),
	fontFaceSrc("font-face-src"),
	fontFaceUri("font-face-uri"),
	foreignObject,
	g,
	glyph,
	glyphRef,
	height,
	hkern,
	id,
	image,
	innerText,
	line,
	linearGradient,
	marker,
	mask,
	metadata,
	middle,
	missingGlyph("missing-glyph"),
	mpath,
	path,
	pattern,
	polygon,
	polyline,
	preservAspectRatio,
	radialGradient,
	rect,
	script,
	set,
	shapeRendering("shape-rendering"),
	start,
	stop,
	style,
	styleClass("class"),
	svg,
	switchTag("switch"),
	symbol,
	text,
	textAnchor("text-anchor"),
	textPath,
	title,
	transform,
	translate,
	tref,
	tspan,
	use,
	view,
	viewBox,
	vkern,
	width,
	x,
	xlinkHref("xlink:href"),
	xmlns("xmlns:svg"),
	xmlnsEv("xmlns:ev"),
	xmlnsEvURL("http://www.w3.org/2001/xml-events"),
	xmlnsSVGURL("http://www.w3.org/2000/svg"),
	xmlnsXlink("xmlns:xlink"),
	xmlnsXlinkURL("http://www.w3.org/1999/xlink"),
	y;

	private static String namespace = "svg";
	private static String namespacePrefix = EnumSVG.getNamespacePrefix();

	/**
	 * @return the namespace
	 */
	public static String getNamespacePrefix() {
		return EnumSVG.namespace == null ? "" : EnumSVG.namespace + ":";
	}

	/**
	 * TODO: inject the namespace once we can find the namespace from the xhtml
	 * document.
	 * 
	 * Because of how this is implemented, the user of the renderkit need to
	 * make sure that the namespace for SVG need to be consistent across the
	 * application - can't change it!.
	 * 
	 * @param namespace
	 *            the namespace to set
	 */
	public static void setNamespace(final String namespace) {
		EnumSVG.namespace = namespace;
	}

	private String toQualifiedString;
	private String toString;

	EnumSVG() {
	}

	EnumSVG(final String toString) {
		this.toString = toString;
	}

	public String toQualifiedString() {
		if (this.toQualifiedString == null) {
			this.toQualifiedString = EnumSVG.namespacePrefix + toString();
		}
		return this.toQualifiedString;
	}

	@Override
	public String toString() {
		return ((this.toString != null) ? this.toString : super.toString());
	}
}
