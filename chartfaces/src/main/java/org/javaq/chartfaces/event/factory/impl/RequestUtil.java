package org.javaq.chartfaces.event.factory.impl;

import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 * A collection of utility method per Faces requests.
 * 
 * @author Hongyan Li
 * 
 */
public final class RequestUtil {
	
	private RequestUtil() {}
	
	/**
	 * @param context
	 * @return the request parameters map.
	 */
	public static Map<String, String> getParameterMap(final FacesContext context) {
		final ExternalContext external = context.getExternalContext();
		return external.getRequestParameterMap();
	}

	/**
	 * @param params
	 * @param clientId
	 * @return true if the request is an JSF standard ajax request.
	 */
	public static boolean isAjaxRequest(final Map<String, String> params,
			final String clientId) {
		final String behaviorSource = params.get("javax.faces.source");
		return behaviorSource != null && behaviorSource.equals(clientId);
	}

}
