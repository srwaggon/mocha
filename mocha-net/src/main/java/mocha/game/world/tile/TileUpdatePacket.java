package mocha.game.world.tile;

import mocha.game.world.chunk.Chunk;
import mocha.net.packet.AbstractPacket;
import mocha.net.packet.PacketType;

public class TileUpdatePacket extends AbstractPacket {

  public TileUpdatePacket() {
  }

  public TileUpdatePacket(Chunk chunk, int x, int y) {
    this.data = new String[5];
    this.data[0] = getType().name();
    this.data[1] = "" + chunk.getId();
    this.data[2] = "" + x;
    this.data[3] = "" + y;
    this.data[4] = "" + chunk.getTile(x, y).getSymbol();
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
