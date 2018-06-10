package mocha.game.world.entity.event;

import mocha.game.world.entity.Entity;

public class EntityRemovedEvent {
  private final Entity entity;

  public EntityRemovedEvent(Entity entity) {
    this.entity = entity;
  }

  public Entity getEntity() {
    return entity;
  }
}
