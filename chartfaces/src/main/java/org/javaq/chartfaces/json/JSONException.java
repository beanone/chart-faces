package org.javaq.chartfaces.json;

/**
 * The JSONException is thrown by the JSON.org classes when things are amiss.
 * @author JSON.org
 * @version 2008-09-18
 */
public class JSONException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5172841210589562127L;
	
	public JSONException(final String format, Object... params) {
		this(String.format(format, params));
	}

    /**
     * Constructs a JSONException with an explanatory message.
     * @param message Detail about the reason for the exception.
     */
    public JSONException(final String message) {
        super(message);
    }

    public JSONException(final String message, final Throwable t) {
        super(message, t);
    }

    public JSONException(final String format, final Throwable t, Object... params) {
        super(String.format(format, params), t);
    }

    public JSONException(final Throwable t) {
        super(t);
    }
}
