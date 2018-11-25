package mocha.client.gfx;

import org.springframework.stereotype.Component;

import java.util.Optional;

import javax.inject.Inject;

import mocha.game.world.Location;
import mocha.game.world.chunk.ChunkService;
import mocha.game.world.tile.TileType;

@Component
public class TileSpriteSelector {

  @Inject
  private ChunkService chunkService;

  public int selectSprite(Location tileLocation) {
    Optional<TileType> tileAt = chunkService.getTileAt(tileLocation);
    if (!tileAt.isPresent()) {
      return 0;
    }

    TileType type = tileAt.get();

    return isConnectedTileSprite(type) ? getSpriteOffset(tileLocation, type) : 0;
  }

  private boolean isConnectedTileSprite(TileType type) {
    return type.equals(TileType.GRASS)
        || type.equals(TileType.WATER)
        || type.equals(TileType.STONE);
  }

  private int getSpriteOffset(Location location, TileType type) {
    return (isType(location.north(), type) ? 1 : 0) +
        (isType(location.east(), type) ? 2 : 0) +
        (isType(location.south(), type) ? 4 : 0) +
        (isType(location.west(), type) ? 8 : 0);
  }

  private boolean isType(Location location, TileType type) {
    Optional<TileType> tileAt = chunkService.getTileAt(location);
    return tileAt.isPresent() && tileAt.get().equals(type);
  }
}
