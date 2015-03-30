package GUI;

import heightMap.AbstractHeightMapGenerator;
import heightMap.PerlinHeightMapGenerator;
import heightMap.SimplexHeightMapGenerator;

public class GUIFactory {

	
	//The expicit downcast should be done here, where the type is checked the line immediately above
	public static GUI getGUI(GUIManager guiManager, AbstractHeightMapGenerator abstractHeightMapGenerator) {
		if(abstractHeightMapGenerator instanceof PerlinHeightMapGenerator)
		{
			return new PerlinGUI(guiManager, (PerlinHeightMapGenerator) abstractHeightMapGenerator);
		}
		if(abstractHeightMapGenerator instanceof SimplexHeightMapGenerator)
		{
			return new SimplexGUI(guiManager, (SimplexHeightMapGenerator) abstractHeightMapGenerator);
		}
		
		return null;
	}

}
