package org.javaq.chartfaces.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.event.ActionEvent;

import org.javaq.chartfaces.event.ChartPanEvent;
import org.javaq.chartfaces.event.ChartResetEvent;
import org.javaq.chartfaces.event.ChartWheelEvent;
import org.javaq.chartfaces.event.ChartZoomEvent;
import org.javaq.chartfaces.util.NumberUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("mandelbrot")
@Scope("session")
public class Mandelbrot {

	private static final double DEFAULT_XMIN = -2.0;
	private static final double DEFAULT_XMAX = 1.0;
	private static final double DEFAULT_YMIN = -1.5;
	private static final double DEFAULT_YMAX = 1.5;
	private static final double[] DEFAULT_XTICK_VALUES = new double[] { -2.0,
			-1.5, -1.0, -0.5, 0, 0.5, 1.0 };
	private static final double[] DEFAULT_YTICK_VALUES = new double[] { -1.5,
			-1.0, -0.5, 0, 0.5, 1.0, 1.5 };
	protected static final int CANVAS_SIZE = 1080;
	private static final int X_WIDTH = 432;
	private static final int Y_WIDTH = X_WIDTH;
	private static final int TICKER_COUNT = 6;
	private final List<List<Double>> values = new ArrayList<List<Double>>();
	protected final List<String> colors = new ArrayList<String>();
	protected final Map<String, Object> propertyMap = new HashMap<String, Object>();
	protected double xmin = DEFAULT_XMIN;
	protected double xmax = DEFAULT_XMAX;
	private double ymin = DEFAULT_YMIN;
	private double ymax = DEFAULT_YMAX;
	private double[] xvalues = DEFAULT_XTICK_VALUES;
	private double[] yvalues = DEFAULT_YTICK_VALUES;
	private static int COLOR_MAX = 255;
	private boolean initialized;
	private String imageType = "svg";
	private long lastZoomTime;

	public boolean getCancel() {
		return colors.size() < 10;
	}

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

	public Mandelbrot() {
		propertyMap.clear();
		propertyMap.put("fill", colors);
	}

	public void reset(ChartResetEvent event) {
		xmin = DEFAULT_XMIN;
		xmax = DEFAULT_XMAX;
		ymin = DEFAULT_YMIN;
		ymax = DEFAULT_YMAX;
		xvalues = DEFAULT_XTICK_VALUES;
		yvalues = DEFAULT_YTICK_VALUES;
		clear();
	}

	public double getScale() {
		return CANVAS_SIZE / (xmax - xmin);
	}

	public double getSize() {
		return CANVAS_SIZE / X_WIDTH;
	}

	protected void clear() {
		values.clear();
		colors.clear();
		initialized = false;
	}

	protected void checkInit() {
		if (!initialized) {
			init();
			initialized = true;
		}
	}

	protected void init() {
		PixelMap pixels = generatePixelMap();
		generateColors(pixels, null, null);
	}

	public List<List<Double>> getValues() {
		checkInit();
		return values;
	}

	public Map<String, Object> getPropertyMap() {
		checkInit();
		return propertyMap;
	}

	protected final PixelMap generatePixelMap() {
		return getColorMap(mapMandelbrot());
	}

	public void generateColors(PixelMap map, List<Integer> sizesInt,
			List<Double> sizes) {
		int greenMin = getGreenMin();
		values.clear();
		final List<Double> xCoords = new ArrayList<Double>();
		final List<Double> yCoords = new ArrayList<Double>();
		filterColors(map, sizesInt, sizes, greenMin, xCoords, yCoords);
		values.add(xCoords);
		values.add(yCoords);

		System.out.println("greenMin=" + greenMin + "\nrecord count: "
				+ xCoords.size());
	}

	protected int getGreenMin() {
		return 20;
	}

