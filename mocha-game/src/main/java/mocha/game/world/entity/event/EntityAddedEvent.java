package mocha.game.world.entity.event;

import mocha.game.event.MochaEvent;
import mocha.game.world.entity.Entity;

public class EntityAddedEvent implements MochaEvent {
  private final Entity entity;

  public EntityAddedEvent(Entity entity) {
    this.entity = entity;
  }

  public Entity getEntity() {
    return entity;
  }
}
