package mocha.game;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

import javax.inject.Inject;

import lombok.Data;
import lombok.NoArgsConstructor;
import mocha.game.rule.GameRule;
import mocha.game.world.World;
import mocha.game.world.entity.Entity;

@Data
@Component
@NoArgsConstructor
public class Game implements Tickable {

  @Inject private World world;
  @Inject @Qualifier("player") private Entity player;
  @Inject @Qualifier("entities") private List<Entity> entities;
  @Inject private List<GameRule> gameRules;

  public void tick(long now) {
    gameRules.forEach(gameRule -> gameRule.apply(this));
  }
}
