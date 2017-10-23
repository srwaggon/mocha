package mocha.game.world.chunk;

import java.util.function.IntConsumer;
import java.util.stream.IntStream;

import mocha.gfx.Drawable;
import mocha.gfx.MochaCanvas;

public class ChunkView implements Drawable {

  private TileSpriteSelector tileSpriteSelector = new TileSpriteSelector();

  private Chunk chunk;

  public ChunkView(Chunk chunk) {
    this.chunk = chunk;
  }

  @Override
  public void draw(MochaCanvas mochaCanvas, int xOffset, int yOffset) {
    drawTiles(mochaCanvas, xOffset, yOffset);
  }

  private void drawTiles(MochaCanvas mochaCanvas, int xOffset, int yOffset) {
    IntConsumer drawRow = (yIndex) -> IntStream.range(0, Chunk.SIZE).forEach((xIndex) -> drawTile(mochaCanvas, xIndex, yIndex, xOffset, yOffset));
    IntStream.range(0, Chunk.SIZE).forEach(drawRow);
  }

  private void drawTile(MochaCanvas mochaCanvas, int xIndex, int yIndex, int xOffset, int yOffset) {
    int spriteId = tileSpriteSelector.selectSprite(chunk, xIndex, yIndex);
    double scale = 2.0;
    int spriteX = (int) (xIndex * 16 * scale) + xOffset;
    int spriteY = (int) (yIndex * 16 * scale) + yOffset;
    mochaCanvas.drawSprite(spriteId, spriteX, spriteY, scale);
  }
}
