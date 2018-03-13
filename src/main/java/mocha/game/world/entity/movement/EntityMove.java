package mocha.game.world.entity.movement;

import mocha.game.world.Direction;
import mocha.game.world.entity.Entity;

public class EntityMove extends Move {
  private final Entity entity;

  public EntityMove(Entity entity, Direction direction) {
    super(direction);
    this.entity = entity;
  }

  public Entity getEntity() {
    return entity;
  }
}
