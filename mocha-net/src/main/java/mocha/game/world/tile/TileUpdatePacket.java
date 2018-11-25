package mocha.game.world.tile;

import mocha.game.world.chunk.Chunk;
import mocha.net.packet.AbstractPacket;
import mocha.net.packet.PacketType;

public class TileUpdatePacket extends AbstractPacket {

  public TileUpdatePacket() {
  }

  public TileUpdatePacket(Chunk chunk, int x, int y) {
    addToData(chunk.getId());
    addToData(x);
    addToData(y);
    addToData(chunk.getTile(x, y).getSymbol());
  }

  @Override
  public PacketType getType() {
    return PacketType.TILE_UPDATE;
  }

  public Integer getChunkId() {
    return getDataAsInt(1);
  }

  public int getTileX() {
    return getDataAsInt(2);
  }

  public int getTileY() {
    return getDataAsInt(3);
  }

  public TileType getTileType() {
    return TileType.valueOf(getDataAsChar(4));
  }
}
