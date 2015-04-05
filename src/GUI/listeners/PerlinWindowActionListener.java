package GUI.listeners;
import heightMap.PerlinHeightMapGenerator;
import heightMap.render.RenderException;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import GUI.PerlinGUI;

/**
* PerlinWindowActionListener.java
* @author Pierfrancesco Soffritti
*
*/

public class PerlinWindowActionListener implements ActionListener {

	private PerlinGUI perlinGUI;
	
	public PerlinWindowActionListener(PerlinGUI perlinGUI) {
		this.perlinGUI = perlinGUI;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void actionPerformed(ActionEvent e) {
		Component c = (Component) e.getSource();
		String id = c.getName();
		
		if(id.equals(PerlinGUI.USE_GRAY_SCALE_CB_ID)) {
			if(perlinGUI.getHeightMapGenerator().getUseGrayScale() == true)
				perlinGUI.getHeightMapGenerator().setUseGrayScale(false);
			else
				perlinGUI.getHeightMapGenerator().setUseGrayScale(true);
			
			try {
				perlinGUI.getHeightMapGenerator().generateBufferedImage();
			} catch (RenderException e1) {
				// TODO handle exception
				e1.printStackTrace();
			}
			perlinGUI.update();
		}
		
		if(id.equals(PerlinGUI.USE_HSB_COLOR_SCALE))
		{
			PerlinHeightMapGenerator perlinGen=perlinGUI.getHeightMapGenerator();
			if(perlinGen.isUseHSBScale() == true)
				perlinGen.setUseHSBScale(false);
			else
				perlinGen.setUseHSBScale(true);
			
			try {
				perlinGUI.getHeightMapGenerator().generateBufferedImage();
			} catch (RenderException e1) {
				// TODO TODO handle exception
				e1.printStackTrace();
			}
			perlinGUI.update();
		}
		
		if(id.equals(PerlinGUI.SIZE_TEXT_FIELD_ID)) {
			
			int mapSize = 0;			
			try {
				String sValue = ((JTextField)c).getText();
				mapSize = Integer.parseInt(sValue);
			} catch (NumberFormatException exc) {
				// handle -- todo
			}
			
			perlinGUI.getHeightMapGenerator().setMapSize(mapSize);
			perlinGUI.getHeightMapGenerator().generateHeightMap();
			perlinGUI.update();
		}
		if(id.equals(PerlinGUI.SEED_TEXT_FIELD_ID)) {
			
			int seed = 0;			
			try {
				String sValue = ((JTextField)c).getText();
				seed = Integer.parseInt(sValue);
			} catch (NumberFormatException exc) {
				// handle -- todo
			}
			
			perlinGUI.getHeightMapGenerator().setSeed(seed);
			perlinGUI.getHeightMapGenerator().generateHeightMap();
			perlinGUI.update();
		}		
		if(id.equals(PerlinGUI.PERLIN_FREQ_TEXT_FIELD_ID)) {
			float perlinFreq = 0;			
			try {
				String sValue = ((JTextField)c).getText();
				perlinFreq = Float.parseFloat(sValue);
			} catch (NumberFormatException exc) {
				// handle -- todo
			}
			
			((PerlinHeightMapGenerator) perlinGUI.getHeightMapGenerator()).setPerlinNoiseFrequency(perlinFreq);
			perlinGUI.getHeightMapGenerator().generateHeightMap();
			perlinGUI.update();			
		}
		if(id.equals(PerlinGUI.PERTURB_FREQ_TEXT_FIELD_ID)) {
			float perturbFreq = 0;			
			try {
				String sValue = ((JTextField)c).getText();
				perturbFreq = Float.parseFloat(sValue);
			} catch (NumberFormatException exc) {
				// handle -- todo
			}
			
			((PerlinHeightMapGenerator) perlinGUI.getHeightMapGenerator()).setPerturbFrequency(perturbFreq);
			perlinGUI.getHeightMapGenerator().generateHeightMap();
			perlinGUI.update();
		}
		if(id.equals(PerlinGUI.PERTURB_DIST_TEXT_FIELD_ID)) {
			float perturbDist = 0;			
			try {
				String sValue = ((JTextField)c).getText();
				perturbDist = Float.parseFloat(sValue);
			} catch (NumberFormatException exc) {
				// handle -- todo
			}
			
			((PerlinHeightMapGenerator) perlinGUI.getHeightMapGenerator()).setPerturbDistance(perturbDist);
			perlinGUI.getHeightMapGenerator().generateHeightMap();
			perlinGUI.update();
		}
		if(id.equals(PerlinGUI.ERODE_ITER_TEXT_FIELD_ID)) {
			int erodIter = 0;			
			try {
				String sValue = ((JTextField)c).getText();
				erodIter = Integer.parseInt(sValue);
			} catch (NumberFormatException exc) {
				// handle -- todo
			}
			
			((PerlinHeightMapGenerator) perlinGUI.getHeightMapGenerator()).setErodeIterations(erodIter);
			perlinGUI.getHeightMapGenerator().generateHeightMap();
			perlinGUI.update();
		}
		if(id.equals(PerlinGUI.ERODE_SMOOTH_TEXT_FIELD_ID)) {
			float erodeSmooth = 0;			
			try {
				String sValue = ((JTextField)c).getText();
				erodeSmooth = Float.parseFloat(sValue);
			} catch (NumberFormatException exc) {
				// handle -- todo
			}
			
			((PerlinHeightMapGenerator) perlinGUI.getHeightMapGenerator()).setErodeSmoothness(erodeSmooth);
			perlinGUI.getHeightMapGenerator().generateHeightMap();
			perlinGUI.update();
		}
		if(id.equals(PerlinGUI.RANDOM_GENERATION_BUTTON_ID)) {
			perlinGUI.getHeightMapGenerator().generateRandomHeightMap();
			perlinGUI.update();
		}
		if(id.equals(PerlinGUI.START_ANIMATION_BUTTON_ID)) {
			if(!perlinGUI.isRunning())
				perlinGUI.startAnimation();
		}
		if(id.equals(PerlinGUI.STOP_ANIMATION_BUTTON_ID)) {
			perlinGUI.stopAnimation();
			perlinGUI.getHeightMapGenerator().generateHeightMap();
			perlinGUI.update();
		}
		if(id.equals(PerlinGUI.TRANSFORMATIONS_NAMES_LIST_ID)) {
			int selected = ((JComboBox)c).getSelectedIndex();
			
			if(selected != perlinGUI.getCurrentTransformation())
				perlinGUI.setCurrentTransformation(selected);
		}
	}
}
