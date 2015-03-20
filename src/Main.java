import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class Main {
	
	private static final int mapSize = 512;

	public static void main(String[] args) {
		HeightMap heightMap = new HeightMap(mapSize);
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
		
		ImageIcon icon = new ImageIcon(heightMapImage);
        JFrame frame = new JFrame();
        frame.setLayout(new FlowLayout());
        frame.setSize(mapSize+100,mapSize+100);
        JLabel lbl = new JLabel();
        lbl.setIcon(icon);
        frame.add(lbl);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		File output = new File("C:\\Users\\Pierfrancesco\\Desktop\\prova\\img.png");
		
		try {
			ImageIO.write(heightMapImage, "png", output);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		printInfo(heightMap);
	}

	private static void printInfo(HeightMap heightMap) {
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
		System.out.println("min: " +min +"  max: " +max);
	}

	private static int getColor(float f) {
		
		int value = (int) ((f+0.5) * 255);
		
		int r = value;// red component 0...255
		int g = value;// green component 0...255
		int b = value;// blue component 0...255
		int color = (r << 16) | (g << 8) | b;
		
		//System.out.println(value);
		
		return color;
	}

}
