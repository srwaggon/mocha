package mocha.net.packet.world.entity.movement.action;

import mocha.game.world.Location;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.brain.SimpleBrain;
import mocha.game.world.entity.movement.SimpleMovement;
import mocha.game.world.entity.movement.collision.SimpleCollision;
import mocha.net.packet.AbstractPacket;
import mocha.net.packet.PacketType;

public class MoveRequestPacket extends AbstractPacket {

  private String[] data = new String[4];

  public MoveRequestPacket() {

  }

  public MoveRequestPacket(Entity entity) {
    this.data[0] = getType().name();
    this.data[1] = "" + entity.getId();
    this.data[2] = "" + entity.getMovement().getLocation().getXAsInt();
    this.data[3] = "" + entity.getMovement().getLocation().getYAsInt();
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
    return PacketType.MOVE_REQUEST;
  }

  @Override
  public String[] getData() {
    return data;
  }

  public int getId() {
    return Integer.parseInt(data[1]);
  }

  public Location getLocation() {
    int x = Integer.parseInt(data[2]);
    int y = Integer.parseInt(data[3]);
    return new Location(x, y);
  }

  public Entity getEntity() {
    SimpleCollision collision = new SimpleCollision();
    SimpleMovement movement = new SimpleMovement(getLocation(), collision);
    SimpleBrain brain = new SimpleBrain();
    return Entity.builder()
        .id(getId())
        .movement(movement)
        .brain(brain)
        .build();
  }
}
