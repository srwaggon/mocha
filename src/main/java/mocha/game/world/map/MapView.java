package mocha.game.world.map;

import java.util.function.IntConsumer;
import java.util.stream.IntStream;

import lombok.Setter;
import mocha.game.world.entity.EntityView;
import mocha.game.world.tile.Tile;
import mocha.gfx.Drawable;
import mocha.gfx.MochaCanvas;

public class MapView implements Drawable {

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
    int spriteId = getSpriteId(x, y);
    double scale = 2.0;
    int spriteX = (int) (x * 16 * scale);
    int spriteY = (int) (y * 16 * scale);
    mochaCanvas.drawSprite(spriteId, spriteX, spriteY, scale);
  }

  private int getSpriteId(int x, int y) {
    Tile tile = map.getTile(x, y);

//    Tile up = map.getTile(x, y - 1);
//    Tile down = map.getTile(x, y + 1);
//    Tile left = map.getTile(x - 1, y);
//    Tile right = map.getTile(x + 1, y);

    return tile.getTileType().getSprite();
  }

  private void drawEntities(MochaCanvas mochaCanvas) {
    map.getEntities().forEach((entity) -> new EntityView(entity).draw(mochaCanvas, 0, 0));
  }
}
