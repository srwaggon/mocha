package mocha.game.world.entity.movement.collision;

import java.util.Optional;

import mocha.game.world.Location;
import mocha.game.world.World;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.tile.TileType;

public class TileCollision extends SimpleCollision {

  private World world;

  public TileCollision(World world) {
    this.world = world;
  }

  @Override
  public boolean collides(Location location) {
    Optional<Chunk> chunkMaybe = world.getChunkAt(location);
    if (!chunkMaybe.isPresent()) {
      return false;
    }
    return chunkMaybe.get().getTileAt(location).map(TileType::isBlocking).orElse(false);
  }
}
