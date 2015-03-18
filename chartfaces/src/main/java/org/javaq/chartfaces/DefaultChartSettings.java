package org.javaq.chartfaces;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

import org.javaq.chartfaces.api.IChartAxis;
import org.javaq.chartfaces.api.IChartingDataConverter;
import org.javaq.chartfaces.constants.EnumLayout;
import org.javaq.chartfaces.constants.EnumPart;
import org.javaq.chartfaces.constants.EnumPosition;
import org.javaq.chartfaces.constants.EnumSVG;
import org.javaq.chartfaces.constants.EnumSymbol;
import org.javaq.chartfaces.constants.EnumTickDirection;
import org.javaq.chartfaces.constants.SVGConstants;
import org.javaq.chartfaces.iterable.IIterableConverter;
import org.javaq.chartfaces.iterable.ISizeAwareIterable;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


/**
 * The defaults of the various charting settings. The user can include his own
 * default settings by overriding this and bind the overriding bean to the chart
 * tag
 *
 * @author Hongyan Li
 *
 */
@Component("defaultChartSettings")
@Scope("singleton")
public class DefaultChartSettings implements IChartSettings {
	private static final Logger LOGGER = Logger
			.getLogger(DefaultChartSettings.class.getName());
	private static final String SETTINGS_FILE = "settings.properties";
	private static final int DEFAULT_OFFSET = 5;
	private static final int DEFAULT_SLACK = 5;

	private final Properties settings;

	public DefaultChartSettings() {
		InputStream inStream = ClassLoader
				.getSystemResourceAsStream(SETTINGS_FILE);
		settings = new Properties();
		if (inStream != null) {
			try {
				settings.load(inStream);
			} catch (IOException e) {
				// Should never happen but warn just in case
				LOGGER.warning("Failed to load settings.properties from the classpath: \n"
						+ e.toString());
			}
		}
	}

	@Override
	public IIterableConverter<IChartAxis, Object, ISizeAwareIterable<Object>> getAxisLabelAccessor() {
		return ChartFacesManager.getInstance().getDefaultAxisLabelAccessor();
	}

	@Override
	public String getAxisLineStyle() {
		return getSetting("axisLineStyle",
				"fill:none;stroke:#000000;stroke-width:4px;");
	}

	@Override
	public double getAxisOffsetPercent() {
		return getSettingInt("axisOffset", DEFAULT_OFFSET);
	}

	@Override
	public double getAxisSlackPercent() {
		return getSettingInt("axisSlack", DEFAULT_SLACK);
	}

	@Override
	public IIterableConverter<IChartAxis, Double, ISizeAwareIterable<Double>> getAxisValueAccessor() {
		return ChartFacesManager.getInstance().getDefaultAxisValueAccessor();
	}

	@Override
	public String getBorderStyle() {
		return getSetting("borderStyle",
				"stroke-width:5px;stroke:#000000;fill:none;");
	}

	@Override
	public String getCaptionStyle() {
		return getSetting("captionStyle", "font-family:Arial;font-size:47px;");
	}

	@Override
	public String getChartHeight() {
		return getSetting("chartHeight", "324px");
	}

	@Override
	public String getChartLineStyle() {
		return getSetting("chartLineStyle",
				"fill:none;stroke:#000000;stroke-width:1px");
	}

	@Override
	public String getChartWidth() {
		return getSetting("chartWidth", "432px");
	}

	@Override
	public IChartingDataConverter getDataConverter() {
		return ChartFacesManager.getInstance().getDefaultDataConverter();
	}

	@Override
	public String getFooterStyle() {
		return getSetting("footerStyle",
				"fill:#008000;font-family:Arial;font-size:30px;");
	}

	@Override
	public String getFooterTextAnchorDefault() {
		return getSetting("footerTextAnchorDefault", SVGConstants.SVG_START);
	}

	@Override
	public int getGridLineDensity() {
		return getSettingInt("gridLineDensity", 0);
	}

	@Override
	public String getGridLineStyle() {
		return getSetting("gridLineStyle",
				"fill:none;stroke:#C0C0C0;stroke-width:3px;stroke-opacity:0.7;");
	}

