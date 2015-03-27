package GUI;

import heightMapGenerator.HeightMapGenerator;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import topography.GraysImageBorders;

/**
* GUIManager.java
* @author Pierfrancesco Soffritti
*
*/

public class GUIManager {
	
	public final String SIZE_TEXT_FIELD_ID = "SIZE_TEXT_FIELD_ID";
	public final String PERLIN_FREQ_TEXT_FIELD_ID = "PERLIN_FREQ_TEXT_FIELD_ID";
	public final String PERTURB_FREQ_TEXT_FIELD_ID = "PERTURB_FREQ_TEXT_FIELD_ID";
	public final String PERTURB_DIST_TEXT_FIELD_ID = "PERTURB_DIST_TEXT_FIELD_ID";
	public final String ERODE_ITER_TEXT_FIELD_ID = "ERODE_ITER_TEXT_FIELD_ID";
	public final String ERODE_SMOOTH_TEXT_FIELD_ID = "ERODE_SMOOTH_TEXT_FIELD_ID";
	public final String SHOW_TOPOGRAPHY_BUTTON_ID = "SHOW_TOPOGRAPHY_BUTTON_ID";
	public final String SEED_TEXT_FIELD_ID = "SEED_TEXT_FIELD_ID";
	public final String SAVE_HEIGHTMAP_BUTTON_ID = "SAVE_HEIGHTMAP_BUTTON_ID";
	public final String USE_GRAY_SCALE_CB_ID = "USE_GRAY_SCALE_CB_ID";
	
	public final String RANDOM_GENERATION_BUTTON_ID = "RANDOM_GENERATION_BUTTON_ID";
	
	public final String THRESHOLD_TEXT_FIELD_ID = "THRESHOLD_TEXT_FIELD_ID";
	public final String SAVE_TOPOGRAPHY_BUTTON_ID = "SAVE_TOPOGRAPHY_BUTTON_ID";
	
	private final HeightMapGenerator heightMapGenerator;
	
	private MainWindowActionListener mainWindowActionListener;
	
	private JFrame frame;
	private JPanel imagePanel;
	
	private JTextField seedTextField;
	private JTextField sizeTextField;
	private JTextField perlinNoiseFreqTextField;
	private JTextField perturbationFreqTextField;
	private JTextField perturbationDistanceTextField;
	private JTextField erodeIterationTextField;
	private JTextField erodeSmoothnessTextField;
	private JLabel minMaxValueLabel;
	
	private JFrame topographyFrame;
	private JPanel topographyImagePanel;
	
	public GUIManager(HeightMapGenerator hmp) {
		this.heightMapGenerator = hmp;
		
		mainWindowActionListener = new MainWindowActionListener(this);
		
		initMainWindow();
	}
	
