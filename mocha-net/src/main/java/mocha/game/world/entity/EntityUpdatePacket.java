package mocha.game.world.entity;

import mocha.game.world.Location;
import mocha.net.packet.PacketType;

public class EntityUpdatePacket extends AbstractEntityPacket {

  public EntityUpdatePacket() {
  }

  public EntityUpdatePacket(Entity entity) {
    data = new String[4];
    data[0] = getType().name();
    data[1] = "" + entity.getId();
    data[2] = "" + entity.getLocation().getX();
    data[3] = "" + entity.getLocation().getY();
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
