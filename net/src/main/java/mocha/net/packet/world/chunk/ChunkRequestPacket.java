package mocha.net.packet.world.chunk;

import mocha.game.world.Location;
import mocha.net.packet.AbstractPacket;
import mocha.net.packet.PacketType;

public class ChunkRequestPacket extends AbstractPacket {
  private final String[] data;

  public ChunkRequestPacket(Location location) {
    data = new String[3];
    data[0] = getType().name();
    data[1] = "" + location.getXAsInt();
    data[2] = "" + location.getYAsInt();
  }

  @Override
  public void build(String[] data) {
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
