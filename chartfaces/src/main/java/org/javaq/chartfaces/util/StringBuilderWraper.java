package org.javaq.chartfaces.util;

/**
 * A wrapper that is used to hide the difference between a {@link StringBuilder}
 * and {@link IStringBuilder} since {@link StringBuilder} is declared as final.
 * 
 * @author Hongyan Li
 * 
 */
public class StringBuilderWraper implements IStringBuilder {
	private final StringBuilder wrapped;

	public StringBuilderWraper(final StringBuilder wrapped) {
		this.wrapped = wrapped;
	}

	@Override
	public IStringBuilder append(final boolean b) {
		this.wrapped.append(b);
		return this;
	}

	@Override
	public IStringBuilder append(final char c) {
		this.wrapped.append(c);
		return this;
	}

	@Override
	public IStringBuilder append(final char[] str) {
		this.wrapped.append(str);
		return this;
	}

	@Override
	public IStringBuilder append(final char[] str, final int offset,
			final int len) {
		this.wrapped.append(str, offset, len);
		return this;
	}

	@Override
	public IStringBuilder append(final CharSequence s) {
		this.wrapped.append(s);
		return this;
	}

	@Override
	public IStringBuilder append(final CharSequence s, final int start,
			final int end) {
		this.wrapped.append(s, start, end);
		return this;
	}

	@Override
	public IStringBuilder append(final double d) {
		this.wrapped.append(d);
		return this;
	}

	@Override
	public IStringBuilder append(final float f) {
		this.wrapped.append(f);
		return this;
	}

	@Override
	public IStringBuilder append(final int i) {
		this.wrapped.append(i);
		return this;
	}

	@Override
	public IStringBuilder append(final long lng) {
		this.wrapped.append(lng);
		return this;
	}

	@Override
	public IStringBuilder append(final Object obj) {
		this.wrapped.append(obj);
		return this;
	}

	@Override
	public IStringBuilder append(final String str) {
		this.wrapped.append(str);
		return this;
	}

	@Override
	public IStringBuilder append(final StringBuffer sb) {
		this.wrapped.append(sb);
		return this;
	}

	@Override
	public IStringBuilder appendCodePoint(final int codePoint) {
		this.wrapped.appendCodePoint(codePoint);
		return this;
	}

	public int capacity() {
		return this.wrapped.capacity();
	}

	@Override
	public char charAt(final int arg0) {
		return this.wrapped.charAt(arg0);
	}

	public int codePointAt(final int arg0) {
		return this.wrapped.codePointAt(arg0);
	}

	public int codePointBefore(final int arg0) {
		return this.wrapped.codePointBefore(arg0);
	}

	public int codePointCount(final int arg0, final int arg1) {
		return this.wrapped.codePointCount(arg0, arg1);
	}

	@Override
	public IStringBuilder delete(final int start, final int end) {
		this.wrapped.delete(start, end);
		return this;
	}

	@Override
	public IStringBuilder deleteCharAt(final int index) {
		this.wrapped.deleteCharAt(index);
		return this;
	}

	public void ensureCapacity(final int arg0) {
		this.wrapped.ensureCapacity(arg0);
	}

	public void getChars(final int arg0, final int arg1, final char[] arg2,
			final int arg3) {
		this.wrapped.getChars(arg0, arg1, arg2, arg3);
	}

	@Override
	public int hashCode() {
		return this.wrapped.hashCode();
	}
	
	@Override
	public boolean equals(Object other) {
		if(other==null) {
			return false;
		}
		return (other instanceof StringBuilderWraper)? toString().equals(other.toString()) : false;
	}

	@Override
	public int indexOf(final String str) {
		return this.wrapped.indexOf(str);
	}

	@Override
	public int indexOf(final String str, final int fromIndex) {
		return this.wrapped.indexOf(str, fromIndex);
	}

	@Override
	public IStringBuilder insert(final int offset, final boolean b) {
		this.wrapped.insert(offset, b);
		return this;
	}

	@Override
	public IStringBuilder insert(final int offset, final char c) {
		this.wrapped.insert(offset, c);
		return this;
	}

	@Override
	public IStringBuilder insert(final int offset, final char[] str) {
		this.wrapped.insert(offset, str);
		return this;
	}

	@Override
	public IStringBuilder insert(final int index, final char[] str,
			final int offset, final int len) {
		this.wrapped.insert(index, str, offset, len);
		return this;
	}

	@Override
	public IStringBuilder insert(final int dstOffset, final CharSequence s) {
		this.wrapped.insert(dstOffset, s);
		return this;
	}

	@Override
	public IStringBuilder insert(final int dstOffset, final CharSequence s,
			final int start,
			final int end) {
		this.wrapped.insert(dstOffset, s, start, end);
		return this;
	}

	@Override
	public IStringBuilder insert(final int offset, final double d) {
		this.wrapped.insert(offset, d);
		return this;
	}

	@Override
	public IStringBuilder insert(final int offset, final float f) {
		this.wrapped.insert(offset, f);
		return this;
	}

	@Override
	public IStringBuilder insert(final int offset, final int i) {
		this.wrapped.insert(offset, i);
		return this;
	}

	@Override
	public IStringBuilder insert(final int offset, final long l) {
		this.wrapped.insert(offset, l);
		return this;
	}

	@Override
	public IStringBuilder insert(final int offset, final Object obj) {
		this.wrapped.insert(offset, obj);
		return this;
	}

	@Override
	public IStringBuilder insert(final int offset, final String str) {
		this.wrapped.insert(offset, str);
		return this;
	}

	@Override
	public int lastIndexOf(final String str) {
		return this.wrapped.lastIndexOf(str);
	}

	@Override
	public int lastIndexOf(final String str, final int fromIndex) {
		return this.wrapped.lastIndexOf(str, fromIndex);
	}

	@Override
	public int length() {
		return this.wrapped.length();
	}

	public int offsetByCodePoints(final int arg0, final int arg1) {
		return this.wrapped.offsetByCodePoints(arg0, arg1);
	}

	@Override
	public IStringBuilder replace(final int start, final int end,
			final String str) {
		this.wrapped.replace(start, end, str);
		return this;
	}

	@Override
	public IStringBuilder reverse() {
		this.wrapped.reverse();
		return this;
	}

	public void setCharAt(final int arg0, final char arg1) {
		this.wrapped.setCharAt(arg0, arg1);
	}

	public void setLength(final int arg0) {
		this.wrapped.setLength(arg0);
	}

	public CharSequence subSequence(final int arg0, final int arg1) {
		return this.wrapped.subSequence(arg0, arg1);
	}

	public String substring(final int arg0) {
		return this.wrapped.substring(arg0);
	}

	public String substring(final int arg0, final int arg1) {
		return this.wrapped.substring(arg0, arg1);
	}

	@Override
	public String toString() {
		return this.wrapped.toString();
	}

	public void trimToSize() {
		this.wrapped.trimToSize();
	}
}