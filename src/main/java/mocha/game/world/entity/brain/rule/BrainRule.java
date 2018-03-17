package mocha.game.world.entity.brain.rule;

import mocha.game.Game;
import mocha.game.rule.GameRule;
import mocha.game.world.entity.Entity;

public class BrainRule implements GameRule {
  @Override
  public void apply(Game game) {
    game.getActiveEntities().stream()
        .map(Entity::getBrain)
        .forEach(brain -> brain.tick(0L));
  }
}
