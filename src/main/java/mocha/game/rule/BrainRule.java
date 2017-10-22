package mocha.game.rule;

import mocha.game.Game;

public class BrainRule implements GameRule {
  @Override
  public void apply(Game game) {
    game.getEntities().forEach(entity -> entity.getBrain().tick(0L));
  }
}
