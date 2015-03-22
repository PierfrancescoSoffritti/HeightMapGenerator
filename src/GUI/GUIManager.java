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
	
	public final String PERLIN_FREQ_TEXT_FIELD_ID = "PERLIN_FREQ_TEXT_FIELD_ID";
	public final String PERTURB_FREQ_TEXT_FIELD_ID = "PERTURB_FREQ_TEXT_FIELD_ID";
	public final String PERTURB_DIST_TEXT_FIELD_ID = "PERTURB_DIST_TEXT_FIELD_ID";
	public final String ERODE_ITER_TEXT_FIELD_ID = "ERODE_ITER_TEXT_FIELD_ID";
	public final String ERODE_SMOOTH_TEXT_FIELD_ID = "ERODE_SMOOTH_TEXT_FIELD_ID";
	public final String SHOW_TOPOGRAPHY_BUTTON_ID = "SHOW_TOPOGRAPHY_BUTTON_ID";
	public final String SEED_TEXT_FIELD_ID = "SEED_TEXT_FIELD_ID";
	public final String SAVE_HEIGHTMAP_BUTTON_ID = "SAVE_HEIGHTMAP_BUTTON_ID";
	
	public final String THRESHOLD_TEXT_FIELD_ID = "THRESHOLD_TEXT_FIELD_ID";
	public final String SAVE_TOPOGRAPHY_BUTTON_ID = "SAVE_TOPOGRAPHY_BUTTON_ID";
	
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
        JTextField seedTextField = new JTextField(""+heightMapGenerator.getSeed());
        seedTextField.setHorizontalAlignment(SwingConstants.CENTER);
        controlsPanel.add(seedLabel);
        controlsPanel.add(seedTextField);
        seedTextField.setName(SEED_TEXT_FIELD_ID);
        seedTextField.addActionListener(mainWindowActionListener);
        
        JLabel perlinNoiseFreqLabel = new JLabel("Perlin noise frequency:", SwingConstants.CENTER);
        JTextField perlinNoiseFreqTextField = new JTextField(""+heightMapGenerator.getPerlinNoiseFrequency());
        perlinNoiseFreqTextField.setHorizontalAlignment(SwingConstants.CENTER);
        controlsPanel.add(perlinNoiseFreqLabel);
        controlsPanel.add(perlinNoiseFreqTextField);
        perlinNoiseFreqTextField.setName(PERLIN_FREQ_TEXT_FIELD_ID);
        perlinNoiseFreqTextField.addActionListener(mainWindowActionListener);
        
        JLabel perturbationFreqLabel = new JLabel("Perturbation frequency:", SwingConstants.CENTER);
        JTextField perturbationFreqTextField = new JTextField(""+heightMapGenerator.getPerturbFrequency());
        perturbationFreqTextField.setHorizontalAlignment(SwingConstants.CENTER);
        controlsPanel.add(perturbationFreqLabel);
        controlsPanel.add(perturbationFreqTextField);
        perturbationFreqTextField.setName(PERTURB_FREQ_TEXT_FIELD_ID);
        perturbationFreqTextField.addActionListener(mainWindowActionListener);
        
        JLabel perturbationDistanceLabel = new JLabel("Perturbation max distance:", SwingConstants.CENTER);
        JTextField perturbationDistanceTextField = new JTextField(""+heightMapGenerator.getPerturbDistance());
        perturbationDistanceTextField.setHorizontalAlignment(SwingConstants.CENTER);
        controlsPanel.add(perturbationDistanceLabel);
        controlsPanel.add(perturbationDistanceTextField);
        perturbationDistanceTextField.setName(PERTURB_DIST_TEXT_FIELD_ID);
        perturbationDistanceTextField.addActionListener(mainWindowActionListener);
        
        JLabel erodeIterationLabel = new JLabel("Erode iteration:", SwingConstants.CENTER);
        JTextField erodeIterationTextField = new JTextField(""+heightMapGenerator.getErodeIterations());
        erodeIterationTextField.setHorizontalAlignment(SwingConstants.CENTER);
        controlsPanel.add(erodeIterationLabel);
        controlsPanel.add(erodeIterationTextField);
        erodeIterationTextField.setName(ERODE_ITER_TEXT_FIELD_ID);
        erodeIterationTextField.addActionListener(mainWindowActionListener);
        
        JLabel erodeSmoothnessLabel = new JLabel("Erode smoothness:", SwingConstants.CENTER);
        JTextField erodeSmoothnessTextField = new JTextField(""+heightMapGenerator.getErodeSmoothness());
        erodeSmoothnessTextField.setHorizontalAlignment(SwingConstants.CENTER);
        controlsPanel.add(erodeSmoothnessLabel);
        controlsPanel.add(erodeSmoothnessTextField);
        erodeSmoothnessTextField.setName(ERODE_SMOOTH_TEXT_FIELD_ID);
        erodeSmoothnessTextField.addActionListener(mainWindowActionListener);
        
        JButton showTopographyButton = new JButton("Show topographic map");
        JButton saveHeightMapButton = new JButton("Save heightmap");
        controlsPanel.add(showTopographyButton);
        controlsPanel.add(saveHeightMapButton);
        showTopographyButton.setName(SHOW_TOPOGRAPHY_BUTTON_ID);
        showTopographyButton.addActionListener(mainWindowActionListener);        
        saveHeightMapButton.setName(SAVE_HEIGHTMAP_BUTTON_ID);
        saveHeightMapButton.addActionListener(mainWindowActionListener);
        
        container.add(controlsPanel, BorderLayout.PAGE_END);
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
