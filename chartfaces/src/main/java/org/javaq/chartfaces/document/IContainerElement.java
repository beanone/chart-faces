package org.javaq.chartfaces.document;


/**
 * Abstraction of an IElement that can container other elements. An
 * {@link IContainerElement} must have an Id attribute for the children to
 * reference to.
 * 
 * @author Hongyan Li
 * 
 */
public interface IContainerElement extends IElement {

	/**
	 * @return the id of the element.
	 */
	String getId();
}
