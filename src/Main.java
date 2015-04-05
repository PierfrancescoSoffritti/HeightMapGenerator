import java.net.UnknownHostException;
import java.util.ArrayList;

import webSocket.HeightMapWebSocketServer;
import heightMap.AbstractHeightMapGenerator;
import heightMap.PerlinHeightMapGenerator;
import heightMap.SimplexHeightMapGenerator;
import heightMap.render.RenderException;
import GUI.GUIManager;

/**
* 
* @author Pierfrancesco Soffritti
*
*/

public class Main {
	
	private static final int mapSize = 512;
	private static int seed = 1000;
	
	private static final int port = 8888;

	public static void main(String[] args) throws RenderException {
		
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
		
		SimplexHeightMapGenerator simplexHeightMapGenerator = new SimplexHeightMapGenerator(mapSize, seed, 398588, 0.7381108399715283, 0f, 0f, 0, 0f);
		
		ArrayList<AbstractHeightMapGenerator> heightMapGenerators = new ArrayList<AbstractHeightMapGenerator>();
		heightMapGenerators.add(perlinHeightMapGenerator);
		heightMapGenerators.add(simplexHeightMapGenerator);
				
		final GUIManager gui = new GUIManager(heightMapGenerators, ws);
		gui.showMainWindow();
	}
}
