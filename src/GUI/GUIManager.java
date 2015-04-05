package GUI;

import heightMap.AbstractHeightMapGenerator;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import webSocket.HeightMapWebSocketServer;
import GUI.listeners.MainWindowActionListener;

/**
* GUIManager.java
* @author Pierfrancesco Soffritti
*
*/

public class GUIManager {
	public final static String GUI_NAMES_LIST_BUTTON_ID = "GUI_NAMES_LIST_BUTTON_ID";
	public final static String SAVE_HEIGHTMAP_BUTTON_ID = "SAVE_HEIGHTMAP_BUTTON_ID";
	public final static String SHOW_TOPOGRAPHY_BUTTON_ID = "SHOW_TOPOGRAPHY_BUTTON_ID";
	public final static String SEND_HEIGHTMAP_BUTTON_ID = "SEND_HEIGHTMAP_BUTTON_ID";
	
	private ArrayList<GUI> GUIList;
	private int currentGUI;
	private JPanel currentGUIPanel;
	private JLabel socketStatusLabel;
	private JFrame frame;
	
	private MainWindowActionListener mainWindowActionListener;
	private HeightMapWebSocketServer ws;
	
	public GUIManager(ArrayList<AbstractHeightMapGenerator> heightMapGenerators, HeightMapWebSocketServer ws) {
		
		GUIList = new ArrayList<GUI>();
		
		this.ws = ws;
		
		for(int i=0; i<heightMapGenerators.size(); i++)
			GUIList.add(GUIFactory.getGUI(this, heightMapGenerators.get(i)));
		
		this.currentGUI = 1;
		
		mainWindowActionListener = new MainWindowActionListener(this);
		initMainWindow();
	}
	
	private void initMainWindow() {
		
        frame = new JFrame("Heightmap generator - Pierfrancesco Soffritti");
        frame.setLayout(new BorderLayout());
        Container container = frame.getContentPane();
        
        String[] guiNames = new String[GUIList.size()];
        for(int i=0; i<GUIList.size(); i++)
        	guiNames[i] = GUIList.get(i).toString();
        
        JComboBox<String> guiNamesList = new JComboBox<String>(guiNames);
        guiNamesList.setSelectedIndex(currentGUI);
        container.add(guiNamesList, BorderLayout.PAGE_START);
        guiNamesList.setName(GUI_NAMES_LIST_BUTTON_ID);
      	guiNamesList.addActionListener(mainWindowActionListener);  
        
        currentGUIPanel = new JPanel();
        currentGUIPanel.setLayout(new BorderLayout());
        currentGUIPanel.add(GUIList.get(currentGUI).getJPanel(), BorderLayout.CENTER);
        container.add(currentGUIPanel, BorderLayout.CENTER);
        
        JPanel controlsPanel = new JPanel();
        controlsPanel.setLayout(new GridLayout(0,2));
        
        JButton showTopographyButton = new JButton("Show topographic map");
        JButton saveHeightMapButton = new JButton("Save heightmap");
        controlsPanel.add(showTopographyButton);
        controlsPanel.add(saveHeightMapButton);
        showTopographyButton.setName(SHOW_TOPOGRAPHY_BUTTON_ID);
        showTopographyButton.addActionListener(mainWindowActionListener);        
        saveHeightMapButton.setName(SAVE_HEIGHTMAP_BUTTON_ID);
        saveHeightMapButton.addActionListener(mainWindowActionListener);
        
        JButton sendHeightMapToSocketButton = new JButton("Send heightmap to WebSocket");
        controlsPanel.add(sendHeightMapToSocketButton);
        sendHeightMapToSocketButton.setName(SEND_HEIGHTMAP_BUTTON_ID);
        sendHeightMapToSocketButton.addActionListener(mainWindowActionListener);        
        saveHeightMapButton.setName(SAVE_HEIGHTMAP_BUTTON_ID);
        
        socketStatusLabel = new JLabel("Socket status: " +ws.getStatus(), SwingConstants.CENTER);
        controlsPanel.add(socketStatusLabel);
        
        Thread checker = new Thread(){
        	@Override
        	public void run() {
        		while(true) {
        			socketStatusLabel.setText("Socket status: " +ws.getStatus());
        			try {
						sleep(1500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
        		}        		
        	}
        };        
        checker.start();
        
        container.add(controlsPanel, BorderLayout.PAGE_END);
	}
	
	public void showMainWindow() {
		frame.pack();
        frame.setResizable(true);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void updateAll() {
		GUIList.get(currentGUI).update();
		update();
	}
	
	protected void updateFromChild() {		
		update();
	}
	
	private void update() {
		frame.invalidate();
        frame.revalidate();
        frame.pack();
        frame.repaint();
	}

	public GUI getCurrentGUI() {
		return GUIList.get(currentGUI);
	}

	public void showTopography(int threshold) {
		new TopographyGUI(threshold, getCurrentGUI().getBufferedImage()).show();		
	}

	public void setCurrentGUI(int selected) {
		this.currentGUI = selected;
		
		currentGUIPanel.removeAll();
		currentGUIPanel.add(GUIList.get(currentGUI).getJPanel());
		
		update();
	}
	
	public int getCurrentGUIIndex() {
		return currentGUI;
	}

	public void sendHeightMap() {
		ws.sendHeightMap(getCurrentGUI().getHeightMapGenerator().getCachedHeightMap().getHeights());
	}
}
