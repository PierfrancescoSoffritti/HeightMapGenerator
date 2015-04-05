package heightMap.render;

import java.awt.Color;

public class GradientPoint {
	
	private int startHeight;
	private int endHeight;
	private Color startColor;
	private Color endColor;
	
	/**
	 * @param startHeight must be a value between -1 and 1. It represents the height from which this color will be applied
	 * @param endColor represents the color that will be applied
	 * 
	 * endHeight is 255 by default. It will change if a new GradientPoint is added to GradientManager
	 */
	protected GradientPoint(int startHeight, Color endColor) {
			
		this.startHeight = startHeight;
		this.endColor = endColor;
		
		this.startColor = new Color(0, 0, 0);
		this.endHeight = 255;
	}
	
	/**
	 * @param startHeight must be a value between -1 and 1. It represents the height from which this color will be applied
	 * @param endColor represents the color that will be applied
	 * @param startColor represents the color from which endColor will start to fade in
	 * 
	 * endHeight is 255 by default. It will change if a new GradientPoint is added to GradientManager
	 */
	protected GradientPoint(int startHeight, Color endColor, Color startColor) {
		
		this.startHeight = startHeight;
		this.endColor = endColor;
		
		this.startColor = startColor;
		this.endHeight = 255;
	}
	
	/**
	 * @param height represents the max height to apply this color.
	 * IMPORTANT: it's a value between 0 and 255.
	 * This method will be used only internally at the package, so there's no need to use the heights from -1 to 1
	 */
	protected void setEndHeight(int height) {
		this.endHeight = height;
	}
	
	/**
	 * @param color represents the color from which the endColor will start to fade in.
	 */
	protected void setStartColor(Color color) {
		this.startColor = color;
	}
	
	protected boolean include(int point) {
		if (point >= startHeight && point <= endHeight)
			return true;
		else
			return false;
	}
	
	protected int getRGBValue(int value) throws RenderException {
		int r = 0, g = 0, b = 0;
		
		int startR = startColor.getRed();
		int startG = startColor.getGreen();
		int startB = startColor.getBlue();
		
		int endR = endColor.getRed();
		int endG = endColor.getGreen();
		int endB = endColor.getBlue();
		
		r = offsetRGB(startR, endR, value);
		g = offsetRGB(startG, endG, value);
		b = offsetRGB(startB, endB, value);
		
		int RGBcolor = (r << 16) | (g << 8) | b;
		
		return RGBcolor;
	}
	
	private int offsetRGB(int minRGB, int maxRGB, int value) throws RenderException {
		if(endHeight == startHeight)
			throw new RenderException("a gradient point can not start and end at the same height. "
					+ " Probably you have set the starting point of a GradientPoint to 1");
		return ((value-startHeight)*(maxRGB-minRGB))/(endHeight-startHeight) +minRGB;
	}
	
	protected Color getStartColor() {
		return this.startColor;
	}
	
	protected Color getEndColor() {
		return this.endColor;
	}
	
	protected int getStartHeight() {
		return this.startHeight;
	}
	
	protected int getEndHeight() {
		return this.endHeight;
	}

	protected void setEndColor(Color color) {
		this.endColor = color;		
	}
}
