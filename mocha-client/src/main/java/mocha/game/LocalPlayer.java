package mocha.game;

import mocha.game.world.entity.Entity;

public class LocalPlayer implements Player {

  private int id;
  private Entity entity;

  public LocalPlayer(int id) {
    this.id = id;
  }

  public LocalPlayer(Entity entity) {
    this.entity = entity;
  }

  @Override
  public Entity getEntity() {
    return entity;
  }

  @Override
  public void setEntity(Entity entity) {
    this.entity = entity;
  }

  @Override
  public void remove() {

  }

  @Override
  public Integer getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }
}
