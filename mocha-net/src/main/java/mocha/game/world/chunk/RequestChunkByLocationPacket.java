package mocha.game.world.chunk;

import mocha.game.world.Location;
import mocha.net.packet.AbstractPacket;
import mocha.net.packet.PacketType;

public class RequestChunkByLocationPacket extends AbstractPacket {

  public RequestChunkByLocationPacket() {
  }

  public RequestChunkByLocationPacket(Location location) {
    data = new String[3];
    data[0] = getType().name();
    data[1] = "" + location.getX();
    data[2] = "" + location.getY();
  }

  @Override
  public PacketType getType() {
    return PacketType.REQUEST_CHUNK_BY_LOCATION;
  }

  public Location getLocation() {
    return new Location(getX(), getY());
  }

  private int getX() {
    return getDataAsInt(1);
  }

  private int getY() {
    return getDataAsInt(2);
  }
}
