package org.javaq.chartfaces.render.tool.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.javaq.chartfaces.api.Box;

/**
 * Abstraction of a model for boxes within boxes that keeps the aspect ratio.
 * Such a model makes it easier to render graphics content in a box because the
 * coordinates will be local to the boxes and thus contents will be rendered
 * within bounds naturally. And since the aspect ratios are preserved, it is
 * easy to work with shapes, and any coordinate transformation should be done
 * outside of the box.
 * 
 * @author Hongyan Li
 * 
 */
public class BoxModel {
	private final Box box;
	private final List<BoxModel> children = new ArrayList<BoxModel>();
	private BoxModel parent;

	/**
	 * Constructs a new instance of this from a {@link Box}.
	 * 
	 * @param box
	 */
	public BoxModel(final Box box) {
		this.box = box;
	}

	/**
	 * Constructs a new instance of this from a width and height value.
	 * 
	 * @param w
	 *            the width of the box. Must be greater than 0.
	 * @param h
	 *            the height of the box. Must be greater than 0.
	 */
	public BoxModel(final int w, final int h) {
		this(0, 0, w, h);
	}

	private BoxModel(final int ox, final int oy, final int w, final int h) {
		this(new Box(ox, oy, w, h));
	}

	/**
	 * @return the children {@link BoxModel} objects as an unmodifiable list.
	 */
	public List<BoxModel> getChildren() {
		return Collections.unmodifiableList(this.children);
	}

	/**
	 * @return height of the box.
	 */
	public int getHeight() {
		return this.box.getHeight();
	}

	/**
	 * @return x-coordinate of the origin (top-left corner) of the box (inside
	 *         the parent box).
	 */
	public int getOriginX() {
		return this.box.getOriginX();
	}

	/**
	 * @return y-coordinate of the origin (top-left corner) of the box (inside
	 *         the parent box).
	 */
	public int getOriginY() {
		return this.box.getOriginY();
	}

	/**
	 * @return the parent {@link BoxModel}
	 */
	public BoxModel getParent() {
		return this.parent;
	}

	/**
	 * Creates a viewBox string corresponding to the box dimensions in this.
	 * 
	 * @return a viewBox string in the form "0 0 width height".
	 */
	public String getViewBoxString() {
		final StringBuilder sb = new StringBuilder().append("0 0 ")
				.append(this.box.getWidth()).append(' ')
				.append(this.box.getHeight());
		return sb.toString();
	}

	/**
	 * @return width of the box.
	 */
	public int getWidth() {
		return this.box.getWidth();
	}

	/**
	 * Makes a new child {@link BoxModel} from the passed in. If the passed in
	 * result is bigger than the box in this, null will be returned..
	 * 
	 * @param childBox
	 *            a child box that will be placed inside the box in this.
	 * @return a new {@link BoxModel} or null if the passed in child box is too
	 *         big to fit in.
	 */
	public BoxModel newChild(final Box childBox) {
		if (childBox.getOriginX() + childBox.getWidth() > this.box.getWidth() ||
				childBox.getOriginY() + childBox.getHeight() > this.box.getHeight()) {
			// the child box is too big to fit in
			throw new IllegalArgumentException(
					"The child box is too big to fit in!");
		}

		final BoxModel child = new BoxModel(childBox);
		this.children.add(child);
		child.parent = this;
		return child;
	}

	/**
	 * Makes a new child {@link BoxModel} from the passed in. If the passed in
	 * results in 0 or negative width or height for the child box, null will be
	 * returned.
	 * 
	 * @param leftMargin
	 *            the distance between the left of the new box and the left of
	 *            the box in this.
	 * @param rightMargin
	 *            the distance between the right of the new box and the right of
	 *            the box in this.
	 * @param topMargin
	 *            the distance between the top of the new box and the top of the
	 *            box in this.
	 * @param bottomMargin
	 *            the distance between the bottom of the new box and the bottom
	 *            of the box in this.
	 * @return a new {@link BoxModel} or null if the dimensions given result in
	 *         a box that does not have width or height.
	 * @throws IllegalArgumentException
	 *             if any of the passed in is negative.
	 */
	public BoxModel newChild(final int leftMargin, final int rightMargin, final int topMargin,
			final int bottomMargin) {
		if (leftMargin < 0 || rightMargin < 0 || topMargin < 0
				|| bottomMargin < 0) {
			throw new IllegalArgumentException(
					"Margin values must all be 0 or a positive number!");
		}
		final BoxModel child = makeModel(this.box.getOriginX() + leftMargin,
				this.box.getOriginY() + topMargin,
				this.box.getWidth() - leftMargin - rightMargin,
				this.box.getHeight() - topMargin - bottomMargin);
		if (child != null) {
			this.children.add(child);
			child.parent = this;
		}
		return child;
	}

	private BoxModel makeModel(final int ox, final int oy, final int w, final int h) {
		if (ox < 0 || oy < 0 || w <= 0 || h <= 0) {
			return null;
		}
		return new BoxModel(ox, oy, w, h);
	}
}
