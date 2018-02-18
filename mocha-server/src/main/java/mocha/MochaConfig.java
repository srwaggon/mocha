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
import mocha.game.rule.PickUpItemsRule;
import mocha.game.rule.RemoveEntityRule;
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
  public List<GameRule> getRules(MochaWorld mochaWorld, EventBus eventBus) {
    MovementRule movementRule = new MovementRule();
    eventBus.register(movementRule);

    PickUpItemsRule pickUpItemsRule = new PickUpItemsRule(mochaWorld, eventBus);
    eventBus.register(pickUpItemsRule);

    RemoveEntityRule removeEntityRule = new RemoveEntityRule(mochaWorld);
    eventBus.register(removeEntityRule);

    return Lists.newArrayList(new BrainRule(), movementRule, pickUpItemsRule, removeEntityRule);
  }

}
