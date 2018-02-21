package mocha;

import com.google.common.collect.Lists;
import com.google.common.eventbus.EventBus;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import mocha.game.Game;
import mocha.game.GameLoop;
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
public class ServerConfiguration {

  @Bean
  public EventBus getEventBus() {
    return new EventBus();
  }

  @Bean
  @Qualifier("player")
  public Entity getPlayer(EntityFactory entityFactory, BrainFactory brainFactory) {
    return entityFactory.createRandom();
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

  @Bean
  public PacketListenerFactory packetListenerFactory(EventBus eventBus) {
    return new PacketListenerFactory(eventBus);
  }

  @Bean
  public PacketFactory getPacketFactory(MochaWorld mochaWorld) {
    return new PacketFactory(mochaWorld);
  }

  @Bean
  public Game game(World world, @Qualifier("player") Entity player, List<GameRule> gameRules, EntityFactory entityFactory) {
    Game game = new Game(world, gameRules);
    game.add(entityFactory.createRandom());
    game.add(entityFactory.createRandomSlider());
    game.add(entityFactory.createRandomAccelerating());
    game.add(entityFactory.newPickaxe());
    return game;
  }

  @Bean
  public GameLoop getGameLoop(Game game) {
    return new GameLoop(game);
  }

  @Bean
  public EntityFactory getEntityFactory(BrainFactory brainFactory, MovementFactory movementFactory) {
    return new EntityFactory(brainFactory, movementFactory);
  }

  @Bean
  public BrainFactory brainFactory(EventBus eventBus) {
    return new BrainFactory(eventBus);
  }

  @Bean
  public MovementFactory movementFactory(CollisionFactory collisionFactory, EventBus eventBus) {
    return new MovementFactory(collisionFactory, eventBus);
  }

  @Bean
  public CollisionFactory collisionFactory(World world) {
    return new CollisionFactory(world);
  }

}
