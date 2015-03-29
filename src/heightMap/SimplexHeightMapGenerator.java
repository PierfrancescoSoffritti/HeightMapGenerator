package heightMap;

import java.awt.image.BufferedImage;
import java.util.Random;

/**
* SimplexHeightMapGenerator.java
* @author Pierfrancesco Soffritti
*
*/

public class SimplexHeightMapGenerator extends AbstractHeightMapGenerator {

	private int largestFeature;	
	// Persistence is used to affect the appearance of the terrai.
	// High persistance (towards 1) gives rocky mountainous terrain. 
	// Low persistance (towards 0) gives slowly varying flat terrain.
	private double persistance;
	
	public SimplexHeightMapGenerator(int mapSize, int seed) {
		super(mapSize, seed);
		
		this.largestFeature = 0;
		this.persistance = 0;
	}
	
	public SimplexHeightMapGenerator(int mapSize, int seed, int largestFeature,
			double persistance,	float  perturbFrequency, float perturbDistance,
			int erodeIterations, float erodeSmoothness) {
		super(mapSize, seed);
		
		this.largestFeature = largestFeature;
		this.persistance = persistance;
		
		this.perturbDistance = perturbDistance;
		this.perturbFrequency = perturbFrequency;
		
		this.erodeIterations = erodeIterations;
		this.erodeSmoothness = erodeSmoothness;
	}
	
	@Override
	public BufferedImage generateHeightMap() {
		
		cachedHeightMap = new HeightMap(mapSize, seed);
		cachedHeightMap.addSimplexNoise(largestFeature, persistance);
		cachedHeightMap.perturb(perturbFrequency, perturbDistance);
		for(int i=0; i<erodeIterations; i++)
			cachedHeightMap.erode(erodeSmoothness);
		cachedHeightMap.smoothen();
		
		int imageType = (useGrayScale == true ? BufferedImage.TYPE_BYTE_GRAY : BufferedImage.TYPE_INT_RGB);
		
		cachedHeightMapImage = new BufferedImage(
				cachedHeightMap.getSize(),
				cachedHeightMap.getSize(),
				imageType );
				
		setMapInfo(cachedHeightMap);
		
		for(int i=0; i<cachedHeightMap.getSize(); i++) {
			for(int j=0; j<cachedHeightMap.getSize(); j++) {
				cachedHeightMapImage.setRGB(i, j, getColor(cachedHeightMap.getHeights()[i][j]));
			}
		}
		
		if(printInfo)
			System.out.println(getMapInfo());
		
		return cachedHeightMapImage;
	}
	
	@Override
	public BufferedImage generateRandomHeightMap() {
		
		Random r = new Random();
		this.largestFeature = r.nextInt(1000000);
		this.persistance = r.nextDouble();
		this.perturbFrequency = r.nextFloat()*r.nextInt(200);
		this.perturbDistance = r.nextFloat()*r.nextInt(200);
		this.erodeIterations = r.nextInt(r.nextInt(200)+1);
		this.erodeSmoothness = r.nextFloat()*r.nextInt();
		
		return this.generateHeightMap();
	}
	
	@Override
	public BufferedImage generateSampleHeightMap() {
		
		this.largestFeature = 100; 
		this.persistance = 0.9;
		this.perturbFrequency = 320.0f;
		this.perturbDistance = 32.0f;
		this.erodeIterations = 20;
		this.erodeSmoothness = 160.0f;
		
		return this.generateHeightMap();
	}
	
	@Override
	protected int getColor(float f) {
		
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
	
	public int getLargestFeature() {
		return this.largestFeature;
	}
	
	public void setLargestFeature(int largestFeature) {
		this.largestFeature = largestFeature;
	}
	
	public double getPersistance() {
		return this.persistance;
	}
	
	public void setPersistance(double persistance) {
		this.persistance = persistance;
	}
}