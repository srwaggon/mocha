package mocha.game.world;

import java.util.Arrays;
import java.util.HashMap;

import javafx.scene.canvas.GraphicsContext;
import mocha.game.gfx.Renderable;
import mocha.game.world.entity.Entity;
import mocha.game.world.tile.Tile;

public class Map implements Renderable {

  private Tile[][] tiles;
  private final int id;
  private HashMap<Integer, Entity> entities = new HashMap<>();

  public Map(int id, int columns, int rows) {
    this.id = id;

    tiles = new Tile[rows][columns];
    for (int y = 0; y < rows; y++) {
      for (int x = 0; x < columns; x++) {
        tiles[y][x] = new Tile(x, y);
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
  public void render(GraphicsContext graphics) {
    Arrays.stream(tiles).forEach((row) -> Arrays.stream(row).forEach((tile) -> tile.render(graphics)));
    getEntities().values().stream().forEach((entity) -> entity.render(graphics));
  }

  void tick() {
    getEntities().values().stream().forEach(Entity::tick);
  }
}
