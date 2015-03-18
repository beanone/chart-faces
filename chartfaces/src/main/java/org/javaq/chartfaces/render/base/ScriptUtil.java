package org.javaq.chartfaces.render.base;

import java.io.IOException;
import java.util.Map;

import javax.faces.application.Resource;
import javax.faces.application.ResourceHandler;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

/**
 * Contains a collection of utility methods that take care of rendering per
 * script reference and script block.
 * 
 * @author Hongyan Li
 * 
 */
public final class ScriptUtil {
	private static final String APPLICATION_X_JAVASCRIPT = "application/x-javascript";
	private static final String CDATA_END = "\n]]>";
	private static final String CDATA_START = "<![CDATA[\n";
	private static final String HREF = "href";
	private static final String JAVA_SCRIPT = "JavaScript";
	private static final String LANGUAGE = "language";
	private static final String LIBRARY = "%1";
	private static final String LIBRARY_NOT_FOUND_MESSAGE = "Library "
			+ ScriptUtil.LIBRARY + " not found!";
	private static final String LINK = "link";
	private static final String REL = "rel";
	private static final String RESOURCE = "%2";
	private static final String RESOURCE_NOT_FOUND_MESSAGE = "Resource "
			+ ScriptUtil.RESOURCE + " not found in library "
			+ ScriptUtil.LIBRARY;
	private static final String SCRIPT = "script";
	private static final String SRC = "src";
	private static final String STYLESHEET = "stylesheet";
	private static final String TEXT_CSS = "text/css";
	private static final String TEXT_JAVASCRIPT = "text/javascript";
	private static final String TYPE = "type";
	
	private ScriptUtil() {}

	/**
	 * Writes a script reference to the response.
	 * 
	 * @param context
	 * @param library
	 * @param aName
	 * @throws IOException
	 */
	public static void writeCSSFile(final FacesContext context,
			final String library, final String aName) throws IOException {
		String name = aName;
		final Map<Object, Object> contextMap = context.getAttributes();
		final String key = name + library;

		if (null == name) {
			return;
		}

		// Ensure this script is not rendered more than once per request
		if (contextMap.containsKey(key)) {
			return;
		}
		contextMap.put(key, Boolean.TRUE);

		// Special case of scripts that have query strings
		// These scripts actually use their query strings internally, not
		// externally
		// so we don't need the resource to know about them
		final int queryPos = name.indexOf('?');
		String query = null;
		if (queryPos > -1 && name.length() > queryPos) {
			query = name.substring(queryPos + 1);
			name = name.substring(0, queryPos);
		}

		final ResourceHandler rh =
				context.getApplication().getResourceHandler();
		Resource resource = null;
		if (rh.libraryExists(library)) {
			resource = rh.createResource(name, library);
			if (resource == null) {
				throw new IOException(ScriptUtil.RESOURCE_NOT_FOUND_MESSAGE
						.replace(
								ScriptUtil.RESOURCE, name).replace(
								ScriptUtil.LIBRARY, library));
			}
		} else {
			throw new IOException(ScriptUtil.LIBRARY_NOT_FOUND_MESSAGE.replace(
					ScriptUtil.LIBRARY,
					library));
		}

		final ResponseWriter writer = context.getResponseWriter();
		writer.startElement(ScriptUtil.LINK, null);
		writer.writeAttribute(ScriptUtil.TYPE, ScriptUtil.TEXT_CSS, null);

		String resourceSrc;
		resourceSrc = resource.getRequestPath();
		if (query != null) {
			resourceSrc = resourceSrc +
						((resourceSrc.indexOf('?') > -1) ? '+' : '?') +
						query;
		}

		writer.writeURIAttribute(ScriptUtil.HREF, resourceSrc, null);
		writer.writeURIAttribute(ScriptUtil.REL, ScriptUtil.STYLESHEET, null);
		writer.endElement(ScriptUtil.LINK);
	}

	/**
	 * Writes a script block to the response.
	 * 
	 * @param context
	 * @param script
	 * @throws IOException
	 */
	public static void writeScriptBlock(final FacesContext context,
			final String script)
			throws IOException {
		final ResponseWriter writer = context.getResponseWriter();
		writer.startElement(ScriptUtil.SCRIPT, null);
		writer.writeAttribute(ScriptUtil.TYPE,
				ScriptUtil.APPLICATION_X_JAVASCRIPT, null);
		writer.writeAttribute(ScriptUtil.LANGUAGE, ScriptUtil.JAVA_SCRIPT, null);
		ScriptUtil.writeScript(writer, script);
		writer.endElement(ScriptUtil.SCRIPT);
	}

	/**
	 * Writes a script reference to the response.
	 * 
	 * @param context
	 * @param library
	 * @param name
	 * @throws IOException
	 */
	public static void writeScriptFile(final FacesContext context,
			final String library,
			String name) throws IOException {

		final Map<Object, Object> contextMap = context.getAttributes();
		final String key = name + library;

		if (null == name) {
			return;
		}

		// Ensure this script is not rendered more than once per request
		if (contextMap.containsKey(key)) {
			return;
		}
		contextMap.put(key, Boolean.TRUE);

		// Special case of scripts that have query strings
		// These scripts actually use their query strings internally, not
		// externally
		// so we don't need the resource to know about them
		final int queryPos = name.indexOf('?');
		String query = null;
		String nameWithoutQueryString = name;
		if (queryPos > -1 && name.length() > queryPos) {
			query = name.substring(queryPos + 1);
			nameWithoutQueryString = name.substring(0, queryPos);
		}

		final ResourceHandler rh = context.getApplication()
				.getResourceHandler();
		Resource resource = null;
		if (rh.libraryExists(library)) {
			resource = rh.createResource(nameWithoutQueryString, library);
			if (resource == null) {
				throw new IOException(ScriptUtil.RESOURCE_NOT_FOUND_MESSAGE
						.replace(
								ScriptUtil.RESOURCE, nameWithoutQueryString).replace(
								ScriptUtil.LIBRARY, library));
			}
		} else {
			throw new IOException(ScriptUtil.LIBRARY_NOT_FOUND_MESSAGE.replace(
					ScriptUtil.LIBRARY,
					library));
		}

		final ResponseWriter writer = context.getResponseWriter();
		writer.startElement(ScriptUtil.SCRIPT, null);
		writer.writeAttribute(ScriptUtil.TYPE, ScriptUtil.TEXT_JAVASCRIPT, null);

		String resourceSrc;
		resourceSrc = resource.getRequestPath();
		if (query != null) {
			resourceSrc = resourceSrc +
						((resourceSrc.indexOf('?') > -1) ? '+' : '?') +
						query;
		}

		writer.writeURIAttribute(ScriptUtil.SRC, resourceSrc, null);
		writer.endElement(ScriptUtil.SCRIPT);
	}

	private static void writeScript(final ResponseWriter writer,
			final String script)
			throws IOException {
		final boolean addCData = !BrowserUtil.shouldNotUseCData();
		if (addCData) {
			writer.write(ScriptUtil.CDATA_START);
		}
		writer.writeText(script, null);
		if (addCData) {
			writer.write(ScriptUtil.CDATA_END);
		}
	}
}
