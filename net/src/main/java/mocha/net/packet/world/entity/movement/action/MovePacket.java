package mocha.net.packet.world.entity.movement.action;

import mocha.game.world.Direction;
import mocha.game.world.entity.movement.command.EntityMoveCommand;
import mocha.net.packet.AbstractPacket;
import mocha.net.packet.PacketType;

public class MovePacket extends AbstractPacket {

  private String[] data = new String[5];

  public MovePacket() {
  }

  public MovePacket(EntityMoveCommand entityMove) {
    this.data[0] = getType().name();
    this.data[1] = "" + entityMove.getEntityId();
    this.data[2] = "" + entityMove.getDirection();
    this.data[3] = "" + entityMove.getXOffset();
    this.data[4] = "" + entityMove.getYOffset();
  }

  @Override
  public void build(String[] data) {
    this.data[0] = getType().name();
    this.data[1] = data[1];
    this.data[2] = data[2];
    this.data[3] = data[3];
    this.data[4] = data[4];
  }

  @Override
  public PacketType getType() {
    return PacketType.MOVE_REQUEST;
  }

  @Override
  public String[] getData() {
    return data;
  }

  private int getId() {
    return getDataAsInt(1);
  }

  private int getXOffset() {
    return getDataAsInt(3);
  }

  private int getYOffset() {
    return getDataAsInt(4);
  }

  private Direction getDirection() {
    return Direction.valueOf(data[2]);
  }

  public EntityMoveCommand getMoveCommand() {
    return new EntityMoveCommand(getId(), getDirection(), getXOffset(), getYOffset());
  }
}