	private void initMainWindow() {
		
        frame = new JFrame("Heightmap generator - Pierfrancesco Soffritti");
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
        seedTextField = new JTextField(""+heightMapGenerator.getSeed());
        seedTextField.setHorizontalAlignment(SwingConstants.CENTER);
        controlsPanel.add(seedLabel);
        controlsPanel.add(seedTextField);
        seedTextField.setName(SEED_TEXT_FIELD_ID);
        seedTextField.addActionListener(mainWindowActionListener);
        
        JLabel sizeLabel = new JLabel("Map size:", SwingConstants.CENTER);
        sizeTextField = new JTextField(""+heightMapGenerator.getMapSize());
        sizeTextField.setHorizontalAlignment(SwingConstants.CENTER);
        controlsPanel.add(sizeLabel);
        controlsPanel.add(sizeTextField);
        sizeTextField.setName(SIZE_TEXT_FIELD_ID);
        sizeTextField.addActionListener(mainWindowActionListener);
        
        JLabel perlinNoiseFreqLabel = new JLabel("Perlin noise frequency:", SwingConstants.CENTER);
        perlinNoiseFreqTextField = new JTextField(""+heightMapGenerator.getPerlinNoiseFrequency());
        perlinNoiseFreqTextField.setHorizontalAlignment(SwingConstants.CENTER);
        controlsPanel.add(perlinNoiseFreqLabel);
        controlsPanel.add(perlinNoiseFreqTextField);
        perlinNoiseFreqTextField.setName(PERLIN_FREQ_TEXT_FIELD_ID);
        perlinNoiseFreqTextField.addActionListener(mainWindowActionListener);
        
        JLabel perturbationFreqLabel = new JLabel("Perturbation frequency:", SwingConstants.CENTER);
        perturbationFreqTextField = new JTextField(""+heightMapGenerator.getPerturbFrequency());
        perturbationFreqTextField.setHorizontalAlignment(SwingConstants.CENTER);
        controlsPanel.add(perturbationFreqLabel);
        controlsPanel.add(perturbationFreqTextField);
        perturbationFreqTextField.setName(PERTURB_FREQ_TEXT_FIELD_ID);
        perturbationFreqTextField.addActionListener(mainWindowActionListener);
        
        JLabel perturbationDistanceLabel = new JLabel("Perturbation max distance:", SwingConstants.CENTER);
        perturbationDistanceTextField = new JTextField(""+heightMapGenerator.getPerturbDistance());
        perturbationDistanceTextField.setHorizontalAlignment(SwingConstants.CENTER);
        controlsPanel.add(perturbationDistanceLabel);
        controlsPanel.add(perturbationDistanceTextField);
        perturbationDistanceTextField.setName(PERTURB_DIST_TEXT_FIELD_ID);
        perturbationDistanceTextField.addActionListener(mainWindowActionListener);
        
        JLabel erodeIterationLabel = new JLabel("Erode iteration:", SwingConstants.CENTER);
        erodeIterationTextField = new JTextField(""+heightMapGenerator.getErodeIterations());
        erodeIterationTextField.setHorizontalAlignment(SwingConstants.CENTER);
        controlsPanel.add(erodeIterationLabel);
        controlsPanel.add(erodeIterationTextField);
        erodeIterationTextField.setName(ERODE_ITER_TEXT_FIELD_ID);
        erodeIterationTextField.addActionListener(mainWindowActionListener);
        
        JLabel erodeSmoothnessLabel = new JLabel("Erode smoothness:", SwingConstants.CENTER);
        erodeSmoothnessTextField = new JTextField(""+heightMapGenerator.getErodeSmoothness());
        erodeSmoothnessTextField.setHorizontalAlignment(SwingConstants.CENTER);
        controlsPanel.add(erodeSmoothnessLabel);
        controlsPanel.add(erodeSmoothnessTextField);
        erodeSmoothnessTextField.setName(ERODE_SMOOTH_TEXT_FIELD_ID);
        erodeSmoothnessTextField.addActionListener(mainWindowActionListener);
        
        JCheckBox useGrayScaleCheckBox = new JCheckBox("Use gray scale");
        useGrayScaleCheckBox.setSelected(true);
        useGrayScaleCheckBox.setHorizontalAlignment(SwingConstants.CENTER);
        controlsPanel.add(useGrayScaleCheckBox);
        useGrayScaleCheckBox.setName(USE_GRAY_SCALE_CB_ID);
        useGrayScaleCheckBox.addActionListener(mainWindowActionListener);
        
         minMaxValueLabel = new JLabel("Info:  " +heightMapGenerator.getMapInfo());
        controlsPanel.add(minMaxValueLabel);
        
        JButton showTopographyButton = new JButton("Show topographic map");
        JButton saveHeightMapButton = new JButton("Save heightmap");
        controlsPanel.add(showTopographyButton);
        controlsPanel.add(saveHeightMapButton);
        showTopographyButton.setName(SHOW_TOPOGRAPHY_BUTTON_ID);
        showTopographyButton.addActionListener(mainWindowActionListener);        
        saveHeightMapButton.setName(SAVE_HEIGHTMAP_BUTTON_ID);
        saveHeightMapButton.addActionListener(mainWindowActionListener);
        
        JButton randomGenerationButton = new JButton("Random Generation");
        controlsPanel.add(randomGenerationButton);
        randomGenerationButton.setName(RANDOM_GENERATION_BUTTON_ID);
        randomGenerationButton.addActionListener(mainWindowActionListener);
        
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
        topographyFrame = new JFrame("Topography");
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
		
		ImageIcon imageIcon = new ImageIcon(heightMapGenerator.getCachedHeightMapImage());
        JLabel imageLabel = new JLabel();
        imageLabel.setIcon(imageIcon);
        imagePanel.add(imageLabel, BorderLayout.CENTER);
        
        seedTextField.setText(heightMapGenerator.getSeed()+"");
    	sizeTextField.setText(heightMapGenerator.getMapSize()+"");
    	perlinNoiseFreqTextField.setText(heightMapGenerator.getPerlinNoiseFrequency()+"");
    	perturbationFreqTextField.setText(heightMapGenerator.getPerturbFrequency()+"");
    	perturbationDistanceTextField.setText(heightMapGenerator.getPerturbDistance()+"");
    	erodeIterationTextField.setText(heightMapGenerator.getErodeIterations()+"");
    	erodeSmoothnessTextField.setText(heightMapGenerator.getErodeSmoothness()+"");
    	minMaxValueLabel.setText("Info:  " +heightMapGenerator.getMapInfo());
        
        frame.invalidate();
        frame.revalidate();
        frame.pack();
        frame.repaint();
	}
	
	public HeightMapGenerator getHeightMapGenerator() {
		return this.heightMapGenerator;
	}
}
