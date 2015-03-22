package GUI;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;


public class TopographyActionListener implements ActionListener {

	private GUIManager gui;
	
	public TopographyActionListener(GUIManager gui) {
		this.gui = gui;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Component c = (Component) e.getSource();
		String id = c.getName();
		
		if(id.equals(gui.THRESHOLD_TEXT_FIELD_ID)) {
			
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
			
			gui.updateTopographyWindow(value);		
		}
		
		if(id.equals(gui.SAVE_TOPOGRAPHY_BUTTON_ID)) {	
		}

	}

}
