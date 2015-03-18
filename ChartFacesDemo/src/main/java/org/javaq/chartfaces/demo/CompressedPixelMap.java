package org.javaq.chartfaces.demo;

import java.util.ArrayList;
import java.util.List;

public final class CompressedPixelMap extends PixelMap {
	public static PixelMap generate(int[][] mapMatrix) {
		CompressedPixelMap map = new CompressedPixelMap(mapMatrix);
		map.init();
		return map;
	}

	private final boolean[][] state;
	private final List<Integer> sizes = new ArrayList<Integer>();

	private CompressedPixelMap(int[][] colorsArray) {
		super(colorsArray);
		this.state = new boolean[getHeight()][getWidth()];
	}

	@Override
	protected void init() {
		int[] bottomRight;
		int[][] colorsArray = getValueArray();
		for (int j = 0; j < getHeight(); j++) {
			for (int i = 0; i < getWidth(); i++) {
				if (!state[j][i]) {
					bottomRight = findSquareBottomRight(i, j);
					getXcoords().add(i);
					getYcoords().add(j);
					getSizes().add(bottomRight[0] - i + 1);
					getValues().add(colorsArray[j][i]);
				}
			}
		}
	}

	private int[] findSquareBottomRight(int leftX, int topY) {
		int[][] colorsArray = getValueArray();
		int rightX = getWidth();
		int j = topY;
		int searchColor = colorsArray[topY][leftX];
		int delta;

		outer: {
			while (j < getHeight() && j < topY + rightX - leftX) {
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

	public List<Integer> getSizes() {
		return this.sizes;
	}
}
