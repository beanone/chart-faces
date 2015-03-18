package org.javaq.chartfaces.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("mandelbrot1")
@Scope("session")
public class MandelbrotWithCompression extends Mandelbrot {
	final List<Double> sizes = new ArrayList<Double>();

	public MandelbrotWithCompression() {
	}

	@Override
	public double getScale() {
		return CANVAS_SIZE / (xmax - xmin);
	}

	public List<Double> getSizes() {
		return sizes;
	}

	@Override
	protected void clear() {
		super.clear();
		sizes.clear();
	}

	@Override
	protected PixelMap getColorMap(int[][] iterations) {
		return CompressedPixelMap.generate(iterations);
	}

	@Override
	protected void init() {
		CompressedPixelMap pixels = (CompressedPixelMap) generatePixelMap();
		generateColors(pixels, pixels.getSizes(), sizes);
	}
}
