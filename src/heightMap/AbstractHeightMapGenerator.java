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
	
	protected boolean isValid;
	
	protected int mapSize;
	protected int seed;
	
	protected float perturbFrequency;
	protected float perturbDistance;
	protected int erodeIterations;
	protected float erodeSmoothness;
	
	protected boolean useGrayScale;
	
	protected float minHeight, maxHeight;
	
	protected AbstractHeightMapGenerator(int mapSize, int seed) {
		this.mapSize = mapSize;
		this.seed = seed;
		
		this.perturbDistance = 0;
		this.perturbFrequency = 0;
		
		this.erodeIterations = 0;
		this.erodeSmoothness = 0;
		
		isValid = false;
		
		useGrayScale = true;
	}
	
	public abstract HeightMap generateHeightMap();	
	public abstract HeightMap generateRandomHeightMap();	
	public abstract HeightMap generateSampleHeightMap();
	
	public BufferedImage generateHeightMapAndBufferedImage() {
		generateHeightMap();
		return generateBufferedImage();
	}
	
	public BufferedImage generateBufferedImage() {
		
		// TODO handle exceptions 
			
		int imageType = (useGrayScale == true ? BufferedImage.TYPE_BYTE_GRAY : BufferedImage.TYPE_INT_RGB);
		
		cachedHeightMapImage = new BufferedImage(
				cachedHeightMap.getSize(),
				cachedHeightMap.getSize(),
				imageType );
		
		for(int i=0; i<cachedHeightMap.getSize(); i++) {
			for(int j=0; j<cachedHeightMap.getSize(); j++) {
				cachedHeightMapImage.setRGB(i, j, getColor(cachedHeightMap.getHeights()[i][j]));
			}
		}
		
		isValid = true;
		
		return cachedHeightMapImage;
	}
	
	protected abstract int getColor(float f);
	
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
	
	public HeightMap getCachedHeightMap() {
		return this.cachedHeightMap;
	}
	
	public BufferedImage getCachedHeightMapImage() {
		if(isValid)
			return this.cachedHeightMapImage;
		else
			return this.generateBufferedImage();
	}
	
	public void invalidate() {
		isValid = false;
	}
	
	public void validate() {
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

}
