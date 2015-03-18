package org.javaq.chartfaces;

import org.javaq.chartfaces.api.IChartPart;
import org.javaq.chartfaces.part.layout.IPartCoordinator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


/**
 * A place for all the request scoped framework objects to be injected.
 * 
 * @author Hongyan Li
 * 
 */
@Component("requestScopeManager")
@Scope("request")
public class ChartFacesRequestScopeManager {

	@Autowired
	@Qualifier(value = "chartPartCoordinator")
	private IPartCoordinator<IChartPart> chartPartCoordinator;

	/**
	 * @return the chartPartCoordinator
	 */
	public IPartCoordinator<IChartPart> getChartPartCoordinator() {
		return this.chartPartCoordinator;
	}

	/**
	 * @param chartPartCoordinator
	 *            the chartPartCoordinator to set
	 */
	public void setChartPartCoordinator(
			final IPartCoordinator<IChartPart> chartPartCoordinator) {
		this.chartPartCoordinator = chartPartCoordinator;
	}

}
