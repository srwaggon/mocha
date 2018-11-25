package mocha.game.world.chunk;

import mocha.net.packet.AbstractPacket;
import mocha.net.packet.PacketType;

public class ChunkUpdatePacket extends AbstractPacket {

  private static final int ID_INDEX = 1;
  private static final int TILE_DATA_INDEX = 2;

  public ChunkUpdatePacket() {
  }

  public ChunkUpdatePacket(int chunkId, String tileData) {
    addToData(chunkId);
    addToData(tileData);
  }

  @Override
  public PacketType getType() {
    return PacketType.CHUNK_UPDATE;
  }

  public int getChunkId() {
    return getDataAsInt(ID_INDEX);
  }

  public String getTilesString() {
    return this.getSplitData()[TILE_DATA_INDEX];
  }

}
