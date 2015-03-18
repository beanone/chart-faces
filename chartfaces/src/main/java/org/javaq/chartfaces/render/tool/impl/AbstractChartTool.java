package org.javaq.chartfaces.render.tool.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;

import org.javaq.chartfaces.api.Box;
import org.javaq.chartfaces.api.IChartAxis;
import org.javaq.chartfaces.api.IChartComponent;
import org.javaq.chartfaces.api.IChartPart;
import org.javaq.chartfaces.api.IPartSpec;
import org.javaq.chartfaces.component.impl.UIChart;
import org.javaq.chartfaces.constants.SVGConstants;
import org.javaq.chartfaces.document.IChartDocument;
import org.javaq.chartfaces.document.IContainerElement;
import org.javaq.chartfaces.document.IDataElement;
import org.javaq.chartfaces.document.IElement;
import org.javaq.chartfaces.document.impl.AbstractChartDocument;
import org.javaq.chartfaces.document.impl.ContainerElement;
import org.javaq.chartfaces.document.impl.Element;
import org.javaq.chartfaces.iterable.ISizeAwareIterable;
import org.javaq.chartfaces.part.layout.IPartCoordinator;
import org.javaq.chartfaces.render.tool.IChartTool;
import org.javaq.chartfaces.render.tool.IChartToolFactory;
import org.javaq.chartfaces.util.NumberRange;


/**
 * Abstract implementation of the IChartBoxTool. Implemented as a composite.
 * 
 * @author Hongyan Li
 * 
 */
public abstract class AbstractChartTool implements IChartTool, SVGConstants {
	private static final class ContainerIdCounter {
		private static int count;
		
		private ContainerIdCounter() {}

		private static int nextValue() {
			return (ContainerIdCounter.count++);
		}
	}

	protected static final String BOX = "Box";
	protected static final String CHARTING_AREA_VIEW_BOX = "charting area view box";
	protected static final String COMPUTED_LABEL_COORD_SHIFT = "computed shift when rotating labels";
	protected static final String X_LABEL_COORDS = "computed x label coordinates";
	protected static final String Y_LABEL_COORDS = "computed y label coordinates";
	private BoxModel boxModel;
	private final IChartPart chartPart;
	private final List<IChartTool> childrenTools = new ArrayList<IChartTool>();
	private IPartCoordinator<IChartPart> coordinator;
	private IContainerElement dataElementContainer;
	private final String dataElementContainerId;
	private final IChartToolFactory factory;
	private final int ordinal;
	private AbstractChartTool parent;
	private boolean processed;
	private AbstractChartDocument document;
	private final Map<Object, Object> state;

	protected AbstractChartTool(final IChartToolFactory factory, final IChartPart part,
			final Map<Object, Object> state) {
		if (!isValid(part)) {
			throw new IllegalArgumentException(
					"Incorrect sub-type of IChartPart!");
		}
		this.factory = factory;
		this.chartPart = part;
		this.state = state;
		this.ordinal = ContainerIdCounter.nextValue();
		this.dataElementContainerId =
				getDocumentType().toString() + this.ordinal;
	}

	@Override
	public final IChartDocument getChartDocument() {
		if (this.document == null) {
			this.document = recursiveCreateDocument();
		}
		return this.document;
	}

	@Override
	public final void process()
			throws IOException {
		prepareData();
		doProcess();
		setProcessed(true);
	}

	@Override
	public final void setCoordinator(final IPartCoordinator<IChartPart> coord) {
		this.coordinator = coord;
	}

	@Override
	public final void setParent(final IChartTool parent) {
		this.parent = (AbstractChartTool) parent;
	}

	private NumberRange getRangeInX() {
		final IChartPart part = getChartPart();
		NumberRange range = null;
		switch (part.getPartType().getBaseType()) {
		case xaxis:
			range = ((IChartAxis) part).getRange();
			break;
		case component:
			range = ((IChartComponent) part).getXRange();
			break;
		default:
			break;
		}
		if (range != null && range.getMin() == null) {
			range = null;
		}
		return range;
	}

