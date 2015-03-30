package GUI.listeners;
import heightMap.PerlinHeightMapGenerator;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

	@Override
	public void actionPerformed(ActionEvent e) {
		Component c = (Component) e.getSource();
		String id = c.getName();
		
		if(id.equals(PerlinGUI.USE_GRAY_SCALE_CB_ID)) {
			if(perlinGUI.getHeightMapGenerator().getUseGrayScale() == true)
				perlinGUI.getHeightMapGenerator().setUseGrayScale(false);
			else
				perlinGUI.getHeightMapGenerator().setUseGrayScale(true);
			
			perlinGUI.getHeightMapGenerator().generateHeightMap();
			perlinGUI.update();
		}
		
		if(id.equals(PerlinGUI.USE_HSB_COLOR_SCALE))
		{
			PerlinHeightMapGenerator perlinGen=perlinGUI.getHeightMapGenerator();
			if(perlinGen.isUseHSBScale() == true)
				perlinGen.setUseHSBScale(false);
			else
				perlinGen.setUseHSBScale(true);
			
			perlinGUI.getHeightMapGenerator().generateHeightMap();
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
	}
}
