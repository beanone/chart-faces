package org.javaq.chartfaces.render.svg;

import java.util.Stack;

import org.javaq.chartfaces.util.IStringBuilder;
import org.javaq.chartfaces.util.StringBuilderWraper;


/**
 * A utility class that helps building string with structures, e.g., XML, JSon,
 * etc.. This modifies the behavior of the super
 * {@link StructuredStringBuilderBase#unquote(Quote)} method so that direct
 * invocation of this method when a tag is started is prohibited.
 * 
 * @author Hongyan Li
 * 
 */
public class StructuredStringBuilder extends StructuredStringBuilderBase {
	private boolean tagStarted;

	public StructuredStringBuilder() {
	}

	public StructuredStringBuilder(final IStringBuilder sb) {
		super(sb);
	}

	public StructuredStringBuilder(final StringBuilder sb) {
		super(sb);
	}

	@Override
	public IStructuredStringBuilder contentStart() {
		this.tagStarted = false;
		return super.contentStart();
	}

	/**
	 * @param target the target to be quoted.
	 * @param quoteBy the {@link Quote} to quote the target.
	 * @throws UnsupportedOperationException
	 *             if directly quote a start tag.
	 * @return this itself so that it can be easily chained.
	 */
	@Override
	public IStructuredStringBuilder quote(final Object target,
			final Quote quoteBy) {
		if (Quote.AngleTag.match(quoteBy) || Quote.EndTag.match(quoteBy)) {
			throw new UnsupportedOperationException(
					"Can't directly unquote a start tag!");
		}
		return super.quote(target, quoteBy);
	}

	/**
	 * @param quoteBy a {@link Quote}.
	 * @throws UnsupportedOperationException
	 *             if directly quote a start tag.
	 * @return this itself so that it can be easily chained.
	 */
	@Override
	public IStructuredStringBuilder quote(final Quote quoteBy) {
		if (Quote.AngleTag.match(quoteBy) || Quote.EndTag.match(quoteBy)) {
			throw new UnsupportedOperationException(
					"Can't directly unquote a start tag!");
		}
		return super.quote(quoteBy);
	}

	/**
	 * @param tagName name of a tag.
	 * @return this itself so that it can be easily chained.
	 */
	@Override
	public IStructuredStringBuilder tagEnd(final String tagName) {
		this.tagStarted = false;
		return super.tagEnd(tagName);
	}

	/**
	 * @param tagName name of a tag.
	 * @return this itself so that it can be easily chained.
	 */
	@Override
	public IStructuredStringBuilder tagStart(final String tagName) {
		this.tagStarted = true;
		return super.tagStart(tagName);
	}

	/**
	 * @param unquoteBy a {@link Quote}.
	 * @throws UnsupportedOperationException
	 *             if directly quote a start tag.
	 * @return this itself so that it can be easily chained.
	 */
	@Override
	public IStructuredStringBuilder unquote(final Quote unquoteBy) {
		if (this.tagStarted && Quote.AngleTag.match(unquoteBy)) {
			throw new UnsupportedOperationException(
					"Can't directly unquote a start tag!");
		}
		return super.unquote(unquoteBy);
	}
}

/**
 * A utility class that helps building string with structures, e.g., XML, JSon,
 * etc..
 * 
 * @author Hongyan Li
 * 
 */
abstract class StructuredStringBuilderBase implements IStructuredStringBuilder {
	private final Stack<Quote> parentQuoteStack = new Stack<Quote>();
	private Quote quotedBy;
	private final IStringBuilder stringBuilder;

	private final Stack<String> tagStack = new Stack<String>();

	protected StructuredStringBuilderBase() {
		this(new StringBuilder());
	}

	protected StructuredStringBuilderBase(final IStringBuilder sb) {
		this.stringBuilder = sb;
	}

	protected StructuredStringBuilderBase(final StringBuilder sb) {
		this(new StringBuilderWraper(sb));
	}

	@Override
	public IStructuredStringBuilder append(final Object target) {
		if (target == null) {
			throw new IllegalArgumentException("Trying to quote a null target!");
		}
		this.stringBuilder.append(target);
		return this;
	}

