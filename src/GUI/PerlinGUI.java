package GUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import GUI.listeners.PerlinWindowActionListener;
import heightMap.PerlinHeightMapGenerator;

/**
* PerlinGUI.java
* @author Pierfrancesco Soffritti
*
*/

public class PerlinGUI implements GUI {	
	
	public static final String SEED_TEXT_FIELD_ID = "SEED_TEXT_FIELD_ID";
	public static final String SIZE_TEXT_FIELD_ID = "SIZE_TEXT_FIELD_ID";
	public static final String PERLIN_FREQ_TEXT_FIELD_ID = "PERLIN_FREQ_TEXT_FIELD_ID";
	public static final String PERTURB_FREQ_TEXT_FIELD_ID = "PERTURB_FREQ_TEXT_FIELD_ID";
	public static final String PERTURB_DIST_TEXT_FIELD_ID = "PERTURB_DIST_TEXT_FIELD_ID";
	public static final String ERODE_ITER_TEXT_FIELD_ID = "ERODE_ITER_TEXT_FIELD_ID";
	public static final String ERODE_SMOOTH_TEXT_FIELD_ID = "ERODE_SMOOTH_TEXT_FIELD_ID";
	public static final String USE_GRAY_SCALE_CB_ID = "USE_GRAY_SCALE_CB_ID";
	public static final String USE_HSB_COLOR_SCALE = "USE_HSB_COLOR_SCALE";
	public final static String RANDOM_GENERATION_BUTTON_ID = "RANDOM_GENERATION_BUTTON_ID";

	private final PerlinHeightMapGenerator perlinHeightMapGenerator;	
	private PerlinWindowActionListener perlinWindowActionListener;
	private GUIManager guiManager;
	
	private JPanel containerPanel;
	private JPanel imageContainer;
	private JTextField seedTextField;
	private JTextField sizeTextField;
	private JTextField perlinNoiseFreqTextField;
	private JTextField perturbationFreqTextField;
	private JTextField perturbationDistanceTextField;
	private JTextField erodeIterationTextField;
	private JTextField erodeSmoothnessTextField;
	private JLabel minMaxValueLabel;
	
	
	//The Perlin GUI should be created with at least a PerlinHeightMapGenerator
	//No need to explicit downcast this way
	public PerlinGUI(GUIManager guiManager, PerlinHeightMapGenerator perlinHeightMapGenerator) {
		
		this.perlinHeightMapGenerator =  perlinHeightMapGenerator;		
		this.perlinWindowActionListener = new PerlinWindowActionListener(this);
		this.guiManager = guiManager;
	}

