package mocha.game.world;

import mocha.game.world.entity.Entity;
import mocha.game.world.entity.Mob;

import java.util.HashMap;

public class Map {

  private Tile[][] tiles;
  private final int id;
  private HashMap<Integer, Entity> entities = new HashMap<Integer, Entity>();

  public Map(int id, int rows, int columns) {
    this.id = id;

    tiles = new Tile[rows][columns];
    for (int x = 0; x < rows; x++) {
      for (int y = 0; y < columns; y++) {
        tiles[x][y] = new Tile();
      }
    }
  }

  public Tile[][] getTiles() {
    return tiles;
  }

  public Tile getTile(int x, int y) {
    return tiles[x][y];
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
}
