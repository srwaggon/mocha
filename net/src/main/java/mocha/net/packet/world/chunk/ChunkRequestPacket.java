package mocha.net.packet.world.chunk;

import mocha.net.packet.AbstractPacket;
import mocha.net.packet.PacketType;

public class ChunkRequestPacket extends AbstractPacket {
  private final String[] data = new String[3];

  @Override
  public void build(String[] data) {
    this.data[0] = getType().name();
    this.data[1] = data[1];
    this.data[2] = data[2];
  }

  @Override
  public PacketType getType() {
    return PacketType.CHUNK_REQUEST;
  }

  @Override
  public String[] getData() {
    return data;
  }
}
