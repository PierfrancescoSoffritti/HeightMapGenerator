# HeightMapGenerator
A pretty extensible java height map generator based on Perlin Noise and Simplex Noise.
It can send data through web socket to [a javascript application](https://github.com/PierfrancescoSoffritti/TerrainGenerator) in order to create a 3D model of the generated height map.

[Simplex Noise video](https://www.youtube.com/watch?v=CLzB6qL86qQ)<br/>
[Psyco Perlin Noise video :)](https://www.youtube.com/watch?v=IBLfilm35xU)

# WebSocket Usage
#### on Windows:
1. Download TerrainGenerator.zip
2. Unzip files
3. Double click on start.bat
4. Wait until `Socket status` is `Ready` and `Connection Status` is `READY SENT. WAITING DATA..` on the web page
5. Click `Send heightmap to WebSocket`
6. Wait a few seconds and the terrain should have been generated on the web page


-

![Alt text](/pcg.PNG?raw=true "Example")
