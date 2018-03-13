package mocha.net.packet.world.entity.movement.action;

import mocha.game.world.Direction;
import mocha.game.world.Location;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.brain.SimpleBrain;
import mocha.game.world.entity.movement.EntityMove;
import mocha.game.world.entity.movement.SimpleMovement;
import mocha.game.world.entity.movement.collision.SimpleCollision;
import mocha.net.packet.AbstractPacket;
import mocha.net.packet.PacketType;

public class MovePacket extends AbstractPacket {

  private String[] data = new String[3];

  public MovePacket() {
  }

  public MovePacket(EntityMove entityMove) {
    this.data[0] = getType().name();
    this.data[1] = "" + entityMove.getEntity().getId();
    this.data[2] = entityMove.getDirection().toString();
  }

  @Override
  public void build(String[] data) {
    this.data[0] = getType().name();
    this.data[1] = data[1];
    this.data[2] = data[2];
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

  public Entity getEntity() {
    SimpleCollision collision = new SimpleCollision();
    SimpleMovement movement = new SimpleMovement(Location.at(0, 0), collision);
    SimpleBrain brain = new SimpleBrain();
    return Entity.builder()
        .id(getId())
        .movement(movement)
        .brain(brain)
        .build();
  }

  private Direction getDirection() {
    return Direction.valueOf(data[2]);
  }

  public EntityMove getMove() {
    return new EntityMove(getEntity(), getDirection());
  }
}
