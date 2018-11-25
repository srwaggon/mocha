package mocha.client.gfx.view;

import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import mocha.client.gfx.TileSpriteSelector;
import mocha.client.gfx.sprite.SpriteSheet;
import mocha.game.world.Location;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.chunk.ChunkService;
import mocha.game.world.tile.TileType;

public class ChunkView extends Group {

  private Chunk chunk;
  private SpriteSheet spriteSheet;
  private SpriteSheet dirtTiles;
  private SpriteSheet grassTiles;
  private SpriteSheet waterTiles;
  private SpriteSheet stoneTiles;
  private TileSpriteSelector tileSpriteSelector;
  private Canvas tileCanvas;
  private ChunkService chunkService;

  public ChunkView(
      Chunk chunk,
      SpriteSheet spriteSheet,
      SpriteSheet dirtTiles,
      SpriteSheet grassTiles,
      SpriteSheet waterTiles,
      SpriteSheet stoneTiles,
      TileSpriteSelector tileSpriteSelector,
      ChunkService chunkService
  ) {
    this.chunk = chunk;
    this.dirtTiles = dirtTiles;
    this.spriteSheet = spriteSheet;
    this.grassTiles = grassTiles;
    this.waterTiles = waterTiles;
    this.stoneTiles = stoneTiles;
    this.tileSpriteSelector = tileSpriteSelector;
    this.chunkService = chunkService;

    tileCanvas = new Canvas();
    tileCanvas.setHeight(512);
    tileCanvas.setWidth(512);
    getChildren().add(tileCanvas);
  }

  public void render() {
    drawTiles();
  }

  public void setChunk(Chunk chunk) {
    this.chunk = chunk;
  }

  private void drawTiles() {
    for (int tileY = 0; tileY < Chunk.getHeight(); tileY += TileType.SIZE) {
      for (int tileX = 0; tileX < Chunk.getWidth(); tileX += TileType.SIZE) {
        drawTileAtLocation(tileX, tileY);
      }
    }
  }

  private void drawTileAtLocation(int tileX, int tileY) {
    int xIndex = tileX / TileType.SIZE;
    int yIndex = tileY / TileType.SIZE;
    Location tileLocation = chunkService.getLocationOfChunk(chunk).addNew(tileX, tileY);
    Image sprite = getSprite(tileLocation);
    tileCanvas.getGraphicsContext2D().drawImage(sprite, getSpriteX(xIndex), getSpriteY(yIndex));
  }

  private Image getSprite(Location tileLocation) {
    TileType tileType = chunkService.getTileAt(tileLocation).orElse(TileType.DIRT);
    SpriteSheet spriteSheet = selectSpriteSheet(tileType);
    return getSprite(spriteSheet, tileLocation);
  }

  private SpriteSheet selectSpriteSheet(TileType tileType) {
    switch (tileType) {
      case DIRT:
        return dirtTiles;
      case GRASS:
        return grassTiles;
      case WATER:
        return waterTiles;
      case STONE:
        return stoneTiles;
      default:
        return spriteSheet;
    }
  }

  private Image getSprite(SpriteSheet spriteSheet, Location tileLocation) {
    int spriteIndex = tileSpriteSelector.selectSprite(tileLocation);
    return spriteSheet.getSprite(spriteIndex, getScale());
  }

  private int getSpriteX(int xIndex) {
    return (int) (xIndex * 16 * getScale());
  }

  private int getSpriteY(int yIndex) {
    return (int) (yIndex * 16 * getScale());
  }

  private double getScale() {
    return 2.0;
  }
}
