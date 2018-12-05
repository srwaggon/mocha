package mocha.game;

import java.util.List;

import mocha.game.rule.GameRule;

public class RuleService implements Tickable {

  private List<GameRule> gameRules;

  public RuleService(List<GameRule> gameRules) {
    this.gameRules = gameRules;
  }

  @Override
  public void tick(long now) {
    gameRules.forEach(GameRule::apply);
  }

}
