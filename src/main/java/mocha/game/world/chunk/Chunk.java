package mocha.game.world.chunk;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mocha.game.world.entity.Entity;
import mocha.game.world.tile.Tile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Chunk {

  private int id;
  private Tile[][] tiles;
  private Set<Entity> entities;

  public Tile getTile(int x, int y) {
    return tiles[y][x];
  }

  public void add(Entity entity) {
    entities.add(entity);
    entity.setChunkId(this.id);
  }

  public void remove(Entity entity) {
    entities.remove(entity);
    entity.setChunkId(-1);
  }

  public int getColumnCount() {
    return tiles[0].length;
  }

  public int getRowCount() {
    return tiles.length;
  }

  public Tile getTileAt(int x, int y) {
    return getTile(x / Tile.SIZE, y / Tile.SIZE);
  }
}
