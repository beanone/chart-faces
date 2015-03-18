package org.javaq.chartfaces.render.svg;

import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.javaq.chartfaces.document.IContainerElement;
import org.javaq.chartfaces.document.IDataElement;
import org.javaq.chartfaces.document.IElement;
import org.javaq.chartfaces.util.IStringBuilder;


/**
 * Rendering utility methods.
 * 
 * @author Hongyan Li
 * 
 */
public class RenderUtils {
	private static class OrdinalComparator
			implements Comparator<IElement>, Serializable {
		private static final long serialVersionUID = 816280976091947848L;

		@Override
		public int compare(final IElement o1, final IElement o2) {
			return o1.getOrdinal() - o2.getOrdinal();
		}
	}

	private static void bufferJSonArray(final IStructuredStringBuilder sb,
			final Iterable<?> indexable) {
		sb.quote(Quote.SquareBracket);
		boolean hasElements = false;
		for (final Object value : indexable) {
			sb.textQuote(value).append(",");
			hasElements = true;
		}
		if (hasElements) {
			sb.removeLast();
		}
		sb.unquote(Quote.SquareBracket);
	}

	private static void bufferJSonEnd(final IStructuredStringBuilder sb) {
		sb.unquote(Quote.CurlyBracket);
	}

	private static StringBuffer makeKey(final IElement element, final int index) {
		final StringBuffer sb = new StringBuffer(element.getTagName());
		if (element instanceof IDataElement) {
			final IContainerElement container =
					((IDataElement) element).getContainerElement();
			sb.append('.').append(container.getId());
		}
		sb.append('.').append(index);
		return sb;
	}

	private static List<IElement> orderChildrenByZIndex(final IElement element) {
		final List<IElement> children = new ArrayList<IElement>(
				element.getChildren());
		Collections.sort(children, new OrdinalComparator());
		return children;
	}

	private static void sortByZIndex(final IElement[] elements) {
		Arrays.sort(elements, new OrdinalComparator());
	}

	private static void sortByZIndex(final List<? extends IElement> elements) {
		Collections.sort(elements, new OrdinalComparator());
	}

	private final IValueFormatPolicy policy;

	private final IStructuredStringBuilderFactory sbFactory;

	public RenderUtils() {
		this(new IStructuredStringBuilderFactory() {
			@Override
			public IStructuredStringBuilder newBuilder() {
				return new StructuredStringBuilder(new StringBuilder());
			}
		});
	}

	public RenderUtils(final IStructuredStringBuilderFactory factory) {
		this(factory, new IValueFormatPolicy() {
			@Override
			public Object format(final Object key, final Object value) {
				// the default policy does not do anything to the value.
				return value;
			}
		});
	}

	public RenderUtils(final IStructuredStringBuilderFactory factory,
			final IValueFormatPolicy policy) {
		if (factory == null) {
			throw new IllegalArgumentException("IStringBuilderFactory is null!");
		}
		this.sbFactory = factory;
		this.policy = policy;
	}

	/**
	 * Renders the {@link IDataElement} to its JSan String form. A Jsan String
	 * is an extension of Json String specification, where any first-level array
	 * member are treated as such that each element of the array represents an
	 * attribute value or child object of the containing object while all the
	 * other non-array members repeats themselves. Here is an example of a Jsan
	 * String:
	 * <p/>
	 * 
	 * <pre>
	 * {
	 *   "use":	{
	 * 	"style":'myStyle',
	 * 	"xlink:href":"#mySymbol",
	 * 	"x":['0','1'],
	 * 	"y":['0','1'],
	 * 	"width":['1','2'],
	 * 	"height":['1','2']
	 *   }
	 * }
	 * </pre>
	 * 
	 * We can see that this is simply a Json object. However, it actually is
	 * used to represent an array of Json objects corresponding to the following
	 * XML tags:
	 * <p/>
	 * 
	 * <pre>
	 * &lt;use style="myStyle" xlink:href="#mySymbol" x="0" y="0" width="1" height="1"/&gt;
	 * &lt;use style="myStyle" xlink:href="#mySymbol" x="1" y="1" width="2" height="2"/&gt;
	 * </pre>
	 * 
	 * The properties and the children of the element will be rendered as the
	 * member property of the JSon object, where the former will be of type
	 * string, and the later JSon object. The custom properties of the element
	 * will be rendered as member property of type array.
	 * 
	 * @param elements
	 *            an array of elements to be rendered.
	 * @return a JSon string equivalent of the passed in element.
	 */
	public IStringBuilder toJSan(final IDataElement... elements) {
		final IStructuredStringBuilder sb = this.sbFactory.newBuilder();
		RenderUtils.sortByZIndex(elements);

		// starts with a curly bracket and end with one.
		sb.quote(Quote.CurlyBracket);
		int index = 0;
		for (final IDataElement element : elements) {
			bufferJSan(sb, element, index++);
			sb.append(',');
		}
		if (elements.length > 0) {
			sb.removeLast();
		}
		sb.unquote(Quote.CurlyBracket);

		return sb.getStringBuilder();
	}

