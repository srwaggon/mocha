package mocha.net.packet.world.entity;

import mocha.game.world.Location;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.brain.SimpleBrain;
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
    Location location = entity.getMovement().getLocation();
    this.data[1] = "" + entity.getId();
    this.data[2] = "" + location.getXAsInt();
    this.data[3] = "" + location.getYAsInt();
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
    int id = Integer.parseInt(data[1]);
    int x = Integer.parseInt(data[2]);
    int y = Integer.parseInt(data[3]);
    Location location = new Location(x, y);
    SimpleCollision collision = new SimpleCollision();
    SimpleMovement movement = new SimpleMovement(location, collision);
    SimpleBrain brain = new SimpleBrain();
    return Entity.builder()
        .id(id)
        .movement(movement)
        .brain(brain)
        .build();
  }
}
