package org.javaq.chartfaces.demo;

import java.util.ArrayList;
import java.util.List;

public class Pixels {
	private final int width;
	private final int height;
	private final int[][] colorsArray;
	private final boolean[][] state;

	private final List<Integer> xcoords = new ArrayList<Integer>();
	private final List<Integer> ycoords = new ArrayList<Integer>();
	private final List<Integer> colors = new ArrayList<Integer>();
	private final List<Integer> sizes = new ArrayList<Integer>();

	public Pixels(int[][] colorsArray) {
		this.width = colorsArray[0].length;
		this.height = colorsArray.length;
		this.colorsArray = colorsArray;
		this.state = new boolean[this.height][this.width];
	}

	public void compress() {
		int[] bottomRight;
		for (int j = 0; j < this.height; j++) {
			for (int i = 0; i < this.width; i++) {
				if (!state[j][i]) {
					bottomRight = findSquareBottomRight(i, j);
					xcoords.add(i);
					ycoords.add(j);
					sizes.add(bottomRight[0] - i + 1);
					colors.add(colorsArray[j][i]);
				}
			}
		}
	}

	protected int[] findSquareBottomRight(int leftX, int topY) {
		int rightX = width;
		int j = topY;
		int searchColor = colorsArray[topY][leftX];
		int delta;

		outer: {
			while (j < height && j < topY + rightX - leftX) {
				for (int i = leftX; i < rightX; i++) {
					if (colorsArray[j][i] != searchColor || state[j][i]) {
						if (i - leftX <= j - topY) {
							break outer;
						} else {
							rightX = i;
							break;
						}
					}
				}
				j++;
			}
		}

		delta = j - topY;
		rightX = leftX + delta - 1;
		int bottomY = topY + delta - 1;

		for (j = topY; j < bottomY + 1; j++) {
			for (int i = leftX; i < rightX + 1; i++) {
				state[j][i] = true;
			}
		}

		return new int[] { rightX, bottomY };
	}

	/**
	 * @return the xCoord
	 */
	public List<Integer> getXcoords() {
		return xcoords;
	}

	/**
	 * @return the yCoords
	 */
	public List<Integer> getYcoords() {
		return ycoords;
	}

	/**
	 * @return the colors
	 */
	public List<Integer> getColors() {
		return colors;
	}

	public List<Integer> getSizes() {
		return this.sizes;
	}
}
