package mocha.client.gfx;

import org.springframework.stereotype.Component;

import mocha.game.world.chunk.Chunk;
import mocha.game.world.tile.TileType;

@Component
public class TileSpriteSelector {
  public int selectSprite(Chunk chunk, int x, int y) {
    TileType type = chunk.getTile(x, y);

    return isConnectedTileSprite(type)
        ? getSpriteOffset(chunk, x, y, type)
        : 0;
  }

  private boolean isConnectedTileSprite(TileType type) {
    return type.equals(TileType.GRASS)
        || type.equals(TileType.WATER)
        || type.equals(TileType.STONE);
  }

  private int getSpriteOffset(Chunk chunk, int x, int y, TileType type) {
    int offset = 0;
    boolean north = y != 0 && chunk.getTile(x, y - 1).equals(type);
    boolean east = x != Chunk.SIZE - 1 && chunk.getTile(x + 1, y).equals(type);
    boolean south = y != Chunk.SIZE - 1 && chunk.getTile(x, y + 1).equals(type);
    boolean west = x != 0 && chunk.getTile(x - 1, y).equals(type);
    offset += north ? 1 : 0;
    offset += east ? 2 : 0;
    offset += south ? 4 : 0;
    offset += west ? 8 : 0;
    return offset;
  }
}
