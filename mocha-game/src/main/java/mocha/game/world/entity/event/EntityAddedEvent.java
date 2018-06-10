package mocha.game.world.entity.event;

import mocha.game.world.entity.Entity;

public class EntityAddedEvent {
  private final Entity entity;

  public EntityAddedEvent(Entity entity) {
    this.entity = entity;
  }

  public Entity getEntity() {
    return entity;
  }
}
