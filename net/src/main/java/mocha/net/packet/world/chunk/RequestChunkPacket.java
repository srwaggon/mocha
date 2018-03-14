package mocha.net.packet.world.chunk;

import mocha.game.world.Location;
import mocha.net.packet.AbstractPacket;
import mocha.net.packet.PacketType;

public class RequestChunkPacket extends AbstractPacket {
  private final String[] data = new String[3];

  public RequestChunkPacket() {
  }

  public RequestChunkPacket(Location location) {
    this.data[0] = getType().name();
    this.data[1] = "" + location.getX();
    this.data[2] = "" + location.getY();
  }

  @Override
  public void build(String[] data) {
    this.data[0] = getType().name();
    this.data[1] = data[1];
    this.data[2] = data[2];
  }

  @Override
  public PacketType getType() {
    return PacketType.REQUEST_CHUNK;
  }

  @Override
  public String[] getData() {
    return data;
  }

  public Location getLocation() {
    int x = Integer.parseInt(data[1]);
    int y = Integer.parseInt(data[2]);
    return new Location(x, y);
  }
}
