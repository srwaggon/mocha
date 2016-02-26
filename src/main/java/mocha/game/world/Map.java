package mocha.game.world;

import mocha.game.world.entity.Entity;
import mocha.game.world.tile.Tile;

import java.util.HashMap;

public class Map {

  private Tile[][] tiles;
  private final int id;
  private HashMap<Integer, Entity> entities = new HashMap<>();

  public Map(int id, int columns, int rows) {
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

  public Tile getTile(int x, int y) {
    return tiles[y][x];
  }

  public int getId() {
    return id;
  }

  public void addEntity(Entity entity) {
    entities.put(entity.getId(), entity);
  }

  public HashMap<Integer, Entity> getEntities() {
    return entities;
  }

  public int getColumnCount() {
    return tiles[0].length;
  }

  public int getRowCount() {
    return tiles.length;
  }
}
