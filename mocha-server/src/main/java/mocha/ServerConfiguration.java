package mocha;

import com.google.common.collect.Lists;
import com.google.common.eventbus.EventBus;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import mocha.game.Game;
import mocha.game.GameLoop;
import mocha.game.rule.GameRule;
import mocha.game.world.World;
import mocha.game.world.chunk.ChunkFactory;
import mocha.game.world.entity.EntityFactory;
import mocha.game.world.entity.EntityIdFactory;
import mocha.game.world.entity.EntityRegistry;
import mocha.game.world.entity.brain.BrainFactory;
import mocha.game.world.entity.brain.rule.BrainRule;
import mocha.game.world.entity.movement.MovementFactory;
import mocha.game.world.entity.movement.collision.CollisionFactory;
import mocha.game.world.entity.movement.rule.MovementRule;
import mocha.game.world.entity.rule.PickUpItemsRule;
import mocha.game.world.entity.rule.RemoveEntityRule;
import mocha.game.world.tile.TileFactory;
import mocha.net.packet.PacketListenerFactory;
import mocha.net.packet.PacketSenderFactory;
import mocha.net.event.NetworkedMochaEventBus;
import mocha.net.packet.PacketFactory;

@Configuration
public class ServerConfiguration {

  @Bean
  public NetworkedMochaEventBus getEventBus(PacketFactory packetFactory) {
    return new NetworkedMochaEventBus(packetFactory);
  }

  @Bean
  public List<GameRule> getRules(World world, NetworkedMochaEventBus networkedMochaEventBus) {
    MovementRule movementRule = new MovementRule(networkedMochaEventBus);
    networkedMochaEventBus.register(movementRule);

    PickUpItemsRule pickUpItemsRule = new PickUpItemsRule(networkedMochaEventBus);
    networkedMochaEventBus.register(pickUpItemsRule);

    RemoveEntityRule removeEntityRule = new RemoveEntityRule(world);
    networkedMochaEventBus.register(removeEntityRule);

    return Lists.newArrayList(new BrainRule(), movementRule, pickUpItemsRule, removeEntityRule);
  }

  @Bean
  public PacketListenerFactory packetListenerFactory(EventBus eventBus) {
    return new PacketListenerFactory(eventBus);
  }

  @Bean
  public PacketFactory getPacketFactory() {
    return new PacketFactory();
  }

  @Bean
  public Game game(World world, List<GameRule> gameRules, EntityFactory entityFactory, EntityRegistry entityRegistry) {
    Game game = new Game(world, gameRules, entityRegistry);
    game.add(entityFactory.createSlider());
    return game;
  }

  @Bean
  public GameLoop getGameLoop(Game game) {
    return new GameLoop(game);
  }

  @Bean
  public EntityRegistry entityRegistry() {
    return new EntityRegistry();
  }

  @Bean
  public EntityIdFactory entityIdFactory(EntityRegistry entityRegistry) {
    return new EntityIdFactory(entityRegistry);
  }

  @Bean
  public EntityFactory getEntityFactory(BrainFactory brainFactory, MovementFactory movementFactory, EntityIdFactory entityIdFactory) {
    return new EntityFactory(brainFactory, movementFactory, entityIdFactory);
  }

  @Bean
  public BrainFactory brainFactory() {
    return new BrainFactory();
  }

  @Bean
  public MovementFactory movementFactory(CollisionFactory collisionFactory) {
    return new MovementFactory(collisionFactory);
  }

  @Bean
  public CollisionFactory collisionFactory(World world) {
    return new CollisionFactory(world);
  }

  @Bean
  public PacketSenderFactory packetSenderFactory(EventBus eventBus) {
    return new PacketSenderFactory(eventBus);
  }

  @Bean
  public TileFactory tileFactory() {
    return new TileFactory();
  }

  @Bean
  public ChunkFactory chunkFactory(TileFactory tileFactory) {
    return new ChunkFactory(tileFactory);
  }

  @Bean
  public ExecutorService executorService() {
    return Executors.newFixedThreadPool(2);
  }
}
