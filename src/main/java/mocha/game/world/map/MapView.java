package mocha.game.world.map;

import org.springframework.stereotype.Component;

import java.util.function.IntConsumer;
import java.util.stream.IntStream;

import javax.inject.Inject;

import lombok.Setter;
import mocha.game.world.entity.EntityView;
import mocha.gfx.Drawable;
import mocha.gfx.MochaCanvas;

@Component
public class MapView implements Drawable {

  @Inject
  private TileSpriteSelector tileSpriteSelector;

  @Setter
  private Map map;

  @Override
  public void draw(MochaCanvas mochaCanvas, int x, int y) {
    drawTiles(mochaCanvas);
    drawEntities(mochaCanvas);
  }

  private void drawTiles(MochaCanvas mochaCanvas) {
    IntConsumer drawRow = (y) -> IntStream.range(0, map.getColumnCount()).forEach((x) -> drawTile(mochaCanvas, x, y));
    IntStream.range(0, map.getRowCount()).forEach(drawRow);
  }

  private void drawTile(MochaCanvas mochaCanvas, int x, int y) {
    int spriteId = tileSpriteSelector.selectSprite(map, x, y);
    double scale = 2.0;
    int spriteX = (int) (x * 16 * scale);
    int spriteY = (int) (y * 16 * scale);
    mochaCanvas.drawSprite(spriteId, spriteX, spriteY, scale);
  }

  private void drawEntities(MochaCanvas mochaCanvas) {
    map.getEntities().forEach((entity) -> new EntityView(entity).draw(mochaCanvas, 0, 0));
  }
}
