package heightMap;
import heightMap.render.GradientManager;
import heightMap.render.RenderException;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
* AbstractHeightMapGenerator.java
* @author Pierfrancesco Soffritti
*
*/

public abstract class AbstractHeightMapGenerator {
	
	protected BufferedImage cachedHeightMapImage;
	protected HeightMap cachedHeightMap;
	
	protected boolean isValid;
	
	protected int mapSize;
	protected int seed;
	
	protected float perturbFrequency;
	protected float perturbDistance;
	protected int erodeIterations;
	protected float erodeSmoothness;
		
	protected float minHeight, maxHeight;
	private GradientManager defaultGradientManager;
	
	private boolean useGrayScale;
	private boolean useHSBScale;
	
	protected AbstractHeightMapGenerator(int mapSize, int seed) {
		this.mapSize = mapSize;
		this.seed = seed;
		
		this.perturbDistance = 0;
		this.perturbFrequency = 0;
		
		this.erodeIterations = 0;
		this.erodeSmoothness = 0;
		
		isValid = false;
		
		useGrayScale = true;
		useHSBScale=false;
	}
	
	public abstract HeightMap generateHeightMap();	
	public abstract HeightMap generateRandomHeightMap();	
	public abstract HeightMap generateSampleHeightMap();
	
	public BufferedImage generateHeightMapAndBufferedImage() throws RenderException {
		generateHeightMap();
		return generateBufferedImage();
	}
	
	public BufferedImage generateBufferedImage(GradientManager gradientManager) throws RenderException {
		
		// TODO handle exceptions 
			
		int imageType = (useGrayScale == true ? BufferedImage.TYPE_BYTE_GRAY : BufferedImage.TYPE_INT_RGB);
		
		cachedHeightMapImage = new BufferedImage(
				cachedHeightMap.getSize(),
				cachedHeightMap.getSize(),
				imageType );
		
		for(int i=0; i<cachedHeightMap.getSize(); i++) {
			for(int j=0; j<cachedHeightMap.getSize(); j++) {
				cachedHeightMapImage.setRGB(i, j, getColor(cachedHeightMap.getHeights()[i][j], gradientManager));
			}
		}
		
		isValid = true;
		
		return cachedHeightMapImage;
	}
	
	public BufferedImage generateBufferedImage() throws RenderException {
		if(this.defaultGradientManager == null || this.defaultGradientManager.isEmpty())
			throw new RenderException("DefaultGradientManager is null or empty");
			
		return generateBufferedImage(this.defaultGradientManager);
	}
	
	protected int getColor(float f, GradientManager gradientManager) throws RenderException {
		
		// i'm adding an offset of |min|. My values will go from 0 to max+|min|
		// in this way i can easily  distribute them on the interval [0, 255]
		// minHeight : x = maxHeight : 255
		int value = (int) ( ((f+Math.abs(minHeight)) * 255)/(maxHeight+Math.abs(minHeight)) );
		
		
		if(useHSBScale)
			return getColorHSB(f);
		else {
			return gradientManager.renderPoint(value);	
			
			
			//old
//			if(value >= 0 && value <= 42) {
//				r = 0; g = 0; b = (value*255)/42;
//			}
//			if(value >= 43 && value <= 85) {
//				r = 0; g = (value*255)/85; b = 255;
//			}
//			if(value >= 86 && value <= 127) {
//				r = 0; g = 255; b = 255-(value*255)/127;
//			}
//			if(value >= 128 && value <= 170) {
//				r = (value*255)/170; g = 255; b = 0;
//			}
//			if(value >= 171 && value <= 245){
//				r = 255; g = 255-(value*255)/245; b = 0;
//			}
//			if(value >= 246 && value <= 255){
//				r = 255; g = 0; b = (value*255)/255;
			// new
//			if(value >= 0 && value <= 42) {
//				r = 0; g = 0; b = offsetRGB(0, 42, 0, 255, value);
//			}
//			if(value >= 43 && value <= 85) {
//				r = 0; g = offsetRGB(43, 85, 0, 255, value); b = 255;
//			}
//			if(value >= 86 && value <= 127) {
//				r = 0; g = 255; b = 255-offsetRGB(86, 127, 0, 255, value);
//			}
//			if(value >= 128 && value <= 170) {
//				r = offsetRGB(128, 170, 0, 255, value); g = 255; b = 0;
//			}
//			if(value >= 171 && value <= 245){
//				r = 255; g = 255-offsetRGB(171, 245, 0, 255, value); b = 0;
//			}
//			if(value >= 246 && value <= 255){
//				r = 255; g = 0; b = offsetRGB(246, 255, 0, 255, value);
//			}
		}
	}
	
	/**
	 *  use the HSB color model to obtain a smooth color transition
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
	
	protected void setMapInfo(HeightMap heightMap) {
		minHeight = 1000;
		maxHeight = -1000;		
		for(int i=0; i<heightMap.getSize(); i++) {
			for(int j=0; j<heightMap.getSize(); j++) {
				if(heightMap.getHeights()[i][j] < minHeight)
					minHeight = heightMap.getHeights()[i][j];
				if(heightMap.getHeights()[i][j] > maxHeight)
					maxHeight = heightMap.getHeights()[i][j];
			}
		}
	}
	
	/**
	 * distributes the values on the interval between -1 and 1
	 */
	public void normalize() {
		int oMax = 2;
		
		// offset heights, to have a range from 0 to maxH+minH
		// distribute those values in the interval from 0 to 2
		// then subtract 1, in order to have them between -1 and 1
		for(int i=0; i<mapSize; i++) {
			for(int j=0; j<mapSize; j++) {
				cachedHeightMap.setHeight(i, j, 
						( ((cachedHeightMap.getHeights()[i][j]+Math.abs(minHeight)) * oMax)
								/(maxHeight+Math.abs(minHeight)) ) -1 );
			}
		}
		
		isValid = false;
	}
	
	public void setMaxMinHeight(float value) {
		if(value>maxHeight)
			maxHeight = value;
		if(value<minHeight)
			minHeight = value;
	}
	
	public String getMapInfo() {		
		String res = "min: " +minHeight +"  max: " +maxHeight;
		return res;
	}
	
	public void setDefaultGradientManager(GradientManager gradientManager) {
		this.defaultGradientManager = gradientManager;
	}
	
	public GradientManager getGradientManager() {
		return this.defaultGradientManager;
	}
	
	public HeightMap getCachedHeightMap() {
		return this.cachedHeightMap;
	}
	
	public BufferedImage getCachedHeightMapImage() throws RenderException {
		if(isValid)
			return this.cachedHeightMapImage;
		else
			return this.generateBufferedImage();
	}
	
	public void invalidate() {
		isValid = false;
	}
	
	public void validate() throws RenderException {
		this.generateBufferedImage();
	}
	
	public boolean isValid() {
		return isValid;
	}
	
	public boolean getUseGrayScale() {
		return useGrayScale;
	}
	
	public void setUseGrayScale(boolean useGrayScale) {
		this.useGrayScale = useGrayScale;
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
	
	public boolean isUseHSBScale() {
		return useHSBScale;
	}

	public void setUseHSBScale(boolean useHSBScale) {
		this.useHSBScale = useHSBScale;
	}

}
