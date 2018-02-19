package mocha.client.gfx.view;

import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import mocha.client.gfx.sprite.SpriteSheet;
import mocha.game.Game;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.tile.TileType;
import mocha.client.gfx.TileSpriteSelector;

public class ChunkView extends Group {

  private Chunk chunk;
  private Game game;
  private SpriteSheet spriteSheet;
  private SpriteSheet dirtTiles;
  private SpriteSheet grassTiles;
  private SpriteSheet waterTiles;
  private SpriteSheet stoneTiles;
  private TileSpriteSelector tileSpriteSelector;
  private Canvas tileCanvas;

  public ChunkView(Chunk chunk, Game game, SpriteSheet spriteSheet, SpriteSheet dirtTiles, SpriteSheet grassTiles, SpriteSheet waterTiles, SpriteSheet stoneTiles, TileSpriteSelector tileSpriteSelector) {
    this.chunk = chunk;
    this.game = game;
    this.dirtTiles = dirtTiles;
    this.spriteSheet = spriteSheet;
    this.grassTiles = grassTiles;
    this.waterTiles = waterTiles;
    this.stoneTiles = stoneTiles;
    this.tileSpriteSelector = tileSpriteSelector;

    tileCanvas = new Canvas();
    tileCanvas.setHeight(512);
    tileCanvas.setWidth(512);
    getChildren().add(tileCanvas);
  }

  public void render(int x, int y) {
    this.setLayoutX(getChunkXOffset(x));
    this.setLayoutY(getChunkYOffset(y));
    drawTiles();
  }

  private int getChunkXOffset(int x) {
    int playerX = game.getPlayer().getMovement().getLocation().getXAsInt() % Chunk.getWidth();
    int playerOffsetX = -1 * ((Chunk.getWidth() + playerX) % Chunk.getWidth());
    int xOffset = x * Chunk.getWidth() + getCanvasXOffset();
    return playerOffsetX + xOffset;
  }

  private int getChunkYOffset(int y) {
    int playerY = game.getPlayer().getMovement().getLocation().getYAsInt() % Chunk.getHeight();
    int playerOffsetY = -1 * ((Chunk.getHeight() + playerY) % Chunk.getHeight());
    int yOffset = y * Chunk.getHeight() + getCanvasYOffset();
    return playerOffsetY + yOffset;
  }

  private int getCanvasXOffset() {
    return 512 / 2;
  }

  private int getCanvasYOffset() {
    return 512 / 2;
  }

  private void drawTiles() {
    for (int tileY = 0; tileY < Chunk.SIZE; tileY++) {
      for (int tileX = 0; tileX < Chunk.SIZE; tileX++) {
        drawTile(tileX, tileY);
      }
    }
  }

  private void drawTile(int xIndex, int yIndex) {
    tileCanvas.getGraphicsContext2D().drawImage(getSprite(xIndex, yIndex), getSpriteX(xIndex), getSpriteY(yIndex));
  }

  private Image getSprite(int xIndex, int yIndex) {
    TileType tile = chunk.getTile(xIndex, yIndex);
    int spriteId = tileSpriteSelector.selectSprite(chunk, xIndex, yIndex);
    if (tile == TileType.DIRT) {
      return dirtTiles.getSprite(spriteId, getScale());
    }
    if (tile == TileType.GRASS) {
      return grassTiles.getSprite(spriteId, getScale());
    }
    if (tile == TileType.WATER) {
      return waterTiles.getSprite(spriteId, getScale());
    }
    if (tile == TileType.STONE) {
      return stoneTiles.getSprite(spriteId, getScale());
    }
    return spriteSheet.getSprite(spriteId, getScale());
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
