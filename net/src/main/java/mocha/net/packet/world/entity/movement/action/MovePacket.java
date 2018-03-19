package mocha.net.packet.world.entity.movement.action;

import mocha.game.world.Direction;
import mocha.game.world.Location;
import mocha.game.world.entity.movement.command.EntityMoveCommand;
import mocha.net.packet.AbstractPacket;
import mocha.net.packet.PacketType;

public class MovePacket extends AbstractPacket {

  private String[] data = new String[7];

  public MovePacket() {
  }

  public MovePacket(EntityMoveCommand entityMove) {
    this.data[0] = getType().name();
    this.data[1] = "" + entityMove.getEntityId();
    this.data[2] = "" + entityMove.getLocation().getX();
    this.data[3] = "" + entityMove.getLocation().getY();
    this.data[4] = "" + entityMove.getDirection();
    this.data[5] = "" + entityMove.getXOffset();
    this.data[6] = "" + entityMove.getYOffset();
  }

  @Override
  public void build(String[] data) {
    this.data[0] = getType().name();
    this.data[1] = data[1];
    this.data[2] = data[2];
    this.data[3] = data[3];
    this.data[4] = data[4];
    this.data[5] = data[5];
    this.data[6] = data[6];
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

  public Location getLocation() {
    return new Location(getX(), getY());
  }

  private int getX() {
    return getDataAsInt(2);
  }

  private int getY() {
    return getDataAsInt(3);
  }

  private Direction getDirection() {
    return Direction.valueOf(data[4]);
  }

  private int getXOffset() {
    return getDataAsInt(5);
  }

  private int getYOffset() {
    return getDataAsInt(6);
  }

  public EntityMoveCommand getMoveCommand() {
    return new EntityMoveCommand(getId(), getLocation(), getDirection(), getXOffset(), getYOffset());
  }
}
