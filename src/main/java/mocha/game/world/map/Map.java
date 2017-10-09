package mocha.game.world.map;

import com.google.common.base.Preconditions;

import java.util.HashMap;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

import lombok.Data;
import mocha.game.world.tile.TileType;
import mocha.gfx.Drawable;
import mocha.game.world.entity.Entity;
import mocha.game.world.tile.Tile;
import mocha.gfx.MochaCanvas;

@Data
public class Map implements Drawable {

  private Tile[][] tiles;
  private int id;

  private HashMap<Integer, Entity> entities = new HashMap<>();

  public Map(int id, int columns, int rows) {
    Preconditions.checkArgument(columns > 0);
    Preconditions.checkArgument(rows > 0);

    this.id = id;

    tiles = new Tile[rows][columns];
    for (int y = 0; y < rows; y++) {
      for (int x = 0; x < columns; x++) {
        Tile tile = new Tile();
        if (x + y % 2 == 0) {
          tile.setTileType(TileType.WATER);
        }
        tiles[y][x] = tile;
      }
    }
  }

  public Tile getTile(int x, int y) {
    return tiles[y][x];
  }

  public void addEntity(Entity entity) {
    entities.put(entity.getId(), entity);
  }

  public int getColumnCount() {
    return tiles[0].length;
  }

  public int getRowCount() {
    return tiles.length;
  }

  @Override
  public void draw(MochaCanvas mochaCanvas, int x, int y) {
    drawTiles(mochaCanvas);
    drawEntities(mochaCanvas);
  }

  private void drawTiles(MochaCanvas mochaCanvas) {
    IntConsumer drawRow = (y) -> IntStream.range(0, tiles[0].length).forEach((x) -> drawTile(mochaCanvas, x, y));
    IntStream.range(0, tiles.length).forEach(drawRow);
  }

  private void drawTile(MochaCanvas mochaCanvas, int x, int y) {
    int spriteId = tiles[y][x].getTileType().getSprite();
    double scale = 2.0;
    int spriteX = (int) (x * 16 * scale);
    int spriteY = (int) (y * 16 * scale);
    mochaCanvas.drawSprite(spriteId, spriteX, spriteY, scale);
  }

  private void drawEntities(MochaCanvas mochaCanvas) {
    getEntities().values().forEach((entity) -> entity.draw(mochaCanvas, -1, -1));
  }

  public void tick() {
    getEntities().values().forEach(Entity::tick);
  }
}
