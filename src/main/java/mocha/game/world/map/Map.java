package mocha.game.world.map;

import com.google.common.base.Preconditions;

import java.util.HashMap;

import lombok.Data;
import mocha.game.world.entity.Entity;
import mocha.game.world.tile.Tile;
import mocha.game.world.tile.TileType;

@Data
public class Map {

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
        if (x + y % (id + 1) == 0) {
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

  public void tick() {
    getEntities().values().forEach(Entity::tick);
  }
}
