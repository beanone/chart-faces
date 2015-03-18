package org.javaq.chartfaces.demo.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.catalina.servlets.DefaultServlet;

public class ViewSourceServlet extends DefaultServlet {
	private static final long serialVersionUID = -1551038753140181962L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpServletResponse myres = new HttpServletResponseWrapper(resp) {

			@Override
			public void setContentType(String type) {
				super.setContentType("text/plain");
			}
			
			@Override
			public String getContentType() {
				return "text/plain";
			}
			
		};
		super.doGet(req, myres);
	}
}