	/**
	 * Ends the bracket for the start tag.
	 * 
	 * @return this for chaining of calls.
	 * @throws IllegalArgumentException
	 *             if a previous quote other than the angle bracket has not been
	 *             unquoted or the tag has not been started.
	 */
	@Override
	public IStructuredStringBuilder contentStart() {
		if (this.stringBuilder.charAt(this.stringBuilder.length() - 1) == ' ') {
			// delete the last space
			this.stringBuilder.delete(this.stringBuilder.length() - 1,
					this.stringBuilder.length());
		}
		return unquote(Quote.AngleTag);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.javaq.chartfaces.render.svg.IStructuredStringBuilder#getStringBuilder
	 * ()
	 */
	@Override
	public IStringBuilder getStringBuilder() {
		return this.stringBuilder;
	}

	@Override
	public boolean isCompleted() {
		return this.quotedBy == null && this.tagStack.isEmpty();
	}

	@Override
	public IStructuredStringBuilder quote(final Object target,
			final Quote quoteBy) {
		return internalQuote(target, quoteBy);
	}

	@Override
	public IStructuredStringBuilder quote(final Quote quoteBy) {
		return internalQuote(quoteBy);
	}

	@Override
	public IStructuredStringBuilder removeLast() {
		if (this.stringBuilder.length() > 0) {
			this.stringBuilder.deleteCharAt(this.stringBuilder.length() - 1);
		}
		return this;
	}

	/**
	 * Ends a tag.
	 * 
	 * @param tagName
	 *            name of the tag to end. Cannot be null.
	 * @return this for chaining of calls.
	 * @throws IllegalArgumentException
	 *             if a previous quote other than the angle bracket has not been
	 *             unquoted or the passed in end tag name does not match the
	 *             start tag name.
	 * @throws NullPointerException
	 *             if the passed in is null.
	 */
	@Override
	public IStructuredStringBuilder tagEnd(final String tagName) {
		final String currentTagName = this.tagStack.pop();
		if (!tagName.equals(currentTagName)) {
			throw new IllegalArgumentException("End tag: " + tagName
					+ " doesn't match the start tag: " + currentTagName + "!");
		}
		if (this.quotedBy == Quote.AngleTag) {
			if (this.stringBuilder.charAt(this.stringBuilder.length() - 1) == ' ') {
				// delete the last space
				this.stringBuilder.delete(this.stringBuilder.length() - 1,
						this.stringBuilder.length());
			}
			unquote(Quote.EmptyTag);
		} else {
			internalQuote(tagName, Quote.EndTag);
		}
		return this;
	}

	/**
	 * Starts a tag. Note: this does not try to validate the tagName syntax.
	 * 
	 * @param tagName
	 *            name of the tag to start. Cannot be null.
	 * @return this for chaining of calls.
	 * @throws IllegalArgumentException
	 *             if a previous quote has not been unquoted.
	 * @throws NullPointerException
	 *             if the passed in is null.
	 */
	@Override
	public IStructuredStringBuilder tagStart(final String tagName) {
		if (tagName == null) {
			throw new IllegalArgumentException("Tag passed in name is null!");
		}
		this.tagStack.push(tagName);
		internalQuote(Quote.AngleTag).append(tagName).append(' ');
		return this;
	}

	@Override
	public IStructuredStringBuilder textQuote(final Object target) {
		return quote(target, Quote.DoubleQuote);
	}

	@Override
	public String toString() {
		return this.stringBuilder.toString();
	}

	/**
	 * End a quote. This method takes a {@link Quote} as parameter, which forces
	 * the caller to make a conscious choice of which quote he wants to
	 * un-quote.
	 * 
	 * @param unquoteBy
	 *            a Quote enum. Cannot be null.
	 * @return this for chaining of calls.
	 * @throws IllegalArgumentException
	 *             if this was not quoted or a current quote is still active.
	 * @throws NullPointerException
	 *             if the passed in is null.
	 */
	@Override
	public IStructuredStringBuilder unquote(final Quote unquoteBy) {
		if (this.quotedBy == null) {
			throw new IllegalArgumentException("Must quote before unquoting!");
		}

		if (unquoteBy == null) {
			throw new IllegalArgumentException("The passed in is null!");
		}

		if (!this.quotedBy.match(unquoteBy)) {
			throw new IllegalArgumentException("Current quote still active : "
					+ this.quotedBy.getStart());
		}

		this.stringBuilder.append(unquoteBy.getEnd());
		this.quotedBy = this.parentQuoteStack.isEmpty() ? null
				: this.parentQuoteStack
						.pop();
		return this;
	}

	private IStructuredStringBuilder internalQuote(final Object target,
			final Quote quoteBy) {
		return internalQuote(quoteBy).append(target).unquote(quoteBy);
	}

	private IStructuredStringBuilder internalQuote(final Quote quoteBy) {
		if (this.quotedBy != null && !this.quotedBy.canNest(quoteBy)) {
			throw new IllegalArgumentException(
					"Must unquote the previous quote before quoting again!");
		}

		if (this.quotedBy != null) {
			this.parentQuoteStack.push(this.quotedBy);
		}
		this.quotedBy = quoteBy;
		this.stringBuilder.append(this.quotedBy.getStart());
		return this;
	}
}