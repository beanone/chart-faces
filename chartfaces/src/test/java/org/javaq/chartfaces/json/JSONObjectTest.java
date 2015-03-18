package org.javaq.chartfaces.json;

import junit.framework.Assert;

import org.junit.Test;

public class JSONObjectTest {

	@Test
	public void testIsByteStandardProperty() {
		assertTrue(Byte.class);
	}

	@Test
	public void testIsShortStandardProperty() {
		assertTrue(Short.class);
	}

	@Test
	public void testIsIntegerStandardProperty() {
		assertTrue(Integer.class);
	}

	@Test
	public void testIsLongStandardProperty() {
		assertTrue(Long.class);
	}

	@Test
	public void testIsFloatStandardProperty() {
		assertTrue(Float.class);
	}

	@Test
	public void testIsDoubleStandardProperty() {
		assertTrue(Double.class);
	}

	@Test
	public void testIsCharacterStandardProperty() {
		assertTrue(Character.class);
	}

	@Test
	public void testIsStringStandardProperty() {
		assertTrue(String.class);
	}

	@Test
	public void testIsBooleanStandardProperty() {
		assertTrue(Boolean.class);
	}
	
	@Test
	public void testIsbooleanStandardProperty() {
		Assert.assertTrue(JSONObject.isStandardProperty(boolean.class));
	}
	
	@Test
	public void testIsintStandardProperty() {
		Assert.assertTrue(JSONObject.isStandardProperty(int.class));
	}
	
	@Test
	public void testIsshortStandardProperty() {
		Assert.assertTrue(JSONObject.isStandardProperty(short.class));
	}
	
	@Test
	public void testIslongStandardProperty() {
		Assert.assertTrue(JSONObject.isStandardProperty(long.class));
	}
	
	@Test
	public void testIscharStandardProperty() {
		Assert.assertTrue(JSONObject.isStandardProperty(char.class));
	}
	
	@Test
	public void testIsfloatStandardProperty() {
		Assert.assertTrue(JSONObject.isStandardProperty(float.class));
	}
	
	@Test
	public void testIsdoubleStandardProperty() {
		Assert.assertTrue(JSONObject.isStandardProperty(double.class));
	}
	
	@Test
	public void testIsbyteStandardProperty() {
		Assert.assertTrue(JSONObject.isStandardProperty(byte.class));
	}
	
	private void assertTrue(Class<?> clazz) {
		Class<?> klass = clazz;
		while (klass != null) {
			Assert.assertTrue(JSONObject.isStandardProperty(klass));
			klass = klass.getSuperclass();
		}
	}
}
