package org.javaq.chartfaces.demo;

import org.javaq.chartfaces.event.ChartLiveEvent;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("randomData")
@Scope("session")
public class RandomData {
	private final double[][] values;
	private final double minx = 0.;
	private final double maxx = 2. * Math.PI;
	private final double miny = 0.;
	private final double maxy = 70.;
	private final CircularCountDown counter = new CircularCountDown(50);
	private final double[] y = { 0, 10, 20, 30, 40, 50, 60 };

	public RandomData() {
		values = new double[2][51];
		double x;
		for (int i = 0; i < 51; i++) {
			x = makeX(i);
			values[0][i] = x;
			values[1][i] = makeY(x);
		}
	}

	private double makeX(int i) {
		return (50 - i) * maxx / 50.;
	}

	private double makeY(double x) {
		return Math.sin(x) * 25. + 30. + getRandomNumber();
	}

	private double getRandomNumber() {
		double random = Math.random();
		return (random - 0.5) * 10.;
	}

	/**
	 * @return the values
	 */
	public double[][] getValues() {
		return values;
	}

	/**
	 * @return the minx
	 */
	public double getMinx() {
		return minx;
	}

	/**
	 * @return the maxx
	 */
	public double getMaxx() {
		return maxx;
	}

	/**
	 * @return the miny
	 */
	public double getMiny() {
		return miny;
	}

	/**
	 * @return the maxy
	 */
	public double getMaxy() {
		return maxy;
	}

	private void shiftLeftOne() {
		for (int i = 50; i > 0; i--) {
			values[1][i] = values[1][i - 1];
		}
	}

	public void refresh(ChartLiveEvent event) {
		shiftLeftOne();
		int count = counter.next();
		values[1][0] = makeY(makeX(count));
	}

	public double[] getY() {
		return y;
	}

	private static class CircularCountDown {
		private final int max;
		private int count;

		private CircularCountDown(int max) {
			this.max = max;
		}

		private int next() {
			return (count--) % max;
		}
	}
}
