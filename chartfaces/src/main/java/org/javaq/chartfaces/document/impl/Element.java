package org.javaq.chartfaces.document.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.javaq.chartfaces.constants.EnumPartZIndex;
import org.javaq.chartfaces.document.IElement;


/**
 * An abstraction of an arbitrary XML element. The tagName is required. An
 * Element can have any number of children Element objects. The properties of
 * the element are stored in the Properties of this. The toString()
 * method can be used to turn the Element into its xml String form. When turned
 * into xml, the properties are sorted by name alphabetically before been
 * rendered.
 * 
 * @author Hongyan Li
 * 
 */
public class Element implements IElement {
	private final List<IElement> children = new ArrayList<IElement>();
	private String innerText;
	private int ordinal;
	private final Map<String, Object> properties = new HashMap<String, Object>();
	private final String tagName;
	private String tagNameNS;

	/**
	 * Construct this from a given tagName.
	 * 
	 * @param tagName
	 *            the name of the tag. Cannot be null.
	 * @throws NullPointerException
	 *             if the passed in tagName is null.
	 */
	public static final Element newInstance(final String tagName) {
		Element returns = new Element(tagName);
		returns.setNameSpacePrefix("");
		return returns;
	}

	/**
	 * Construct this from a given tagName.
	 * 
	 * @param tagName
	 *            the name of the tag. Cannot be null.
	 * @throws NullPointerException
	 *             if the passed in tagName is null.
	 */
	protected Element(final String tagName) {
		if (tagName == null) {
			throw new IllegalArgumentException("The tagName is null!");
		}
		this.tagName = tagName;
	}

	/**
	 * For delegate subclass to initialize.
	 */
	protected Element() {
		// both will be shadowed by the delegatee
		this.tagName = null;
	}

	@Override
	public IElement addChildren(final IElement... children) {
		for (final IElement child : children) {
			this.children.add(child);
		}
		return this;
	}

	@Override
	public IElement addProperty(final String key, final Object value) {
		if ("innerText".equals(key)) {
			throw new UnsupportedOperationException(
					"Use setInnerText(String) to set innerText!");
		}
		if (key == null) {
			throw new IllegalArgumentException("Key can't be null!");
		}
		if (value == null || value.toString().trim().length() == 0) {
			removeProperties(key);
		} else {
			properties().put(key, value);
		}
		return this;
	}

	@Override
	public List<IElement> getChildren() {
		return Collections.unmodifiableList(this.children);
	}

	/**
	 * @return the innerText
	 */
	@Override
	public String getInnerText() {
		return this.innerText;
	}

	@Override
	public int getOrdinal() {
		return this.ordinal;
	}

	@Override
	public Map<String, Object> getProperties() {
		return Collections.unmodifiableMap(this.properties);
	}

	@Override
	public String getTagName() {
		return this.tagNameNS;
	}

	@Override
	public IElement removeChildren(final IElement... children) {
		for (final IElement child : children) {
			this.children.remove(child);
		}
		return this;
	}

	@Override
	public IElement removeProperties(final String... keys) {
		for (final String key : keys) {
			properties().remove(key);
		}
		return this;
	}

	/**
	 * @param innerText
	 *            the innerText to set
	 */
	@Override
	public IElement setInnerText(final String innerText) {
		this.innerText = innerText;
		if (innerText == null) {
			properties().remove("innerText");
		} else {
			properties().put("innerText", innerText);
		}
		return this;
	}

	@Override
	public void setNameSpacePrefix(final String nsPrefix) {
		final String prefix = nsPrefix == null ? "" : nsPrefix;
		this.tagNameNS = prefix + this.tagName;
		for (final IElement child : getChildren()) {
			child.setNameSpacePrefix(nsPrefix);
		}
	}

	public void setOrdinal(final EnumPartZIndex zIndex) {
		setOrdinal(zIndex.toIndex());
	}

	public void setOrdinal(final int zIndex) {
		this.ordinal = zIndex;
	}

	protected Map<String, Object> properties() {
		return this.properties;
	}
}
