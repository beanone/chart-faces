package org.javaq.chartfaces.demo;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.Serializable;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component(TabNavigationController.NAME)
@Scope("session")
public class TabNavigationController implements Serializable {
	private static final long serialVersionUID = 7766983014587775419L;

	public static final String NAME = "tabNavigationController";

	@Autowired
	@Qualifier(DemoApplication.NAME)
	private DemoApplication application;

	public void setApplication(DemoApplication app) {
		this.application = app;
	}

	public DemoApplication getApplication() {
		return application;
	}

	public String toUsage(ActionEvent event) {
		return null;
	}

	public void toViewSource(ActionEvent event) throws IOException {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext extContext = facesContext.getExternalContext();
		extContext
				.redirect("http://localhost:8080/ChartFacesDemo/bar_horizontal.xhtml");
	}

	public String toTagInformation(ActionEvent event) {
		return null;
	}
}
