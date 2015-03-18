package org.javaq.chartfaces.util;

/**
 * An {@link IStringBuilder} that rounds numbers to certain precision while
 * building the String buffer.
 *
 * @author Hongyan Li
 *
 */
public class NumberRoundingStringBuilder implements IStringBuilder {
	private final StringBuilder sb;
	private final int sigDigit;

	public NumberRoundingStringBuilder() {
		this(-1);
	}

	public NumberRoundingStringBuilder(final int sigDigit) {
		this.sb = new StringBuilder();
		this.sigDigit = sigDigit;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.javaq.chartfaces.util.IStringBuilder#append(boolean)
	 */
	@Override
	public IStringBuilder append(final boolean b) {
		this.sb.append(b);
		return this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.javaq.chartfaces.util.IStringBuilder#append(char)
	 */
	@Override
	public IStringBuilder append(final char c) {
		this.sb.append(c);
		return this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.javaq.chartfaces.util.IStringBuilder#append(char[])
	 */
	@Override
	public IStringBuilder append(final char[] chars) {
		this.sb.append(chars);
		return this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.javaq.chartfaces.util.IStringBuilder#append(char[], int, int)
	 */
	@Override
	public IStringBuilder append(final char[] str,
			final int offset, final int len) {
		this.sb.append(str, offset, len);
		return this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.javaq.chartfaces.util.IStringBuilder#append(java.lang.CharSequence)
	 */
	@Override
	public IStringBuilder append(final CharSequence str) {
		this.sb.append(str);
		return this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.javaq.chartfaces.util.IStringBuilder#append(java.lang.CharSequence,
	 * int, int)
	 */
	@Override
	public IStringBuilder append(final CharSequence s,
			final int start, final int end) {
		this.sb.append(s, start, end);
		return this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.javaq.chartfaces.util.IStringBuilder#append(double)
	 */
	@Override
	public IStringBuilder append(final double d) {
//		this.sb.append(NumberUtils.truncate(d, this.sigDigit));
		this.sb.append(d);
		return this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.javaq.chartfaces.util.IStringBuilder#append(float)
	 */
	@Override
	public IStringBuilder append(final float f) {
		this.append((double) f);
		return this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.javaq.chartfaces.util.IStringBuilder#append(int)
	 */
	@Override
	public IStringBuilder append(final int i) {
		this.sb.append(i);
		return this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.javaq.chartfaces.util.IStringBuilder#append(long)
	 */
	@Override
	public IStringBuilder append(final long l) {
		this.sb.append(l);
		return this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.javaq.chartfaces.util.IStringBuilder#append(java.lang.Object)
	 */
	@Override
	public IStringBuilder append(final Object o) {
		if (o instanceof Double) {
			append(((Double) o).doubleValue());
		} else {
			this.sb.append(o);
		}

		return this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.javaq.chartfaces.util.IStringBuilder#append(java.lang.String)
	 */
	@Override
	public IStringBuilder append(final String s) {
		this.sb.append(s);
		return this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.javaq.chartfaces.util.IStringBuilder#append(java.lang.StringBuffer)
	 */
	@Override
	public IStringBuilder append(final StringBuffer sbuf) {
		this.sb.append(sbuf);
		return this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.javaq.chartfaces.util.IStringBuilder#appendCodePoint(int)
	 */
	@Override
	public IStringBuilder appendCodePoint(final int i) {
		this.sb.appendCodePoint(i);
		return this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.javaq.chartfaces.util.IStringBuilder#charAt(int)
	 */
	@Override
	public char charAt(final int pos) {
		return this.sb.charAt(pos);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.javaq.chartfaces.util.IStringBuilder#delete(int, int)
	 */
	@Override
	public IStringBuilder delete(final int start, final int end) {
		this.sb.delete(start, end);
		return this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.javaq.chartfaces.util.IStringBuilder#deleteCharAt(int)
	 */
	@Override
	public IStringBuilder deleteCharAt(final int pos) {
		this.sb.deleteCharAt(pos);
		return this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.javaq.chartfaces.util.IStringBuilder#indexOf(java.lang.String)
	 */
	@Override
	public int indexOf(final String s) {
		return this.sb.indexOf(s);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.javaq.chartfaces.util.IStringBuilder#indexOf(java.lang.String,
	 * int)
	 */
	@Override
	public int indexOf(final String s, final int i) {
		return this.sb.indexOf(s, i);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.javaq.chartfaces.util.IStringBuilder#insert(int, boolean)
	 */
	@Override
	public IStringBuilder insert(final int offset, final boolean b) {
		this.sb.insert(offset, b);
		return this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.javaq.chartfaces.util.IStringBuilder#insert(int, char)
	 */
	@Override
	public IStringBuilder insert(final int offset, final char c) {
		this.sb.insert(offset, c);
		return this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.javaq.chartfaces.util.IStringBuilder#insert(int, char[])
	 */
	@Override
	public IStringBuilder insert(final int offset,
			final char[] chars) {
		this.sb.insert(offset, chars);
		return this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.javaq.chartfaces.util.IStringBuilder#insert(int, char[], int,
	 * int)
	 */
	@Override
	public IStringBuilder insert(final int index,
			final char[] str,
			final int offset, final int len) {
		this.sb.insert(index, str, offset, len);
		return this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.javaq.chartfaces.util.IStringBuilder#insert(int,
	 * java.lang.CharSequence)
	 */
	@Override
	public IStringBuilder insert(final int offset,
			final CharSequence s) {
		this.sb.insert(offset, s);
		return this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.javaq.chartfaces.util.IStringBuilder#insert(int,
	 * java.lang.CharSequence, int, int)
	 */
	@Override
	public IStringBuilder insert(final int dstOffset,
			final CharSequence s,
			final int start, final int end) {
		this.sb.insert(dstOffset, s, start, end);
		return this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.javaq.chartfaces.util.IStringBuilder#insert(int, double)
	 */
	@Override
	public IStringBuilder insert(final int offset, final double d) {
		this.sb.insert(offset, NumberUtils.truncate(d, this.sigDigit));
		return this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.javaq.chartfaces.util.IStringBuilder#insert(int, float)
	 */
	@Override
	public IStringBuilder insert(final int offset, final float f) {
		this.sb.insert(offset, (double) f);
		return this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.javaq.chartfaces.util.IStringBuilder#insert(int, int)
	 */
	@Override
	public IStringBuilder insert(final int offset, final int i) {
		this.sb.insert(offset, i);
		return this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.javaq.chartfaces.util.IStringBuilder#insert(int, long)
	 */
	@Override
	public IStringBuilder insert(final int offset, final long l) {
		this.sb.insert(offset, l);
		return this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.javaq.chartfaces.util.IStringBuilder#insert(int,
	 * java.lang.Object)
	 */
	@Override
	public IStringBuilder insert(final int offset, final Object o) {
		this.sb.insert(offset, o);
		return this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.javaq.chartfaces.util.IStringBuilder#insert(int,
	 * java.lang.String)
	 */
	@Override
	public IStringBuilder insert(final int offset, final String s) {
		this.sb.insert(offset, s);
		return this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.javaq.chartfaces.util.IStringBuilder#lastIndexOf(java.lang.String)
	 */
	@Override
	public int lastIndexOf(final String str) {
		return this.sb.lastIndexOf(str);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.javaq.chartfaces.util.IStringBuilder#lastIndexOf(java.lang.String,
	 * int)
	 */
	@Override
	public int lastIndexOf(final String str, final int fromIndex) {
		return this.sb.lastIndexOf(str, fromIndex);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.javaq.chartfaces.util.IStringBuilder#length()
	 */
	@Override
	public int length() {
		return this.sb.length();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.javaq.chartfaces.util.IStringBuilder#replace(int, int,
	 * java.lang.String)
	 */
	@Override
	public IStringBuilder replace(final int start, final int end,
			final String str) {
		this.sb.replace(start, end, str);
		return this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.javaq.chartfaces.util.IStringBuilder#reverse()
	 */
	@Override
	public IStringBuilder reverse() {
		this.sb.reverse();
		return this;
	}

	@Override
	public String toString() {
		return this.sb.toString();
	}
}
