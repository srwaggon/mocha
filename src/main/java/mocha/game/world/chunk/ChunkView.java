package mocha.game.world.chunk;

import org.springframework.stereotype.Component;

import java.util.function.IntConsumer;
import java.util.stream.IntStream;

import javax.inject.Inject;

import lombok.Setter;
import mocha.gfx.Drawable;
import mocha.gfx.MochaCanvas;

@Component
public class ChunkView implements Drawable {

  @Inject
  private TileSpriteSelector tileSpriteSelector;

  @Setter
  private Chunk chunk;

  @Override
  public void draw(MochaCanvas mochaCanvas, int x, int y) {
    drawTiles(mochaCanvas);
  }

  private void drawTiles(MochaCanvas mochaCanvas) {
    IntConsumer drawRow = (y) -> IntStream.range(0, Chunk.SIZE).forEach((x) -> drawTile(mochaCanvas, x, y));
    IntStream.range(0, Chunk.SIZE).forEach(drawRow);
  }

  private void drawTile(MochaCanvas mochaCanvas, int x, int y) {
    int spriteId = tileSpriteSelector.selectSprite(chunk, x, y);
    double scale = 2.0;
    int spriteX = (int) (x * 16 * scale);
    int spriteY = (int) (y * 16 * scale);
    mochaCanvas.drawSprite(spriteId, spriteX, spriteY, scale);
  }
}
