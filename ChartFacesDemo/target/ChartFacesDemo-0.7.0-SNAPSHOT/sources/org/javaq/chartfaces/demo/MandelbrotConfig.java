package org.javaq.chartfaces.demo;

import javax.faces.event.ActionEvent;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("mandelbrot_tt")
@Scope("session")
public class MandelbrotConfig {

	static final double DEFAULT_XMIN = -2.0;
	static final double DEFAULT_XMAX = 1.0;
	static final double DEFAULT_YMIN = -1.5;
	static final double DEFAULT_YMAX = 1.5;
	static final double[] DEFAULT_XTICK_VALUES = new double[] { -2.0, -1.5,
			-1.0, -0.5, 0, 0.5, 1.0 };
	static final double[] DEFAULT_YTICK_VALUES = new double[] { -1.5, -1.0,
			-0.5, 0, 0.5, 1.0, 1.5 };
	static final int CANVAS_SIZE = 1080;
	double xmin = DEFAULT_XMIN;
	double xmax = DEFAULT_XMAX;
	private double ymin = DEFAULT_YMIN;
	private double ymax = DEFAULT_YMAX;
	private double[] xvalues = DEFAULT_XTICK_VALUES;
	private double[] yvalues = DEFAULT_YTICK_VALUES;
	private String imageType = "svg";

	/**
	 * @return the imageType
	 */
	public String getImageType() {
		return imageType;
	}

	/**
	 * @param imageType
	 *            the imageType to set
	 */
	public void setImageType(String imageType) {
		this.imageType = imageType;
	}

	public void updateImageType(ActionEvent event) {
	}

	/**
	 * @return the xmin
	 */
	public double getXmin() {
		return xmin;
	}

	/**
	 * @return the xmax
	 */
	public double getXmax() {
		return xmax;
	}

	/**
	 * @return the ymin
	 */
	public double getYmin() {
		return ymin;
	}

	/**
	 * @return the ymax
	 */
	public double getYmax() {
		return ymax;
	}

	/**
	 * @return the xValues
	 */
	public double[] getXvalues() {
		return xvalues;
	}

	/**
	 * @return the yValues
	 */
	public double[] getYvalues() {
		return yvalues;
	}

}