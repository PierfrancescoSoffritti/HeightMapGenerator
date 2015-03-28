package GUI;

import heightMap.AbstractHeightMapGenerator;

import java.awt.image.BufferedImage;

import javax.swing.JPanel;

/**
* GUI.java
* @author Pierfrancesco Soffritti
*
*/

public interface GUI {	
	public JPanel getJPanel();
	public void update();
	public AbstractHeightMapGenerator getHeightMapGenerator();
	public BufferedImage getBufferedImage();
	public String toString();
}
