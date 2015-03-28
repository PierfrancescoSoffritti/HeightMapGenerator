package GUI.listeners;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;

import GUI.TopographyGUI;

/**
* TopographyActionListener.java
* @author Pierfrancesco Soffritti
*
*/

public class TopographyActionListener implements ActionListener {

	private TopographyGUI topographyGUI;
	
	public TopographyActionListener(TopographyGUI topographyGUI) {
		this.topographyGUI = topographyGUI;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Component c = (Component) e.getSource();
		String id = c.getName();
		
		if(id.equals(TopographyGUI.THRESHOLD_TEXT_FIELD_ID)) {
			
			int value = 0;			
			try {
				String sValue = ((JTextField)c).getText();
				value = Integer.parseInt(sValue);
				
				if(sValue.contains("."))
					throw new Exception();		
				
				if(value < 0 || value > 255)
					throw new Exception();
			} catch (NumberFormatException exc) {
				// handle -- todo
			} catch (Exception exc2) {
				// handle -- todo
			}
			
			topographyGUI.update(value);		
		}
		
		if(id.equals(TopographyGUI.SAVE_TOPOGRAPHY_BUTTON_ID)) {
			// parent component of the dialog
			JFrame parentFrame = new JFrame();
			
			// start from current dir
			JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
			fileChooser.setDialogTitle("Save topography");
			 
			int userSelection = fileChooser.showSaveDialog(parentFrame);
			 
			if (userSelection == JFileChooser.APPROVE_OPTION) {
				// save file
			    File fileToSave = fileChooser.getSelectedFile();
			    try {
					ImageIO.write(topographyGUI.getBufferedImage(), "png", fileToSave);
				} catch (IOException exc) {
					exc.printStackTrace();
				}
			}
		}

	}

}
