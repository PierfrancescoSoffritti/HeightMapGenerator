import java.awt.Color;
import java.net.UnknownHostException;
import java.util.ArrayList;

import webSocket.HeightMapWebSocketServer;
import heightMap.AbstractHeightMapGenerator;
import heightMap.PerlinHeightMapGenerator;
import heightMap.SimplexHeightMapGenerator;
import heightMap.render.GradientManager;
import heightMap.render.RenderException;
import GUI.GUIManager;

/**
* 
* @author Pierfrancesco Soffritti
*
*/

public class Main {
	
	private static final int mapSize = 512;
	private static int seed = 1;
	
	private static final int port = 8888;

	public static void main(String[] args) throws RenderException {
		
		GradientManager grayScaleGradientManager = new GradientManager();
		try {
			grayScaleGradientManager.addGradientPoint(-1, new Color(255,255,255));
		} catch (RenderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		GradientManager RGBGradientManager = new GradientManager();
		try {
			RGBGradientManager.addGradientPoint (-1.0000f, new Color (  0,   0, 128)); // deeps
			RGBGradientManager.addGradientPoint (-0.24706f, new Color (  0,   0, 255)); // shallow
			RGBGradientManager.addGradientPoint ( 0.00000f, new Color (  0, 128, 255)); // shore
			RGBGradientManager.addGradientPoint ( 0.07451f, new Color (240, 240,  64)); // sand
			RGBGradientManager.addGradientPoint ( 0.12941f, new Color ( 32, 160,   0)); // grass
			RGBGradientManager.addGradientPoint ( 0.38039f, new Color (196, 164,   10)); // dirt
			RGBGradientManager.addGradientPoint ( 0.65490f, new Color (128, 128, 128)); // rock
			RGBGradientManager.addGradientPoint ( 0.85882f, new Color (255, 255, 255)); // snow
		} catch (RenderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		HeightMapWebSocketServer ws = null;
		try {
			ws = new HeightMapWebSocketServer(port);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		ws.start();
		
		PerlinHeightMapGenerator perlinHeightMapGenerator = new PerlinHeightMapGenerator(mapSize, seed, 6.0f, 320.0f,
				32.0f, 20, 160.0f);
		perlinHeightMapGenerator.setDefaultGradientManager(grayScaleGradientManager);
		
		SimplexHeightMapGenerator simplexHeightMapGenerator = new SimplexHeightMapGenerator(mapSize, seed, 832910, 0.577922, 0f, 0f, 0, 0f);
		simplexHeightMapGenerator.setDefaultGradientManager(grayScaleGradientManager);
		
		ArrayList<AbstractHeightMapGenerator> heightMapGenerators = new ArrayList<AbstractHeightMapGenerator>();
		heightMapGenerators.add(perlinHeightMapGenerator);
		heightMapGenerators.add(simplexHeightMapGenerator);
				
		final GUIManager gui = new GUIManager(heightMapGenerators, ws);
		gui.showMainWindow();
	}
}
