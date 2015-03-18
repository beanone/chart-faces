package org.javaq.chartfaces.jdepend;

import java.io.IOException;
import java.util.Collection;

import jdepend.framework.JDepend;
import jdepend.framework.JavaPackage;
import junit.framework.Assert;
import junit.framework.TestCase;

import org.javaq.chartfaces.ChartFacesManager;
import org.javaq.chartfaces.api.IChartPart;
import org.javaq.chartfaces.comet.CometContext;
import org.javaq.chartfaces.component.impl.UIChart;
import org.javaq.chartfaces.converter.ValueScaleIterable;
import org.javaq.chartfaces.dataspec.RangeFinder;
import org.javaq.chartfaces.document.impl.Element;
import org.javaq.chartfaces.event.ChartLiveEvent;
import org.javaq.chartfaces.facelets.MethodRule;
import org.javaq.chartfaces.iterable.ArrayIterable;
import org.javaq.chartfaces.json.JSONString;
import org.javaq.chartfaces.part.axis.XAxis;
import org.javaq.chartfaces.part.component.ChartComponent;
import org.javaq.chartfaces.part.layout.impl.LayoutManager;
import org.javaq.chartfaces.part.legend.ChartLegend;
import org.javaq.chartfaces.render.BarChartRenderer;
import org.javaq.chartfaces.render.base.ChartRenderer;
import org.javaq.chartfaces.render.exporter.ExporterFactory;
import org.javaq.chartfaces.render.svg.RenderUtils;
import org.javaq.chartfaces.render.tool.impl.MainChartTool;
import org.javaq.chartfaces.renderkit.ChartFacesRenderKit;
import org.javaq.chartfaces.util.NumberRange;
import org.javaq.chartfaces.viewspec.impl.ChartSpecCalculator;


public class CycleTest extends TestCase {

	public static void main(final String[] args) {
		junit.textui.TestRunner.run(CycleTest.class);
	}

	private JDepend jdepend;

	public CycleTest(final String name) {
		super(name);
	}

	/**
	 * Tests that a package dependency cycle does not exist for any of the
	 * analyzed packages.
	 */
	public void testAllPackages() {

		final Collection<?> packages = this.jdepend.analyze();
		Assert.assertTrue(packages.size() > 0);
		for (final Object pObj : packages) {
			final JavaPackage pkg = (JavaPackage) pObj;
			Assert.assertEquals("Cycle exists: " + pkg.getName(), false,
					pkg.containsCycle());
		}
	}

	@Override
	protected void setUp() throws IOException {

		this.jdepend = new JDepend();
		final Class<?>[] classes = new Class<?>[] { ChartSpecCalculator.class,
				NumberRange.class, ChartFacesRenderKit.class,
				MainChartTool.class, RenderUtils.class, ExporterFactory.class,
				ChartRenderer.class, BarChartRenderer.class, ChartLegend.class,
				LayoutManager.class, ChartComponent.class, XAxis.class,
				JSONString.class, ArrayIterable.class, MethodRule.class,
				ChartLiveEvent.class, Element.class, RangeFinder.class,
				ValueScaleIterable.class, UIChart.class, CometContext.class,
				IChartPart.class, ChartFacesManager.class
					};

		System.out.println("Packages checked for circular dependency:");
		String packageName;
		for (final Class<?> clazz : classes) {
			packageName = clazz.getPackage().getName();
			System.out.println(packageName);
			if (this.jdepend.getPackage(packageName) == null) {
				this.jdepend.addPackage(new JavaPackage(packageName));
			}
		}
	}
}
