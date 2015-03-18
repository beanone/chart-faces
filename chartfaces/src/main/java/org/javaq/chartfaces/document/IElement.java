package org.javaq.chartfaces.document;

import java.util.List;
import java.util.Map;

/**
 * Abstraction of a XML document object that has a tagName, a list of children
 * objects and a list of properties.
 * 
 * @author Hongyan Li
 * 
 */
public interface IElement {
	/**
	 * Adds the passed in children Element to this as children.
	 * 
	 * @param children
	 *            Elements to be added as children of this. Cannot be null.
	 * @return this so that method calls can be chained.
	 * @throws NullPointerException
	 *             if the passed in is null.
	 */
	IElement addChildren(IElement... children);

	/**
	 * Adds a single property key/value pair to this.
	 * 
	 * @param key
	 *            a property key. Cann't be null.
	 * @param value
	 *            a property value. If null, property is removed.
	 * @return this so that method calls can be chained.
	 * @throws NullPointerException
	 *             if the passed in key is null.
	 */
	IElement addProperty(String key, Object value);

	/**
	 * @return the list of children of this.
	 */
	List<IElement> getChildren();

	/**
	 * @return the inner text of the element.
	 */
	String getInnerText();

	/**
	 * An integer hint of the element order within its parent.
	 * 
	 * @return the ordinal of this.
	 */
	int getOrdinal();

	/**
	 * @return the map of properties of this.
	 */
	Map<String, Object> getProperties();

	/**
	 * @return the tagName.
	 */
	String getTagName();

	/**
	 * Removes the passed in children form this.
	 * 
	 * @param children
	 *            an array of Elements. Cannot be null.
	 * @return this so that method calls can be chained.
	 * @throws NullPointerException
	 *             if the passed in is null.
	 */
	IElement removeChildren(IElement... children);

	/**
	 * Removes properties from this.
	 * 
	 * @param keys
	 *            an array of keys. Cannot be null.
	 * @return this so that method calls can be chained.
	 * @throws NullPointerException
	 *             if the passed in is null.
	 */
	IElement removeProperties(String... keys);

	/**
	 * @param innerText
	 *            the inner text of this element.
	 * @return this so that method calls can be chained.
	 */
	IElement setInnerText(String innerText);

	/**
	 * Sets the namespace prefix to this.
	 * 
	 * @param nsPrefix
	 */
	void setNameSpacePrefix(String nsPrefix);
}