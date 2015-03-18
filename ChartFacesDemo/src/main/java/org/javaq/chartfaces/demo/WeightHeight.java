package org.javaq.chartfaces.demo;

import java.util.HashMap;
import java.util.Map;

import org.javaq.chartfaces.DefaultChartSettings;
import org.javaq.chartfaces.IChartSettings;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("weightHeight")
@Scope("request")
public class WeightHeight {
	private final double[][] weightHeights = new double[][] {
			{ 1.47, 1.50, 1.52, 1.55, 1.57, 1.60, 1.63, 1.65, 1.68, 1.70, 1.73,
					1.75, 1.78, 1.80, 1.83 },
			{ 52.21, 53.12, 54.48, 55.84, 57.20, 58.57, 59.93, 61.29, 63.11,
					64.47, 66.28, 68.10, 69.92, 72.19, 74.46 } };
	private final int[] sizes = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11,
			12, 13, 14, 15 };
	private final Map<String, Object> propertyMap = new HashMap<String, Object>();

	/**
	 * @return the propertyMap
	 */
	public Map<String, Object> getPropertyMap() {
		return propertyMap;
	}

	public WeightHeight() {
		final String[] colors = new String[15];
		propertyMap.put("fill", colors);
		int alpha = 0xff;
		int red, green, blue;
		for (int i = 0; i < colors.length; i++) {
			red = (int) (255. * i / colors.length);
			blue = (int) (255. * (15 - i) / colors.length);
			green = (int) (255. * Math.abs(i - 7) / (colors.length / 2 + 1));
			colors[i] = '#' + Integer.toString(
					Math.abs(alpha << 24 | red << 16 | green << 8 | blue), 16);
		}
	}

	/**
	 * @return the sizes
	 */
	public int[] getSizes() {
		return sizes;
	}

	private final double[] heightLabelValues = { 1.5, 1.6, 1.7, 1.8 };
	private final double[] weightLabelValues = { 50, 55, 60, 65, 70, 75 };
	private final double heightMin = 1.42;
	private final double heightMax = 1.88;
	private final double weightMin = 50;
	private final double weightMax = 77;
	private final String heightCaption = "Height(m)";
	private final String weightCaption = "Weight(kg)";

	public String getHeader() {
		return "Weight vs. Height";
	}

	public String getFooter() {
		return "My footer for weight and height, etc.";
	}

	public double[][] getRevertedWeightHeights() {
		double[][] wh = getWeightHeights();
		return new double[][] { revert(wh[0]), wh[1] };
	}

	/**
	 * @return the heights
	 */
	public double[][] getWeightHeights() {
		return weightHeights;
	}

	/**
	 * @return the heightLabelValues
	 */
	public double[] getHeightLabelValues() {
		return heightLabelValues;
	}

	/**
	 * @return the weightLabelValues
	 */
	public double[] getWeightLabelValues() {
		return weightLabelValues;
	}

	/**
	 * @return the heightMin
	 */
	public double getHeightMin() {
		return heightMin;
	}

	/**
	 * @return the heightMax
	 */
	public double getHeightMax() {
		return heightMax;
	}

	/**
	 * @return the weightMin
	 */
	public double getWeightMin() {
		return weightMin;
	}

	/**
	 * @return the weightMax
	 */
	public double getWeightMax() {
		return weightMax;
	}

	/**
	 * @return the heightCaption
	 */
	public String getHeightCaption() {
		return heightCaption;
	}

	/**
	 * @return the weightCaption
	 */
	public String getWeightCaption() {
		return weightCaption;
	}

	private double[] revert(double[] array) {
		int len = array.length;
		double[] returns = new double[len];
		for (int i = 0; i < len; i++) {
			returns[len - i - 1] = array[i];
		}

		return returns;
	}

	public IChartSettings getChartSettings() {
		return new DefaultChartSettings() {
			@Override
			public String getCaptionStyle() {
				return "font-family:Arial;font-size:40px;font-weight:bold;text-anchor:middle";
			}

			@Override
			public int getPadding() {
				return 40;
			}
		};
	}
}
