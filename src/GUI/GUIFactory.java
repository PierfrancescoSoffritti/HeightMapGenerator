package GUI;

import heightMap.AbstractHeightMapGenerator;
import heightMap.PerlinHeightMapGenerator;
import heightMap.SimplexHeightMapGenerator;

public class GUIFactory {

	public static GUI getGUI(GUIManager guiManager, AbstractHeightMapGenerator abstractHeightMapGenerator) {
		if(abstractHeightMapGenerator instanceof PerlinHeightMapGenerator)
			return new PerlinGUI(guiManager, abstractHeightMapGenerator);
		if(abstractHeightMapGenerator instanceof SimplexHeightMapGenerator)
			return new SimplexGUI(guiManager, abstractHeightMapGenerator);
		
		return null;
	}

}
