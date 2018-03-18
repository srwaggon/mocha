package mocha.game.rule;

import mocha.game.Game;
import mocha.net.event.NetworkedMochaEventBus;

public class ArtificialIntelligenceRule implements GameRule {

  private NetworkedMochaEventBus networkedMochaEventBus;

  public ArtificialIntelligenceRule(NetworkedMochaEventBus networkedMochaEventBus) {
    this.networkedMochaEventBus = networkedMochaEventBus;
  }

  @Override
  public void apply(Game game) {
    game.getActiveEntities().stream()
        .filter(entity -> !entity.getMovement().isMoving())
        .forEach(networkedMochaEventBus::postMoveCommand);
  }
}
