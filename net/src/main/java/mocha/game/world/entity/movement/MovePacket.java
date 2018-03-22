package mocha.game.world.entity.movement;

import mocha.game.world.Direction;
import mocha.game.world.Location;
import mocha.game.world.entity.AbstractEntityPacket;
import mocha.game.world.entity.movement.command.EntityMoveCommand;
import mocha.net.packet.PacketType;

public class MovePacket extends AbstractEntityPacket {

  public MovePacket() {
  }

  public MovePacket(EntityMoveCommand entityMove) {
    data = new String[7];
    data[0] = getType().name();
    data[1] = "" + entityMove.getEntityId();
    data[2] = "" + entityMove.getLocation().getX();
    data[3] = "" + entityMove.getLocation().getY();
    data[4] = "" + entityMove.getDirection();
    data[5] = "" + entityMove.getXOffset();
    data[6] = "" + entityMove.getYOffset();
  }

  @Override
  public PacketType getType() {
    return PacketType.MOVE;
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
