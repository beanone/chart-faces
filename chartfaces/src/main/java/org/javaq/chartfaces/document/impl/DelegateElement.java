package org.javaq.chartfaces.document.impl;

import java.util.List;
import java.util.Map;

import org.javaq.chartfaces.document.IElement;


/**
 * A delegate of an {@link IElement}.
 * 
 * @author Hongyan Li
 * 
 */
public abstract class DelegateElement implements IElement {
	private final IElement delegate;

	public DelegateElement(final IElement delegate) {
		this.delegate = delegate;
	}

	/**
	 * @param children
	 * @see org.javaq.chartfaces.document.IElement#addChildren(org.javaq.chartfaces.document.IElement[])
	 */
	@Override
	public IElement addChildren(final IElement... children) {
		this.delegate.addChildren(children);
		return this;
	}

	/**
	 * @param key
	 * @param value
	 * @see org.javaq.chartfaces.document.IElement#addProperty(java.lang.String,
	 *      java.lang.Object)
	 */
	@Override
	public IElement addProperty(final String key, final Object value) {
		this.delegate.addProperty(key, value);
		return this;
	}

	/**
	 * @return a list of children elements of this.
	 * @see org.javaq.chartfaces.document.IElement#getChildren()
	 */
	@Override
	public List<IElement> getChildren() {
		return this.delegate.getChildren();
	}

	/**
	 * @return the innerText of this element.
	 * @see org.javaq.chartfaces.document.IElement#getInnerText()
	 */
	@Override
	public String getInnerText() {
		return this.delegate.getInnerText();
	}

	/**
	 * @return the ordinal of this.
	 */
	@Override
	public int getOrdinal() {
		return this.delegate.getOrdinal();
	}

	/**
	 * @return the properties of this as a map.
	 * @see org.javaq.chartfaces.document.IElement#getProperties()
	 */
	@Override
	public Map<String, Object> getProperties() {
		return this.delegate.getProperties();
	}

	/**
	 * @return the tag name of this.
	 * @see org.javaq.chartfaces.document.IElement#getTagName()
	 */
	@Override
	public String getTagName() {
		return this.delegate.getTagName();
	}

	/**
	 * @param children
	 * @return this element itself so that calls can be chained easily.
	 * @see org.javaq.chartfaces.document.IElement#removeChildren(org.javaq.chartfaces.document.IElement[])
	 */
	@Override
	public IElement removeChildren(final IElement... children) {
		this.delegate.removeChildren(children);
		return this;
	}

	/**
	 * @param keys
	 * @return this element itself so that calls can be chained easily.
	 * @see org.javaq.chartfaces.document.IElement#removeProperties(java.lang.String[])
	 */
	@Override
	public IElement removeProperties(final String... keys) {
		this.delegate.removeProperties(keys);
		return this;
	}

	/**
	 * @param innerText
	 * @return this element itself so that calls can be chained easily.
	 * @see org.javaq.chartfaces.document.IElement#setInnerText(java.lang.String)
	 */
	@Override
	public IElement setInnerText(final String innerText) {
		this.delegate.setInnerText(innerText);
		return this;
	}

	@Override
	public void setNameSpacePrefix(final String nsPrefix) {
		this.delegate.setNameSpacePrefix(nsPrefix);
	}
}