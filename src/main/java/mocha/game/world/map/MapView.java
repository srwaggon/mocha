package mocha.game.world.map;

import java.util.function.IntConsumer;
import java.util.stream.IntStream;

import mocha.game.world.entity.EntityView;
import mocha.gfx.Drawable;
import mocha.gfx.MochaCanvas;

public class MapView implements Drawable {

  private Map map;

  public void setMap(Map map) {
    this.map = map;
  }

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
    int spriteId = map.getTile(x, y).getTileType().getSprite();
    double scale = 2.0;
    int spriteX = (int) (x * 16 * scale);
    int spriteY = (int) (y * 16 * scale);
    mochaCanvas.drawSprite(spriteId, spriteX, spriteY, scale);
  }

  private void drawEntities(MochaCanvas mochaCanvas) {
    map.getEntities().values().forEach((entity) -> new EntityView(entity).draw(mochaCanvas, 0, 0));
  }
}
