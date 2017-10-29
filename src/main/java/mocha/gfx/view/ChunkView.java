package mocha.gfx.view;

import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import mocha.game.Game;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.tile.item.TileItem;
import mocha.gfx.TileSpriteSelector;
import mocha.gfx.sprite.SpriteSheet;

public class ChunkView extends Group {

  private final Chunk chunk;
  private final Game game;
  private final SpriteSheet spriteSheet;
  private final TileSpriteSelector tileSpriteSelector;
  private final Canvas tileCanvas;

  public ChunkView(Chunk chunk, Game game, SpriteSheet spriteSheet, TileSpriteSelector tileSpriteSelector) {
    this.chunk = chunk;
    this.game = game;
    this.spriteSheet = spriteSheet;
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
    int spriteId = tileSpriteSelector.selectSprite(chunk, xIndex, yIndex);
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
