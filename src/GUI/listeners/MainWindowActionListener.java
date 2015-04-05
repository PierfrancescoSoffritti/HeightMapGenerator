package GUI.listeners;

import heightMap.render.RenderException;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import GUI.GUIManager;

/**
* MainWindowActionListener.java
* @author Pierfrancesco Soffritti
*
*/

public class MainWindowActionListener implements ActionListener {

	private GUIManager guiManager;
	
	public MainWindowActionListener(GUIManager guiManager) {
		this.guiManager = guiManager;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void actionPerformed(ActionEvent e) {
		Component c = (Component) e.getSource();
		String id = c.getName();
		
		if(id.equals(GUIManager.SAVE_HEIGHTMAP_BUTTON_ID)) {
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
					ImageIO.write(guiManager.getCurrentGUI().getBufferedImage(), "png", fileToSave);
				} catch (IOException | RenderException exc) {
					exc.printStackTrace();
				}
			}			
		}
		if(id.equals(GUIManager.SHOW_TOPOGRAPHY_BUTTON_ID)) {
			try {
				guiManager.showTopography(1);
			} catch (RenderException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if(id.equals(GUIManager.GUI_NAMES_LIST_BUTTON_ID)) {
			int selected = ((JComboBox)c).getSelectedIndex();
			
			if(selected != guiManager.getCurrentGUIIndex())
				try {
					guiManager.setCurrentGUI(selected);
				} catch (RenderException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		}
		if(id.equals(GUIManager.SEND_HEIGHTMAP_BUTTON_ID)) {
			guiManager.sendHeightMap();
		}
	}

}
