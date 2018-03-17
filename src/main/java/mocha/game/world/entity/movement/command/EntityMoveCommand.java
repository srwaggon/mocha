package mocha.game.world.entity.movement.command;

import mocha.game.Game;
import mocha.game.world.Direction;
import mocha.game.world.entity.Entity;

public class EntityMoveCommand extends MoveCommand {
  private final Entity entity;

  public EntityMoveCommand(Entity entity, Direction direction) {
    super(direction);
    this.entity = entity;
  }

  public Entity getEntity() {
    return entity;
  }

  @Override
  public void apply(Game game) {
    game.getEntityRegistry().get(entity.getId())
        .ifPresent(entity -> entity.getMovement().handle(this));
  }
}
