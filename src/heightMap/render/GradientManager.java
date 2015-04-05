package heightMap.render;

import java.awt.Color;
import java.util.ArrayList;

public class GradientManager {

	private ArrayList<GradientPoint> gradientPoints;
	
	public GradientManager() {
		gradientPoints = new ArrayList<GradientPoint>();
	}
	
	/**
	 * @param height must be a value between -1 and 1. It represents the height from which this color will be applied
	 * @param color represents the color that will be applied
	 * @throws RenderException if height  is not between -1 and 1
	 */
	public void addGradientPoint(float height, Color color) throws RenderException {
		
		if(height < -1 || height > 1)
			throw new RenderException("the height of a GrandientPoint must be >= -1 and <= 1");
		
		// since i'm working with RGB, internally i'm using heights from 0 to 255	
		int RGBheight = (int) (((height+1) * 255)/2);
		
		if(gradientPoints.isEmpty())
			gradientPoints.add(new GradientPoint(RGBheight, color));
		else {
			
			// sets the endHeight of the previous color to the startHeight of the new color -1
			gradientPoints.get(gradientPoints.size()-1).setEndHeight(RGBheight-1);

			// create a new GraidientPoint, with a starting color == to the endColor of the previous GradientPoint
			GradientPoint newPoint = new GradientPoint(RGBheight, color, gradientPoints.get(gradientPoints.size()-1).getEndColor());
			gradientPoints.add(newPoint);		
		}
	}
	
	public void clearGradientPoints() {
		this.gradientPoints = new ArrayList<GradientPoint>();
	}
	
	/**
	 * if a point has no interval the method returns black
	 * @param point is expected to be a value from 0 to be 255
	 * @throws RenderException 
	 */
	public int renderPoint(int point) throws RenderException {
		for(int i=0; i<gradientPoints.size(); i++) {
			if(gradientPoints.get(i).include(point))
				return gradientPoints.get(i).getRGBValue(point);
		}
		
		// the point has no interval. Return black
//		int r = 0; int g = 0; int b = 0;
//		int RGBcolor = (r << 16) | (g << 8) | b;
//		return RGBcolor;
		
		return -1000;
		
	}
	
	public boolean isEmpty() {
		if(gradientPoints.isEmpty())
			return true;
		else
			return false;
	}
}
