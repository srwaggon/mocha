package mocha.client.gfx;

import org.springframework.stereotype.Component;

import java.util.Map;

import javax.inject.Inject;

import mocha.game.world.Direction;
import mocha.game.world.Location;
import mocha.game.world.chunk.ChunkService;
import mocha.game.world.tile.TileType;

import static mocha.game.world.Direction.EAST;
import static mocha.game.world.Direction.NORTH;
import static mocha.game.world.Direction.SOUTH;
import static mocha.game.world.Direction.WEST;
import static mocha.game.world.tile.TileType.GRASS;
import static mocha.game.world.tile.TileType.STONE;
import static mocha.game.world.tile.TileType.WATER;

@Component
public class TileSpriteSelector {

  @Inject
  private ChunkService chunkService;

  public int selectSprite(Location tileLocation) {
    TileType type = chunkService.getTileAt(tileLocation);
    return isConnectedTileSprite(type) ? getSpriteOffset(tileLocation, type) : 0;
  }

  private boolean isConnectedTileSprite(TileType type) {
    return type.equals(GRASS)
        || type.equals(WATER)
        || type.equals(STONE);
  }

  private int getSpriteOffset(Location tileLocation, TileType type) {
    Map<Direction, TileType> tileNeighbors = chunkService.getTileNeighbors(tileLocation);
    return (tileNeighbors.get(NORTH).equals(type) ? 1 : 0) +
        (tileNeighbors.get(EAST).equals(type) ? 2 : 0) +
        (tileNeighbors.get(SOUTH).equals(type) ? 4 : 0) +
        (tileNeighbors.get(WEST).equals(type) ? 8 : 0);
  }
}
