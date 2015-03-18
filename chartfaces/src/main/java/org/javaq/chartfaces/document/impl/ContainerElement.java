package org.javaq.chartfaces.document.impl;

import org.javaq.chartfaces.document.IContainerElement;
import org.javaq.chartfaces.document.IElement;

public final class ContainerElement extends Element implements IContainerElement {
	private final String id;

	public static ContainerElement newInstance(final String tagName, final String id) {
		ContainerElement returns = new ContainerElement(tagName, id);
		returns.setNameSpacePrefix("");
		return returns;
	}

	private ContainerElement(final String tagName, final String id) {
		super(tagName);
		if (id == null) {
			throw new IllegalArgumentException("The passed in id is null.");
		}
		this.id = id;
		super.properties().put("id", id);
	}

	@Override
	public IElement addProperty(final String key, final Object value) {
		if ("id".equals(key)) {
			throw new IllegalArgumentException("ID must be set with setId()!");
		}
		return super.addProperty(key, value);
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public IElement removeProperties(final String... keys) {
		for (final String key : keys) {
			if ("id".equals(key)) {
				throw new IllegalArgumentException(
						"ID property can't be removed!");
			}
		}
		return super.removeProperties(keys);
	}
}
