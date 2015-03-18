package org.javaq.chartfaces.render.svg;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.javaq.chartfaces.document.IDataElement;
import org.javaq.chartfaces.document.IElement;


/**
 * An {@link Iterator} wrapper around the DataElement to help iterate
 * through all the {@link IElement}s the wrapped DelegateElement
 * represents.
 * <p/>
 * Note: the wrapped {@link IDataElement} is altered after wrapping it with
 * this. This is, in theory, not good. But since we are not going to reuse the
 * {@link IDataElement} for other purposes afterwards, I believe that it is OK.
 * If, one day, when there is a use-case for reusing the {@link IDataElement}
 * afterwards, we need to change the implementation to make a deep-clone of the
 * {@link IDataElement} and only alter the clone.
 * 
 * @author Hongyan Li
 * 
 */
public class ElementIterator implements Iterator<IElement> {
	private final IDataElement dataElement;
	private final Iterator<?> firstIter;
	private final Map<String, Iterator<?>> itersMap = new HashMap<String, Iterator<?>>();

	/**
	 * Constructs this from a DelegateElement.
	 * 
	 * @param dataElement
	 */
	public ElementIterator(final IDataElement dataElement) {
		this.dataElement = dataElement;
		final Map<String, Iterable<?>> iterablesMap =
				dataElement.getCustomProperties();

		if (iterablesMap.size() > 0) {
			Entry<String, Iterable<?>> firstEntry = null;
			for (final Entry<String, Iterable<?>> entry : iterablesMap
					.entrySet()) {
				if (firstEntry == null) {
					firstEntry = entry;
				}
				this.itersMap.put(entry.getKey(), entry.getValue().iterator());
			}
			this.firstIter = firstEntry.getValue().iterator();
		} else {
			this.firstIter = null;
		}
	}

	@Override
	public boolean hasNext() {
		return this.firstIter != null && this.firstIter.hasNext();
	}

	@Override
	public IElement next() {
		String key, value;
		for (final Entry<String, Iterator<?>> entry : this.itersMap.entrySet()) {
			key = entry.getKey();
			value = entry.getValue().next().toString();
			if ("innerText".equals(key)) {
				this.dataElement.setInnerText(value);
			} else {
				this.dataElement.addProperty(key, value);
			}
		}
		this.firstIter.next();
		return this.dataElement;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
