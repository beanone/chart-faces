package org.javaq.chartfaces.render.tool.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;

import org.javaq.chartfaces.api.Box;
import org.javaq.chartfaces.api.IChartComponent;
import org.javaq.chartfaces.api.IChartPart;
import org.javaq.chartfaces.api.ILegendHandler;
import org.javaq.chartfaces.api.IPartSpec;
import org.javaq.chartfaces.component.impl.ParserUtil;
import org.javaq.chartfaces.component.impl.UIChart;
import org.javaq.chartfaces.constants.Constants;
import org.javaq.chartfaces.constants.EnumPart;
import org.javaq.chartfaces.constants.EnumPartZIndex;
import org.javaq.chartfaces.constants.SVGConstants;
import org.javaq.chartfaces.document.IContainerElement;
import org.javaq.chartfaces.document.IDataElement;
import org.javaq.chartfaces.document.IElement;
import org.javaq.chartfaces.document.impl.AbstractChartDocument;
import org.javaq.chartfaces.document.impl.ContainerElement;
import org.javaq.chartfaces.document.impl.DelegateElement;
import org.javaq.chartfaces.document.impl.Element;
import org.javaq.chartfaces.part.layout.IPartCoordinator;
import org.javaq.chartfaces.render.tool.IChartTool;
import org.javaq.chartfaces.render.tool.IChartToolFactory;


/**
 * A IChartBoxTool for the {@link UIChart} component. All {@link IChartTool}s
 * are implemented as a composite and this {@link MainChartTool} is a
 * special composite that acts at the head of all.
 * 
 * @author Hongyan Li
 * 
 */
public class MainChartTool extends AbstractChartTool {
	private static final class MaskedElement extends DelegateElement {
		private final IElement innerElement;

		private MaskedElement(IElement delegate, IElement innerElement) {
			super(delegate);
			this.innerElement = innerElement;
		}

		@Override
		public IElement addChildren(final IElement... children) {
			innerElement.addChildren(children);
			return this;
		}
	}

	private int chartComponentCount;
	private IContainerElement chartingAreaContainer;
	private final List<IDataElement> chartingAreaDataList = new ArrayList<IDataElement>();
	private String height;
	private final Map<IChartComponent, ILegendHandler> legendHandlerMap = new HashMap<IChartComponent, ILegendHandler>();
	private BoxModel outerBoxModel;

	private String width;

	MainChartTool(final IChartToolFactory factory, final IChartPart part,
			final Map<Object, Object> state) {
		super(factory, part, state);
	}

	public String getChartingAreaContainerId() {
		return getDataElementContainerId() + "main";
	}

	/**
	 * @return the height
	 */
	public String getHeight() {
		return this.height;
	}

	/**
	 * @return the width
	 */
	public String getWidth() {
		return this.width;
	}

	@Override
	public void synchToolState(final Map<Object, Object> state) {
		// no state variable to initialize
	}

	private void addBorder(final IElement element) {
		final int borderWidth = ParserUtil.getBorderWidth(getChart());
		if (borderWidth > 0) {
			final IElement border = Element.newInstance(SVGConstants.SVG_G_NS);
			border.addProperty(SVGConstants.SVG_STYLE, getChart()
					.getBorderStyle());
			border.addProperty(SVGConstants.SVG_SHAPE_RENDERING,
					SVGConstants.SVG_CRISP_EDGES);
			final IElement borderRect =
					Element.newInstance(SVGConstants.SVG_RECT_NS);
			borderRect.addProperty(SVGConstants.SVG_X, "0");
			borderRect.addProperty(SVGConstants.SVG_Y, "0");
			borderRect.addProperty(SVGConstants.SVG_WIDTH, ""
					+ getOuterBoxModel().getWidth());
			borderRect.addProperty(SVGConstants.SVG_HEIGHT, ""
					+ getOuterBoxModel().getHeight());
			border.addChildren(borderRect);
			element.addChildren(border);
		}
	}

