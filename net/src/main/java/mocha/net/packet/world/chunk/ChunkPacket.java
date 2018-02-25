package mocha.net.packet.world.chunk;

import mocha.net.packet.AbstractPacket;
import mocha.net.packet.Packet;
import mocha.net.packet.PacketType;

public class ChunkPacket extends AbstractPacket implements Packet {

  private String[] data = new String[2];

  @Override
  public void build(String[] data) {
    this.data[0] = getType().name();
    this.data[1] = data[1];
  }

  @Override
  public PacketType getType() {
    return PacketType.CHUNK;
  }

  @Override
  public String[] getData() {
    return data;
  }

}
