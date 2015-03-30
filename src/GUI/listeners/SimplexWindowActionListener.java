package GUI.listeners;
import heightMap.SimplexHeightMapGenerator;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import GUI.SimplexGUI;

/**
* PerlinWindowActionListener.java
* @author Pierfrancesco Soffritti
*
*/

public class SimplexWindowActionListener implements ActionListener {

	private SimplexGUI simplexGUI;
	
	public SimplexWindowActionListener(SimplexGUI simplexGUI) {
		this.simplexGUI = simplexGUI;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Component c = (Component) e.getSource();
		String id = c.getName();
		
		if(id.equals(SimplexGUI.USE_GRAY_SCALE_CB_ID)) {
			if(simplexGUI.getHeightMapGenerator().getUseGrayScale() == true)
				simplexGUI.getHeightMapGenerator().setUseGrayScale(false);
			else
				simplexGUI.getHeightMapGenerator().setUseGrayScale(true);
			
			simplexGUI.getHeightMapGenerator().generateHeightMap();
			simplexGUI.update();
		}
		
		if(id.equals(SimplexGUI.USE_HSB_COLOR_SCALE))
		{
			//I have to downcast it (by the way, why does the SimplexGUI.getHeightMapGenerator() return
			//an abstract class, and not the Simplex one?
			SimplexHeightMapGenerator simplexGen= (SimplexHeightMapGenerator) simplexGUI.getHeightMapGenerator();
			if(simplexGen.isUseHSBScale() == true)
				simplexGen.setUseHSBScale(false);
			else
				simplexGen.setUseHSBScale(true);
			
			simplexGUI.getHeightMapGenerator().generateHeightMap();
			simplexGUI.update();
		}
		
		if(id.equals(SimplexGUI.SIZE_TEXT_FIELD_ID)) {
			
			int mapSize = 0;			
			try {
				String sValue = ((JTextField)c).getText();
				mapSize = Integer.parseInt(sValue);
			} catch (NumberFormatException exc) {
				// handle -- todo
			}
			
			simplexGUI.getHeightMapGenerator().setMapSize(mapSize);
			simplexGUI.getHeightMapGenerator().generateHeightMap();
			simplexGUI.update();
		}
		if(id.equals(SimplexGUI.SEED_TEXT_FIELD_ID)) {
			
			int seed = 0;			
			try {
				String sValue = ((JTextField)c).getText();
				seed = Integer.parseInt(sValue);
			} catch (NumberFormatException exc) {
				// handle -- todo
			}
			
			simplexGUI.getHeightMapGenerator().setSeed(seed);
			simplexGUI.getHeightMapGenerator().generateHeightMap();
			simplexGUI.update();
		}
		if(id.equals(SimplexGUI.LARGEST_FEATURE_TEXT_FIELD_ID)) {
			
			int largestFeature = 0;			
			try {
				String sValue = ((JTextField)c).getText();
				largestFeature = Integer.parseInt(sValue);
			} catch (NumberFormatException exc) {
				// handle -- todo
			}
			
			((SimplexHeightMapGenerator) simplexGUI.getHeightMapGenerator()).setLargestFeature(largestFeature);
			simplexGUI.getHeightMapGenerator().generateHeightMap();
			simplexGUI.update();
		}
		if(id.equals(SimplexGUI.PERSISTANCE_TEXT_FIELD_ID)) {
			
			double persistance = 0;			
			try {
				String sValue = ((JTextField)c).getText();
				persistance = Double.parseDouble(sValue);
			} catch (NumberFormatException exc) {
				// handle -- todo
			}
			
			((SimplexHeightMapGenerator) simplexGUI.getHeightMapGenerator()).setPersistance(persistance);
			simplexGUI.getHeightMapGenerator().generateHeightMap();
			simplexGUI.update();
		}
		if(id.equals(SimplexGUI.PERTURB_FREQ_TEXT_FIELD_ID)) {
			float perturbFreq = 0;			
			try {
				String sValue = ((JTextField)c).getText();
				perturbFreq = Float.parseFloat(sValue);
			} catch (NumberFormatException exc) {
				// handle -- todo
			}
			
			simplexGUI.getHeightMapGenerator().setPerturbFrequency(perturbFreq);
			simplexGUI.getHeightMapGenerator().generateHeightMap();
			simplexGUI.update();
		}
		if(id.equals(SimplexGUI.PERTURB_DIST_TEXT_FIELD_ID)) {
			float perturbDist = 0;			
			try {
				String sValue = ((JTextField)c).getText();
				perturbDist = Float.parseFloat(sValue);
			} catch (NumberFormatException exc) {
				// handle -- todo
			}
			
			simplexGUI.getHeightMapGenerator().setPerturbDistance(perturbDist);
			simplexGUI.getHeightMapGenerator().generateHeightMap();
			simplexGUI.update();
		}
		if(id.equals(SimplexGUI.ERODE_ITER_TEXT_FIELD_ID)) {
			int erodIter = 0;			
			try {
				String sValue = ((JTextField)c).getText();
				erodIter = Integer.parseInt(sValue);
			} catch (NumberFormatException exc) {
				// handle -- todo
			}
			
			simplexGUI.getHeightMapGenerator().setErodeIterations(erodIter);
			simplexGUI.getHeightMapGenerator().generateHeightMap();
			simplexGUI.update();
		}
		if(id.equals(SimplexGUI.ERODE_SMOOTH_TEXT_FIELD_ID)) {
			float erodeSmooth = 0;			
			try {
				String sValue = ((JTextField)c).getText();
				erodeSmooth = Float.parseFloat(sValue);
			} catch (NumberFormatException exc) {
				// handle -- todo
			}
			
			simplexGUI.getHeightMapGenerator().setErodeSmoothness(erodeSmooth);
			simplexGUI.getHeightMapGenerator().generateHeightMap();
			simplexGUI.update();
		}
		if(id.equals(SimplexGUI.RANDOM_GENERATION_BUTTON_ID)) {
			simplexGUI.getHeightMapGenerator().generateRandomHeightMap();
			simplexGUI.update();
		}
	}
}
