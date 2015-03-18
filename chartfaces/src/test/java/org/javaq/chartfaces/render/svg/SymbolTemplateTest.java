package org.javaq.chartfaces.render.svg;

import junit.framework.Assert;

import org.junit.Test;

public class SymbolTemplateTest {

	@Test
	public void testCircleSymbol() {
		final SymbolTemplate t = SymbolTemplate.newCircle("test");
		Assert.assertEquals("test", t.getId());
		Assert.assertEquals(
				"<svg:symbol id=\"test\" viewBox=\"0 0 2 2\"><svg:circle cx=\"1\" cy=\"1\" r=\"1\"></svg:circle></svg:symbol>",
				t.toString());
	}

	@Test
	public void testEuilateralTriangleSymbol() {
		final SymbolTemplate t = SymbolTemplate.newEquilateralTriangle("test");
		Assert.assertEquals("test", t.getId());
		Assert.assertEquals(
				"<svg:symbol id=\"test\" viewBox=\"0 0 1 1\"><svg:path d=\"M0 0.933L0.5 0.067L1 0.933z\"></svg:path></svg:symbol>",
				t.toString());
	}

	@Test
	public void testFromPathSymbol() {
		final SymbolTemplate t = SymbolTemplate.newFromPaths("test", Paths.newPaths(
				new double[] { 0.0, 0.5, 1 }, new double[] { 0.933, 0.067,
						0.933 }), 0.001);
		Assert.assertEquals("test", t.getId());
		Assert.assertEquals(
				"<svg:symbol id=\"test\" viewBox=\"0.0 0.067 1.0 0.933\"><svg:path d=\"M0.0 0.933L0.5 0.067L1.0 0.933z\"></svg:path></svg:symbol>",
				t.toString());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNewCircleSymbolIdNull() {
		SymbolTemplate.newCircle(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNewEuilateralTriangleSymbolIdNull() {
		SymbolTemplate.newEquilateralTriangle(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNewFromPathSymbolIdNull() {
		SymbolTemplate.newFromPaths(null, Paths.newPaths(
				new double[] { 0.0, 0.5, 1 }, new double[] { 0.933, 0.067,
						0.933 }), 0.001);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNewRectangleSymbolIdNull() {
		SymbolTemplate.newRectangle(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNewSquareSymbolIdNull() {
		SymbolTemplate.newSquare(null);
	}

	@Test
	public void testRectangleSymbol() {
		final SymbolTemplate t = SymbolTemplate.newRectangle("test");
		Assert.assertEquals("test", t.getId());
		Assert.assertEquals(
				"<svg:symbol id=\"test\"><svg:rect height=\"100%\" width=\"100%\" x=\"0\" y=\"0\"></svg:rect></svg:symbol>",
				t.toString());
	}

	@Test
	public void testSquareSymbol() {
		final SymbolTemplate t = SymbolTemplate.newSquare("test");
		Assert.assertEquals("test", t.getId());
		Assert.assertEquals(
				"<svg:symbol id=\"test\" viewBox=\"0 0 1 1\"><svg:rect height=\"1\" width=\"1\" x=\"0\" y=\"0\"></svg:rect></svg:symbol>",
				t.toString());
	}

	@Test
	public void testSquareUpdateShape() {
		final SymbolTemplate t = SymbolTemplate.newSquare("mySquare");
		t.updateShape("style", "stroke:blue");
		Assert.assertEquals(
				"<svg:symbol id=\"mySquare\" viewBox=\"0 0 1 1\"><svg:rect height=\"1\" style=\"stroke:blue\" width=\"1\" x=\"0\" y=\"0\"></svg:rect></svg:symbol>",
				t.toString());
	}

	@Test
	public void testSquareUpdateSymbol() {
		final SymbolTemplate t = SymbolTemplate.newSquare("mySquare");
		t.updateSymbol("style", "stroke:blue");
		Assert.assertEquals(
				"<svg:symbol id=\"mySquare\" style=\"stroke:blue\" viewBox=\"0 0 1 1\"><svg:rect height=\"1\" width=\"1\" x=\"0\" y=\"0\"></svg:rect></svg:symbol>",
				t.toString());
	}
}
