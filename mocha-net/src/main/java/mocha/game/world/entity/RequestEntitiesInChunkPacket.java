package mocha.game.world.entity;

import mocha.game.world.Location;
import mocha.net.packet.AbstractPacket;
import mocha.net.packet.PacketType;

public class RequestEntitiesInChunkPacket extends AbstractPacket {

  public RequestEntitiesInChunkPacket() {
  }

  public RequestEntitiesInChunkPacket(Location location) {
    addToData(location.getX());
    addToData(location.getY());
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
