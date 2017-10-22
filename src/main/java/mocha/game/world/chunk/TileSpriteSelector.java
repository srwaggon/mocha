package mocha.game.world.chunk;

import org.springframework.stereotype.Component;

import mocha.game.world.tile.TileType;

@Component
public class TileSpriteSelector {
  public int selectSprite(Chunk chunk, int x, int y) {
    TileType type = chunk.getTile(x, y).getTileType();

    int offset = type.equals(TileType.GRASS) || type.equals(TileType.WATER) ? getSpriteOffset(chunk, x, y, type) : 0;
    return type.getSprite() + offset;
  }

  private int getSpriteOffset(Chunk chunk, int x, int y, TileType type) {
    int offset = 0;
    boolean north = y == 0 || chunk.getTile(x, y - 1).getTileType().equals(type);
    boolean east = x == chunk.getColumnCount() - 1 || chunk.getTile(x + 1, y).getTileType().equals(type);
    boolean south = y == chunk.getRowCount() - 1 || chunk.getTile(x, y + 1).getTileType().equals(type);
    boolean west = x == 0 || chunk.getTile(x - 1, y).getTileType().equals(type);
    offset += north ? 1 : 0;
    offset += east ? 2 : 0;
    offset += south ? 4 : 0;
    offset += west ? 8 : 0;
    return offset;
  }
}
