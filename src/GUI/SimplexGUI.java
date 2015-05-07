package GUI;

import heightMap.SimplexHeightMapGenerator;
import heightMap.render.RenderException;
import heightMap.transformations.MysticalEffect;
import heightMap.transformations.Transformation;
import heightMap.transformations.Translation;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
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
	public final static String START_ANIMATION_BUTTON_ID = "START_ANIMATION_BUTTON_ID";
	public final static String STOP_ANIMATION_BUTTON_ID = "STOP_ANIMATION_BUTTON_ID";
	public static final String USE_HSB_COLOR_SCALE = "USE_HSB_COLOR_SCALE";
	public final static String TRANSFORMATIONS_NAMES_LIST_ID = "TRANSFORMATIONS_NAMES_LIST_ID";
	
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
	private ImageIcon imageIcon;
	private JComboBox<String> transformationList;
	private JCheckBox useHSBScaleCheckBox;
	
	private boolean isRunning;	
	
	private ArrayList<Transformation> transformations;
	private int currentTransformation;
	
	public SimplexGUI(GUIManager guiManager, SimplexHeightMapGenerator simplexHeightMapGenerator) {
		
		this.simplexHeightMapGenerator = simplexHeightMapGenerator;		
		this.simplexWindowActionListener = new SimplexWindowActionListener(this);
		this.guiManager = guiManager;
		
		this.transformations = new ArrayList<Transformation>();
		this.currentTransformation = 0;
	}

	@Override
	public JPanel getJPanel() throws RenderException {		
		
		containerPanel = new JPanel();
        containerPanel.setLayout(new BorderLayout());
        
        imageContainer = new JPanel();
        imageContainer.setLayout(new BorderLayout());
        imageIcon = new ImageIcon(simplexHeightMapGenerator.generateHeightMapAndBufferedImage());
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
        
        useHSBScaleCheckBox = new JCheckBox("Use HSB color scale");
        useHSBScaleCheckBox.setSelected(false);
        useHSBScaleCheckBox.setHorizontalAlignment(SwingConstants.CENTER);
        controlsPanel.add(useHSBScaleCheckBox);
        useHSBScaleCheckBox.setName(USE_HSB_COLOR_SCALE);
        useHSBScaleCheckBox.addActionListener(simplexWindowActionListener);
        useHSBScaleCheckBox.setEnabled(false);
        
        JLabel transformationLabel = new JLabel("Transformation:", SwingConstants.CENTER);
        controlsPanel.add(transformationLabel);
        String[] transformationNames = initTransformations();
        transformationList = new JComboBox<String>(transformationNames);
        transformationList.setSelectedIndex(currentTransformation);
        controlsPanel.add(transformationList);
        transformationList.setName(TRANSFORMATIONS_NAMES_LIST_ID);
        transformationList.addActionListener(simplexWindowActionListener);        
        
        JButton startAnimation = new JButton("Start real-time rendering");
        controlsPanel.add(startAnimation);
        startAnimation.setName(START_ANIMATION_BUTTON_ID);
        startAnimation.addActionListener(simplexWindowActionListener);
        
        JButton stopAnimation = new JButton("Stop real-time rendering");
        controlsPanel.add(stopAnimation);
        stopAnimation.setName(STOP_ANIMATION_BUTTON_ID);
        stopAnimation.addActionListener(simplexWindowActionListener);
        
        JButton randomGenerationButton = new JButton("Random Generation");
        controlsPanel.add(randomGenerationButton);
        randomGenerationButton.setName(RANDOM_GENERATION_BUTTON_ID);
        randomGenerationButton.addActionListener(simplexWindowActionListener);
        
        minMaxValueLabel = new JLabel("Info:  " +simplexHeightMapGenerator.getMapInfo());
        controlsPanel.add(minMaxValueLabel);
        
        containerPanel.add(controlsPanel, BorderLayout.PAGE_END);
        
        return containerPanel;
	}
	
	private String[] initTransformations() {
		transformations.add(new Translation(simplexHeightMapGenerator));
		transformations.add(new MysticalEffect(simplexHeightMapGenerator));
		
		String[] trasformationNames = new String[transformations.size()];
        for(int i=0; i<transformations.size(); i++)
        	trasformationNames[i] = transformations.get(i).toString();
        
        return trasformationNames;
	}

	@Override
	public BufferedImage getBufferedImage() throws RenderException {
		return simplexHeightMapGenerator.getCachedHeightMapImage();
	}

	@Override
	public SimplexHeightMapGenerator getHeightMapGenerator() {
		return this.simplexHeightMapGenerator;
	}

	@Override
	public void update()  {
		
		try {
			imageIcon.setImage(simplexHeightMapGenerator.getCachedHeightMapImage());
		} catch (RenderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        largestFeatureTextField.setText(simplexHeightMapGenerator.getLargestFeature()+"");
        persistanceTextField.setText(simplexHeightMapGenerator.getPersistance()+"");
        seedTextField.setText(simplexHeightMapGenerator.getSeed()+"");
    	sizeTextField.setText(simplexHeightMapGenerator.getMapSize()+"");
    	perturbationFreqTextField.setText(simplexHeightMapGenerator.getPerturbFrequency()+"");
    	perturbationDistanceTextField.setText(simplexHeightMapGenerator.getPerturbDistance()+"");
    	erodeIterationTextField.setText(simplexHeightMapGenerator.getErodeIterations()+"");
    	erodeSmoothnessTextField.setText(simplexHeightMapGenerator.getErodeSmoothness()+"");
    	minMaxValueLabel.setText("Info:  " +simplexHeightMapGenerator.getMapInfo());
    	
    	if(simplexHeightMapGenerator.getUseGrayScale()) {
    		useHSBScaleCheckBox.setEnabled(false);
    		useHSBScaleCheckBox.setSelected(false);
    		simplexHeightMapGenerator.setUseHSBScale(false);
    	}
    	else
    		useHSBScaleCheckBox.setEnabled(true);
        
        guiManager.updateFromChild();
	}
	
	@Override
	public String toString() {
		return "Simplex Noise";
	}
	
	public void startAnimation() {
		isRunning = true;

		Thread animationThread = new Thread() {
			@Override
			public void run() {
				
				Transformation transformation = transformations.get(currentTransformation);
				int x = 0;
				int translateX = 2;
				int translateY = 0;	
				//60 fps
				long fps=60;
				long timeToSleep=1000/fps; //millis
				long startTime=0;
				long endTime=0;
				long diffTime=0;
				while(isRunning) {
					
					//count the milliseconds of the operation
					startTime = System.currentTimeMillis();
					
					// TODO at the moment i'm to lazy to do it better
					if(transformation instanceof MysticalEffect) {
						((MysticalEffect)transformation).applyMysticalEffect();
					}
					if(transformation instanceof Translation && !(transformation instanceof MysticalEffect)) {
						x++;
						((Translation)transformation).translate(translateX, translateY, x*translateX, x*translateY);
					}
					
					update();
					
					//count the milliseconds of the operation
					endTime = System.currentTimeMillis();
					diffTime = (endTime-startTime);
					
					try {
						//sleeps the number of milliseconds that remain to have a smooth, locked 60 fps animation
						if(diffTime<timeToSleep)
							sleep(timeToSleep-diffTime);
						//if the time needed is more, then no sleep is slept
						
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		
		animationThread.start();
	}

	public void stopAnimation() {
		isRunning = false;
	}
	
	public boolean isRunning() {
		return isRunning;
	}
	
	public int getCurrentTransformation () {
		return currentTransformation;
	}

	public void setCurrentTransformation(int selected) {
		this.currentTransformation = selected;
	}
}
