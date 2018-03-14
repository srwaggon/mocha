package mocha.game.world.entity.brain;

import mocha.game.world.entity.Entity;

public class BaseBrain extends SimpleBrain {

  protected Entity entity;

  @Override
  public void setEntity(Entity entity) {
    this.entity = entity;
  }
}
