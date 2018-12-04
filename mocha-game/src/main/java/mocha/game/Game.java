package mocha.game;

import java.util.List;

import mocha.game.rule.GameRule;

public class Game implements Tickable {

  private List<GameRule> gameRules;

  public Game(List<GameRule> gameRules) {
    this.gameRules = gameRules;
  }

  @Override
  public void tick(long now) {
    gameRules.forEach(gameRule -> gameRule.apply(this));
  }

}