	private IContainerElement createChartingAreaContainer() {
		final ContainerElement element =
				ContainerElement.newInstance(SVGConstants.SVG_G_NS,
						getChartingAreaContainerId());
		final Box chartingAreaBox = getChartingAreaViewBox();
		element.addProperty(SVGConstants.SVG_X,
				"" + chartingAreaBox.getOriginX());
		element.addProperty(SVGConstants.SVG_Y,
				"" + chartingAreaBox.getOriginY());
		element.addProperty(SVGConstants.SVG_WIDTH, ""
				+ chartingAreaBox.getWidth());
		element.addProperty(SVGConstants.SVG_HEIGHT, ""
				+ chartingAreaBox.getHeight());
		final String transformString = new StringBuilder()
				.append(SVGConstants.SVG_TRANSLATE).append("(")
				.append(chartingAreaBox.getOriginX()).append(",")
				.append(chartingAreaBox.getOriginY()).append(")")
				.toString();
		element.addProperty(SVGConstants.SVG_TRANSFORM,
				transformString);
		element.setOrdinal(EnumPartZIndex.datapoint);
		return element;
	}

	private IElement createInnerElement() {
		final IElement element = ChartToolUtil.createLayoutBox(getBoxModel(), null);;
		element.addChildren(getChartingAreaContainer());
		// final IElement header = Element.newInstance(SVG_G);
		// element.addChildren(header);
		// header.addProperties(SVG_ID, HEADER_ID);
		// header.addProperty(SVG_STYLE, getChart().getHeaderStyle());
		return element;
	}

	private BoxModel getOuterBoxModel() {
		return this.outerBoxModel;
	}

	private void makeOuterBoxModel() {
		final IPartSpec partSpecs = getPartSpecs();
		final Box viewBox = partSpecs.getViewBox();
		setOuterBoxModel(new BoxModel(viewBox));
	}

	private void setOuterBoxModel(final BoxModel outerBoxModel) {
		this.outerBoxModel = outerBoxModel;
	}

	@Override
	protected void addDataElementContainer(final IElement element) {
		// do nothing
	}

	/**
	 * Registers the passed in component with the {@link ILegendHandler}.
	 * 
	 * @param chartComponent the {@link IChartComponent} the registered legend handler is for.
	 * @param handler a {@link ILegendHandler}
	 */
	protected void registerLegendHandler(IChartComponent chartComponent, final ILegendHandler handler) {
		this.legendHandlerMap.put(chartComponent, handler);
	}

	@Override
	protected List<IDataElement> createDataElementList() {
		return null;
	}

	@Override
	protected IElement createLayoutElement() {
		final IElement innerElement = createInnerElement();
		final IElement outerElement = Element.newInstance(SVGConstants.SVG_SVG_NS);
		outerElement.addProperty(SVGConstants.SVG_WIDTH, getWidth());
		outerElement.addProperty(SVGConstants.SVG_HEIGHT, getHeight());
		outerElement.addProperty(SVGConstants.SVG_VIEWBOX,
				getOuterBoxModel().getViewBoxString());
		outerElement.addProperty(SVGConstants.SVG_XML_NS,
				SVGConstants.W3_SVG_URL);
		outerElement.addProperty(SVGConstants.SVG_XMLNS_XLINK,
				SVGConstants.W3_XLINK_URL);
		outerElement.addProperty(SVGConstants.SVG_XMLNS_EV,
				SVGConstants.W3_SVG_EV_URL);
		outerElement.addChildren(innerElement);
		addBorder(outerElement);
		// simply masking the addChildren() method so it adds children to the
		// innerElement instead of the outerElement.
		return new MaskedElement(outerElement, innerElement);
	}

	@Override
	protected void doProcess() throws IOException {
		final UIChart chart = getChart();

		// coordination among the parts of the chart so that we can be ready to
		// box them in later.
		final IPartCoordinator<IChartPart> coordinator = getCoordinator();
		coordinator.register(chart);
		coordinator.execute();

		final IPartSpec specs = chart.getPartSpec();
		this.width = specs.getProperties().get(Constants.WIDTH);
		this.height = specs.getProperties().get(Constants.HEIGHT);

		// We must init first - create the BoxModel and the IChartTool
		initBoxModel((BoxModel) null);

		// now process the parts to box them in one at a time
		for (final IChartTool childTool : getChildrenTools()) {
			childTool.process();
		}

		final Box chartingAreaViewBox = coordinator.getChartingAreaViewBox();
		updateState(AbstractChartTool.CHARTING_AREA_VIEW_BOX, chartingAreaViewBox);

		final int ox = chart.getLeftPadding() + chartingAreaViewBox.getOriginX();
		final int oy = chart.getTopPadding() + chartingAreaViewBox.getOriginY();
		final Box box = new Box(ox, oy, chartingAreaViewBox.getWidth(), chartingAreaViewBox.getHeight());
		chart.setChartingAreaBounds(box);
	}

