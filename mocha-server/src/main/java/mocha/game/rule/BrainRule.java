package mocha.game.rule;

import mocha.game.Game;
import mocha.game.world.entity.Entity;

public class BrainRule implements GameRule {
  @Override
  public void apply(Game game) {
    game.getActiveEntities().stream()
        .filter(entity -> !entity.equals(game.getPlayer()))
        .map(Entity::getBrain)
        .forEach(brain -> brain.tick(0L));
    game.getPlayer().getBrain().tick(0L);
  }
}
