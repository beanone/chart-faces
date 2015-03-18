package org.javaq.chartfaces.render.svg;

import junit.framework.Assert;

import org.junit.Test;


public class StructuredStringBuilderTest {
	@Test
	public void testAppend() {
		Assert.assertEquals("test", new StructuredStringBuilder()
				.append("test").toString());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAppendNull() {
		new StructuredStringBuilder().append(null);
	}

	@Test
	public void testContentStart() {
		Assert.assertEquals("<test>",
				new StructuredStringBuilder().tagStart("test").contentStart()
						.toString());
		Assert.assertEquals("test<test1>", new StructuredStringBuilder()
				.append("test").tagStart("test1").contentStart().toString());
		Assert.assertEquals("<test anything>", new StructuredStringBuilder()
				.tagStart("test").append("anything").contentStart().toString());
		Assert.assertEquals("test<test1 anything>",
				new StructuredStringBuilder().append("test").tagStart("test1")
						.append("anything").contentStart().toString());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testContentStartNotUnquoted() {
		new StructuredStringBuilder().quote(Quote.DoubleQuote).contentStart();
	}

	@Test
	public void testIsComplete() {
		Assert.assertTrue(new StructuredStringBuilder().append("test")
				.isCompleted());
		Assert.assertFalse(new StructuredStringBuilder().quote(
				Quote.CurlyBracket).isCompleted());
		Assert.assertFalse(new StructuredStringBuilder().append("test")
				.quote(Quote.CurlyBracket).append("test").isCompleted());
		Assert.assertFalse(new StructuredStringBuilder().tagStart("test")
				.append("test").isCompleted());
		Assert.assertFalse(new StructuredStringBuilder().tagStart("test")
				.append("test").contentStart().isCompleted());
		Assert.assertTrue(new StructuredStringBuilder().tagStart("test")
				.append("test").tagEnd("test").isCompleted());
		Assert.assertTrue(new StructuredStringBuilder().tagStart("test")
				.append("test").contentStart().tagEnd("test").isCompleted());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testQuoteNotUnquoted() {
		new StructuredStringBuilder().quote(Quote.DoubleQuote).quote(
				Quote.DoubleQuote);
	}

	@Test
	public void testQuoteObjectQuote() {
		Assert.assertEquals("\"test\"",
				new StructuredStringBuilder().quote("test", Quote.DoubleQuote)
						.toString());
		Assert.assertEquals("test\"test1\"", new StructuredStringBuilder()
				.append("test").quote("test1", Quote.DoubleQuote).toString());
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testQuoteObjectQuoteDirectQuoteEndTag() {
		new StructuredStringBuilder().quote("test", Quote.EmptyTag);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testQuoteObjectQuoteDirectQuoteTagStart() {
		new StructuredStringBuilder().quote("test", Quote.AngleTag);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testQuoteObjectQuoteNotUnquoted() {
		new StructuredStringBuilder().quote(Quote.DoubleQuote).quote("test",
				Quote.SquareBracket);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testQuoteObjectQuoteObjectNull() {
		new StructuredStringBuilder().quote(null, Quote.DoubleQuote);
	}

	@Test(expected = NullPointerException.class)
	public void testQuoteObjectQuoteQuoteNull() {
		new StructuredStringBuilder().quote("test", null);
	}

	@Test
	public void testQuoteQuote() {
		Assert.assertEquals("\"",
				new StructuredStringBuilder().quote(Quote.DoubleQuote)
						.toString());
		Assert.assertEquals(
				"test\"",
				new StructuredStringBuilder().append("test")
						.quote(Quote.DoubleQuote).toString());
	}

	@Test(expected = NullPointerException.class)
	public void testQuoteQuoteArgumentNull() {
		new StructuredStringBuilder().quote(null);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testQuoteQuoteDirectQuoteEndTag() {
		new StructuredStringBuilder().quote(Quote.EmptyTag);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testQuoteQuoteDirectQuoteTagStart() {
		new StructuredStringBuilder().quote(Quote.AngleTag);
	}

	@Test
	public void testQuoteWithinQuoteAllowed() {
		new StructuredStringBuilder().quote(Quote.CurlyBracket).quote(
				Quote.CurlyBracket);
		new StructuredStringBuilder().quote(Quote.CurlyBracket).quote(
				Quote.SquareBracket);
		new StructuredStringBuilder().quote(Quote.CurlyBracket).quote(
				Quote.DoubleQuote);
		new StructuredStringBuilder().quote(Quote.CurlyBracket).quote(
				Quote.SingleQuote);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testQuoteWithinQuoteNotAllowed() {
		new StructuredStringBuilder().quote(Quote.CurlyBracket)
				.tagStart("test");
	}

	@Test
	public void testRemoveLast() {
		Assert.assertEquals("", new StructuredStringBuilder().removeLast()
				.toString());
		Assert.assertEquals("tes", new StructuredStringBuilder().append("test")
				.removeLast().toString());
	}

	@Test
	public void testStructuredStringBuilder() {
		new StructuredStringBuilder();
	}

	@Test
	public void testTagEnd() {
		Assert.assertEquals("<test/>",
				new StructuredStringBuilder().tagStart("test").tagEnd("test")
						.toString());
		Assert.assertEquals("test1<test/>", new StructuredStringBuilder()
				.append("test1").tagStart("test").tagEnd("test").toString());
		Assert.assertEquals("<test>test1</test>", new StructuredStringBuilder()
				.tagStart("test").contentStart().append("test1").tagEnd("test")
				.toString());
		Assert.assertEquals("test2<test>test1</test>",
				new StructuredStringBuilder().append("test2").tagStart("test")
						.contentStart().append("test1").tagEnd("test")
						.toString());
		Assert.assertEquals("<test anything/>", new StructuredStringBuilder()
				.tagStart("test").append("anything").tagEnd("test").toString());
		Assert.assertEquals(
				"<test anything></test>",
				new StructuredStringBuilder().tagStart("test")
						.append("anything").contentStart().tagEnd("test")
						.toString());
		Assert.assertEquals("test<test1 anything/>",
				new StructuredStringBuilder().append("test").tagStart("test1")
						.append("anything").tagEnd("test1").toString());
		Assert.assertEquals("test<test1 anything></test1>",
				new StructuredStringBuilder().append("test").tagStart("test1")
						.append("anything").contentStart().tagEnd("test1")
						.toString());
	}

	@Test(expected = NullPointerException.class)
	public void testTagEndArgumentNull() {
		new StructuredStringBuilder().tagStart("test").tagEnd(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testTagEndNotUnquoted() {
		new StructuredStringBuilder().tagStart("test")
				.quote(Quote.CurlyBracket).tagEnd("test");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testTagEndUnmatchedTagStart() {
		new StructuredStringBuilder().tagStart("test").tagEnd("test1");
	}

	@Test
	public void testTagStart() {
		Assert.assertEquals("<test ",
				new StructuredStringBuilder().tagStart("test").toString());
		Assert.assertEquals("test<test1 ", new StructuredStringBuilder()
				.append("test").tagStart("test1").toString());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testTagStartArgumentNull() {
		new StructuredStringBuilder().tagStart(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testTagStartNotUnquoted() {
		new StructuredStringBuilder().quote(Quote.DoubleQuote).tagStart("test");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testTagStartNotUnquoted1() {
		new StructuredStringBuilder().tagStart("test").tagStart("test");
	}

	@Test
	public void testTextQuote() {
		Assert.assertEquals("\"test\"", new StructuredStringBuilder()
				.textQuote("test").toString());
		Assert.assertEquals("test\"test1\"", new StructuredStringBuilder()
				.append("test").textQuote("test1").toString());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testTextQuoteArgumentNull() {
		new StructuredStringBuilder().textQuote(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testTextQuoteNotUnquoted() {
		new StructuredStringBuilder().quote(Quote.DoubleQuote)
				.textQuote("test");
	}

	@Test
	public void testUnquote() {
		Assert.assertEquals(
				"\"test\"",
				new StructuredStringBuilder().quote(Quote.DoubleQuote)
						.append("test").unquote(Quote.DoubleQuote).toString());
		Assert.assertEquals("test\"test1\"", new StructuredStringBuilder()
				.append("test").quote(Quote.DoubleQuote).append("test1")
				.unquote(Quote.DoubleQuote).toString());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testUnquoteArgumentNull() {
		new StructuredStringBuilder().quote(Quote.CurlyBracket).unquote(null);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testUnquoteEmptyTag() {
		new StructuredStringBuilder().tagStart("test").unquote(Quote.EmptyTag);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testUnquoteNotQuoted() {
		new StructuredStringBuilder().append("test").unquote(Quote.SingleQuote);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testUnquoteQuoteNotMatched() {
		new StructuredStringBuilder().quote(Quote.DoubleQuote).append("test")
				.unquote(Quote.SingleQuote);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testUnquoteStartTag() {
		new StructuredStringBuilder().tagStart("test").unquote(Quote.AngleTag);
	}
}