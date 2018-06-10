package mocha.game.world.entity.event;

import mocha.game.world.entity.Entity;

public class EntityUpdatedEvent {
  private final Entity entity;

  public EntityUpdatedEvent(Entity entity) {
    this.entity = entity;
  }

  public Entity getEntity() {
    return entity;
  }
}
