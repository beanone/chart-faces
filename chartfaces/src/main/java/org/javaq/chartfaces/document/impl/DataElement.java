package org.javaq.chartfaces.document.impl;

import java.util.Collections;
import java.util.Hashtable;
import java.util.Map;

import org.javaq.chartfaces.document.IContainerElement;
import org.javaq.chartfaces.document.IDataElement;
import org.javaq.chartfaces.iterable.IterableUtility;


/**
 * An IElement that represents a list of IElements. The
 * attributes that are common to all IElements are still called
 * properties, but the attributes that are specific to each individual
 * IElement are called custom properties.
 * 
 * @author Hongyan Li
 * 
 */
public class DataElement extends Element implements IDataElement {
	private final IContainerElement containerElement;
	private Integer count;
	private final Map<String, Iterable<?>> customProperties = new Hashtable<String, Iterable<?>>();

	public static final DataElement newInstance(final String tagName, final IContainerElement container) {
		DataElement returns = new DataElement(tagName, container);
		returns.setNameSpacePrefix("");
		return returns;
	}
	
	protected DataElement(final String tagName, final IContainerElement container) {
		super(tagName);
		if (container == null) {
			throw new IllegalArgumentException("The passed in container object is null.");
		}
		this.containerElement = container;
	}

	@Override
	public void addCustomProperty(final String name, final Iterable<?> values) {
		if (this.count == null) {
			this.count = IterableUtility.getSize(values);
		} else if (this.count != IterableUtility.getSize(values)) {
			throw new IllegalArgumentException(
					"Element count different from existing custom properties!");
		}
		customProperties().put(name, values);
	}

	/**
	 * @return the containerElement
	 */
	@Override
	public IContainerElement getContainerElement() {
		return this.containerElement;
	}

	@Override
	public Map<String, Iterable<?>> getCustomProperties() {
		return Collections.unmodifiableMap(this.customProperties);
	}

	@Override
	public void removeCustomProperty(final String name) {
		customProperties().remove(name);
	}

	private Map<String, Iterable<?>> customProperties() {
		return this.customProperties;
	}
}