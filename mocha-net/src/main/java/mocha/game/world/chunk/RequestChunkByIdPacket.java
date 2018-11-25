package mocha.game.world.chunk;

import mocha.net.packet.AbstractPacket;
import mocha.net.packet.PacketType;

public class RequestChunkByIdPacket extends AbstractPacket {

  public RequestChunkByIdPacket() {
  }

  public RequestChunkByIdPacket(Integer chunkId) {
    addToData(chunkId);
  }

  @Override
  public PacketType getType() {
    return PacketType.REQUEST_CHUNK_BY_ID;
  }

  public int getChunkId() {
    return getDataAsInt(1);
  }
}