	private NumberRange getRangeInY() {
		final IChartPart part = getChartPart();
		NumberRange range = null;
		switch (part.getPartType().getBaseType()) {
		case yaxis:
			range = ((IChartAxis) part).getRange();
			break;
		case component:
			range = ((IChartComponent) part).getYRange();
			break;
		default:
			break;
		}
		if (range != null && range.getMin() == null) {
			range = null;
		}
		return range;
	}

	/**
	 * @return the {@link Map} that holds the state pertains to the rendering
	 *         request scope.
	 */
	private Map<Object, Object> getState() {
		return this.state;
	}

	private void setProcessed(final boolean processed) {
		this.processed = processed;
	}

	protected void addDataElementContainer(final IElement element) {
		// the node that will hold the data elements to be generated on the fly
		element.addChildren(getDataElementContainer());
	}

	/**
	 * Adds a child {@link IChartTool} to this.
	 * 
	 * @param child
	 */
	protected final void appendChildTool(final IChartTool child) {
		getChildrenTools().add(child);
		child.setParent(this);
	}

	protected final String createContainerId() {
		final String id = this.dataElementContainerId;
		getChartPart().setSvgContainerId(id);
		return id;
	}

	protected abstract List<IDataElement> createDataElementList();

	/**
	 * Makes the {@link IChartDocument} from this. NO children
	 * {@link IChartTool} is involved here!
	 * 
	 * @return the chart document object.
	 */
	protected AbstractChartDocument createDocument() {
		final IElement layoutElement = createLayoutElement();

		if (hasContainerElementForData() && layoutElement != null) {
			addDataElementContainer(layoutElement);
		}

		// creates the chart document
		final AbstractChartDocument chartDoc = new AbstractChartDocument() {
			@Override
			public IElement getLayoutElement() {
				return layoutElement;
			}
		};
		return chartDoc;
	}

	protected IElement createLayoutElement() {
		return Element.newInstance(SVGConstants.SVG_G_NS);
	}

	/**
	 * @throws IOException if there is any error.
	 */
	protected void doProcess() throws IOException {
		// empty implementation as the default behavior
	}

	protected final BoxModel getBoxModel() {
		return this.boxModel;
	}

	protected final UIChart getChart() {
		UIComponent part = (UIComponent) getChartPart();
		while (!(part instanceof UIChart)) {
			part = part.getParent();
		}

		return (UIChart) part;
	}

	protected final Box getChartingAreaViewBox() {
		return (Box) getState().get(AbstractChartTool.CHARTING_AREA_VIEW_BOX);
	}

	/**
	 * @return the chartPart
	 */
	protected final IChartPart getChartPart() {
		return this.chartPart;
	}

	protected final MainChartTool getMainChartTool() {
		AbstractChartTool p = getParent();
		while (!(p instanceof MainChartTool) && p != null) {
			p = p.getParent();
		}
		return (MainChartTool) p;
	}

	/**
	 * @return the childrenTools
	 */
	protected final List<IChartTool> getChildrenTools() {
		return this.childrenTools;
	}

	protected final IPartCoordinator<IChartPart> getCoordinator() {
		// TODO: simplify
		IPartCoordinator<IChartPart> coord = this.coordinator;
		if (coord == null && this.parent != null) {
			coord = this.parent.getCoordinator();
		}

		return coord;
	}

	protected final IContainerElement getDataElementContainer() {
		if (hasContainerElementForData() && this.dataElementContainer == null) {
			this.dataElementContainer = ContainerElement.newInstance(
					SVGConstants.SVG_G_NS, getDataElementContainerId());
		}
		return this.dataElementContainer;
	}

	/**
	 * @return the id of the container node that contains the dynamically
	 *         generated SVG elements.
	 */
	protected final String getDataElementContainerId() {
		String id = getChartPart().getSvgContainerId();
		if (id == null) {
			id = createContainerId();
		}
		return id;
	}

	protected abstract ChartDocumentType getDocumentType();

	/**
	 * @return the factory
	 */
	protected final IChartToolFactory getFactory() {
		return this.factory;
	}

