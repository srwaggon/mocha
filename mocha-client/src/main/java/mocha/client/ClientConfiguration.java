package mocha.client;

import com.google.common.collect.Lists;
import com.google.common.eventbus.EventBus;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import mocha.game.Game;
import mocha.game.rule.BrainRule;
import mocha.game.rule.GameRule;
import mocha.game.rule.MovementRule;
import mocha.game.rule.PickUpItemsRule;
import mocha.game.rule.RemoveEntityRule;
import mocha.game.world.World;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.EntityFactory;
import mocha.game.world.entity.brain.BrainFactory;
import mocha.game.world.entity.movement.MovementFactory;
import mocha.game.world.entity.movement.collision.CollisionFactory;
import mocha.net.PacketListenerFactory;
import mocha.net.packet.PacketFactory;

@Configuration
public class ClientConfiguration {

  @Bean
  public PacketListenerFactory packetListenerFactory(PacketFactory packetFactory) {
    return new PacketListenerFactory(packetFactory);
  }

  @Bean
  public PacketFactory packetFactory(World world) {
    return new PacketFactory(world);
  }

  @Bean
  public World world() {
    return new World();
  }

  @Bean
  public EventBus eventBus() {
    return new EventBus();
  }

  @Bean
  public BrainFactory brainFactory(EventBus eventBus) {
    return new BrainFactory(eventBus);
  }

  @Bean
  public CollisionFactory collisionFactory(World world) {
    return new CollisionFactory(world);
  }

  @Bean
  public MovementFactory movementFactory(CollisionFactory collisionFactory, EventBus eventBus) {
    return new MovementFactory(collisionFactory, eventBus);
  }

  @Bean
  public EntityFactory entityFactory(BrainFactory brainFactory, MovementFactory movementFactory) {
    return new EntityFactory(brainFactory, movementFactory);
  }

  @Bean
  @Qualifier("player")
  public Entity player(EntityFactory entityFactory, BrainFactory brainFactory) {
    return entityFactory.createRandom();
  }

  @Bean
  public Game game(World world, @Qualifier("player") Entity player, List<GameRule> gameRules) {
    Game game = new Game(world, gameRules);
    game.setPlayer(player);
    return game;
  }

  @Bean
  public List<GameRule> getRules(World world, EventBus eventBus) {
    MovementRule movementRule = new MovementRule();
    eventBus.register(movementRule);

    PickUpItemsRule pickUpItemsRule = new PickUpItemsRule(world, eventBus);
    eventBus.register(pickUpItemsRule);

    RemoveEntityRule removeEntityRule = new RemoveEntityRule(world);
    eventBus.register(removeEntityRule);

    return Lists.newArrayList(new BrainRule(), movementRule, pickUpItemsRule, removeEntityRule);
  }

}