	/**
	 * Filters the colors - throw away some so that the client won't run out of
	 * memory.
	 *
	 * @param map
	 * @param sizesInt
	 * @param sizes
	 * @param greenMin
	 * @param xCoords
	 * @param yCoords
	 */
	public void filterColors(PixelMap map, List<Integer> sizesInt,
			List<Double> sizes, int greenMin, final List<Double> xCoords,
			final List<Double> yCoords) {
		xCoords.clear();
		yCoords.clear();
		colors.clear();
		if (sizes != null) {
			sizes.clear();
		}
		double deltaX = getDeltaX();
		double deltaY = getDeltaY();
		List<Integer> xcoords = map.getXcoords();
		List<Integer> ycoords = map.getYcoords();
		List<Integer> colorsInt = map.getValues();

		int color, iteration, xcoord, ycoord, size = 1;
		Iterator<Integer> xcoordsIter = xcoords.iterator();
		Iterator<Integer> ycoordsIter = ycoords.iterator();
		Iterator<Integer> colorsIter = colorsInt.iterator();
		Iterator<Integer> sizesIter = (sizesInt == null) ? null : sizesInt
				.iterator();

		if (sizesInt != null) {
			sizes.clear();
		}

		int alpha = 0xff;
		int red = 0xff;
		int green = 0xff;
		int blue = 0xff;
		red = 100;
		blue = 100;
		// 1. we don't want black color and that is why the upper limit is 240
		// 2. we subtract the greenMin since we want to have bigger color range
		double colorScale = 240. / (findMax(colorsInt) - greenMin);
		while (colorsIter.hasNext()) {
			iteration = colorsIter.next();
			xcoord = xcoordsIter.next();
			ycoord = ycoordsIter.next();
			if (sizesInt != null) {
				size = sizesIter.next();
			}
			green = (int) Math.floor((iteration - greenMin) * colorScale);
			if (iteration <= COLOR_MAX && iteration > greenMin && iteration > 0) {
				color = createColorValue(alpha, red, green, blue);
				xCoords.add(xcoord * deltaX + xmin + size * deltaX / 2.);
				yCoords.add(ycoord * deltaY + ymin + size * deltaY / 2.);
				colors.add('#' + Integer.toHexString(color));
				if (sizesInt != null) {
					sizes.add(1.0 * (size + 1.) * deltaX);
				}
			}
		}
	}

	protected PixelMap getColorMap(int[][] iterations) {
		return PixelMap.generate(iterations);
	}

	protected int[][] mapMandelbrot() {
		double x0 = 0.0;
		double y0 = 0.0;
		double x = 0.0;
		double y = 0.0;
		double deltaX = getDeltaX();
		double deltaY = getDeltaY();
		final int[][] iterations = new int[Y_WIDTH][X_WIDTH];
		double x1, y1;
		for (int i = 0; i < X_WIDTH; i++) {
			x0 = xmin + deltaX * (i + 1);
			y0 = ymin;
			for (int j = 0; j < Y_WIDTH; j++) {
				y0 += deltaY;
				x = 0.0;
				y = 0.0;
				int iteration = 0;
				while ((x * x + y * y <= 4) && (iteration != COLOR_MAX)) {
					x1 = xmapMandelbrot(x, y, x0);
					y1 = ymapMandelbrot(x, y, y0);
					x = x1;
					y = y1;
					iteration++;
				}
				iterations[j][i] = iteration;
			}
		}
		return iterations;
	}

	private double getDeltaY() {
		return (1.0 * (ymax - ymin) / Y_WIDTH);
	}

	private double getDeltaX() {
		return (1.0 * (xmax - xmin) / X_WIDTH);
	}

	private int findMax(List<Integer> original) {
		int returns = 0;
		if (original != null) {
			List<Integer> copy = new ArrayList<Integer>(original);
			Collections.sort(copy);
			returns = copy.get(copy.size() - 1);
		}
		return returns;
	}

	public void zoom(ChartZoomEvent event) {
		double xLeftPct = event.getStartX();
		double xRightPct = event.getEndX();
		double yTopPct = event.getStartY();
		double yBottomPct = event.getEndY();

		double newXMin = (getXmax() - getXmin()) * xLeftPct + getXmin();
		double newYMax = (getYmax() - getYmin()) * yTopPct + getYmin();
		double newXMax = (getXmax() - getXmin()) * xRightPct + getXmin();
		double newYMin = (getYmax() - getYmin()) * yBottomPct + getYmin();
		if (newYMax - newYMin > newXMax - newXMin) {
			newXMax = newXMin + (newYMax - newYMin);
		} else {
			newYMin = newYMax - (newXMax - newXMin);
		}

		xvalues = createTickValues(newXMin, newXMax, TICKER_COUNT);
		yvalues = createTickValues(newYMin, newYMax, TICKER_COUNT);

		xmin = newXMin;
		ymin = newYMin;
		xmax = newXMax;
		ymax = newYMax;

		clear();
	}

