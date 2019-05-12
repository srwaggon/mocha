package mocha.game.world.entity.event;

import mocha.game.event.MochaEvent;
import mocha.game.world.entity.Entity;

public class EntityUpdatedEvent implements MochaEvent {

  private Entity entity;

  public EntityUpdatedEvent(Entity entity) {
    this.entity = entity;
  }

  public Entity getEntity() {
    return entity;
  }
}
