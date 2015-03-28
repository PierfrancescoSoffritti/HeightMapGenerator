package noise.perlin;

import java.util.Random;

// This Perlin noise generator uses a WhiteNoise map as starting point.
// From this WhiteNoise are generated a number of arrays containing “smooth” noise.
// Each array is called an octave, and the smoothness is different for each octave. (depending on samplePeriod and sampleFrquency)
// Those octaves are blended together to generate Perlin noise.

public class PerlinNoiseGenerator2 {
	
	public float[][] generateWhiteNoise(int width, int height, int seed) {
	    Random random = new Random(seed);
	    float[][] noise = new float[width][height];
	 
	    for (int i = 0; i<width; i++) {
	    	for (int j = 0; j<height; j++) {
	    		noise[i][j] = (float)random.nextDouble() % 1;
	    	}
	    }
	    
	    return noise;
	}
	
	public float[][] GenerateSmoothNoise(float[][] baseNoise, int octave)
	{
	   int width = baseNoise.length;
	   int height = baseNoise[0].length;
	 
	   float[][] smoothNoise = new float[width][height];
	 
	   int samplePeriod = (int) Math.pow(2, octave); // calculates 2 ^ k // wave lenght
	   float sampleFrequency = 1.0f / samplePeriod;
	 
	   for (int i=0; i<width; i++) {
	      //calculate the horizontal sampling indices
	      int sample_i0 = (i / samplePeriod) * samplePeriod;
	      int sample_i1 = (sample_i0 + samplePeriod) % width; //wrap around
	      float horizontal_blend = (i - sample_i0) * sampleFrequency;
	 
	      for (int j=0; j<height; j++) {
	         //calculate the vertical sampling indices
	         int sample_j0 = (j / samplePeriod) * samplePeriod;
	         int sample_j1 = (sample_j0 + samplePeriod) % height; //wrap around
	         float vertical_blend = (j - sample_j0) * sampleFrequency;
	 
	         //blend the top two corners
	         float top = Interpolate(baseNoise[sample_i0][sample_j0],
	            baseNoise[sample_i1][sample_j0], horizontal_blend);
	 
	         //blend the bottom two corners
	         float bottom = Interpolate(baseNoise[sample_i0][sample_j1],
	            baseNoise[sample_i1][sample_j1], horizontal_blend);
	 
	         //final blend
	         smoothNoise[i][j] = Interpolate(top, bottom, vertical_blend);
	      }
	   }
	 
	   return smoothNoise;
	}
	
	public float[][] generatePerlinNoise(float[][] baseNoise, int octaveCount, float noisePersistance)
	{
	   int width = baseNoise.length;
	   int height = baseNoise[0].length;
	 
	   float[][][] smoothNoise = new float[octaveCount][][]; //an array of 2D arrays containing
	 
	   //generate smooth noise
	   for (int i = 0; i<octaveCount; i++)
	   {
	       smoothNoise[i] = GenerateSmoothNoise(baseNoise, i);
	   }
	 
	    float[][] perlinNoise = new float[width][height];
	    float amplitude = 1.0f;
	    float totalAmplitude = 0.0f;
	 
	    //blend noise together
	    for (int octave= octaveCount - 1; octave>= 0; octave--) {
	       amplitude *= noisePersistance;
	       totalAmplitude += amplitude;
	 
	       for (int i = 0; i<width; i++) {
	          for (int j = 0; j<height; j++) {
	             perlinNoise[i][j] += smoothNoise[octave][i][j] * amplitude;
	          }
	       }
	    }
	 
	   //normalisation
	   for (int i = 0; i<width; i++)
	   {
	      for (int j = 0; j<height; j++)
	      {
	         perlinNoise[i][j] /= totalAmplitude;
	      }
	   }
	 
	   return perlinNoise;
	}
	
	private float Interpolate(float x0, float x1, float alpha)
	{
	   return x0 * (1 - alpha) + alpha * x1;
	}
}
