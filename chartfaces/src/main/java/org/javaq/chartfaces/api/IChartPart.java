package org.javaq.chartfaces.api;

import java.util.List;

import javax.faces.component.UIComponent;

import org.javaq.chartfaces.constants.EnumPart;
import org.javaq.chartfaces.constants.EnumPosition;


/**
 * Abstraction of a part of a chart such as the legends, the axes, and the
 * labels, etc.. The actual implementation is most probably an UIComponent. Note
 * that an {@link IChartPart} can be given a {@link IPartSpec} which defines the
 * dimension of the part in both the user space (user coordinate system) and the
 * graphing space (view-port coordinate system).
 *
 * @author Hongyan Li
 * @since 1.0
 */
public interface IChartPart {
	/**
	 * @return a String that hints for the position of the part.
	 * @see EnumPosition
	 */
	EnumPosition getEnumPosition();

	/**
	 * Note: The pixel or point units only apply to the whole Chart not the
	 * parts and thus only the numeric value of this attribute will be used by
	 * the internal parts of the chart as the pixel value in the view box
	 * coordinate system. Such an approach is important to guarantee the proper
	 * behavior when the chart is resized.
	 *
	 * @return the height of this part.
	 */
	String getHeight();

	/**
	 * @return a String that represents the name of the part. This and the name
	 *         of the result of {@link #getPartType()} may be used to uniquely
	 *         identify the part within the parent chart component
	 */
	String getName();

	/**
	 * @return the padding of the part inside the view port. Default to the
	 *         padding in default charting setting.
	 */
	int getPadding();

	/**
	 * A key that uniquely identifies this.
	 *
	 * @return the unique key that identifies the chart part.
	 */
	String getPartKey();

	/**
	 * @return the part dimension specification.
	 */
	IPartSpec getPartSpec();

	/**
	 * @return a String that represents the type of the part. This and the name
	 *         of the result of {@link #getName()} may be used to uniquely
	 *         identify the part within the parent chart component.
	 */
	EnumPart getPartType();

	/**
	 * @return the style of this part.
	 */
	String getStyle();

	/**
	 * @return the id that uniquely identifies the SVG container element that
	 *         holds the to be dynamically created elements.
	 */
	String getSvgContainerId();

	/**
	 * @return an Object that contains data for the part.
	 */
	Object getValue();

	/**
	 * Note: The pixel or point units only apply to the whole Chart not the
	 * parts and thus only the numeric value of this attribute will be used by
	 * the internal parts of the chart as the pixel value in the view box
	 * coordinate system. Such an approach is important to guarantee the proper
	 * behavior when the chart is resized.
	 *
	 * @return the width of this part.
	 */
	String getWidth();

	/**
	 * @return the x coordinate of the part. This part must be absolutely
	 *         positioned along the x-axis if this is set.
	 */
	int getX();

	/**
	 * @return the y coordinate of the part. This part must be absolutely
	 *         positioned along the y-axis if this is set.
	 */
	int getY();

	/**
	 * @return the anchor's x coordinator. The anchor is the corner of the
	 * box that is closest to the charting area.
	 */
	int getAnchorX();

	/**
	 * @return the anchor's y coordinator. The anchor is the corner of the
	 * box that is closest to the charting area.
	 */
	int getAnchorY();

	/**
	 * @param id
	 *            sets the svg container id.
	 */
	void setSvgContainerId(String id);

	/**
	 * Retrieves the parent IChartPart.
	 *
	 * @return the parent IChartPart.
	 */
	IChartPart getParentPart();

	/**
	 * Retrieves the children of this IChartPart.
	 *
	 * @return a list of {@link UIComponent}.
	 */
	List<UIComponent> getChildren();
}