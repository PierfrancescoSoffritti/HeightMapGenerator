package heightMap.transformations;

import heightMap.AbstractHeightMapGenerator;

/**
* Manipulation.java
* @author Pierfrancesco Soffritti
*
*/

public class Transformation {
	
	protected AbstractHeightMapGenerator heightMapGenerator;
	
	public Transformation(AbstractHeightMapGenerator heightMapGenerator) {
		this.heightMapGenerator = heightMapGenerator;
	}
	
	public void setHeightMapGenerator(AbstractHeightMapGenerator heightMapGenerator) {
		this.heightMapGenerator = heightMapGenerator;
	}
	
	public AbstractHeightMapGenerator getHeightMapGenerator() {
		return this.heightMapGenerator;
	}
}
