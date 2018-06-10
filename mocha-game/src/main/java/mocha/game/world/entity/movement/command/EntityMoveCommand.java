package mocha.game.world.entity.movement.command;

import mocha.game.command.Command;
import mocha.game.world.Direction;
import mocha.game.world.Location;

public class EntityMoveCommand implements Command {
  private int entityId;
  private Location location;
  private Direction direction;
  private int xOffset;
  private int yOffset;

  public EntityMoveCommand(int entityId, Location location, Direction direction, int xOffset, int yOffset) {
    this.entityId = entityId;
    this.location = location;
    this.direction = direction;
    this.xOffset = xOffset;
    this.yOffset = yOffset;
  }

  public void setEntityId(int entityId) {
    this.entityId = entityId;
  }

  public int getEntityId() {
    return entityId;
  }

  public Location getLocation() {
    return location;
  }

  public Direction getDirection() {
    return direction;
  }

  public int getXOffset() {
    return xOffset;
  }

  public int getYOffset() {
    return yOffset;
  }

  public static EntityMoveCommandBuilder builder() {
    return new EntityMoveCommandBuilder();
  }

  public static class EntityMoveCommandBuilder {
    private int entityId;
    private Location location;
    private Direction direction;
    private int xOffset;
    private int yOffset;

    private EntityMoveCommandBuilder() {
    }

    public EntityMoveCommandBuilder entityId(int entityId) {
      this.entityId = entityId;
      return this;
    }

    public EntityMoveCommandBuilder location(Location location) {
      this.location = location;
      return this;
    }

    public EntityMoveCommandBuilder direction(Direction direction) {
      this.direction = direction;
      return this;
    }

    public EntityMoveCommandBuilder xOffset(int xOffset) {
      this.xOffset = xOffset;
      return this;
    }

    public EntityMoveCommandBuilder yOffset(int yOffset) {
      this.yOffset = yOffset;
      return this;
    }

    public EntityMoveCommand build() {
      return new EntityMoveCommand(entityId, location, direction, xOffset, yOffset);
    }
  }
}
