import heightMapGenerator.HeightMapGenerator;

import GUI.GUIManager;

/**
* 
* @author Pierfrancesco Soffritti
*
*/

public class Main {
	
	private static final int mapSize = 512;
	private static int seed = 1;

	public static void main(String[] args) {
		
		HeightMapGenerator heightMapGenerator = new HeightMapGenerator(mapSize, seed, 6.0f, 32.0f,
				32.0f, 10, 16.0f);
		heightMapGenerator.enablePrintInfo(true);
				
		final GUIManager gui = new GUIManager(heightMapGenerator);
		gui.showMainWindow();
	}
}
