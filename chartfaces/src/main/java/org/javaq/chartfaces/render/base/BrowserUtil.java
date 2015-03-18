package org.javaq.chartfaces.render.base;

import java.util.Map;

import javax.faces.context.FacesContext;

public final class BrowserUtil {
	
	private BrowserUtil() {}
	
	public static boolean isAppleWebkit() {
		// both Opera and Google Chrome does not like CData
		final Map<String, String> headerMap = FacesContext.getCurrentInstance()
				.getExternalContext().getRequestHeaderMap();
		final String userAgent = headerMap.get("User-Agent");
		return userAgent != null && userAgent.indexOf("AppleWebKit") > -1;
	}

	public static boolean isGoogleChrome() {
		return BrowserUtil.isAppleWebkit();
	}

	public static boolean shouldNotUseCData() {
		return BrowserUtil.isAppleWebkit();
	}
}
