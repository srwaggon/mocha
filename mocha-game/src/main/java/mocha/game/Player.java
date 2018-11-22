package mocha.game;

import mocha.game.world.entity.Entity;
import mocha.shared.Identified;

public interface Player extends Identified<Integer> {

  Entity getEntity();

  void setEntity(Entity entity);

  void remove();
}