	@Override
	public String getHeaderStyle() {
		return getSetting(
				"headerStyle",
				"font-family:Times;font-weight:bold;fill:#8B0000;font-size:45px;");
	}

	@Override
	public String getHeaderTextAnchorDefault() {
		return getSetting("headerTextAnchorDefault", EnumSVG.middle.toString());
	}

	@Override
	public int getInternalPadding() {
		return getSettingInt("internalPadding", 5);
	}

	@Override
	public String getLabelStyle() {
		return getSetting("labelStyle", "font-family:Arial;font-size:37px;");
	}

	@Override
	public int getLabelTickSpacing() {
		return getSettingInt("labelTickSpacing", 5);
	}

	@Override
	public int getLegendHeight() {
		return getSettingInt("legendHeight", 130);
	}

	@Override
	public EnumLayout getLegendLayout() {
		return null;
	}

	@Override
	public EnumPosition getLegendPosition() {
		return EnumPosition.top;
	}

	@Override
	public int getLegendWidth() {
		return getSettingInt("legendWidth", 370);
	}

	@Override
	public int getMargin() {
		return getSettingInt("margin", 15);
	}

	@Override
	public int getPadding() {
		return getSettingInt("padding", 30);
	}

	@Override
	public int getRefreshInterval() {
		return getSettingInt("refreshInterval", 200);
	}

	@Override
	public EnumSymbol getScatterSymbol() {
		return EnumSymbol.circle;
	}

	@Override
	public String getScatterSymbolFill() {
		return null;
	}

	@Override
	public double getScatterSymbolOpacity() {
		return getSettingDouble("scatterSymbolOpacity", 1.0);
	}

	@Override
	public int getScatterSymbolSize() {
		return getSettingInt("scatterSymbolSize", 10);
	}

	@Override
	public int getScatterSymbolStrokeWidth() {
		return getSettingInt("scatterSymbolStrokeWidth", 0);
	}

	@Override
	public EnumTickDirection getTickDirection(final EnumPart partType,
			final EnumPosition partPosition) {
		EnumTickDirection direction = null;
		switch (partType) {
		case xaxis:
			switch (partPosition) {
			case bottom:
				direction = EnumTickDirection.x_bottom;
				break;
			case top:
				direction = EnumTickDirection.x_top;
				break;
			default:
				direction = EnumTickDirection.middle;
				break;
			}
			break;
		case yaxis:
			switch (partPosition) {
			case left:
				direction = EnumTickDirection.y_left;
				break;
			case right:
				direction = EnumTickDirection.y_right;
				break;
			default:
				direction = EnumTickDirection.middle;
				break;
			}
			break;
		case radial:
			direction = EnumTickDirection.radial_clockwise;
			break;
		case angular:
			direction = EnumTickDirection.angular_inward;
			break;
		default:
			direction = EnumTickDirection.middle;
			break;
		}
		return direction;
	}

	@Override
	public int getTickHeight() {
		return 18;
	}

	@Override
	public String getTickStyle() {
		return getSetting("tickStyle",
				"fill:none;stroke:#000000;stroke-width:2px");
	}

	@Override
	public int getXAxisHeight() {
		return getSettingInt("xAxisHeight", 150);
	}

	@Override
	public EnumPosition getXAxisPosition() {
		return EnumPosition.bottom;
	}

	@Override
	public EnumPosition getYAxisPosition() {
		return EnumPosition.left;
	}

	@Override
	public int getYAxisWidth() {
		return getSettingInt("yAxisWidth", 240);
	}

	private String getSetting(String key, String defaultVal) {
		String val = settings.getProperty(key);
		return val == null ? defaultVal : val;
	}

	private int getSettingInt(String key, int defaultVal) {
		String val = getSetting(key, null);
		return val == null ? defaultVal : Integer.parseInt(val);
	}

	private double getSettingDouble(String key, double defaultVal) {
		String val = getSetting(key, null);
		return val == null ? defaultVal : Double.parseDouble(val);
	}
}
