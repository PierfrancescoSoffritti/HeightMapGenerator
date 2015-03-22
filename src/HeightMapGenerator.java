import java.awt.image.BufferedImage;
import java.util.Random;

public class HeightMapGenerator {
	
	private int mapSize;
	private int seed;
	
	private float perlinNoiseFrequency;
	
	private float perturbDistance;
	private float perturbFrequency;
	
	private int erodeIterations;
	private float erodeSmoothness;
	
	private boolean printInfo;
	
	public HeightMapGenerator(int mapSize, int seed) {
		this.mapSize = mapSize;
		this.seed = seed;
		
		this.perlinNoiseFrequency = 0;
		
		this.perturbDistance = 0;
		this.perturbFrequency = 0;
		
		this.erodeIterations = 0;
		this.erodeSmoothness = 0;
		
		printInfo = false;
	}
	
	
	public HeightMapGenerator(int mapSize, int seed, float perlinNoiseFrequency,
			float  perturbDistance, float perturbFrequency, int erodeIterations, 
			float erodeSmoothness) {
		this.mapSize = mapSize;
		this.seed = seed;
		
		this.perlinNoiseFrequency = perlinNoiseFrequency;
		
		this.perturbDistance = perturbDistance;
		this.perturbFrequency = perturbFrequency;
		
		this.erodeIterations = erodeIterations;
		this.erodeSmoothness = erodeSmoothness;
		
		printInfo = false;
	}
	
	public BufferedImage generateHeightMap() {
		
		HeightMap heightMap = new HeightMap(mapSize, seed);
		heightMap.addPerlinNoise(perlinNoiseFrequency);
		heightMap.perturb(perturbFrequency, perturbDistance);		
		for(int i=0; i<erodeIterations; i++)
			heightMap.erode(erodeSmoothness);
		heightMap.smoothen();
		
		BufferedImage heightMapImage = new BufferedImage(
				heightMap.getSize(),
				heightMap.getSize(),
				BufferedImage.TYPE_BYTE_GRAY );
		
		for(int i=0; i<heightMap.getSize(); i++) {
			for(int j=0; j<heightMap.getSize(); j++) {
				heightMapImage.setRGB(i, j, getColor(heightMap.getHeights()[i][j]));
			}
		}
		
		if(printInfo)
			printInfo(heightMap);
		
		return heightMapImage;
	}
	
	public BufferedImage generateRandomHeightMap() {
		
		Random r = new Random();
		HeightMap heightMap = new HeightMap(mapSize, seed);
		heightMap.addPerlinNoise(r.nextFloat()*r.nextInt(100));
		heightMap.perturb(r.nextFloat()*r.nextInt(200), r.nextFloat()*r.nextInt(200));		
		for(int i=0; i<r.nextInt(r.nextInt(200)+1); i++)
			heightMap.erode(r.nextFloat()*r.nextInt());
		heightMap.smoothen();
		
		BufferedImage heightMapImage = new BufferedImage(
				heightMap.getSize(),
				heightMap.getSize(),
				BufferedImage.TYPE_BYTE_GRAY );
		
		for(int i=0; i<heightMap.getSize(); i++) {
			for(int j=0; j<heightMap.getSize(); j++) {
				heightMapImage.setRGB(i, j, getColor(heightMap.getHeights()[i][j]));
			}
		}
		
		if(printInfo)
			printInfo(heightMap);
		
		return heightMapImage;
	}
	
	public BufferedImage generateSampleHeightMap() {
		
		HeightMap heightMap = new HeightMap(mapSize, seed);
		heightMap.addPerlinNoise(6.0f);
		heightMap.perturb(32.0f, 32.0f);		
		for(int i=0; i<10; i++)
			heightMap.erode(16.0f);
		heightMap.smoothen();
		
		BufferedImage heightMapImage = new BufferedImage(
				heightMap.getSize(),
				heightMap.getSize(),
				BufferedImage.TYPE_BYTE_GRAY );
		
		for(int i=0; i<heightMap.getSize(); i++) {
			for(int j=0; j<heightMap.getSize(); j++) {
				heightMapImage.setRGB(i, j, getColor(heightMap.getHeights()[i][j]));
			}
		}
		
		if(printInfo)
			printInfo(heightMap);
		
		return heightMapImage;
	}
	
	private static int getColor(float f) {
		
		// ?? naive solution
		int value = (int) ((f+0.5) * 255);
		
		int r = value;// red component 0...255
		int g = value;// green component 0...255
		int b = value;// blue component 0...255
		int color = (r << 16) | (g << 8) | b;
		
		return color;
	}
	
	private String printInfo(HeightMap heightMap) {
		//System.out.println(heightMap);
		float min = 1000, max = -1000;
		for(int i=0; i<heightMap.getSize(); i++) {
			for(int j=0; j<heightMap.getSize(); j++) {
				if(heightMap.getHeights()[i][j] < min)
					min = heightMap.getHeights()[i][j];
				if(heightMap.getHeights()[i][j] > max)
					max = heightMap.getHeights()[i][j];
			}
		}
		
		String res = "min: " +min +"  max: " +max;
		
		System.out.println(res);
		return res;
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