	/**
	 * @return the ordinal
	 */
	protected final int getOrdinal() {
		return this.ordinal;
	}

	/**
	 * @return the parent {@link IChartTool} that contains this.
	 */
	protected final AbstractChartTool getParent() {
		return this.parent;
	}

	/**
	 * @return the PartSpecs for this part.
	 */
	protected final IPartSpec getPartSpecs() {
		return getChartPart().getPartSpec();
	}

	protected final IChartTool getTool(final IChartPart part)
			throws IOException {
		return getFactory().getTool(part, getState());
	}

	protected final Box getViewBox() {
		return getChartPart().getPartSpec().getViewBox();
	}

	protected boolean hasContainerElementForData() {
		return true;
	}

	protected void initBoxModel(final BoxModel parentBoxModel)
			throws IOException {
		setBoxModel(makeBoxModel(parentBoxModel));
	}

	/**
	 * Adds a child {@link IChartTool} to this.
	 * 
	 * @param child
	 */
	protected final void insertChildTool(final IChartTool child) {
		getChildrenTools().add(0, child);
		child.setParent(this);
	}

	/**
	 * @return the processed
	 */
	protected final boolean isProcessed() {
		return this.processed;
	}

	/**
	 * Validates that passed in {@link IChartPart} is the correct type.
	 * 
	 * @param part
	 * @return true if the passed in is the expected subtype of {@link IChartPart},
	 *         false otherwise.
	 */
	protected abstract boolean isValid(IChartPart part);

	protected BoxModel makeBoxModel(final BoxModel parentBoxModel) {
		final IPartSpec partSpecs = getPartSpecs();
		final Box viewBox = partSpecs.getViewBox();
		return parentBoxModel.newChild(viewBox);
	}

	/**
	 * Implement this to prepare the charting data - mainly scaling and
	 * translating to the local coordinates in the viewBox so that it is ready
	 * for document generation.
	 */
	protected abstract void prepareData();

	protected AbstractChartDocument recursiveCreateDocument() {
		synchToolState();
		if (!isProcessed()) {
			throw new IllegalStateException(
					"Must call process() before getting the chart document!");
		}

		final AbstractChartDocument chartDoc = createDocument();
		// add the data for the dynamically generated elements
		chartDoc.addDataElementList(createDataElementList());
		return chartDoc;
	}

	protected final ISizeAwareIterable<Double> scaleX(
			final Iterable<? extends Number> values) {
		final Box viewBox = getPartSpecs().getViewBox();
		final NumberRange range = getRangeInX();
		double scale = range.getMax() - range.getMin();
		scale = scale == 0 ? 1 : scale;
		final double factor = viewBox.getWidth() / scale;
		return ChartToolUtil.scaleTranslate(values, factor, 0, range.getMin());
	}

	protected final ISizeAwareIterable<Double> scaleY(
			final Iterable<? extends Number> values) {
		final Box viewBox = getPartSpecs().getViewBox();
		final NumberRange range = getRangeInY();
		double scale = range.getMax() - range.getMin();
		scale = scale == 0 ? 1 : scale;
		final double factor = viewBox.getHeight() / scale;
		return ChartToolUtil.scaleTranslate(values, factor, 0, range.getMin());
	}

	protected final void setBoxModel(final BoxModel boxModel) {
		this.boxModel = boxModel;
	}

	/**
	 * Certain quantities are calculated and saved as the state of the tools as
	 * we go through the process of making the pieces of the document. This
	 * provides a single place whether the latest state is updated to this.
	 */
	protected final void synchToolState() {
		synchToolState(Collections.unmodifiableMap(getState()));
	}

	/**
	 * Reads the request scope rendering state object from the passed in
	 * {@link Map} and saves them for later use in rendering.
	 * 
	 * @param state
	 *            a {@link Map} that contains all the intermediary request scope
	 *            rendering state data.
	 */
	protected abstract void synchToolState(Map<Object, Object> state);

	protected final void updateState(final Object key, final Object value) {
		getState().put(key, value);
	}
}