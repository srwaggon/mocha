package mocha.game.world.entity.movement.collision;

import com.google.common.collect.Sets;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import mocha.game.world.Location;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.tile.TileType;
import mocha.shared.Repository;

public class TileCollision extends SimpleCollision {

  private Repository<Chunk, Integer> chunkRepository;

  TileCollision(Repository<Chunk, Integer> chunkRepository) {
    this.chunkRepository = chunkRepository;
  }

  @Override
  public Set<Collider> getColliders(Location location) {
    return chunkRepository.findById(Chunk.getIdForChunkAt(location))
        .map(chunk -> getCollisions(location, chunk))
        .orElse(Collections.emptySet());
  }

  private Set<Collider> getCollisions(Location location, Chunk chunk) {
    Optional<TileType> tileAt = chunk.getTileAt(location);
    return tileAt.isPresent()
        ? Sets.newHashSet(tileAt.get())
        : Collections.emptySet();
  }
}
