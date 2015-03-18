package org.javaq.chartfaces.render.svg;

import java.util.ArrayList;
import java.util.List;

/**
 * Encapsulation of quote pairs as enumerations.
 * 
 * @author Hongyan Li
 * 
 */
public enum Quote {
	AngleTag("<", ">") {
		@Override
		public void init() {
			this.getNests().add(DoubleQuote);
			this.getNests().add(SingleQuote);
		}
	},
	CurlyBracket("{", "}") {
		@Override
		public void init() {
			this.getNests().add(DoubleQuote);
			this.getNests().add(SingleQuote);
			this.getNests().add(CurlyBracket);
			this.getNests().add(SquareBracket);
		}
	},
	DoubleQuote("\"", "\"") {
		@Override
		public void init() {
			this.getNests().add(SingleQuote);
		}
	},
	EmptyTag("<", "/>"),
	EndTag("</", ">"),
	SingleQuote("'", "'") {
		@Override
		public void init() {
			this.getNests().add(DoubleQuote);
		}
	},
	SquareBracket("[", "]") {
		@Override
		public void init() {
			this.getNests().add(DoubleQuote);
			this.getNests().add(SingleQuote);
			this.getNests().add(CurlyBracket);
			this.getNests().add(SquareBracket);
		}
	};

	private final String end;

	private boolean inistialized;

	private final String start;

	private final List<Quote> nests = new ArrayList<Quote>();

	Quote(final String start, final String end) {
		this.start = start;
		this.end = end;
	}

	/**
	 * Checks whether nesting the passed in Quote within this is OK.
	 * 
	 * @param child
	 *            the child quote to nest.
	 * @return a boolean.
	 */
	public boolean canNest(final Quote child) {
		if (!this.inistialized) {
			init();
		}
		return this.getNests().contains(child);
	}

	public String getEnd() {
		return this.end;
	}

	public String getStart() {
		return this.start;
	}

	/**
	 * Checks whether the start quote matches.
	 * 
	 * @param other
	 *            the Quote to check against.
	 * @return true if the passed in has the same start quote.
	 */
	public boolean match(final Quote other) {
		return (other == null) ? false : getStart().equals(other.getStart());
	}

	protected void init() {
	} // should be overriden to add allowed nestings

	protected List<Quote> getNests() {
		return nests;
	}
}