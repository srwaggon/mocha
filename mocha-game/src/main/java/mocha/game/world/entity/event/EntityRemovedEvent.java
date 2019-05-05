package mocha.game.world.entity.event;

import mocha.game.event.MochaEvent;
import mocha.game.world.entity.Entity;

public class EntityRemovedEvent implements MochaEvent {
  private final Entity entity;

  public EntityRemovedEvent(Entity entity) {
    this.entity = entity;
  }

  public Entity getEntity() {
    return entity;
  }
}
