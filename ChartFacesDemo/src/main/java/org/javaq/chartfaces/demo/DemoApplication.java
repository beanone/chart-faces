package org.javaq.chartfaces.demo;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component(DemoApplication.NAME)
@Scope("application")
public class DemoApplication {
	public static final String NAME = "demoApp";

	public String getContextPath() {
		ExternalContext context = FacesContext.getCurrentInstance()
				.getExternalContext();
		return context.getRequestContextPath();
	}

	public String getCurrentPageUrl() {
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext();
		return request.getRequestURI();
	}

	public String[] getSources() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		String sourceString = facesContext.getExternalContext().getRequestParameterMap().get("sources");
		return sourceString.split(",");
	}
}
