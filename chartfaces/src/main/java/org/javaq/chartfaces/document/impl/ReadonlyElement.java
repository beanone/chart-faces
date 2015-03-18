package org.javaq.chartfaces.document.impl;

import java.util.Collections;
import java.util.List;

import org.javaq.chartfaces.document.IElement;


/**
 * A readonly wrapper on an IElement.
 * 
 * @author Hongyan Li
 * 
 */
public class ReadonlyElement extends DelegateElement {
	public ReadonlyElement(final IElement delegate) {
		super(delegate);
	}

	/**
	 * @param children
	 * @see org.javaq.chartfaces.document.IElement#addChildren(org.javaq.chartfaces.document.IElement[])
	 */
	@Override
	public IElement addChildren(final IElement... children) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @param key
	 * @param value
	 * @see org.javaq.chartfaces.document.IElement#addProperty(java.lang.String,
	 *      java.lang.Object)
	 */
	@Override
	public IElement addProperty(final String key, final Object value) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @return the children elements of this.
	 * @see org.javaq.chartfaces.document.IElement#getChildren()
	 */
	@Override
	public List<IElement> getChildren() {
		return Collections.unmodifiableList(super.getChildren());
	}

	/**
	 * @param children
	 * @see org.javaq.chartfaces.document.IElement#removeChildren(org.javaq.chartfaces.document.IElement[])
	 */
	@Override
	public IElement removeChildren(final IElement... children) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @param keys
	 * @see org.javaq.chartfaces.document.IElement#removeProperties(java.lang.String[])
	 */
	@Override
	public IElement removeProperties(final String... keys) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @param innerText
	 * @see org.javaq.chartfaces.document.IElement#setInnerText(java.lang.String)
	 */
	@Override
	public IElement setInnerText(final String innerText) {
		throw new UnsupportedOperationException();
	}
}
