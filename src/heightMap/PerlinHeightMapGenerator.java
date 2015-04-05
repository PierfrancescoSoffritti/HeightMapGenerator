package heightMap;

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
