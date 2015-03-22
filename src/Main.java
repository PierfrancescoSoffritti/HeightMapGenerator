import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import topography.GraysImageBorders;

public class Main {
	
	private static final int mapSize = 512;
	private static int seed = 1;

	public static void main(String[] args) {
		
		HeightMapGenerator heightMapGenerator = new HeightMapGenerator(mapSize, seed, 6.0f, 32.0f,
				32.0f, 10, 16.0f);
		heightMapGenerator.enablePrintInfo(true);
		BufferedImage heightMapImage = heightMapGenerator.generateHeightMap();
		
		initGUI(heightMapImage);
		
		File output = new File("C:\\Users\\Pierfrancesco\\Desktop\\prova\\img.png");
		
		try {
			ImageIO.write(heightMapImage, "png", output);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void initGUI(BufferedImage heightMapImage) {
		
		ImageIcon icon = new ImageIcon(heightMapImage);
        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        Container container = frame.getContentPane();        
        
        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new BorderLayout());
        JLabel label = new JLabel();
        label.setIcon(icon);
        imagePanel.add(label, BorderLayout.CENTER);
        container.add(imagePanel, BorderLayout.CENTER);
        
        JPanel controlsPanel = new JPanel();
        controlsPanel.setLayout(new GridLayout(0,2));
        JLabel seedLabel = new JLabel("seed:", SwingConstants.CENTER);
        JTextField textField = new JTextField(""+seed);
        textField.setHorizontalAlignment(SwingConstants.CENTER);
        JButton showTopogButton = new JButton("Show topographic map");
        showTopogButton.addActionListener(new MyActionListener(heightMapImage));
        JButton saveHeightMapButton = new JButton("Save heightmap");
        controlsPanel.add(seedLabel);
        controlsPanel.add(textField);
        controlsPanel.add(showTopogButton);
        controlsPanel.add(saveHeightMapButton);
        container.add(controlsPanel, BorderLayout.PAGE_END);
        
        frame.pack();
        //frame.setSize(mapSize, mapSize);
        //frame.setSize(mapSize, frame.getHeight());
        frame.setResizable(true);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}

	private static int getColor(float f) {
		
		int value = (int) ((f+0.5) * 255);
		
		int r = value;// red component 0...255
		int g = value;// green component 0...255
		int b = value;// blue component 0...255
		int color = (r << 16) | (g << 8) | b;
		
		//System.out.println(value);
		
		return color;
	}

}

class MyActionListener implements ActionListener {
	
	private BufferedImage heightMapImage;

	public MyActionListener(BufferedImage heightMapImage) {
		this.heightMapImage = heightMapImage;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		ImageIcon icon = new ImageIcon(GraysImageBorders.getBordersImage(heightMapImage, 1));
        JFrame frame = new JFrame();
        JLabel label = new JLabel();
        label.setIcon(icon);
        frame.add(label);
        
        frame.pack();
        frame.setResizable(true);
        frame.setVisible(true);		
	}
	
}
