package mocha;

import com.google.common.collect.Lists;
import com.google.common.eventbus.EventBus;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import mocha.game.rule.BrainRule;
import mocha.game.rule.GameRule;
import mocha.game.rule.MovementRule;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.EntityFactory;
import mocha.game.world.entity.brain.BrainFactory;

@Configuration
public class MochaConfig {

  @Bean
  public EventBus getEventBus() {
    return new EventBus();
  }

  @Bean
  @Qualifier("player")
  public Entity getPlayer(EntityFactory entityFactory, BrainFactory brainFactory) {
    Entity player = entityFactory.createRandom();
    player.setBrain(brainFactory.newInputBrain(player));
    return player;
  }

  @Bean
  @Qualifier("entities")
  public List<Entity> getEntities(EntityFactory entityFactory, @Qualifier("player") Entity player) {
    List<Entity> entities = Lists.newArrayList();
    entities.add(player);
    for (int i = 0; i < 1; i++) {
      entities.add(entityFactory.createRandom());
      entities.add(entityFactory.createRandomSlider());
      entities.add(entityFactory.createRandomAccelerating());
    }
    entities.add(entityFactory.newPickaxe());
    return entities;
  }

  @Bean
  public List<GameRule> getRules() {
    return Lists.newArrayList(new BrainRule(), new MovementRule());
  }

}
