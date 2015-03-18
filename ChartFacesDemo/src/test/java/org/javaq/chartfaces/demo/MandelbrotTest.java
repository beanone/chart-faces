package org.javaq.chartfaces.demo;

import junit.framework.Assert;

import org.junit.Test;

public class MandelbrotTest {

	@Test
	public void testCreateTickValues() {
		Mandelbrot mb = new Mandelbrot();
		double[] values = mb.createTickValues(-2, 1, 6);
		assertArrayEquals(new double[]{-2,-1.5,-1,-0.5,0,0.5, 1}, values);

		values = mb.createTickValues(-1.7321, -1.7, 6);
		assertArrayEquals(new double[]{-1.73, -1.725, -1.72, -1.715, -1.71, -1.705, -1.7}, values);
	}

	private void assertArrayEquals(double [] arr1, double[] arr2) {
		if(arr1==null) {
			Assert.assertNull(arr2);
		} else {
			Assert.assertNotNull(arr2);
			Assert.assertEquals(arr1.length, arr2.length);
			for(int i=0; i<arr1.length; i++) {
				Assert.assertEquals(arr1[i], arr2[i]);
			}
		}
	}
}
