package mocha.game.world.entity.movement.collision;

import com.google.common.collect.Sets;

import java.util.Set;

import mocha.game.world.Location;
import mocha.game.world.chunk.ChunkService;

public class TileCollision extends SimpleCollision {

  private ChunkService chunkService;

  TileCollision(ChunkService chunkService) {
    this.chunkService = chunkService;
  }

  @Override
  public Set<Collider> getColliders(Location location) {
    return Sets.newHashSet(chunkService.getTileAt(location));
  }

}
