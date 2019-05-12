package mocha.game.world.entity.command;

import mocha.game.command.Command;
import mocha.game.world.entity.Entity;

public class RemoveEntityCommand implements Command {

  private Entity entity;

  public RemoveEntityCommand(Entity entity) {
    this.entity = entity;
  }

  public Entity getEntity() {
    return entity;
  }
}
