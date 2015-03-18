package org.javaq.chartfaces.demo;

import java.util.ArrayList;
import java.util.List;

public class PixelMap {
	private final int index;
	private final int skip;

	public static PixelMap generate(int[][] mapMatrix) {
		PixelMap map = new PixelMap(mapMatrix, 0, 1);
		map.init();
		return map;
	}

	public static PixelMap generate(int[][] mapMatrix, int index, int skip) {
		PixelMap map = new PixelMap(mapMatrix, index, skip);
		map.init();
		return map;
	}

	private final int width;
	private final int height;

	/**
	 * @return the colorsArray
	 */
	protected int[][] getValueArray() {
		return valueArray;
	}

	private final int[][] valueArray;

	private final List<Integer> xcoords = new ArrayList<Integer>();
	private final List<Integer> ycoords = new ArrayList<Integer>();
	private final List<Integer> values = new ArrayList<Integer>();

	protected PixelMap(int[][] colorsArray) {
		this(colorsArray, 0, 1);
	}

	protected PixelMap(int[][] colorsArray, int index, int skip) {
		this.width = colorsArray[0].length;
		this.height = colorsArray.length;
		this.valueArray = colorsArray;
		this.index = index;
		this.skip = skip;
	}

	protected void init() {
		for (int i = 0; i < getWidth(); i++) {
			for (int j = 0; j < getHeight(); j++) {
				values.add(valueArray[j][i]);
				xcoords.add(i * skip + index);
				ycoords.add(j);
			}
		}
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
	public List<Integer> getValues() {
		return values;
	}

	/**
	 * @return the width
	 */
	protected int getWidth() {
		return width;
	}

	/**
	 * @return the height
	 */
	protected int getHeight() {
		return height;
	}
}