package org.javaq.chartfaces.render.svg;

import junit.framework.Assert;

import org.junit.Test;


public class QuoteTest {
	@Test
	public void testCanNest() {
		Assert.assertFalse(Quote.DoubleQuote.canNest(Quote.CurlyBracket));
		Assert.assertFalse(Quote.DoubleQuote.canNest(Quote.SquareBracket));
		Assert.assertFalse(Quote.DoubleQuote.canNest(Quote.DoubleQuote));
		Assert.assertTrue(Quote.DoubleQuote.canNest(Quote.SingleQuote));
		Assert.assertFalse(Quote.DoubleQuote.canNest(Quote.AngleTag));
		Assert.assertFalse(Quote.DoubleQuote.canNest(Quote.EmptyTag));
		Assert.assertFalse(Quote.DoubleQuote.canNest(Quote.EndTag));

		Assert.assertFalse(Quote.SingleQuote.canNest(Quote.CurlyBracket));
		Assert.assertFalse(Quote.SingleQuote.canNest(Quote.SquareBracket));
		Assert.assertTrue(Quote.SingleQuote.canNest(Quote.DoubleQuote));
		Assert.assertFalse(Quote.SingleQuote.canNest(Quote.SingleQuote));
		Assert.assertFalse(Quote.SingleQuote.canNest(Quote.AngleTag));
		Assert.assertFalse(Quote.SingleQuote.canNest(Quote.EmptyTag));
		Assert.assertFalse(Quote.SingleQuote.canNest(Quote.EndTag));

		Assert.assertFalse(Quote.AngleTag.canNest(Quote.CurlyBracket));
		Assert.assertFalse(Quote.AngleTag.canNest(Quote.SquareBracket));
		Assert.assertTrue(Quote.AngleTag.canNest(Quote.DoubleQuote));
		Assert.assertTrue(Quote.AngleTag.canNest(Quote.SingleQuote));
		Assert.assertFalse(Quote.AngleTag.canNest(Quote.AngleTag));
		Assert.assertFalse(Quote.AngleTag.canNest(Quote.EmptyTag));
		Assert.assertFalse(Quote.AngleTag.canNest(Quote.EndTag));

		Assert.assertFalse(Quote.EmptyTag.canNest(Quote.CurlyBracket));
		Assert.assertFalse(Quote.EmptyTag.canNest(Quote.SquareBracket));
		Assert.assertFalse(Quote.EmptyTag.canNest(Quote.DoubleQuote));
		Assert.assertFalse(Quote.EmptyTag.canNest(Quote.SingleQuote));
		Assert.assertFalse(Quote.EmptyTag.canNest(Quote.AngleTag));
		Assert.assertFalse(Quote.EmptyTag.canNest(Quote.EmptyTag));
		Assert.assertFalse(Quote.EmptyTag.canNest(Quote.EndTag));

		Assert.assertFalse(Quote.EndTag.canNest(Quote.CurlyBracket));
		Assert.assertFalse(Quote.EndTag.canNest(Quote.SquareBracket));
		Assert.assertFalse(Quote.EndTag.canNest(Quote.DoubleQuote));
		Assert.assertFalse(Quote.EndTag.canNest(Quote.SingleQuote));
		Assert.assertFalse(Quote.EndTag.canNest(Quote.AngleTag));
		Assert.assertFalse(Quote.EndTag.canNest(Quote.EmptyTag));
		Assert.assertFalse(Quote.EndTag.canNest(Quote.EndTag));

		Assert.assertTrue(Quote.CurlyBracket.canNest(Quote.CurlyBracket));
		Assert.assertTrue(Quote.CurlyBracket.canNest(Quote.SquareBracket));
		Assert.assertTrue(Quote.CurlyBracket.canNest(Quote.DoubleQuote));
		Assert.assertTrue(Quote.CurlyBracket.canNest(Quote.SingleQuote));
		Assert.assertFalse(Quote.CurlyBracket.canNest(Quote.AngleTag));
		Assert.assertFalse(Quote.CurlyBracket.canNest(Quote.EmptyTag));
		Assert.assertFalse(Quote.CurlyBracket.canNest(Quote.EndTag));

		Assert.assertTrue(Quote.SquareBracket.canNest(Quote.CurlyBracket));
		Assert.assertTrue(Quote.SquareBracket.canNest(Quote.SquareBracket));
		Assert.assertTrue(Quote.SquareBracket.canNest(Quote.DoubleQuote));
		Assert.assertTrue(Quote.SquareBracket.canNest(Quote.SingleQuote));
		Assert.assertFalse(Quote.SquareBracket.canNest(Quote.AngleTag));
		Assert.assertFalse(Quote.SquareBracket.canNest(Quote.EmptyTag));
		Assert.assertFalse(Quote.SquareBracket.canNest(Quote.EndTag));
	}

	@Test
	public void testMatch() {
		Assert.assertTrue(Quote.DoubleQuote.match(Quote.DoubleQuote));
		Assert.assertTrue(Quote.SingleQuote.match(Quote.SingleQuote));
		Assert.assertFalse(Quote.DoubleQuote.match(Quote.SingleQuote));
		Assert.assertTrue(Quote.AngleTag.match(Quote.EmptyTag));
		Assert.assertFalse(Quote.AngleTag.match(Quote.EndTag));
	}
}