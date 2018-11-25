package mocha.game.world.entity;

import mocha.game.world.Location;
import mocha.net.packet.PacketType;

public class EntityUpdatePacket extends AbstractEntityPacket {

  public EntityUpdatePacket() {
  }

  public EntityUpdatePacket(Entity entity) {
    addToData(entity.getId());
    addToData(entity.getLocation().getX());
    addToData(entity.getLocation().getY());
  }

  @Override
  public PacketType getType() {
    return PacketType.ENTITY_UPDATE;
  }

  public Entity getEntity() {
    return new Entity(getId(), getLocation());
  }

  public Location getLocation() {
    return new Location(getX(), getY());
  }

  private int getX() {
    return getDataAsInt(2);
  }

  private int getY() {
    return getDataAsInt(3);
  }
}
