package mocha.game.world.entity;

import mocha.game.world.Location;
import mocha.net.packet.AbstractPacket;
import mocha.net.packet.PacketType;

public class RequestEntitiesInChunkPacket extends AbstractPacket {

  public RequestEntitiesInChunkPacket() {
  }

  public RequestEntitiesInChunkPacket(Location location) {
    data = new String[3];
    data[0] = getType().name();
    data[1] = "" + location.getX();
    data[2] = "" + location.getY();
  }

  @Override
  public PacketType getType() {
    return PacketType.REQUEST_ENTITIES_IN_CHUNK;
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
