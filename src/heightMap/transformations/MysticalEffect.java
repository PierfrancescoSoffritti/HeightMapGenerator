package heightMap.transformations;

import heightMap.AbstractHeightMapGenerator;
import heightMap.PerlinHeightMapGenerator;
import heightMap.SimplexHeightMapGenerator;

/**
* MysticalEffect.java
* @author Pierfrancesco Soffritti
*
*/

public class MysticalEffect extends Translation {

	public MysticalEffect(AbstractHeightMapGenerator heightMapGenerator) {
		super(heightMapGenerator);
	}
	
	public void applyMysticalEffect() {
		this.translateRightLeftTopBottom(1, 0);
		
		if(heightMapGenerator instanceof PerlinHeightMapGenerator)
			heightMapGenerator.getCachedHeightMap()
			.addPerlinNoise(
					((PerlinHeightMapGenerator)heightMapGenerator).getPerlinNoiseFrequency(),
					1, size, 0, size, 0, 0);
		if(heightMapGenerator instanceof SimplexHeightMapGenerator)
			heightMapGenerator.getCachedHeightMap()
			.setSimplexNoise(
					((SimplexHeightMapGenerator)heightMapGenerator).getLargestFeature(),
					((SimplexHeightMapGenerator)heightMapGenerator).getPersistance(),
					1, size, 0, size, 0, 0);
		
		heightMapGenerator.invalidate();
	}
	
	@Override
	public String toString() {
		return "MysticalEffect";
	}
}
