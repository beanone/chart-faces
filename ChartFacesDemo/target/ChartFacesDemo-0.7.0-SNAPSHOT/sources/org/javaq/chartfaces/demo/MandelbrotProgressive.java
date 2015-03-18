package org.javaq.chartfaces.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.javaq.chartfaces.event.ChartProgressiveRenderEvent;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("mandelbrot2")
@Scope("session")
public class MandelbrotProgressive extends Mandelbrot {
	private int progress = 0;
	private final int progressCount = 48;
	private final List<List<Double>> progressiveValues = new ArrayList<List<Double>>();
	protected final Map<String, Object> progressivePropertyMap = new HashMap<String, Object>();
	final List<Double> sizes = new ArrayList<Double>();
	final List<Double> progressiveSizes = new ArrayList<Double>();
	private boolean cancel = true;
	private boolean start = false;

	public MandelbrotProgressive() {
	}

	protected int getGreenMin() {
		return 20;
	}

	public boolean getCancel() {
		return cancel;
	}

	public boolean getStart() {
		return start;
	}

	@Override
	public List<List<Double>> getValues() {
		return progressiveValues;
	}

	public void progressiveCompute(ChartProgressiveRenderEvent event) {
		checkInit();
		progressiveValues.clear();
		progressivePropertyMap.clear();
		progressiveSizes.clear();
		List<Double> xCoords = new ArrayList<Double>();
		List<Double> yCoords = new ArrayList<Double>();
		List<String> pColors = new ArrayList<String>();
		int index = event.getIndex();
		cancel = true;
		start = (index == 0);
		if (index >= 0 && index < progressCount) {
			cancel = false;
			for (int i = index; i < sizes.size(); i += progressCount) {
				pColors.add(colors.get(i));
				progress = (int) ((index + 1) * 100. / progressCount);
				xCoords.add(super.getValues().get(0).get(i));
				yCoords.add(super.getValues().get(1).get(i));
				progressiveSizes.add(sizes.get(i));
			}
		}
		progressiveValues.add(xCoords);
		progressiveValues.add(yCoords);
		progressivePropertyMap.put("fill", pColors);
	}

	public List<Double> getProgressiveSizes() {
		return progressiveSizes;
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
		cancel = false;
		start = false;
		progress = 0;
		progressiveValues.clear();
		progressivePropertyMap.clear();
		progressiveSizes.clear();
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

	public List<List<Double>> getProgressiveValues() {
		return progressiveValues;
	}

	public Map<String, Object> getProgressivePropertyMap() {
		return progressivePropertyMap;
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}
}
