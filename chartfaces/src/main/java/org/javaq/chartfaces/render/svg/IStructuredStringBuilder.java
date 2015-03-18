package org.javaq.chartfaces.render.svg;

import org.javaq.chartfaces.util.IStringBuilder;

public interface IStructuredStringBuilder {

	/**
	 * Append the passed in.
	 * 
	 * @param target
	 *            the to be appended. Cannot be null.
	 * @return this for chaining of calls.
	 * @throws NullPointerException
	 *             if the passed in is null.
	 */
	IStructuredStringBuilder append(final Object target);

	/**
	 * Start of, e.g., tag content.
	 * 
	 * @return this for chaining of calls.
	 */
	IStructuredStringBuilder contentStart();

	/**
	 * @return the stringBuilder
	 */
	IStringBuilder getStringBuilder();

	/**
	 * Checks whether the buffer can be regarded as completed, that is, whether
	 * all quotes are unquoted and tags are ended.
	 * 
	 * @return true if all quotes are unquoted and tags are ended.
	 */
	boolean isCompleted();

	/**
	 * Quotes the passed in with the passed in quote type.
	 * 
	 * @param target
	 * @param quoteBy
	 * @return this for chaining of calls.
	 */
	IStructuredStringBuilder quote(final Object target, final Quote quoteBy);

	/**
	 * Quote the passed in target using the passed in quote.
	 * 
	 * @param quoteBy
	 *            a Quote enum. Cannot be null.
	 * @return this for chaining of calls.
	 * @throws IllegalArgumentException
	 *             if a previous quote has not been unquoted.
	 * @throws NullPointerException
	 *             if any of the passed in is null.
	 */
	IStructuredStringBuilder quote(final Quote quoteBy);

	/**
	 * Removes the last character.
	 * 
	 * @return this for chaining of calls.
	 */
	IStructuredStringBuilder removeLast();

	/**
	 * Start a quote.
	 * 
	 * @param tagName
	 *            Name of a tag. Cannot be null.
	 * @return this for chaining of calls.
	 * @throws IllegalArgumentException
	 *             if a previous quote has not been unquoted.
	 * @throws NullPointerException
	 *             if the passed in is null.
	 */
	IStructuredStringBuilder tagEnd(final String tagName);

	/**
	 * Start a tag.
	 * 
	 * @param tagName
	 * @return this for chaining of calls.
	 */
	IStructuredStringBuilder tagStart(final String tagName);

	/**
	 * Quote the passed in target using {@link Quote#DoubleQuote}.
	 * 
	 * @param target
	 *            the to be quoted. Cannot be null.
	 * @return this for chaining of calls.
	 * @throws IllegalArgumentException
	 *             if a previous quote has not been unquoted.
	 * @throws NullPointerException
	 *             if any of the passed in is null.
	 */
	IStructuredStringBuilder textQuote(final Object target);

	IStructuredStringBuilder unquote(final Quote unquoteBy);
}