	/**
	 * @return the chartComponentCount
	 */
	protected int getChartComponentCount() {
		return this.chartComponentCount;
	}

	/**
	 * @return the chartingAreaContainer
	 */
	protected IContainerElement getChartingAreaContainer() {
		if (this.chartingAreaContainer == null) {
			this.chartingAreaContainer = createChartingAreaContainer();
		}
		return this.chartingAreaContainer;
	}

	protected List<IDataElement> getChartingAreaDataList() {
		return this.chartingAreaDataList;
	}

	@Override
	protected ChartDocumentType getDocumentType() {
		return ChartDocumentType.chart;
	}

	/**
	 * Retrieves the {@link ILegendHandler} for the passed in component index.
	 * @param chartComponent an {@link IChartComponent}.
	 * 
	 * @return the {@link ILegendHandler} of the passed in {@link IChartComponent}.
	 */
	protected ILegendHandler getLegendHandler(IChartComponent chartComponent) {
		return this.legendHandlerMap.get(chartComponent);
	}

	@Override
	protected void initBoxModel(final BoxModel parentBoxModel)
			throws IOException {
		super.initBoxModel(parentBoxModel);
		setBoxModel(makeBoxModel(null));
		final UIChart chart = getChart();
		final List<UIComponent> children = chart.getChildren();
		AbstractChartTool childTool = null;
		this.chartComponentCount = 0;
		for (final UIComponent child : children) {
			if (child instanceof IChartPart) {
				IChartPart childPart = (IChartPart) child;
				// TODO: map part to tool
				// TODO: provide method to get tool for part
				// TODO: create legend in the part tool since only the part tool knows about the scale
				childTool = (AbstractChartTool) getTool(childPart);
				if (childTool != null) {
					if (childPart.getPartType().getBaseType() == EnumPart.component) {
						this.chartComponentCount++;
						// we want to process components first
						insertChildTool(childTool);
					} else {
						appendChildTool(childTool);
					}
					childTool.initBoxModel(getBoxModel());
				}
			} else {
				// TODO what to do with none IChartPart children???
				
			}
		}
	}

	@Override
	protected boolean isValid(final IChartPart part) {
		return part.getPartType()==EnumPart.chart;
	}

	@Override
	protected BoxModel makeBoxModel(final BoxModel parentBoxModel) {
		final UIChart chart = getChart();
		makeOuterBoxModel();
		return getOuterBoxModel().newChild(chart.getLeftPadding(),
				chart.getRightPadding(), chart.getTopPadding(),
				chart.getBottomPadding());
	}

	@Override
	protected void prepareData() {
		// nothing to prepare for in this.
	}

	// TODO: Is there a better way to do this?
	@Override
	protected AbstractChartDocument recursiveCreateDocument() {
		synchToolState();
		if (!isProcessed()) {
			throw new IllegalStateException(
					"Must call process() before getting the chart document!");
		}
		final AbstractChartDocument chartDoc = createDocument();
		List<IDataElement> dataElementList;
		IElement layoutElement;

		for (final IChartTool tool : getChildrenTools()) {
			layoutElement = tool.getChartDocument().getLayoutElement();
			if (layoutElement != null) {
				chartDoc.addLayoutElements(layoutElement);
			}
			dataElementList = tool.getChartDocument().getDataElementList();
			if (dataElementList != null) {
				chartDoc.addDataElementList(dataElementList);
			}
		}

		chartDoc.addDataElementList(getChartingAreaDataList());

		return chartDoc;
	}
}