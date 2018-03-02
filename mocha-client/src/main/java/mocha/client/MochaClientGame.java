package mocha.client;

import com.google.common.eventbus.Subscribe;

import java.util.List;

import mocha.game.Game;
import mocha.game.rule.GameRule;
import mocha.game.world.World;
import mocha.game.world.entity.AddEntityEvent;

public class MochaClientGame extends Game {
  MochaClientGame(World world, List<GameRule> gameRules) {
    super(world, gameRules);
  }

  @Subscribe
  public void handle(AddEntityEvent addEntityEvent) {
    this.add(addEntityEvent.getEntity());
  }
}
