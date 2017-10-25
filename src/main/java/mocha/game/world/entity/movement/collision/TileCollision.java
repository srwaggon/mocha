package mocha.game.world.entity.movement.collision;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Builder;
import mocha.game.world.Location;
import mocha.game.world.World;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.tile.Tile;

@Builder
@AllArgsConstructor
public class TileCollision extends SimpleCollision {

  private World world;

  @Override
  public boolean collides(Location location) {
    Optional<Chunk> chunkMaybe = world.getChunkAt(location);
    if (!chunkMaybe.isPresent()) {
      return false;
    }
    int x = (Chunk.getWidth() + location.getXAsInt() % Chunk.getWidth()) % Chunk.getWidth();
    int y = (Chunk.getHeight() + location.getYAsInt() % Chunk.getHeight()) % Chunk.getHeight();
    Location locationInChunk = new Location(x, y);
    Optional<Tile> tileMaybe = chunkMaybe.get().getTileAt(locationInChunk);
    return tileMaybe.map(tile -> tile.getTileType().isBlocking()).orElse(false);
  }
}
