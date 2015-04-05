package heightMap.render;

import java.awt.Color;

public final class TerrainGradientManager extends GradientManager {
	public TerrainGradientManager() {
		super();
		
		try {
			this.addGradientPoint (-1.0000f, new Color (  0,   0, 128)); // deeps
			this.addGradientPoint (-0.24706f, new Color (  0,   0, 255)); // shallow
			this.addGradientPoint ( 0.00000f, new Color (  0, 128, 255)); // shore
			this.addGradientPoint ( 0.07451f, new Color (240, 240,  64)); // sand
			this.addGradientPoint ( 0.12941f, new Color ( 32, 160,   0)); // grass
			this.addGradientPoint ( 0.38039f, new Color (196, 164,   10)); // dirt
			this.addGradientPoint ( 0.65490f, new Color (128, 128, 128)); // rock
			this.addGradientPoint ( 0.85882f, new Color (255, 255, 255)); // snow
		} catch (RenderException e) {
			
		}
	}

}
