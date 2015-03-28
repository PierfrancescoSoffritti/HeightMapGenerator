package heightMap;

import noise.perlin.PerlinNoiseGenerator;
import noise.perlin.PerlinNoiseGenerator2;
import noise.simplex.SimplexNoise;

/**
* HeightMap.java
* @author Pierfrancesco Soffritti
*
*/

public class HeightMap {
	
	private float[][] heights;
	private int size;
	
	private int seed;
	private PerlinNoiseGenerator perlin;
	
	// size = 512
	public HeightMap(int size, int seed) {
		this.size = size;
		this.heights = new float[size][size];
		this.seed = seed;
		
		this.perlin = new PerlinNoiseGenerator(seed);
	}
	
	public void addPerlinNoise(float f) {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				heights[i][j] += perlin.noise(f * i / (float)size, f * j / (float)size, 0);
			}
		}
	}
	
	// this function uses a different Perlin noise generator. It is more heavy and the results are not so good.
	// i've introduced it only for testing purpose.
	// note that at the moment it is initializating a new heightMap each time it is called, unlike the 'addPerlinNoise' function.
	public void initPerlinNoise2(int octaveCount, float noisePersistance) {
		PerlinNoiseGenerator2 perlin2 = new PerlinNoiseGenerator2();
		heights = perlin2.generatePerlinNoise(perlin2.generateWhiteNoise(size, size, seed), octaveCount, noisePersistance);
	}
	
	public void addSimplexNoise(int largestFeature, double persistence) {
		
		SimplexNoise simplexNoise = new SimplexNoise(largestFeature, persistence, seed);
		double xStart=0;
		double XEnd=500;
		double yStart=0;
		double yEnd=500;
		
		//int xResolution=200;
		//int yResolution=200;
		
		//double[][] result=new double[xResolution][yResolution];
		
		for(int i=0; i<size; i++) {
			for(int j=0; j<size; j++) {
				int x = (int)(xStart+i*((XEnd-xStart)/size));
				int y = (int)(yStart+j*((yEnd-yStart)/size));
				heights[i][j] += (float) ( 0.5*(1+simplexNoise.getNoise2D(x,y)) );
			}
		}
	}
	
	/*
	* this step displaces the height elements according to another Perlin noise map
	* with a much higher frequency. It is very similar to creating an ocean wave effect.
	*  
	* The d parameter determines the maximum distance an element can move. 
	* The f parameter is again the frequency. 
	*/
	public void perturb(float f, float d) {
		int u, v;
		float[][] temp = new float[size][size];
		for (int i=0; i<size; ++i) {
			for (int j=0; j<size; ++j) {
				u = i + (int)(perlin.noise(f * i / (float)size, f * j / (float)size, 0) * d);
				v = j + (int)(perlin.noise(f * i / (float)size, f * j / (float)size, 1) * d);
				
				// clamp
				if (u < 0) u = 0;
				if (u >= size) u = size - 1;
				
				if (v < 0) v = 0;
				if (v >= size) v = size - 1;
				
				temp[i][j] = heights[u][v];
			}
		}
		
		heights = temp;
	}
	
	/*
	* What this function does is go through every elements Moore neighbourhood (excluding itself)
	* and look for the lowest point, the match.
	* If the difference between the element and its match is between 0 and a smoothness factor,
	* some of the height will be transferred.
	*/
	public void erode(float smoothness) {
		for (int i=1; i<size-1; i++) {
			for (int j=1; j<size-1; j++) {
				float d_max = 0.0f;
				int[] match = { 0, 0 };
				
				for (int u=-1; u<=1; u++) {
					for (int v=-1; v<=1; v++) {
						if(Math.abs(u) + Math.abs(v) > 0) {
							float d_i = heights[i][j] - heights[i + u][j + v];
							if (d_i > d_max) {
								d_max = d_i;
								match[0] = u; match[1] = v;
							}
						}
					}
				}
				
				if(0 < d_max && d_max <= (smoothness / (float)size)) {
					float d_h = 0.5f * d_max;
					heights[i][j] -= d_h;
					heights[i + match[0]][j + match[1]] += d_h;
				}
			}
		}
	}
	
	/*
	* this function will smooth it out a bit. We’ll use a standard 3×3 box filter
	*/
	public void smoothen() {
		for (int i=1; i<size-1; ++i) {
			for (int j=1; j<size-1; ++j) {
				float total = 0.0f;
				for (int u=-1; u<=1; u++) {
					for (int v=-1; v<=1; v++) {
						total += heights[i + u][j + v];
					}
				}
				
				heights[i][j] = total / 9.0f;
			}
		}
	}
	
	public void setHeights(float[][] heights) {
		this.heights = heights;
	}
	
	public float[][] getHeights() {
		return this.heights;
	}
	
	public void setSize(int size) {
		this.size = size;
	}
	
	public int getSize() {
		return this.size;
	}
	
	public PerlinNoiseGenerator getPerlinGenerator() {
		return this.perlin;
	}
	
	public void setPerlinGenerator(PerlinNoiseGenerator perlin) {
		this.perlin = perlin;
	}
	
	public String toString() {		
		StringBuilder res = new StringBuilder();
		res.append("[ ");
		
		for(int i=0; i<size; i++) {
			for(int j=0; j<size; j++) {
				res.append(heights[i][j] +", ");
			}
			res.append("\n");
		}
		
		res.append(" ]");
		
		return res.toString();
	}

}
