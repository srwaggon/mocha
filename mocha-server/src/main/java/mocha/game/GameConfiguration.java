package mocha.game;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

import mocha.game.rule.GameRule;

@Configuration
public class GameConfiguration {

  @Bean
  public RuleService game(List<GameRule> gameRules) {
    return new RuleService(gameRules);
  }

  @Bean
  @Profile("!test")
  public GameLoop getGameLoop() {
    return new GameLoop();
  }

}
