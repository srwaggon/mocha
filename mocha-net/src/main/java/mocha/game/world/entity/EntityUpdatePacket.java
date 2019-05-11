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
    addToData(entity.getEntityType().name());
    addToData(entity.getTypeId());
  }

  @Override
  public PacketType getType() {
    return PacketType.ENTITY_UPDATE;
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

  public EntityType getEntityType() {
    return EntityType.valueOf(getData(4));
  }

  public int getTypeId() {
    return getDataAsInt(5);
  }
}
