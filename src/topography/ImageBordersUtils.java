package topography;

/**
* ImageBordersUtils.java
* @author Paolo Sarti
*
*/

import java.awt.Color;
import java.awt.image.BufferedImage;

public class ImageBordersUtils 
{
	
	public static int[][] getColorIntMatrix(BufferedImage image)
	{
		
		int height=image.getHeight();
		int width=image.getWidth();
		int[][] colorMatrix=new int[width][height];
		
		for(int i=0; i<width; i++)
		{
			for(int j=0; j<height; j++)
			{
				colorMatrix[i][j]=image.getRGB(i, j);
			}
		}
		
		return colorMatrix;
		
	}
	
	
	public static int[][] getGreysIntMatrix(int[][] colorIntMatrix, int width, int height)
	{
		int[][] greysIntMatrix= new int[width][height];
		Color c = null;
		
		
		for(int i=0; i<width; i++)
		{
			for(int j=0; j<height; j++)
			{
				c=new Color(colorIntMatrix[i][j]);
				//average of the three colors
				greysIntMatrix[i][j] = (c.getBlue()+c.getRed()+c.getGreen())/3;
			}
		}
		
		
		return greysIntMatrix;
	}
	
	//optimized Version, get the matrix directly from the image
	public static int[][] getGreysIntMatrix(BufferedImage image)
	{
		int height=image.getHeight();
		int width=image.getWidth();
		int[][] greysIntMatrix=new int[width][height];
		Color c=null;
		
		for(int i=0; i<width; i++)
		{
			for(int j=0; j<height; j++)
			{
				c = new Color(image.getRGB(i, j));
				greysIntMatrix[i][j] = (c.getBlue()+c.getRed()+c.getGreen())/3;
			}
		}
		
		
		return greysIntMatrix;
	}
	
	
	public static int[][] getDifferentialIntMatrix(int[][] intMatrix, int width, int height)
	{
		int[][] differentialIntMatrix= new int[width-1][height-1];

		
		for(int i=0; i<width-1; i++)
		{
			for(int j=0; j<height-1; j++)
			{
				//two components of the "gradient" vector
				int diffx = intMatrix[i][j+1]-intMatrix[i][j];
				int diffy = intMatrix[i+1][j]-intMatrix[i][j];
				//I put the module in the matrix though
				differentialIntMatrix[i][j]=(int) Math.floor(Math.sqrt(diffx*diffx+diffy*diffy));
			}
		}
		
		return differentialIntMatrix;
	}
	
	
	/*
	//deep Copy method from StackOverflow
	private BufferedImage deepCopy(BufferedImage bi)
	{
		 ColorModel cm = bi.getColorModel();
		 boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		 WritableRaster raster = bi.copyData(null);
		 return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}
	*/
	
	public static void drawBordersBlackAndWhite(BufferedImage imageBorders, int[][] differentialIntMatrix, int width, int height, int threshold)
	{
		for(int i=0; i<width-1; i++)
		{
			for(int j=0; j<height-1; j++)
			{

				boolean isBorder = differentialIntMatrix[i][j]>threshold?true:false;
				if(isBorder)
				{
					imageBorders.setRGB(i, j, Color.black.getRGB());
				}
				else
				{
					imageBorders.setRGB(i, j, Color.white.getRGB());
				}

			}
		}
	}
	
	public static BufferedImage getWhiteImage(int width, int height)
	{
		BufferedImage image=new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		
		for(int i=0; i<width-1; i++)
		{
			for(int j=0; j<height-1; j++)
			{
				image.setRGB(i, j, Color.white.getRGB());
			}
		}
		
		return image;
	}

	public static BufferedImage getBlackImage(int width, int height)
	{
		BufferedImage image=new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		
		for(int i=0; i<width-1; i++)
		{
			for(int j=0; j<height-1; j++)
			{
				image.setRGB(i, j, Color.black.getRGB());
			}
		}
		
		return image;
	}
	
	public static int[][] getSingleColorIntMatrix(int[][] colorIntMatrix, int width, int height, Color color)
	{
		int[][] singleColorRGBIntMatrix= new int[width][height];
		Color c = null;
		
		
		for(int i=0; i<width; i++)
		{
			for(int j=0; j<height; j++)
			{
				c=new Color(colorIntMatrix[i][j]);
				if(color.equals(Color.red))
				{
					singleColorRGBIntMatrix[i][j] = c.getRed();
				}
				else if(color.equals(Color.blue))
				{
					singleColorRGBIntMatrix[i][j] = c.getBlue();
				}
				else if(color.equals(Color.green))
				{
					singleColorRGBIntMatrix[i][j] = c.getGreen();
				}
				
			}
		}
		
		
		return singleColorRGBIntMatrix;
	}
	
	public static BufferedImage invertBlackAndWhite(BufferedImage image)
	{
		BufferedImage imageOut= getBlackImage(image.getWidth(), image.getHeight());
		for(int i=0; i<image.getWidth(); i++)
		{
			for(int j=0; j<image.getHeight(); j++)
			{
				Color c = new Color(image.getRGB(i, j));
				//invert white into black
				if(c.equals(Color.white))
				{
					imageOut.setRGB(i, j,Color.black.getRGB());
				}
				//invert black into white
				else if (c.equals(Color.black))
				{
					imageOut.setRGB(i, j,Color.white.getRGB());
				}
				//copy the other colors
				else
				{
					imageOut.setRGB(i, j, image.getRGB(i, j));
				}


			}
		}
		
		return imageOut;
	}
	
	public static void drawBorders(BufferedImage imageBorders, int[][] differentialIntMatrix, int width, int height, int threshold, Color borderColor)
	{
		for(int i=0; i<width-1; i++)
		{
			for(int j=0; j<height-1; j++)
			{

				boolean isBorder = differentialIntMatrix[i][j]>threshold?true:false;
				if(isBorder)
				{
					//System.out.println("\nBlue: "+borderColor.getBlue()+"\nGreen: "+borderColor.getGreen()+"\nRed: "+borderColor.getRed()+"\n");
					imageBorders.setRGB(i, j, imageBorders.getRGB(i, j)+borderColor.getRGB());
				}

			}
		}
		
	}

}
