package mocha.game.world.chunk;

import mocha.net.packet.AbstractPacket;
import mocha.net.packet.PacketType;

public class ChunkUpdatePacket extends AbstractPacket {

  public ChunkUpdatePacket() {
  }

  public ChunkUpdatePacket(int chunkId, int x, int y, String tileData) {
    addToData(chunkId);
    addToData(x);
    addToData(y);
    addToData(tileData);
  }

  @Override
  public PacketType getType() {
    return PacketType.CHUNK_UPDATE;
  }

  public int getChunkId() {
    return getDataAsInt(1);
  }

  public int getX() {
    return getDataAsInt(2);
  }

  public int getY() {
    return getDataAsInt(3);
  }

  public String getTilesString() {
    return this.getSplitData()[4];
  }

}
