package org.javaq.chartfaces.demo;

import java.io.Serializable;

import org.javaq.chartfaces.DefaultChartSettings;
import org.javaq.chartfaces.IChartSettings;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("carbon")
@Scope("request")
public class Carbon implements Serializable {
	private static final long serialVersionUID = 662217750531529837L;

	private final String[] countryLabels = new String[] { "Indonesia", "Korea",
			"Brazil", "Australia", "Mexico", "S Africa", "Canada", "India",
			"UK", "Japan", "Germany", "China", "US" };

	private final double[] populations = new double[] { 243.0, 48.6, 201.1,
			21.5, 112.5, 49.1, 33.8, 1173.1, 62.3, 126.8, 82.3, 1330.1, 310.2 };

	private final int[] populationLabelAmounts = new int[] { 0, 200, 400, 600,
			800, 1000, 1200, 1400 };

	private final int[] emissionLabelAmounts = new int[] { 0, 10000, 20000,
			30000, 40000, 50000 };

	private final int[] emissionValues = new int[] { 600, 1100, 1700, 2200,
			2220, 3000, 3800, 4400, 8300, 8900, 12190, 17200, 51500 };

	public String getEmissionCaption() {
		return "Million tons of carbon";
	}

	public String getHeader() {
		return "Cumulative carbon emissions, 1950 - 1996";
	}

	/**
	 * @return the countryLabels
	 */
	public String[] getCountryLabels() {
		return countryLabels;
	}

	/**
	 * @return the emissionLabelAmounts
	 */
	public int[] getEmissionLabelAmounts() {
		return emissionLabelAmounts;
	}

	public double[] getPopulations() {
		return populations;
	}

	/**
	 * @return the emissionValues
	 */
	public int[] getEmissionValues() {
		return emissionValues;
	}

	public IChartSettings getChartSettings() {
		return new DefaultChartSettings() {
			@Override
			public String getCaptionStyle() {
				return "font-family:Arial;font-size:47px;font-weight:bold;text-anchor:middle";
			}

			@Override
			public int getPadding() {
				return 40;
			}
		};
	}

	public int[] getPopulationLabelAmounts() {
		return populationLabelAmounts;
	}
}
