package mocha.game.world;

import com.google.common.base.Preconditions;

import java.util.HashMap;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

import mocha.gfx.Drawable;
import mocha.game.world.entity.Entity;
import mocha.game.world.tile.Tile;
import mocha.gfx.MochaCanvas;

public class Map implements Drawable {

  private Tile[][] tiles;
  private final int id;
  private HashMap<Integer, Entity> entities = new HashMap<>();

  public Map(int id, int columns, int rows) {
    Preconditions.checkArgument(columns > 0);
    Preconditions.checkArgument(rows > 0);

    this.id = id;

    tiles = new Tile[rows][columns];
    for (int y = 0; y < rows; y++) {
      for (int x = 0; x < columns; x++) {
        tiles[y][x] = new Tile();
      }
    }
  }

  public Tile[][] getTiles() {
    return tiles;
  }

  Tile getTile(int x, int y) {
    return tiles[y][x];
  }

  public int getId() {
    return id;
  }

  public void addEntity(Entity entity) {
    entities.put(entity.getId(), entity);
  }

  HashMap<Integer, Entity> getEntities() {
    return entities;
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

  void tick() {
    getEntities().values().forEach(Entity::tick);
  }
}
