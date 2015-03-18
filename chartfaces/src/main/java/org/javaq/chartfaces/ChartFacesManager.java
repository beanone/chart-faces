package org.javaq.chartfaces;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.javaq.chartfaces.api.IChartAxis;
import org.javaq.chartfaces.api.IChartPart;
import org.javaq.chartfaces.api.IChartingDataConverter;
import org.javaq.chartfaces.api.IExporterFactory;
import org.javaq.chartfaces.dataspec.IRangeCalculator;
import org.javaq.chartfaces.event.factory.IChartEventFactory;
import org.javaq.chartfaces.iterable.IIterableConverter;
import org.javaq.chartfaces.iterable.ISizeAwareIterable;
import org.javaq.chartfaces.part.layout.IPartCoordinator;
import org.javaq.chartfaces.render.tool.IChartToolFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;


/**
 * Entry point for convenient access to the various factory classes. This is the
 * best target for dependency injection.
 * 
 * @author Hongyan Li
 * 
 */
public final class ChartFacesManager {
	@Autowired
	private ApplicationContext springContext;
	
	private static ChartFacesManager instance;
	
	private ChartFacesManager() {
	}
	
	public ApplicationContext getSpringContext() {
		return this.springContext;
	}
	
	public static ChartFacesManager createInstance() {
		instance = new ChartFacesManager();
		return instance;
	}
	
	/**
	 * Creates a {@link ValueExpression} from the passed in value and
	 * {@link Class} type of the value.
	 * 
	 * @param value
	 *            the String value of the expression
	 * @param type
	 *            the Class type of the bound value
	 * @return a {@link ValueExpression} if value is not null, null otherwise
	 */
	public static ValueExpression
			createValueExpression(final String value, final Class<?> type) {

		if (value == null) {
			return null;
		}

		final FacesContext context = FacesContext.getCurrentInstance();
		final ELContext elContext = context.getELContext();
		final Application application = context.getApplication();
		final ExpressionFactory expressionFactory =
				application.getExpressionFactory();
		return expressionFactory.createValueExpression(
				elContext, value, type);
	}

	public static ChartFacesManager getInstance() {
		return instance;
	}

	public static <T> T getManagedBean(final String beanName,
			final Class<T> beanClass) {
		return getInstance().getSpringContext().getBean(beanName, beanClass);
	}

	@Autowired
	@Qualifier(value = "cartesianRangeCalculator")
	private IRangeCalculator<UIComponent> cartesianRangeCalculator;

	@Autowired
	@Qualifier(value = "chartEventFactory")
	private IChartEventFactory chartEventFactory;

	@Autowired
	@Qualifier(value = "chartToolFactory")
	private IChartToolFactory chartToolFactory;

	@Autowired
	@Qualifier(value = "defaultAxisLabelAccessor")
	private IIterableConverter<IChartAxis, Object, ISizeAwareIterable<Object>> defaultAxisLabelAccessor;

	@Autowired
	@Qualifier(value = "defaultAxisValueAccessor")
	private IIterableConverter<IChartAxis, Double, ISizeAwareIterable<Double>> defaultAxisValueAccessor;

	@Autowired
	@Qualifier(value = "defaultChartSettings")
	private IChartSettings defaultChartSettings;

	@Autowired
	@Qualifier(value = "chartingDataConverter")
	private IChartingDataConverter defaultDataConverter;

	@Autowired
	@Qualifier(value = "exporterFactory")
	private IExporterFactory exporterFactory;


	/**
	 * @return the strategy that is responsible for calculating the range of
	 *         Cartesian coordinate values.
	 */
	public IRangeCalculator<UIComponent> getCartesianRangeCalculator() {
		return this.cartesianRangeCalculator;
	}

	/**
	 * @return the factory for chart events.
	 */
	public IChartEventFactory getChartEventFactory() {
		return this.chartEventFactory;
	}

	/**
	 * @return the chartPartCoordinator
	 */
	public IPartCoordinator<IChartPart> getChartPartCoordinator() {
		return getRequestScopeManager().getChartPartCoordinator();
	}

	/**
	 * @return the boxToolFactory
	 */
	public IChartToolFactory getChartToolFactory() {
		return this.chartToolFactory;
	}

	public IIterableConverter<IChartAxis, Object, ISizeAwareIterable<Object>> getDefaultAxisLabelAccessor() {
		return this.defaultAxisLabelAccessor;
	}

	public IIterableConverter<IChartAxis, Double, ISizeAwareIterable<Double>> getDefaultAxisValueAccessor() {
		return this.defaultAxisValueAccessor;
	}

	/**
	 * @return the defaultChartSettings
	 */
	public IChartSettings getDefaultChartSettings() {
		return this.defaultChartSettings;
	}

	public IChartingDataConverter getDefaultDataConverter() {
		return this.defaultDataConverter;
	}

	/**
	 * @return the outputExporterFactory
	 */
	public IExporterFactory getExporterFactory() {
		return this.exporterFactory;
	}

	public ChartFacesRequestScopeManager getRequestScopeManager() {
		return (ChartFacesRequestScopeManager) ChartFacesManager.getManagedBean(
				"requestScopeManager",
				ChartFacesRequestScopeManager.class);
	}

	/**
	 * @param cartesianRangeCalculator
	 *            the cartesianRangeCalculator to set
	 */
	public void setCartesianRangeCalculator(
			final IRangeCalculator<UIComponent> cartesianRangeCalculator) {
		this.cartesianRangeCalculator = cartesianRangeCalculator;
	}

	/**
	 * @param chartEventFactory
	 *            the chartEventFactory to set
	 */
	public void setChartEventFactory(final IChartEventFactory chartEventFactory) {
		this.chartEventFactory = chartEventFactory;
	}

	public void setChartToolFactory(final IChartToolFactory boxToolFactory) {
		this.chartToolFactory = boxToolFactory;
	}

	public void setDefaultAxisLabelAccessor(
			final IIterableConverter<IChartAxis, Object, ISizeAwareIterable<Object>> defaultAxisLabelAccessor) {
		this.defaultAxisLabelAccessor = defaultAxisLabelAccessor;
	}

	public void setDefaultAxisValueAccessor(
			final IIterableConverter<IChartAxis, Double, ISizeAwareIterable<Double>> defaultAxisValueAccessor) {
		this.defaultAxisValueAccessor = defaultAxisValueAccessor;
	}

	public void setDefaultChartSettings(
			final IChartSettings defaultChartSettings) {
		this.defaultChartSettings = defaultChartSettings;
	}

	public void setDefaultDataConverter(final IChartingDataConverter converter) {
		this.defaultDataConverter = converter;
	}

	/**
	 * @param exporterFactory
	 *            the outputExporterFactory to set
	 */
	public void setExporterFactory(final IExporterFactory exporterFactory) {
		this.exporterFactory = exporterFactory;
	}

	public void setApplicationContext(ApplicationContext context) {
		this.springContext = context;
	}
}