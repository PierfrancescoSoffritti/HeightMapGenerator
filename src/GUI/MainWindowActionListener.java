package GUI;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;

/**
* MainWindowActionListener.java
* @author Pierfrancesco Soffritti
*
*/

public class MainWindowActionListener implements ActionListener {

	private GUIManager gui;
	
	public MainWindowActionListener(GUIManager gui) {
		this.gui = gui;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Component c = (Component) e.getSource();
		String id = c.getName();
		
		if(id.equals(gui.SAVE_HEIGHTMAP_BUTTON_ID)) {
			// parent component of the dialog
			JFrame parentFrame = new JFrame();
			
			// start from current dir
			JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
			fileChooser.setDialogTitle("Save heightmap");
			 
			int userSelection = fileChooser.showSaveDialog(parentFrame);
			 
			if (userSelection == JFileChooser.APPROVE_OPTION) {
				// save file
			    File fileToSave = fileChooser.getSelectedFile();
			    try {
					ImageIO.write(gui.getHeightMapGenerator().getCachedHeightMapImage(), "png", fileToSave);
				} catch (IOException exc) {
					exc.printStackTrace();
				}
			}			
		}
		
		if(id.equals(gui.USE_GRAY_SCALE_CB_ID)) {
			if(gui.getHeightMapGenerator().getUseGrayScale() == true)
				gui.getHeightMapGenerator().setUseGrayScale(false);
			else
				gui.getHeightMapGenerator().setUseGrayScale(true);
			
			gui.getHeightMapGenerator().generateHeightMap();
			gui.update();
		}
		
		if(id.equals(gui.SIZE_TEXT_FIELD_ID)) {
			
			int mapSize = 0;			
			try {
				String sValue = ((JTextField)c).getText();
				mapSize = Integer.parseInt(sValue);
			} catch (NumberFormatException exc) {
				// handle -- todo
			}
			
			gui.getHeightMapGenerator().setMapSize(mapSize);
			gui.getHeightMapGenerator().generateHeightMap();
			gui.update();
		}
		if(id.equals(gui.SEED_TEXT_FIELD_ID)) {
			
			int seed = 0;			
			try {
				String sValue = ((JTextField)c).getText();
				seed = Integer.parseInt(sValue);
			} catch (NumberFormatException exc) {
				// handle -- todo
			}
			
			gui.getHeightMapGenerator().setSeed(seed);
			gui.getHeightMapGenerator().generateHeightMap();
			gui.update();
		}		
		if(id.equals(gui.PERLIN_FREQ_TEXT_FIELD_ID)) {
			float perlinFreq = 0;			
			try {
				String sValue = ((JTextField)c).getText();
				perlinFreq = Float.parseFloat(sValue);
			} catch (NumberFormatException exc) {
				// handle -- todo
			}
			
			gui.getHeightMapGenerator().setPerlinNoiseFrequency(perlinFreq);
			gui.getHeightMapGenerator().generateHeightMap();
			gui.update();			
		}
		if(id.equals(gui.PERTURB_FREQ_TEXT_FIELD_ID)) {
			float perturbFreq = 0;			
			try {
				String sValue = ((JTextField)c).getText();
				perturbFreq = Float.parseFloat(sValue);
			} catch (NumberFormatException exc) {
				// handle -- todo
			}
			
			gui.getHeightMapGenerator().setPerturbFrequency(perturbFreq);
			gui.getHeightMapGenerator().generateHeightMap();
			gui.update();
		}
		if(id.equals(gui.PERTURB_DIST_TEXT_FIELD_ID)) {
			float perturbDist = 0;			
			try {
				String sValue = ((JTextField)c).getText();
				perturbDist = Float.parseFloat(sValue);
			} catch (NumberFormatException exc) {
				// handle -- todo
			}
			
			gui.getHeightMapGenerator().setPerturbDistance(perturbDist);
			gui.getHeightMapGenerator().generateHeightMap();
			gui.update();
		}
		if(id.equals(gui.ERODE_ITER_TEXT_FIELD_ID)) {
			int erodIter = 0;			
			try {
				String sValue = ((JTextField)c).getText();
				erodIter = Integer.parseInt(sValue);
			} catch (NumberFormatException exc) {
				// handle -- todo
			}
			
			gui.getHeightMapGenerator().setErodeIterations(erodIter);
			gui.getHeightMapGenerator().generateHeightMap();
			gui.update();
		}
		if(id.equals(gui.ERODE_SMOOTH_TEXT_FIELD_ID)) {
			float erodeSmooth = 0;			
			try {
				String sValue = ((JTextField)c).getText();
				erodeSmooth = Float.parseFloat(sValue);
			} catch (NumberFormatException exc) {
				// handle -- todo
			}
			
			gui.getHeightMapGenerator().setErodeSmoothness(erodeSmooth);
			gui.getHeightMapGenerator().generateHeightMap();
			gui.update();
		}
		
		if(id.equals(gui.SHOW_TOPOGRAPHY_BUTTON_ID)) {
			gui.showTopographyWindow(1);			
		}
		if(id.equals(gui.RANDOM_GENERATION_BUTTON_ID)) {
			gui.getHeightMapGenerator().generateRandomHeightMap();
			gui.update();
		}
	}

}