	@Override
	public JPanel getJPanel() {		
		
		containerPanel = new JPanel();
        containerPanel.setLayout(new BorderLayout());
        
        imageContainer = new JPanel();
        imageContainer.setLayout(new BorderLayout());
        ImageIcon imageIcon = new ImageIcon(perlinHeightMapGenerator.generateHeightMap());
        JLabel imageLabel = new JLabel();
        imageLabel.setIcon(imageIcon);
        imageContainer.add(imageLabel, BorderLayout.CENTER);
        containerPanel.add(imageContainer, BorderLayout.CENTER);
        
        JPanel controlsPanel = new JPanel();
        controlsPanel.setLayout(new GridLayout(0,2));
        
        JLabel seedLabel = new JLabel("seed:", SwingConstants.CENTER);
        seedTextField = new JTextField(""+perlinHeightMapGenerator.getSeed());
        seedTextField.setHorizontalAlignment(SwingConstants.CENTER);
        controlsPanel.add(seedLabel);
        controlsPanel.add(seedTextField);
        seedTextField.setName(SEED_TEXT_FIELD_ID);
        seedTextField.addActionListener(perlinWindowActionListener);
        
        JLabel sizeLabel = new JLabel("Map size:", SwingConstants.CENTER);
        sizeTextField = new JTextField(""+perlinHeightMapGenerator.getMapSize());
        sizeTextField.setHorizontalAlignment(SwingConstants.CENTER);
        controlsPanel.add(sizeLabel);
        controlsPanel.add(sizeTextField);
        sizeTextField.setName(SIZE_TEXT_FIELD_ID);
        sizeTextField.addActionListener(perlinWindowActionListener);
        
        JLabel perlinNoiseFreqLabel = new JLabel("Perlin noise frequency:", SwingConstants.CENTER);
        perlinNoiseFreqTextField = new JTextField(""+perlinHeightMapGenerator.getPerlinNoiseFrequency());
        perlinNoiseFreqTextField.setHorizontalAlignment(SwingConstants.CENTER);
        controlsPanel.add(perlinNoiseFreqLabel);
        controlsPanel.add(perlinNoiseFreqTextField);
        perlinNoiseFreqTextField.setName(PERLIN_FREQ_TEXT_FIELD_ID);
        perlinNoiseFreqTextField.addActionListener(perlinWindowActionListener);
        
        JLabel perturbationFreqLabel = new JLabel("Perturbation frequency:", SwingConstants.CENTER);
        perturbationFreqTextField = new JTextField(""+perlinHeightMapGenerator.getPerturbFrequency());
        perturbationFreqTextField.setHorizontalAlignment(SwingConstants.CENTER);
        controlsPanel.add(perturbationFreqLabel);
        controlsPanel.add(perturbationFreqTextField);
        perturbationFreqTextField.setName(PERTURB_FREQ_TEXT_FIELD_ID);
        perturbationFreqTextField.addActionListener(perlinWindowActionListener);
        
        JLabel perturbationDistanceLabel = new JLabel("Perturbation max distance:", SwingConstants.CENTER);
        perturbationDistanceTextField = new JTextField(""+perlinHeightMapGenerator.getPerturbDistance());
        perturbationDistanceTextField.setHorizontalAlignment(SwingConstants.CENTER);
        controlsPanel.add(perturbationDistanceLabel);
        controlsPanel.add(perturbationDistanceTextField);
        perturbationDistanceTextField.setName(PERTURB_DIST_TEXT_FIELD_ID);
        perturbationDistanceTextField.addActionListener(perlinWindowActionListener);
        
        JLabel erodeIterationLabel = new JLabel("Erode iteration:", SwingConstants.CENTER);
        erodeIterationTextField = new JTextField(""+perlinHeightMapGenerator.getErodeIterations());
        erodeIterationTextField.setHorizontalAlignment(SwingConstants.CENTER);
        controlsPanel.add(erodeIterationLabel);
        controlsPanel.add(erodeIterationTextField);
        erodeIterationTextField.setName(ERODE_ITER_TEXT_FIELD_ID);
        erodeIterationTextField.addActionListener(perlinWindowActionListener);
        
        JLabel erodeSmoothnessLabel = new JLabel("Erode smoothness:", SwingConstants.CENTER);
        erodeSmoothnessTextField = new JTextField(""+perlinHeightMapGenerator.getErodeSmoothness());
        erodeSmoothnessTextField.setHorizontalAlignment(SwingConstants.CENTER);
        controlsPanel.add(erodeSmoothnessLabel);
        controlsPanel.add(erodeSmoothnessTextField);
        erodeSmoothnessTextField.setName(ERODE_SMOOTH_TEXT_FIELD_ID);
        erodeSmoothnessTextField.addActionListener(perlinWindowActionListener);
        
        JCheckBox useGrayScaleCheckBox = new JCheckBox("Use gray scale");
        useGrayScaleCheckBox.setSelected(true);
        useGrayScaleCheckBox.setHorizontalAlignment(SwingConstants.CENTER);
        controlsPanel.add(useGrayScaleCheckBox);
        useGrayScaleCheckBox.setName(USE_GRAY_SCALE_CB_ID);
        useGrayScaleCheckBox.addActionListener(perlinWindowActionListener);
        
        JCheckBox useHSBScaleCheckBox = new JCheckBox("Use HSB color scale");
        useHSBScaleCheckBox.setSelected(false);
        useHSBScaleCheckBox.setHorizontalAlignment(SwingConstants.CENTER);
        controlsPanel.add(useHSBScaleCheckBox);
        useHSBScaleCheckBox.setName(USE_HSB_COLOR_SCALE);
        useHSBScaleCheckBox.addActionListener(perlinWindowActionListener);
        
        JButton randomGenerationButton = new JButton("Random Generation");
        controlsPanel.add(randomGenerationButton);
        randomGenerationButton.setName(RANDOM_GENERATION_BUTTON_ID);
        randomGenerationButton.addActionListener(perlinWindowActionListener);
        
        minMaxValueLabel = new JLabel("Info:  " +perlinHeightMapGenerator.getMapInfo());
        controlsPanel.add(minMaxValueLabel);
        
        containerPanel.add(controlsPanel, BorderLayout.PAGE_END);
        
        return containerPanel;
	}

	@Override
	public BufferedImage getBufferedImage() {
		return perlinHeightMapGenerator.getCachedHeightMapImage();
	}

	@Override
	public PerlinHeightMapGenerator getHeightMapGenerator() {
		return this.perlinHeightMapGenerator;
	}

	@Override
	public void update() {
		imageContainer.removeAll();
		
		ImageIcon imageIcon = new ImageIcon(perlinHeightMapGenerator.getCachedHeightMapImage());
        JLabel imageLabel = new JLabel();
        imageLabel.setIcon(imageIcon);
        imageContainer.add(imageLabel, BorderLayout.CENTER);
        
        seedTextField.setText(perlinHeightMapGenerator.getSeed()+"");
    	sizeTextField.setText(perlinHeightMapGenerator.getMapSize()+"");
    	perlinNoiseFreqTextField.setText(perlinHeightMapGenerator.getPerlinNoiseFrequency()+"");
    	perturbationFreqTextField.setText(perlinHeightMapGenerator.getPerturbFrequency()+"");
    	perturbationDistanceTextField.setText(perlinHeightMapGenerator.getPerturbDistance()+"");
    	erodeIterationTextField.setText(perlinHeightMapGenerator.getErodeIterations()+"");
    	erodeSmoothnessTextField.setText(perlinHeightMapGenerator.getErodeSmoothness()+"");
    	minMaxValueLabel.setText("Info:  " +perlinHeightMapGenerator.getMapInfo());
        
        guiManager.updateFromChild();
	}
	
	@Override
	public String toString() {
		return "Perlin Noise";
	}
}
