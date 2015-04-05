package heightMap.render;

import java.awt.Color;

public final class GrayScaleGradientManager extends GradientManager {
	public GrayScaleGradientManager() {
		super();
		
		try {
			this.addGradientPoint(-1, new Color(255,255,255));
		} catch (RenderException exc) {
			// TODO Auto-generated catch block
			exc.printStackTrace();
		}
	}

}