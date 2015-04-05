package heightMap.transformations;

import heightMap.AbstractHeightMapGenerator;
import heightMap.PerlinHeightMapGenerator;
import heightMap.SimplexHeightMapGenerator;

/**
* Translation.java
* @author Pierfrancesco Soffritti
*
*/

public class Translation extends Transformation {
	
	protected int size;
	
	public Translation(AbstractHeightMapGenerator heightMapGenerator) {
		super(heightMapGenerator);
		
		this.size = heightMapGenerator.getMapSize();
	}

	/**
	 * translate the map of X in the x direction and of Y in the y direction.
	 * Then adds noise to the now empty coordinates of the map.
	 *
	 * @param  x translation on the X axis. If x > 0 translate right to left. If x < 0 translate left to right.
	 * @param  y translation on the Y axis. If y > 0 translate top to bottom. If x < 0 translate bottom to top.
	 * @param xNoiseOffset fill empty space with noise generated at this x coordinate
	 * @param yNoiseOffset fill empty space with noise generated at this y coordinate
	 * 
	 * @TODO x < 0 || y < 0 not working
	 */	
	public void translate(int x, int y, int xNoiseOffset, int yNoiseOffset) {
		
		if(x>=0 && y>=0)
			translateRightLeftTopBottom(x, y);
//		else if(x<=0 && y>=0)
//			translateLeftRightTopBottom(x, y);
//		else if(x<=0 && y<=0)
//			translateLeftRightBottomTop(x, y);
		else if(x>=0 && y<=0)
			translateRightLeftBottomTop(x, y);
		
		if(heightMapGenerator instanceof PerlinHeightMapGenerator) {			
			// generates noise on empty coloumns
			heightMapGenerator.getCachedHeightMap()
			.setPerlinNoise(
					((PerlinHeightMapGenerator)heightMapGenerator).getPerlinNoiseFrequency(),
					size-x-1, size, 0, size, xNoiseOffset, yNoiseOffset);

			// generates noise on empty rows
			heightMapGenerator.getCachedHeightMap()
			.setPerlinNoise(
					((PerlinHeightMapGenerator)heightMapGenerator).getPerlinNoiseFrequency(),
					0, size, size-y-1, size, xNoiseOffset, yNoiseOffset);
		}
		if(heightMapGenerator instanceof SimplexHeightMapGenerator) {			
			// generates noise on empty coloumns
			heightMapGenerator.getCachedHeightMap()
			.setSimplexNoise(
					((SimplexHeightMapGenerator)heightMapGenerator).getLargestFeature(),
					((SimplexHeightMapGenerator)heightMapGenerator).getPersistance(),
					size-x-1, size, 0, size, xNoiseOffset, yNoiseOffset);

			// generates noise on empty rows
			heightMapGenerator.getCachedHeightMap()
			.setSimplexNoise(
					((SimplexHeightMapGenerator)heightMapGenerator).getLargestFeature(),
					((SimplexHeightMapGenerator)heightMapGenerator).getPersistance(),
					0, size, size-y-1, size, xNoiseOffset, yNoiseOffset);
		}
		
		heightMapGenerator.invalidate();
	}
	
	protected void translateRightLeftTopBottom(int x, int y) {
		for(int i=0; i<size-x; i++) {
			for(int j=0; j<size-y; j++) {
				heightMapGenerator.getCachedHeightMap()
					.setHeight(i, j, heightMapGenerator.getCachedHeightMap().getHeights()[i+x][j+y]);
			}
		}
	}
	
//	protected void translateLeftRightTopBottom(int x, int y) {
//		x = x*-1;
//		for(int i=size-x-1; i>=0; i--) {
//			for(int j=0; j<size-y; j++) {
//				heightMapGenerator.getCachedHeightMap()
//					.setHeights(i+x, j, heightMapGenerator.getCachedHeightMap().getHeights()[i+x][j+y]);
//			}
//		}
//		
//		if(heightMapGenerator instanceof PerlinHeightMapGenerator)
//			heightMapGenerator.getCachedHeightMap()
//			.addPerlinNoise(
//					((PerlinHeightMapGenerator)heightMapGenerator).getPerlinNoiseFrequency(),
//					0, x+1, y, size);
//		
//		System.out.println(x);
//	}
//	
//	protected void translateLeftRightBottomTop(int x, int y) {
//		for(int i=size-x; i>=0; i--) {
//			for(int j=size-y; j>=0; j--) {
//				heightMapGenerator.getCachedHeightMap()
//					.setHeights(i, j, heightMapGenerator.getCachedHeightMap().getHeights()[i+x][j+y]);
//			}
//		}
//	}
	
	protected void translateRightLeftBottomTop(int x, int y) {
		for(int i=0; i<size-x; i++) {
			for(int j=size-y; j>=0; j--) {
				heightMapGenerator.getCachedHeightMap()
					.setHeight(i, j, heightMapGenerator.getCachedHeightMap().getHeights()[i+x][j+y]);
			}
		}
	}
	
	@Override
	public String toString() {
		return "Translation";
	}
}
