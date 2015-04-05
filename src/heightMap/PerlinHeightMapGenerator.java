package heightMap;

import java.awt.Color;
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
	public HeightMap generateHeightMap() {
		
		cachedHeightMap = new HeightMap(mapSize, seed);
		// testing
//		cachedHeightMap.initPerlinNoise2(7, 0.5f);
		cachedHeightMap.addPerlinNoise(perlinNoiseFrequency);
		cachedHeightMap.perturb(perturbFrequency, perturbDistance);
		for(int i=0; i<erodeIterations; i++)
			cachedHeightMap.erode(erodeSmoothness);
		cachedHeightMap.smoothen();
		
		// TODO move to superclass
		setMapInfo(cachedHeightMap);		
		isValid = false;
		// ----
		
		normalize();
		setMapInfo(cachedHeightMap);
		
		return cachedHeightMap;
	}
	
	@Override
	public HeightMap generateRandomHeightMap() {
		
		Random r = new Random();
		this.perlinNoiseFrequency = r.nextFloat()*r.nextInt(100);
		this.perturbFrequency = r.nextFloat()*r.nextInt(200);
		this.perturbDistance = r.nextFloat()*r.nextInt(200);
		this.erodeIterations = r.nextInt(r.nextInt(200)+1);
		this.erodeSmoothness = r.nextFloat()*r.nextInt();
		
		return this.generateHeightMap();
	}
	
	@Override
	public HeightMap generateSampleHeightMap() {
		
		this.perlinNoiseFrequency = 6.0f;
		this.perturbFrequency = 320.0f;
		this.perturbDistance = 32.0f;
		this.erodeIterations = 20;
		this.erodeSmoothness = 160.0f;
		
		return this.generateHeightMap();
	}

	@Override
	protected int getColor(float f) {
		
		// i'm adding an offset of |min|. My values will go from 0 to max+|min|
		// in this way i can easily  distribute them on the interval [0, 255]
		int value = (int) ( ((f+Math.abs(minHeight)) * 255)/(maxHeight+Math.abs(minHeight)) );
		
		// approximating errors in animation loop
//		if(value < 0)
//			value = 0;
//		if(value > 255)
//			value = 255;
		
		int r = 0, g = 0, b = 0;
		
		if(useGrayScale) {
			r = value;// red component 0...255
			g = value;// green component 0...255
			b = value;// blue component 0...255
		}
		else if(useHSBScale) {
			return getColorHSB(f);
		}
		else
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
		}
		int color = (r << 16) | (g << 8) | b;
		
		return color;
	}
	
	/**
	 *  uses the HSB color model to obtain a smooth color transition
	 *  @author Paolo Sarti
	 */
	private int getColorHSB(float f) {
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
