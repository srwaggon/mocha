package mocha.game.world.entity.brain;

import mocha.game.Tickable;
import mocha.game.world.entity.Entity;

public interface Brain extends Tickable {
  void setEntity(Entity entity);
}
