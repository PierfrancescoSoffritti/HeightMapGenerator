package heightMap;
import java.awt.image.BufferedImage;

/**
* AbstractHeightMapGenerator.java
* @author Pierfrancesco Soffritti
*
*/

public abstract class AbstractHeightMapGenerator {
	
	protected BufferedImage cachedHeightMapImage;
	protected HeightMap cachedHeightMap;
	
	protected int mapSize;
	protected int seed;
	
	protected float perturbFrequency;
	protected float perturbDistance;
	protected int erodeIterations;
	protected float erodeSmoothness;
	
	protected boolean useGrayScale;
	protected boolean printInfo;

	
	protected float min, max;
	
	protected AbstractHeightMapGenerator(int mapSize, int seed) {
		this.mapSize = mapSize;
		this.seed = seed;
		
		this.perturbDistance = 0;
		this.perturbFrequency = 0;
		
		this.erodeIterations = 0;
		this.erodeSmoothness = 0;
		
		useGrayScale = true;
		
		printInfo = false;
	}
	
	public abstract BufferedImage generateHeightMap();	
	public abstract BufferedImage generateRandomHeightMap();	
	public abstract BufferedImage generateSampleHeightMap();
	
	protected abstract int getColor(float f);
	
	protected void setMapInfo(HeightMap heightMap) {
		min = 1000;
		max = -1000;		
		for(int i=0; i<heightMap.getSize(); i++) {
			for(int j=0; j<heightMap.getSize(); j++) {
				if(heightMap.getHeights()[i][j] < min)
					min = heightMap.getHeights()[i][j];
				if(heightMap.getHeights()[i][j] > max)
					max = heightMap.getHeights()[i][j];
			}
		}
	}
	
	public String getMapInfo() {		
		String res = "min: " +min +"  max: " +max;
		return res;
	}
	
	public HeightMap getCachedHeightMap() {
		return this.cachedHeightMap;
	}
	
	public BufferedImage getCachedHeightMapImage() {
		return this.cachedHeightMapImage;
	}
	
	public boolean getUseGrayScale() {
		return useGrayScale;
	}
	
	public void setUseGrayScale(boolean useGrayScale) {
		this.useGrayScale = useGrayScale;
	}
	
	public boolean isPrintInfoEnabled() {
		return printInfo;
	}
	
	public void enablePrintInfo(boolean printInfo) {
		this.printInfo = printInfo;
	}


	public int getMapSize() {
		return mapSize;
	}


	public void setMapSize(int mapSize) {
		this.mapSize = mapSize;
	}


	public int getSeed() {
		return seed;
	}


	public void setSeed(int seed) {
		this.seed = seed;
	}
	
	public float getPerturbDistance() {
		return perturbDistance;
	}


	public void setPerturbDistance(float perturbDistance) {
		this.perturbDistance = perturbDistance;
	}


	public float getPerturbFrequency() {
		return perturbFrequency;
	}


	public void setPerturbFrequency(float perturbFrequency) {
		this.perturbFrequency = perturbFrequency;
	}


	public int getErodeIterations() {
		return erodeIterations;
	}


	public void setErodeIterations(int erodeIterations) {
		this.erodeIterations = erodeIterations;
	}


	public float getErodeSmoothness() {
		return erodeSmoothness;
	}


	public void setErodeSmoothness(float erodeSmoothness) {
		this.erodeSmoothness = erodeSmoothness;
	}

}
