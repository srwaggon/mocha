package mocha.net.packet.world.entity.movement.action;

import mocha.game.world.Direction;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.brain.SimpleBrain;
import mocha.game.world.entity.movement.EntityMoveCommand;
import mocha.game.world.entity.movement.SimpleMovement;
import mocha.game.world.entity.movement.collision.SimpleCollision;
import mocha.net.packet.AbstractPacket;
import mocha.net.packet.PacketType;

public class MovePacket extends AbstractPacket {

  private String[] data = new String[3];

  public MovePacket() {
  }

  public MovePacket(EntityMoveCommand entityMove) {
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
    Entity entity = Entity.builder().id(getId()).build();
    entity.setMovement(new SimpleMovement(new SimpleCollision()));
    entity.setBrain(new SimpleBrain());
    return entity;
  }

  private Direction getDirection() {
    return Direction.valueOf(data[2]);
  }

  public EntityMoveCommand getMove() {
    return new EntityMoveCommand(getEntity(), getDirection());
  }
}