	/**
	 * Renders the {@link IDataElement} to its JSan String form. A Jsan String
	 * is an extension of Json String specification, where any first-level array
	 * member are treated as such that each element of the array represents an
	 * attribute value or child object of the containing object while all the
	 * other non-array members repeats themselves. Here is an example of a Jsan
	 * String:
	 * <p/>
	 * 
	 * <pre>
	 * {
	 *   "use":	{
	 * 	"style":'myStyle',
	 * 	"xlink:href":"#mySymbol",
	 * 	"x":['0','1'],
	 * 	"y":['0','1'],
	 * 	"width":['1','2'],
	 * 	"height":['1','2']
	 *   }
	 * }
	 * </pre>
	 * 
	 * We can see that this is simply a Json object. However, it actually is
	 * used to represent an array of Json objects corresponding to the following
	 * XML tags:
	 * <p/>
	 * 
	 * <pre>
	 * &lt;use style="myStyle" xlink:href="#mySymbol" x="0" y="0" width="1" height="1"/&gt;
	 * &lt;use style="myStyle" xlink:href="#mySymbol" x="1" y="1" width="2" height="2"/&gt;
	 * </pre>
	 * 
	 * The properties and the children of the element will be rendered as the
	 * member property of the JSon object, where the former will be of type
	 * string, and the later JSon object. The custom properties of the element
	 * will be rendered as member property of type array.
	 * 
	 * @param elements
	 *            an array of elements to be rendered.
	 * @return a JSon string equivalent of the passed in element.
	 */
	public IStringBuilder toJSan(final List<IDataElement> elements) {
		final IStructuredStringBuilder sb = this.sbFactory.newBuilder();
		RenderUtils.sortByZIndex(elements);

		// starts with a curly bracket and end with one.
		sb.quote(Quote.CurlyBracket);
		int index = 0;
		for (final IDataElement element : elements) {
			bufferJSan(sb, element, index++);
			sb.append(',');
		}
		if (elements.size() > 0) {
			sb.removeLast();
		}
		sb.unquote(Quote.CurlyBracket);

		return sb.getStringBuilder();
	}

	/**
	 * Renders the {@link IElement} to its JSon String form. The properties and
	 * the children of the element will be rendered as the member property of
	 * the JSon object, where the former will be of type string, and the later
	 * JSon object.
	 * 
	 * @param elements
	 *            an array of elements to be rendered.
	 * @return a JSon string equivalent of the passed in element.
	 */
	public IStringBuilder toJSon(final IElement... elements) {
		final IStructuredStringBuilder sb = this.sbFactory.newBuilder();

		// starts with a curly bracket and end with one.
		sb.quote(Quote.CurlyBracket);
		int index = 0;
		for (final IElement element : elements) {
			bufferJSon(sb, element, index++);
			sb.append(',');
		}
		if (elements.length > 0) {
			sb.removeLast();
		}
		sb.unquote(Quote.CurlyBracket);

		return sb.getStringBuilder();
	}

	/**
	 * Renders the {@link IElement} to its XML String form. The properties of
	 * the element will be rendered as the attributes of the tag body, and the
	 * children elements will be rendered as the content of the tag.
	 * 
	 * @param elements
	 *            an array of elements to be rendered.
	 * @return a XML string equivalent of the passed in element.
	 */
	public IStringBuilder toXML(final IElement... elements) {
		final IStructuredStringBuilder sb = this.sbFactory.newBuilder();
		RenderUtils.sortByZIndex(elements);
		for (final IElement element : elements) {
			bufferXML(sb, element);
		}
		return sb.getStringBuilder();
	}

