package mocha.game.world.chunk;

import mocha.net.packet.AbstractPacket;
import mocha.net.packet.PacketType;

public class ChunkUpdatePacket extends AbstractPacket {

  private static final int ID_INDEX = 1;
  private static final int TILE_DATA_INDEX = 2;

  public ChunkUpdatePacket() {
  }

  public ChunkUpdatePacket(int chunkId, String tileData) {
    data = new String[3];
    data[0] = getType().name();
    data[ID_INDEX] = "" + chunkId;
    data[TILE_DATA_INDEX] = tileData;
  }

  @Override
  public PacketType getType() {
    return PacketType.CHUNK_UPDATE;
  }

  public int getChunkId() {
    return getDataAsInt(ID_INDEX);
  }

  public String getTilesString() {
    return this.data[TILE_DATA_INDEX];
  }

}
