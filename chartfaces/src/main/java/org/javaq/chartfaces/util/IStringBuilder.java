package org.javaq.chartfaces.util;

/**
 * This interface has the exact same methods as {@link StringBuilder}. Since the
 * StringBuilder is final (a pain) thus I have to extract this interface so that
 * I can hide the difference between a normal StringBuilder and my custom
 * implementation, more specifically, the NumberRoundingStringBuilder.
 * 
 * @author Hongyan Li
 * 
 */
public interface IStringBuilder {

	IStringBuilder append(final boolean b);

	IStringBuilder append(final char c);

	IStringBuilder append(final char[] chars);

	IStringBuilder append(final char[] str,
			final int offset, final int len);

	IStringBuilder append(final CharSequence str);

	IStringBuilder append(final CharSequence s,
			final int start, final int end);

	IStringBuilder append(final double d);

	IStringBuilder append(final float f);

	IStringBuilder append(final int i);

	IStringBuilder append(final long l);

	IStringBuilder append(final Object o);

	IStringBuilder append(final String s);

	IStringBuilder append(final StringBuffer sbuf);

	IStringBuilder appendCodePoint(final int i);

	char charAt(final int pos);

	IStringBuilder delete(final int start, final int end);

	IStringBuilder deleteCharAt(final int pos);

	int indexOf(final String s);

	int indexOf(final String s, final int i);

	IStringBuilder insert(final int offset,
			final boolean b);

	IStringBuilder insert(final int offset,
			final char c);

	IStringBuilder insert(final int offset,
			final char[] chars);

	IStringBuilder insert(final int index,
			final char[] str,
			final int offset, final int len);

	IStringBuilder insert(final int offset,
			final CharSequence s);

	IStringBuilder insert(final int dstOffset,
			final CharSequence s,
			final int start, final int end);

	IStringBuilder insert(final int offset,
			final double d);

	IStringBuilder insert(final int offset,
			final float f);

	IStringBuilder insert(final int offset,
			final int i);

	IStringBuilder insert(final int offset,
			final long l);

	IStringBuilder insert(final int offset,
			final Object o);

	IStringBuilder insert(final int offset,
			final String s);

	int lastIndexOf(final String str);

	int lastIndexOf(final String str, final int fromIndex);

	int length();

	IStringBuilder replace(final int start,
			final int end,
			final String str);

	IStringBuilder reverse();

}