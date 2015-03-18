package org.javaq.chartfaces.document;

import java.util.Map;


/**
 * Abstraction of an {@link IElement} that can be used to represent a list of
 * {@link IElement}. Such a representation is intended to improve efficiency of
 * memory storage or data communication.
 * <p/>
 * 
 * A {@link IDataElement} is an extension of the {@link IElement} interface. It
 * adds a map of the so called custom properties. The value of each map entry
 * contains a {@link java.util.List} object which contains a list of data values. Each
 * value in the {@link java.util.List} will be applied to a different instance of
 * {@link IElement}, while the regular properties and children of this will be
 * simply repeated in those instances of {@link IElement}. It is apparent that
 * for an {@link IDataElement} to be valid, all the {@link java.util.List} objects in the
 * custom properties map must have the same size.
 * 
 * @author Hongyan Li
 * 
 */
public interface IDataElement extends IElement {
	/**
	 * Add a list of values that will be applied to the named property of the
	 * list of {@link IElement} that this is representing.
	 * 
	 * @param name
	 * @param values
	 */
	void addCustomProperty(String name, Iterable<?> values);

	/**
	 * @return the parent element.
	 */
	IContainerElement getContainerElement();

	/**
	 * @return a map that contains all the template data.
	 */
	Map<String, Iterable<?>> getCustomProperties();

	/**
	 * Removes a template property.
	 * 
	 * @param name
	 */
	void removeCustomProperty(String name);
}