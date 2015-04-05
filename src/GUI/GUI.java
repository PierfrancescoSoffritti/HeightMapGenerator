package GUI;

import heightMap.AbstractHeightMapGenerator;
import heightMap.render.RenderException;

import java.awt.image.BufferedImage;

import javax.swing.JPanel;

/**
* GUI.java
* @author Pierfrancesco Soffritti
*
*/

public interface GUI {	
	public JPanel getJPanel() throws RenderException;
	public void update() throws RenderException;
	public AbstractHeightMapGenerator getHeightMapGenerator();
	public BufferedImage getBufferedImage() throws RenderException;
	public String toString();
}
