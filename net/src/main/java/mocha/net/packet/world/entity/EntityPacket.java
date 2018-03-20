package mocha.net.packet.world.entity;

import mocha.game.world.Location;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.movement.SimpleMovement;
import mocha.game.world.entity.movement.collision.SimpleCollision;
import mocha.net.packet.AbstractPacket;
import mocha.net.packet.PacketType;

public class EntityPacket extends AbstractPacket {
  private String[] data = new String[4];

  public EntityPacket() {
  }

  public EntityPacket(Entity entity) {
    this.data[0] = getType().name();
    Location location = entity.getLocation();
    this.data[1] = "" + entity.getId();
    this.data[2] = "" + location.getX();
    this.data[3] = "" + location.getY();
  }

  @Override
  public void build(String[] data) {
    this.data[0] = getType().name();
    this.data[1] = data[1];
    this.data[2] = data[2];
    this.data[3] = data[3];
  }

  @Override
  public PacketType getType() {
    return PacketType.ENTITY;
  }

  @Override
  public String[] getData() {
    return data;
  }

  public Entity getEntity() {
    Entity entity = Entity.builder()
        .id(getId())
        .location(getLocation())
        .build();
    entity.setMovement(new SimpleMovement(new SimpleCollision()));
    return entity;
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

  private int getId() {
    return getDataAsInt(1);
  }
}