	public void zoom2(ChartWheelEvent event) {
		long currentZoomTime = System.currentTimeMillis();
		if(currentZoomTime-lastZoomTime > 3000) {
			double xCenterPct = event.getCenterX();
			double yCenterPct = event.getCenterY();
			double delta = event.getDelta();
			final double xRange = getXmax() - getXmin();
			final double yRange = getYmax() - getYmin();

			final double newXCenter = xRange * xCenterPct + getXmin();
			final double newYCenter = yRange * yCenterPct + getYmin();
			// just zoom in and out at the center ignore the requested center
			// final double newXCenter = xRange * .5 + getXmin();
			// final double newYCenter = yRange * .5 + getYmin();

			// negative for zoom-in, positive, out, and 0, no zooming
			final double zoomingFactor = Math.pow(2, delta/30);
			final double dx = zoomingFactor * xRange / 2.;
			final double newXMin = newXCenter - dx;
			final double newXMax = newXCenter + dx;
			final double dy = zoomingFactor * yRange / 2.;
			final double newYMin = newYCenter - dy;
			final double newYMax = newYCenter + dy;

			xvalues = createTickValues(newXMin, newXMax, 5);
			yvalues = createTickValues(newYMin, newYMax, 5);

			xmin = newXMin;
			ymin = newYMin;
			xmax = newXMax;
			ymax = newYMax;

			clear();
		}
		this.lastZoomTime = currentZoomTime;
	}

	public void move(ChartPanEvent event) {
		String dir = event.getDirection();
		if (dir != null && dir.length() > 0) {
			move(dir);
		}
		double dx = -event.getDx() * (getXmax() - getXmin());
		double dy = -event.getDy() * (getYmax() - getYmin());
		final double newXMin = xmin + dx;
		final double newXMax = xmax + dx;
		final double newYMin = ymin + dy;
		final double newYMax = ymax + dy;

		xvalues = createTickValues(newXMin, newXMax, 5);
		yvalues = createTickValues(newYMin, newYMax, 5);

		xmin = newXMin;
		ymin = newYMin;
		xmax = newXMax;
		ymax = newYMax;

		clear();
	}

	private void move(String direction) {
		if ("up".equals(direction)) {
			moveUp();
		} else if ("down".equals(direction)) {
			moveDown();
		} else if ("left".equals(direction)) {
			moveLeft();
		} else if ("right".equals(direction)) {
			moveRight();
		}
	}

	public void moveUp() {
		final double delta = (ymax - ymin) / 10.;
		ymin -= delta;
		ymax -= delta;
		yvalues = createTickValues(ymin, ymax, 5);
		clear();
	}

	public void moveDown() {
		final double delta = (ymax - ymin) / 10.;
		ymin += delta;
		ymax += delta;
		yvalues = createTickValues(ymin, ymax, 5);
		clear();
	}

	public void moveLeft() {
		final double delta = (xmax - xmin) / 10.;
		xmin += delta;
		xmax += delta;
		xvalues = createTickValues(xmin, xmax, 5);
		clear();
	}

	public void moveRight() {
		final double delta = (xmax - xmin) / 10.;
		xmin -= delta;
		xmax -= delta;
		xvalues = createTickValues(xmin, xmax, 5);
		clear();
	}

	protected double[] createTickValues(double min, double max, int count) {
		if (count < 3) {
			count = 3;
		}
		double range = max - min;
		double unitEstimate = range / count;
		int unitDigit = NumberUtils.getMostSignificantDigit(unitEstimate);
		int unitIndex = NumberUtils.getMostSignificantIndex(unitEstimate);
		int startIndex = NumberUtils.getMostSignificantIndex(range);
		List<Double> ticks = new ArrayList<Double>();
		double dv = unitDigit * Math.pow(10, unitIndex);
		double startValue = NumberUtils.truncate(min, startIndex);
		while (startValue > min) {
			startValue -= dv;
		}
		for (double v = startValue; v <= max; v += dv) {
			if (v >= min) {
				ticks.add(v);
			}
		}

		double[] returns = new double[ticks.size()];
		for (int i = 0; i < ticks.size(); i++) {
			returns[i] = NumberUtils.truncate(ticks.get(i), unitIndex);
		}
		return returns;
	}

	private double xmapMandelbrot(double x, double y, double a) {
		return x * x - y * y + a;
	}

	private double ymapMandelbrot(double x, double y, double b) {
		return 2.0 * x * y + b;
	}

	private int createColorValue(int alpha, int red, int green, int blue) {
		if (isValidColorComponent(alpha) && isValidColorComponent(red)
				&& isValidColorComponent(green) && isValidColorComponent(blue)) {
			return Math.abs(alpha << 24 | red << 16 | green << 8 | blue);
		} else {
			throw new IllegalArgumentException(
					"Color code values must be in between 0 and 255!");
		}
	}

	private boolean isValidColorComponent(int value) {
		return isInRange(value, 0, 255);
	}

	private boolean isInRange(int value, int min, int max) {
		return value >= min && value <= max;
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