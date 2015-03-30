package GUI;

import heightMap.AbstractHeightMapGenerator;
import heightMap.SimplexHeightMapGenerator;

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

import GUI.listeners.SimplexWindowActionListener;

/**
* SimplexGUI.java
* @author Pierfrancesco Soffritti
*
*/

public class SimplexGUI implements GUI {

	public static final String LARGEST_FEATURE_TEXT_FIELD_ID = "LARGEST_FEATURE_TEXT_FIELD_ID";
	public static final String PERSISTANCE_TEXT_FIELD_ID = "PERSISTANCE_TEXT_FIELD_ID";
	public static final String SEED_TEXT_FIELD_ID = "SEED_TEXT_FIELD_ID";
	public static final String SIZE_TEXT_FIELD_ID = "SIZE_TEXT_FIELD_ID";
	public static final String PERTURB_FREQ_TEXT_FIELD_ID = "PERTURB_FREQ_TEXT_FIELD_ID";
	public static final String PERTURB_DIST_TEXT_FIELD_ID = "PERTURB_DIST_TEXT_FIELD_ID";
	public static final String ERODE_ITER_TEXT_FIELD_ID = "ERODE_ITER_TEXT_FIELD_ID";
	public static final String ERODE_SMOOTH_TEXT_FIELD_ID = "ERODE_SMOOTH_TEXT_FIELD_ID";
	public static final String USE_GRAY_SCALE_CB_ID = "USE_GRAY_SCALE_CB_ID";
	public static final String USE_HSB_COLOR_SCALE = "USE_HSB_COLOR_SCALE";
	
	public final static String RANDOM_GENERATION_BUTTON_ID = "RANDOM_GENERATION_BUTTON_ID";

	private final SimplexHeightMapGenerator simplexHeightMapGenerator;	
	private SimplexWindowActionListener simplexWindowActionListener;
	private GUIManager guiManager;
	
	private JPanel containerPanel;
	private JPanel imageContainer;
	private JTextField largestFeatureTextField;
	private JTextField persistanceTextField;
	private JTextField seedTextField;
	private JTextField sizeTextField;
	private JTextField perturbationFreqTextField;
	private JTextField perturbationDistanceTextField;
	private JTextField erodeIterationTextField;
	private JTextField erodeSmoothnessTextField;
	private JLabel minMaxValueLabel;
	
	public SimplexGUI(GUIManager guiManager, AbstractHeightMapGenerator ahmg) {
		
		this.simplexHeightMapGenerator = (SimplexHeightMapGenerator) ahmg;		
		this.simplexWindowActionListener = new SimplexWindowActionListener(this);
		this.guiManager = guiManager;
	}

