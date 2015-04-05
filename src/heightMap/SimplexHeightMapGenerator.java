package heightMap;

import java.util.Random;

/**
* SimplexHeightMapGenerator.java
* @author Pierfrancesco Soffritti
*
*/

public class SimplexHeightMapGenerator extends AbstractHeightMapGenerator {

	private int largestFeature;	
	// Persistence is used to affect the appearance of the terrain.
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
	public HeightMap generateHeightMap() {
		
		cachedHeightMap = new HeightMap(mapSize, seed);
		cachedHeightMap.addSimplexNoise(largestFeature, persistance);
		cachedHeightMap.perturb(perturbFrequency, perturbDistance);
		for(int i=0; i<erodeIterations; i++)
			cachedHeightMap.erode(erodeSmoothness);
		cachedHeightMap.smoothen();
		
		// TODO move to superclass
		setMapInfo(cachedHeightMap);	
		isValid = false;
		// ---- 
		
		
//		normalize();
//		setMapInfo(cachedHeightMap);		
		return cachedHeightMap;
	}
	
	@Override
	public HeightMap generateRandomHeightMap() {
		
		Random r = new Random();
		this.largestFeature = r.nextInt(1000000);
		this.persistance = r.nextDouble();
//		this.perturbFrequency = r.nextFloat()*r.nextInt(200);
		this.perturbFrequency = 0;
//		this.perturbDistance = r.nextFloat()*r.nextInt(200);
		this.perturbDistance = 0;
//		this.erodeIterations = r.nextInt(r.nextInt(200)+1);
		this.erodeIterations = 0;
//		this.erodeSmoothness = r.nextFloat()*r.nextInt();
		this.erodeSmoothness = 0;
		
		return this.generateHeightMap();
	}
	
	@Override
	public HeightMap generateSampleHeightMap() {
		
		this.largestFeature = 100; 
		this.persistance = 0.9;
		this.perturbFrequency = 320.0f;
		this.perturbDistance = 32.0f;
		this.erodeIterations = 20;
		this.erodeSmoothness = 160.0f;
		
		return this.generateHeightMap();
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