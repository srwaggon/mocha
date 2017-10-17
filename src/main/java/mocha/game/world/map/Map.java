package mocha.game.world.map;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mocha.game.Tickable;
import mocha.game.world.entity.Entity;
import mocha.game.world.tile.Tile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Map implements Tickable {

  private int id;
  private Tile[][] tiles;
  private Set<Entity> entities;

  public Tile getTile(int x, int y) {
    return tiles[y][x];
  }

  public void add(Entity entity) {
    entities.add(entity);
    entity.setMapId(this.id);
  }

  public void remove(Entity entity) {
    entities.remove(entity);
    entity.setMapId(-1);
  }

  public int getColumnCount() {
    return tiles[0].length;
  }

  public int getRowCount() {
    return tiles.length;
  }

  public void tick(long now) {
    getEntities().forEach(entity -> entity.tick(now));
  }
}
