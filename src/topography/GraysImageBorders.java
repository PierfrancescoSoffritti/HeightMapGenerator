package topography;

/**
* GraysImageBorders.java
* @author Paolo Sarti
*
*/

import java.awt.Color;
import java.awt.image.BufferedImage;

public class GraysImageBorders
{
	public static BufferedImage getBordersImage(BufferedImage image, int threshold)
	{
		int height=image.getHeight();
		int width=image.getWidth();
		int[][] greysIntMatrix=ImageBordersUtils.getGreysIntMatrix(image);
		//modules of the "gradient" vector
		int[][] differentialIntMatrix = ImageBordersUtils.getDifferentialIntMatrix(greysIntMatrix, width, height);
		
		BufferedImage imageBorders=new BufferedImage(width-1, height-1, BufferedImage.TYPE_INT_ARGB);
		
		ImageBordersUtils.drawBordersBlackAndWhite(imageBorders, differentialIntMatrix, width, height, threshold);
		
		return imageBorders;
	}
	
	//I get a gray-scale image by averaging the RGB components, and setting every output RGB component (in each pixel) to the found average
	public static BufferedImage getGreysImage(BufferedImage image)
	{
		int height=image.getHeight();
		int width=image.getWidth();
		int[][] greysIntMatrix=ImageBordersUtils.getGreysIntMatrix(image);
		
		BufferedImage greysImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		
		for(int i=0; i<width; i++)
		{
			for(int j=0; j<height; j++)
			{
				Color grey = new Color(greysIntMatrix[i][j], greysIntMatrix[i][j], greysIntMatrix[i][j]);
				greysImage.setRGB(i, j, grey.getRGB());
			}
		}
		
		return greysImage;
	}
	
	//get an image in which the grey tonality is blacker where the module of the gradient is more pronounced
	//OPPOSITE BEHAVIOUR!!
	public static BufferedImage getGreyGradientModuleImage(BufferedImage image)
	{
		int height=image.getHeight();
		int width=image.getWidth();
		int[][] colorIntMatrix=ImageBordersUtils.getColorIntMatrix(image);
		int[][] greysIntMatrix=ImageBordersUtils.getGreysIntMatrix(colorIntMatrix, width, height);
		//modules of the "gradient" vector
		int[][] differentialIntMatrix = ImageBordersUtils.getDifferentialIntMatrix(greysIntMatrix, width, height);
		
		BufferedImage gradientModuleImage=new BufferedImage(width-1, height-1, BufferedImage.TYPE_INT_ARGB);
		
		for(int i=0; i<width-1; i++)
		{
			for(int j=0; j<height-1; j++)
			{
				//I set the "opposite" grey tonality, to obtain the borders blacker, and the rest whiter
				Color grey = new Color(255-differentialIntMatrix[i][j],255-differentialIntMatrix[i][j],255- differentialIntMatrix[i][j]);
				gradientModuleImage.setRGB(i, j, grey.getRGB());
			}
		}
		
		
		return gradientModuleImage;
	}
}
