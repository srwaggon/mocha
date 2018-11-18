package mocha.game;

import mocha.game.world.entity.Entity;

public class LocalPlayer implements Player {

  private Entity entity;

  public LocalPlayer(Entity entity) {
    this.entity = entity;
  }

  @Override
  public Entity getEntity() {
    return entity;
  }

  @Override
  public void remove() {

  }

  @Override
  public int getId() {
    return 0;
  }
}
