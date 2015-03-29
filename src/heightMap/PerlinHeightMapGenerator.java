package heightMap;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
* PerlinHeightMapGenerator.java
* @author Pierfrancesco Soffritti
*
*/

public class PerlinHeightMapGenerator extends AbstractHeightMapGenerator {

	private float perlinNoiseFrequency;
	
	private boolean useHSBScale;
	
	public PerlinHeightMapGenerator(int mapSize, int seed) {
		super(mapSize, seed);
		
		this.perlinNoiseFrequency = 0;
		useHSBScale=false;
	}
	
	public PerlinHeightMapGenerator(int mapSize, int seed, float perlinNoiseFrequency,
			float  perturbFrequency, float perturbDistance, int erodeIterations, 
			float erodeSmoothness) {
		super(mapSize, seed);
		
		this.perlinNoiseFrequency = perlinNoiseFrequency;
		
		this.perturbDistance = perturbDistance;
		this.perturbFrequency = perturbFrequency;
		
		this.erodeIterations = erodeIterations;
		this.erodeSmoothness = erodeSmoothness;
	}
	
	@Override
	public BufferedImage generateHeightMap() {
		
		cachedHeightMap = new HeightMap(mapSize, seed);
		//System.out.println(heightMap.toString());
		// testing
		//heightMap.initPerlinNoise2(7, 0.5f);
		cachedHeightMap.addPerlinNoise(perlinNoiseFrequency);
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
		this.perlinNoiseFrequency = r.nextFloat()*r.nextInt(100);
		this.perturbFrequency = r.nextFloat()*r.nextInt(200);
		this.perturbDistance = r.nextFloat()*r.nextInt(200);
		this.erodeIterations = r.nextInt(r.nextInt(200)+1);
		this.erodeSmoothness = r.nextFloat()*r.nextInt();
		
		return this.generateHeightMap();
	}
	
	@Override
	public BufferedImage generateSampleHeightMap() {
		
		this.perlinNoiseFrequency = 6.0f;
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
		else if(useHSBScale)  //optional
		{
			return this.getColorHSB(f);
		}
		else	//original color version
		{
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
	
	//Paolo Sarti uses the HSB color model to obtain a smooth color transition
	private int getColorHSB(float f)
	{
		float saturation=0.8f;
		float brightness=0.6f;
		//change the [-1,1] f to [0,1] hue
		//I have to use a linear map hue=m*f+q
		float minF=-1;
		float maxF=1;
		float m=1/(maxF-minF);
		float q=-m*minF;
		float hue=m*f+q;
		
		return Color.HSBtoRGB(hue, saturation, brightness);
	}
	

	public float getPerlinNoiseFrequency() {
		return perlinNoiseFrequency;
	}

	public void setPerlinNoiseFrequency(float perlinNoiseFrequency) {
		this.perlinNoiseFrequency = perlinNoiseFrequency;
	}
	
	public boolean isUseHSBScale() {
		return useHSBScale;
	}

	public void setUseHSBScale(boolean useHSBScale) {
		this.useHSBScale = useHSBScale;
	}
}