	/**
	 * Renders the {@link IElement} to its XML String form. The properties of
	 * the element will be rendered as the attributes of the tag body, and the
	 * children elements will be rendered as the content of the tag.
	 * 
	 * @param elements
	 *            an array of elements to be rendered.
	 * @return a InputStream of XML content translated from the passed in
	 *         element.
	 */
	public InputStream toXMLStream(final IElement... elements)
			throws IOException {
		final PipedOutputStream out = new PipedOutputStream();
		final PipedInputStream inputStream = new PipedInputStream(out);
		final StructuredStreamBuilder sb = new StructuredStreamBuilder(out);
		RenderUtils.sortByZIndex(elements);
		for (final IElement element : elements) {
			bufferXML(sb, element);
		}
		return inputStream;
	}

	private void bufferDataElementXML(final IStructuredStringBuilder sb,
			final IDataElement element) {
		final Iterator<IElement> iter = new ElementIterator(element);
		if (iter.hasNext()) {
			while (iter.hasNext()) {
				bufferOneElement(sb, iter.next());
			}
		} else {
			bufferOneElement(sb, element);
		}
	}

	private void bufferJSan(final IStructuredStringBuilder sb,
			final IDataElement element, final int index) {
		bufferJSonStart(sb, element, index);

		// render the custom properties
		final Map<String, Iterable<?>> customProperties = element
				.getCustomProperties();
		for (final String name : new TreeSet<String>(customProperties.keySet())) {
			sb.textQuote(name).append(":");
			RenderUtils.bufferJSonArray(sb, customProperties.get(name));
			sb.append(",");
		}

		// remove the last extra comma if any
		if (customProperties.size() > 0 || element.getChildren().size() > 0
				|| element.getProperties().size() > 0) {
			sb.removeLast();
		}

		RenderUtils.bufferJSonEnd(sb);
	}

	private void bufferJSon(final IStructuredStringBuilder sb,
			final IElement element, final int index) {
		if (element instanceof IDataElement) {
			bufferJSan(sb, (IDataElement) element, index);
		} else {
			bufferJSonStart(sb, element, index);
			if (element.getChildren().size() > 0
					|| element.getProperties().size() > 0) {
				sb.removeLast();
			}
			RenderUtils.bufferJSonEnd(sb);
		}
	}

	private void bufferJSonProperties(final IStructuredStringBuilder sb,
			final Map<String, Object> properties) {
		Object val;
		for (final Object key : new TreeSet<Object>(properties.keySet())) {
			val = this.policy.format(key, properties.get(key));
			sb.textQuote(key).append(":").textQuote(val)
					.append(",");
		}
	}

	private void bufferJSonStart(final IStructuredStringBuilder sb,
			final IElement element, final int index) {
		sb.textQuote(RenderUtils.makeKey(element, index)).append(":")
				.quote(Quote.CurlyBracket);
		bufferJSonProperties(sb, element.getProperties());
		final List<IElement> children = RenderUtils
				.orderChildrenByZIndex(element);
		for (final IElement child : children) {
			bufferJSon(sb, child, 0);
			sb.append(",");
		}
	}

	private void bufferOneElement(final IStructuredStringBuilder sb,
			final IElement element) {
		sb.tagStart(element.getTagName());
		bufferXML(sb, element.getProperties());
		sb.contentStart();
		if (element.getInnerText() != null) {
			sb.append(element.getInnerText());
		}
		final List<IElement> children = RenderUtils
				.orderChildrenByZIndex(element);
		for (final IElement child : children) {
			bufferXML(sb, child);
		}
		sb.tagEnd(element.getTagName());
	}

	private void bufferXML(final IStructuredStringBuilder sb,
			final IElement element) {
		if (element instanceof IDataElement) {
			bufferDataElementXML(sb, (IDataElement) element);
		} else {
			bufferOneElement(sb, element);
		}
	}

	private void bufferXML(final IStructuredStringBuilder sb,
			final Map<String, Object> props) {
		Object val;
		for (final Object key : new TreeSet<Object>(props.keySet())) {
			if (!"innerText".equals(key)) {
				val = this.policy.format(key, props.get(key));
				sb.append(key).append('=')
						.textQuote(val).append(' ');
			}
		}
	}
}
