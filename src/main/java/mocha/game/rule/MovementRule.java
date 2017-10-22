package mocha.game.rule;

import mocha.game.Game;

public class MovementRule implements GameRule {

  @Override
  public void apply(Game game) {
    game.getEntities().forEach(entity -> entity.getMovement().tick(0L));
  }
}
