package mocha.game.world.entity.command;

import mocha.game.world.entity.Entity;

public class RemoveEntityCommand {
  private final Entity entity;

  public RemoveEntityCommand(Entity entity) {
    this.entity = entity;
  }
}
