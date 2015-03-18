package org.javaq.chartfaces.dataspec.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import junit.framework.Assert;

import org.javaq.chartfaces.api.IChartingDataConverter;
import org.javaq.chartfaces.api.IChartingDataXYView;
import org.javaq.chartfaces.component.impl.ChartingDataXYView;
import org.junit.Test;


public class DefaultChartingDataConverterTest {

	@Test
	public void testToChartingDateFrom2DArray() {
		final double[][] data = new double[][] { new double[] { 1.0, 2.0 },
				new double[] { 10., 20. } };
		final IChartingDataConverter cdc = new DefaultChartingDataConverter();
		final IChartingDataXYView cd = cdc.toChartingDateXYView(data);
		Assert.assertNotNull(cd);
		final Iterator<?> xIter = cd.getXValues().iterator();
		final Iterator<?> yIter = cd.getYValues().iterator();
		Assert.assertEquals(1.0, xIter.next());
		Assert.assertEquals(2.0, xIter.next());
		Assert.assertEquals(10.0, yIter.next());
		Assert.assertEquals(20.0, yIter.next());
	}

	@Test
	public void testToChartingDateFromArray() {
		final double[] data = new double[] { 1.0, 2.0 };
		final IChartingDataConverter cdc = new DefaultChartingDataConverter();
		final IChartingDataXYView cd = cdc.toChartingDateXYView(data);
		Assert.assertNotNull(cd);
		final Iterator<?> yIter = cd.getYValues().iterator();
		Assert.assertEquals(1.0, yIter.next());
		Assert.assertEquals(2.0, yIter.next());

		Assert.assertNull(cd.getXValues());
	}

	@Test
	public void testToChartingDateFromArrayOfList() {
		final List<Double> dataX = new ArrayList<Double>();
		dataX.add(1.0);
		dataX.add(2.0);
		final List<Double> dataY = new ArrayList<Double>();
		dataY.add(10.0);
		dataY.add(20.0);
		final List<?>[] data = new List[] { dataX, dataY };
		final IChartingDataConverter cdc = new DefaultChartingDataConverter();
		final IChartingDataXYView cd = cdc.toChartingDateXYView(data);
		Assert.assertNotNull(cd);
		final Iterator<?> xIter = cd.getXValues().iterator();
		final Iterator<?> yIter = cd.getYValues().iterator();
		Assert.assertEquals(1.0, xIter.next());
		Assert.assertEquals(2.0, xIter.next());
		Assert.assertEquals(10.0, yIter.next());
		Assert.assertEquals(20.0, yIter.next());
	}

	@Test
	public void testToChartingDateFromArrayOfObjects() {
		final Double[] data = new Double[] { 1.0, 2.0 };
		final IChartingDataConverter cdc = new DefaultChartingDataConverter();
		final IChartingDataXYView cd = cdc.toChartingDateXYView(data);
		Assert.assertNotNull(cd);
		final Iterator<?> yIter = cd.getYValues().iterator();
		Assert.assertEquals(1.0, yIter.next());
		Assert.assertEquals(2.0, yIter.next());

		Assert.assertNull(cd.getXValues());
	}

	@Test
	public void testToChartingDateFromArrayOfObjectsWithNull() {
		final Double[] data = new Double[] { null, 2.0 };
		final IChartingDataConverter cdc = new DefaultChartingDataConverter();
		final IChartingDataXYView cd = cdc.toChartingDateXYView(data);
		Assert.assertNotNull(cd);
		final Iterator<?> yIter = cd.getYValues().iterator();
		Assert.assertNull(yIter.next());
		Assert.assertEquals(2.0, yIter.next());

		Assert.assertNull(cd.getXValues());
	}

	@Test
	public void testToChartingDateFromChartingData() {
		final IChartingDataXYView cd = new ChartingDataXYView(null, null);
		final IChartingDataConverter cdc = new DefaultChartingDataConverter();
		final IChartingDataXYView cd1 = cdc.toChartingDateXYView(cd);
		Assert.assertSame(cd, cd1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testToChartingDateFromInvalidData() {
		final IChartingDataConverter cdc = new DefaultChartingDataConverter();
		cdc.toChartingDateXYView(new Object());
	}

	@Test
	public void testToChartingDateFromList() {
		final List<?> data = Arrays.asList(new double[] { 1.0, 2.0 });
		final IChartingDataConverter cdc = new DefaultChartingDataConverter();
		final IChartingDataXYView cd = cdc.toChartingDateXYView(data);
		Assert.assertNotNull(cd);
		final Iterator<?> yIter = cd.getYValues().iterator();
		Assert.assertEquals(1.0, yIter.next());
		Assert.assertEquals(2.0, yIter.next());

		Assert.assertNull(cd.getXValues());
	}

	@Test
	public void testToChartingDateFromListOfArray() {
		final List<double[]> data = new ArrayList<double[]>();
		data.add(new double[] { 1.0, 2.0 });
		data.add(new double[] { 10., 20. });
		final IChartingDataConverter cdc = new DefaultChartingDataConverter();
		final IChartingDataXYView cd = cdc.toChartingDateXYView(data);
		Assert.assertNotNull(cd);
		final Iterator<?> xIter = cd.getXValues().iterator();
		final Iterator<?> yIter = cd.getYValues().iterator();
		Assert.assertEquals(1.0, xIter.next());
		Assert.assertEquals(2.0, xIter.next());
		Assert.assertEquals(10.0, yIter.next());
		Assert.assertEquals(20.0, yIter.next());
	}

	@Test
	public void testToChartingDateFromListOfList() {
		final List<Double> dataX = new ArrayList<Double>();
		dataX.add(1.0);
		dataX.add(2.0);
		final List<Double> dataY = new ArrayList<Double>();
		dataY.add(10.0);
		dataY.add(20.0);
		final List<List<Double>> data = new ArrayList<List<Double>>();
		data.add(dataX);
		data.add(dataY);
		final IChartingDataConverter cdc = new DefaultChartingDataConverter();
		final IChartingDataXYView cd = cdc.toChartingDateXYView(data);
		Assert.assertNotNull(cd);
		final Iterator<?> xIter = cd.getXValues().iterator();
		final Iterator<?> yIter = cd.getYValues().iterator();
		Assert.assertEquals(1.0, xIter.next());
		Assert.assertEquals(2.0, xIter.next());
		Assert.assertEquals(10.0, yIter.next());
		Assert.assertEquals(20.0, yIter.next());
	}
}
