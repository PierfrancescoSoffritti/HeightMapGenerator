package GUI;

import heightMapGenerator.HeightMapGenerator;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import topography.GraysImageBorders;


public class GUIManager {
	
	public final String SHOW_TOPOGRAPHY_BUTTON_ID = "showTopographyButton";
	public final String SEED_TEXT_FIELD_ID = "seedTextField";
	public final String SAVE_HEIGHTMAP_BUTTON_ID = "saveHeightMapButton";
	
	public final String THRESHOLD_TEXT_FIELD_ID = "thresholdTextField";
	public final String SAVE_TOPOGRAPHY_BUTTON_ID = "saveTopographyButton";
	
	private final HeightMapGenerator heightMapGenerator;
	
	private MainWindowActionListener mainWindowActionListener;
	
	private JFrame frame;
	private JPanel imagePanel;
	
	private JFrame topographyFrame;
	private JPanel topographyImagePanel;
	
	public GUIManager(HeightMapGenerator hmp) {
		this.heightMapGenerator = hmp;
		
		mainWindowActionListener = new MainWindowActionListener(this);
		
		initMainWindow();
	}
	
	private void initMainWindow() {
		
        frame = new JFrame();
        frame.setLayout(new BorderLayout());
        Container container = frame.getContentPane();        
        
        imagePanel = new JPanel();
        imagePanel.setLayout(new BorderLayout());
        ImageIcon imageIcon = new ImageIcon(heightMapGenerator.generateHeightMap());
        JLabel imageLabel = new JLabel();
        imageLabel.setIcon(imageIcon);
        imagePanel.add(imageLabel, BorderLayout.CENTER);
        container.add(imagePanel, BorderLayout.CENTER);
        
        JPanel controlsPanel = new JPanel();
        controlsPanel.setLayout(new GridLayout(0,2));
        JLabel seedLabel = new JLabel("seed:", SwingConstants.CENTER);
        JTextField textField = new JTextField(""+heightMapGenerator.getSeed());
        textField.setHorizontalAlignment(SwingConstants.CENTER);
        JButton showTopographyButton = new JButton("Show topographic map");
        JButton saveHeightMapButton = new JButton("Save heightmap");
        controlsPanel.add(seedLabel);
        controlsPanel.add(textField);
        controlsPanel.add(showTopographyButton);
        controlsPanel.add(saveHeightMapButton);
        container.add(controlsPanel, BorderLayout.PAGE_END);
        
        showTopographyButton.setName(SHOW_TOPOGRAPHY_BUTTON_ID);
        showTopographyButton.addActionListener(mainWindowActionListener);
        textField.setName(SEED_TEXT_FIELD_ID);
        textField.addActionListener(mainWindowActionListener);
        saveHeightMapButton.setName(SAVE_HEIGHTMAP_BUTTON_ID);
        saveHeightMapButton.addActionListener(mainWindowActionListener);
	}
	
	public void showMainWindow() {
		frame.pack();
        frame.setResizable(true);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	// 0<=threshold<=255
	private void initTopographyWindow(int threshold) {		
        topographyFrame = new JFrame();
        topographyFrame.setLayout(new BorderLayout());
        Container container = topographyFrame.getContentPane();        
        
        topographyImagePanel = new JPanel();
        topographyImagePanel.setLayout(new BorderLayout());
        ImageIcon imageIcon = new ImageIcon(GraysImageBorders.getBordersImage(heightMapGenerator.getCachedHeightMapImage(), threshold));
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
	
	public void updateTopographyWindow(int threshold) {
		topographyImagePanel.removeAll();
		
		boolean printInfo = heightMapGenerator.isPrintInfoEnabled(); 
		heightMapGenerator.enablePrintInfo(false);
		ImageIcon imageIcon = new ImageIcon(GraysImageBorders.getBordersImage(heightMapGenerator.getCachedHeightMapImage(), threshold));
		heightMapGenerator.enablePrintInfo(printInfo);
		
        JLabel imageLabel = new JLabel();
        imageLabel.setIcon(imageIcon);
        topographyImagePanel.add(imageLabel, BorderLayout.CENTER);
        
        topographyFrame.invalidate();
        topographyFrame.revalidate();
        topographyFrame.repaint();
	}
	
	public void showTopographyWindow(int threshold) {
		// i need to disable it because if is enabled i don't want to print the infos for a second time
		boolean printInfo = heightMapGenerator.isPrintInfoEnabled(); 
		heightMapGenerator.enablePrintInfo(false);
		
        initTopographyWindow(threshold);
        
        topographyFrame.pack();
        topographyFrame.setResizable(true);
        topographyFrame.setVisible(true);
        
        // restore printInfo
        heightMapGenerator.enablePrintInfo(printInfo);
	}
	
	public void update() {
		imagePanel.removeAll();
		
		ImageIcon imageIcon = new ImageIcon(heightMapGenerator.generateHeightMap());
        JLabel imageLabel = new JLabel();
        imageLabel.setIcon(imageIcon);
        imagePanel.add(imageLabel, BorderLayout.CENTER);
        
        frame.invalidate();
        frame.revalidate();
        frame.repaint();
	}
	
	public HeightMapGenerator getHeightMapGenerator() {
		return this.heightMapGenerator;
	}
}
