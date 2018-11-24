package mocha.game.world.chunk;

import mocha.net.packet.AbstractPacket;
import mocha.net.packet.PacketType;

public class RequestChunkByIdPacket extends AbstractPacket {

  public RequestChunkByIdPacket() {
  }

  public RequestChunkByIdPacket(Integer chunkId) {
    data = new String[3];
    data[0] = getType().name();
    data[1] = "" + chunkId;
  }

  @Override
  public PacketType getType() {
    return PacketType.REQUEST_CHUNK_BY_ID;
  }

  private int getId() {
    return getDataAsInt(1);
  }
}
