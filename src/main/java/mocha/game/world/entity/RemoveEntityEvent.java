package mocha.game.world.entity;

import lombok.Data;

@Data
public class RemoveEntityEvent {

  private final Entity entity;

  public RemoveEntityEvent(Entity entity) {
    this.entity = entity;
  }
}
