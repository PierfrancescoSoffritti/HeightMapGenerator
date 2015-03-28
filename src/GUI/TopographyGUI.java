package GUI;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import topography.GraysImageBorders;
import GUI.listeners.TopographyActionListener;

/**
* TopographyGUI.java
* @author Pierfrancesco Soffritti
*
*/

public class TopographyGUI {
	
	public static final String THRESHOLD_TEXT_FIELD_ID = "THRESHOLD_TEXT_FIELD_ID";
	public static final String SAVE_TOPOGRAPHY_BUTTON_ID = "SAVE_TOPOGRAPHY_BUTTON_ID";
	
	private JFrame topographyFrame;
	private JPanel topographyImagePanel;
	
	private BufferedImage image;
	private int threshold;
	
	public TopographyGUI(int threshold, BufferedImage image) {
		
		this.image = image;
		this.threshold = threshold;
		
		initTopographyWindow();
	}
	
	public void show() {
		topographyFrame.pack();
        topographyFrame.setResizable(true);
        topographyFrame.setVisible(true);
	}
	
	// 0<=threshold<=255
	private void initTopographyWindow() {		
        topographyFrame = new JFrame("Topography");
        topographyFrame.setLayout(new BorderLayout());
        Container container = topographyFrame.getContentPane();        
        
        topographyImagePanel = new JPanel();
        topographyImagePanel.setLayout(new BorderLayout());
        ImageIcon imageIcon = new ImageIcon(GraysImageBorders.getBordersImage(image, threshold));
        JLabel imageLabel = new JLabel();
        imageLabel.setIcon(imageIcon);
        topographyImagePanel.add(imageLabel, BorderLayout.CENTER);
        container.add(topographyImagePanel, BorderLayout.CENTER);
        
        JPanel controlsPanel = new JPanel();
        controlsPanel.setLayout(new GridLayout(0,2));
        JLabel seedLabel = new JLabel("Threshold:", SwingConstants.CENTER);
        JTextField textField = new JTextField(""+threshold);
        textField.setHorizontalAlignment(SwingConstants.CENTER);
        JButton saveTopographicMapButton = new JButton("Save topographic map");
        controlsPanel.add(seedLabel);
        controlsPanel.add(textField);
        controlsPanel.add(saveTopographicMapButton);
        container.add(controlsPanel, BorderLayout.PAGE_END);
        
        TopographyActionListener tal = new TopographyActionListener(this);
        textField.setName(THRESHOLD_TEXT_FIELD_ID);
        textField.addActionListener(tal);
        saveTopographicMapButton.setName(SAVE_TOPOGRAPHY_BUTTON_ID);
        saveTopographicMapButton.addActionListener(tal);
	}
	
	public void update(int thresh) {
		topographyImagePanel.removeAll();
		
		this.threshold = thresh;
		ImageIcon imageIcon = new ImageIcon(GraysImageBorders.getBordersImage(image, threshold));	
        JLabel imageLabel = new JLabel();
        imageLabel.setIcon(imageIcon);
        topographyImagePanel.add(imageLabel, BorderLayout.CENTER);
        
        topographyFrame.invalidate();
        topographyFrame.revalidate();
        topographyFrame.repaint();
	}

	public RenderedImage getBufferedImage() {
		return GraysImageBorders.getBordersImage(image, threshold);
	}

}
