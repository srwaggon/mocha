package mocha.game.world.entity.movement.command;

import mocha.game.world.Direction;
import mocha.game.world.entity.Entity;

public class EntityMoveCommandFactory {
  public static EntityMoveCommand buildEntityStopMoveCommand(Entity entity, Direction direction) {
    return buildEntityMoveCommand(entity, direction, false);
  }

  public static EntityMoveCommand buildEntityStartMoveCommand(Entity entity, Direction direction) {
    return buildEntityMoveCommand(entity, direction, true);
  }

  private static EntityMoveCommand buildEntityMoveCommand(Entity entity, Direction direction, boolean isStart) {
    return new EntityMoveCommand(
        entity.getId(),
        entity.getLocation(),
        direction,
        isStart ? direction.getXMultiplier() : 0,
        isStart ? direction.getYMultiplier() : 0
    );
  }
}