	@Override
	public JPanel getJPanel() {		
		
		containerPanel = new JPanel();
        containerPanel.setLayout(new BorderLayout());
        
        imageContainer = new JPanel();
        imageContainer.setLayout(new BorderLayout());
        ImageIcon imageIcon = new ImageIcon(simplexHeightMapGenerator.generateHeightMap());
        JLabel imageLabel = new JLabel();
        imageLabel.setIcon(imageIcon);
        imageContainer.add(imageLabel, BorderLayout.CENTER);
        containerPanel.add(imageContainer, BorderLayout.CENTER);
        
        JPanel controlsPanel = new JPanel();
        controlsPanel.setLayout(new GridLayout(0,2));
        
        JLabel seedLabel = new JLabel("seed:", SwingConstants.CENTER);
        seedTextField = new JTextField(""+simplexHeightMapGenerator.getSeed());
        seedTextField.setHorizontalAlignment(SwingConstants.CENTER);
        controlsPanel.add(seedLabel);
        controlsPanel.add(seedTextField);
        seedTextField.setName(SEED_TEXT_FIELD_ID);
        seedTextField.addActionListener(simplexWindowActionListener);
        
        JLabel sizeLabel = new JLabel("Map size:", SwingConstants.CENTER);
        sizeTextField = new JTextField(""+simplexHeightMapGenerator.getMapSize());
        sizeTextField.setHorizontalAlignment(SwingConstants.CENTER);
        controlsPanel.add(sizeLabel);
        controlsPanel.add(sizeTextField);
        sizeTextField.setName(SIZE_TEXT_FIELD_ID);
        sizeTextField.addActionListener(simplexWindowActionListener);
        
        JLabel largestFeatureLabel = new JLabel("Largest feature:", SwingConstants.CENTER);
        largestFeatureTextField = new JTextField(""+simplexHeightMapGenerator.getLargestFeature());
        largestFeatureTextField.setHorizontalAlignment(SwingConstants.CENTER);
        controlsPanel.add(largestFeatureLabel);
        controlsPanel.add(largestFeatureTextField);
        largestFeatureTextField.setName(LARGEST_FEATURE_TEXT_FIELD_ID);
        largestFeatureTextField.addActionListener(simplexWindowActionListener);
        
        JLabel persistanceFeatureLabel = new JLabel("Persistance:", SwingConstants.CENTER);
        persistanceTextField = new JTextField(""+simplexHeightMapGenerator.getPersistance());
        persistanceTextField.setHorizontalAlignment(SwingConstants.CENTER);
        controlsPanel.add(persistanceFeatureLabel);
        controlsPanel.add(persistanceTextField);
        persistanceTextField.setName(PERSISTANCE_TEXT_FIELD_ID);
        persistanceTextField.addActionListener(simplexWindowActionListener);
        
        JLabel perturbationFreqLabel = new JLabel("Perturbation frequency:", SwingConstants.CENTER);
        perturbationFreqTextField = new JTextField(""+simplexHeightMapGenerator.getPerturbFrequency());
        perturbationFreqTextField.setHorizontalAlignment(SwingConstants.CENTER);
        controlsPanel.add(perturbationFreqLabel);
        controlsPanel.add(perturbationFreqTextField);
        perturbationFreqTextField.setName(PERTURB_FREQ_TEXT_FIELD_ID);
        perturbationFreqTextField.addActionListener(simplexWindowActionListener);
        
        JLabel perturbationDistanceLabel = new JLabel("Perturbation max distance:", SwingConstants.CENTER);
        perturbationDistanceTextField = new JTextField(""+simplexHeightMapGenerator.getPerturbDistance());
        perturbationDistanceTextField.setHorizontalAlignment(SwingConstants.CENTER);
        controlsPanel.add(perturbationDistanceLabel);
        controlsPanel.add(perturbationDistanceTextField);
        perturbationDistanceTextField.setName(PERTURB_DIST_TEXT_FIELD_ID);
        perturbationDistanceTextField.addActionListener(simplexWindowActionListener);
        
        JLabel erodeIterationLabel = new JLabel("Erode iteration:", SwingConstants.CENTER);
        erodeIterationTextField = new JTextField(""+simplexHeightMapGenerator.getErodeIterations());
        erodeIterationTextField.setHorizontalAlignment(SwingConstants.CENTER);
        controlsPanel.add(erodeIterationLabel);
        controlsPanel.add(erodeIterationTextField);
        erodeIterationTextField.setName(ERODE_ITER_TEXT_FIELD_ID);
        erodeIterationTextField.addActionListener(simplexWindowActionListener);
        
        JLabel erodeSmoothnessLabel = new JLabel("Erode smoothness:", SwingConstants.CENTER);
        erodeSmoothnessTextField = new JTextField(""+simplexHeightMapGenerator.getErodeSmoothness());
        erodeSmoothnessTextField.setHorizontalAlignment(SwingConstants.CENTER);
        controlsPanel.add(erodeSmoothnessLabel);
        controlsPanel.add(erodeSmoothnessTextField);
        erodeSmoothnessTextField.setName(ERODE_SMOOTH_TEXT_FIELD_ID);
        erodeSmoothnessTextField.addActionListener(simplexWindowActionListener);
        
        JCheckBox useGrayScaleCheckBox = new JCheckBox("Use gray scale");
        useGrayScaleCheckBox.setSelected(true);
        useGrayScaleCheckBox.setHorizontalAlignment(SwingConstants.CENTER);
        controlsPanel.add(useGrayScaleCheckBox);
        useGrayScaleCheckBox.setName(USE_GRAY_SCALE_CB_ID);
        useGrayScaleCheckBox.addActionListener(simplexWindowActionListener);
        
        JCheckBox useHSBScaleCheckBox = new JCheckBox("Use HSB color scale");
        useHSBScaleCheckBox.setSelected(false);
        useHSBScaleCheckBox.setHorizontalAlignment(SwingConstants.CENTER);
        controlsPanel.add(useHSBScaleCheckBox);
        useHSBScaleCheckBox.setName(USE_HSB_COLOR_SCALE);
        useHSBScaleCheckBox.addActionListener(simplexWindowActionListener);
        
        JButton randomGenerationButton = new JButton("Random Generation");
        controlsPanel.add(randomGenerationButton);
        randomGenerationButton.setName(RANDOM_GENERATION_BUTTON_ID);
        randomGenerationButton.addActionListener(simplexWindowActionListener);
        
        minMaxValueLabel = new JLabel("Info:  " +simplexHeightMapGenerator.getMapInfo());
        controlsPanel.add(minMaxValueLabel);
        
        containerPanel.add(controlsPanel, BorderLayout.PAGE_END);
        
        return containerPanel;
	}

	@Override
	public BufferedImage getBufferedImage() {
		return simplexHeightMapGenerator.getCachedHeightMapImage();
	}

	@Override
	public AbstractHeightMapGenerator getHeightMapGenerator() {
		return this.simplexHeightMapGenerator;
	}

	@Override
	public void update() {
		imageContainer.removeAll();
		
		ImageIcon imageIcon = new ImageIcon(simplexHeightMapGenerator.getCachedHeightMapImage());
        JLabel imageLabel = new JLabel();
        imageLabel.setIcon(imageIcon);
        imageContainer.add(imageLabel, BorderLayout.CENTER);
        
        largestFeatureTextField.setText(simplexHeightMapGenerator.getLargestFeature()+"");
        persistanceTextField.setText(simplexHeightMapGenerator.getPersistance()+"");
        seedTextField.setText(simplexHeightMapGenerator.getSeed()+"");
    	sizeTextField.setText(simplexHeightMapGenerator.getMapSize()+"");
    	perturbationFreqTextField.setText(simplexHeightMapGenerator.getPerturbFrequency()+"");
    	perturbationDistanceTextField.setText(simplexHeightMapGenerator.getPerturbDistance()+"");
    	erodeIterationTextField.setText(simplexHeightMapGenerator.getErodeIterations()+"");
    	erodeSmoothnessTextField.setText(simplexHeightMapGenerator.getErodeSmoothness()+"");
    	minMaxValueLabel.setText("Info:  " +simplexHeightMapGenerator.getMapInfo());
        
        guiManager.updateFromChild();
	}
	
	@Override
	public String toString() {
		return "Simplex Noise";
	}
}
