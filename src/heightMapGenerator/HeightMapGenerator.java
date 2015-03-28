package heightMapGenerator;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
* HeightMapGenerator.java
* @author Pierfrancesco Soffritti
*
*/

public class HeightMapGenerator {
	
	private BufferedImage cachedHeightMapImage;
	private int mapSize;
	private int seed;
	
	private float perlinNoiseFrequency;
	
	private float perturbDistance;
	private float perturbFrequency;
	
	private int erodeIterations;
	private float erodeSmoothness;
	
	private boolean useGrayScale;
	private boolean printInfo;
	
	private float min, max;
	
	public HeightMapGenerator(int mapSize, int seed) {
		this.mapSize = mapSize;
		this.seed = seed;
		
		this.perlinNoiseFrequency = 0;
		
		this.perturbDistance = 0;
		this.perturbFrequency = 0;
		
		this.erodeIterations = 0;
		this.erodeSmoothness = 0;
		
		useGrayScale = true;
		printInfo = false;
	}
	
	
	public HeightMapGenerator(int mapSize, int seed, float perlinNoiseFrequency,
			float  perturbFrequency, float perturbDistance, int erodeIterations, 
			float erodeSmoothness) {
		this.mapSize = mapSize;
		this.seed = seed;
		
		this.perlinNoiseFrequency = perlinNoiseFrequency;
		
		this.perturbDistance = perturbDistance;
		this.perturbFrequency = perturbFrequency;
		
		this.erodeIterations = erodeIterations;
		this.erodeSmoothness = erodeSmoothness;
		
		useGrayScale = true;
		printInfo = false;
	}
	
	public BufferedImage generateHeightMap() {
		
		HeightMap heightMap = new HeightMap(mapSize, seed);
		// testing
		//heightMap.initPerlinNoise2(7, 0.5f);
		heightMap.addPerlinNoise(perlinNoiseFrequency);
		heightMap.perturb(perturbFrequency, perturbDistance);
		for(int i=0; i<erodeIterations; i++)
			heightMap.erode(erodeSmoothness);
		heightMap.smoothen();
		
		int imageType = (useGrayScale == true ? BufferedImage.TYPE_BYTE_GRAY : BufferedImage.TYPE_INT_RGB);
		
		cachedHeightMapImage = new BufferedImage(
				heightMap.getSize(),
				heightMap.getSize(),
				imageType );
		
		setMapInfo(heightMap);
		
		for(int i=0; i<heightMap.getSize(); i++) {
			for(int j=0; j<heightMap.getSize(); j++) {
				cachedHeightMapImage.setRGB(i, j, getColor(heightMap.getHeights()[i][j]));
			}
		}
		
		if(printInfo)
			System.out.println(getMapInfo());
		
		return cachedHeightMapImage;
	}
	
	public BufferedImage generateRandomHeightMap() {
		
		Random r = new Random();
		this.perlinNoiseFrequency = r.nextFloat()*r.nextInt(100);
		this.perturbFrequency = r.nextFloat()*r.nextInt(200);
		this.perturbDistance = r.nextFloat()*r.nextInt(200);
		this.erodeIterations = r.nextInt(r.nextInt(200)+1);
		this.erodeSmoothness = r.nextFloat()*r.nextInt();
		
		return this.generateHeightMap();
	}
	
	public BufferedImage generateSampleHeightMap() {
		
		this.perlinNoiseFrequency = 6.0f;
		this.perturbFrequency = 320.0f;
		this.perturbDistance = 32.0f;
		this.erodeIterations = 20;
		this.erodeSmoothness = 160.0f;
		
		return this.generateHeightMap();
	}
	
	private int getColor(float f) {
		
		// ?? naive solution
		//int value = (int) ((f+0.5) * 255);
		
		// i'm adding an offset of |min|. My values will go from 0 to max+|min|
		// in this way i can easily  distribute them on the interval [0, 255]
		int value = (int) ( ((f+Math.abs(min)) * 255)/(max+Math.abs(min)) );
		
		int r = 0, g = 0, b = 0;
		
		if(useGrayScale) {
			r = value;// red component 0...255
			g = value;// green component 0...255
			b = value;// blue component 0...255
		}
		else {
			if(value >= 0 && value <= 42) {
				r = 0; g = 0; b = (value*255)/42;
			}
			if(value >= 43 && value <= 85) {
				r = 0; g = (value*255)/85; b = 255;
			}
			if(value >= 86 && value <= 127) {
				r = 0; g = 255; b = 255-(value*255)/127;
			}
			if(value >= 128 && value <= 170) {
				r = (value*255)/170; g = 255; b = 0;
			}
			if(value >= 171 && value <= 245){
				r = 255; g = 255-(value*255)/245; b = 0;
			}
			if(value >= 246 && value <= 255){
				r = 255; g = 0; b = (value*255)/255;
			}
			
			/*
			if(value >= 0 && value <= 85) {
				b = (value*255)/85 ; g = 0; r = 0;
			}
			else if(value >= 86 && value <= 170) {
				b = 0; g = (value*255)/170; r = 0;
			}
			else {
				b = 0; g = 0; r = value;
			}
			*/
		}
		int color = (r << 16) | (g << 8) | b;
		
		return color;
	}
	
	private void setMapInfo(HeightMap heightMap) {
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


	public float getPerlinNoiseFrequency() {
		return perlinNoiseFrequency;
	}


	public void setPerlinNoiseFrequency(float perlinNoiseFrequency) {
		this.perlinNoiseFrequency = perlinNoiseFrequency;
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
