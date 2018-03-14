package mocha.client;

import com.google.common.collect.Lists;
import com.google.common.eventbus.EventBus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import mocha.game.Game;
import mocha.game.GameLoop;
import mocha.game.rule.BrainRule;
import mocha.game.rule.GameRule;
import mocha.game.rule.MovementRule;
import mocha.game.rule.PickUpItemsRule;
import mocha.game.rule.RemoveEntityRule;
import mocha.game.world.Location;
import mocha.game.world.World;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.chunk.ChunkFactory;
import mocha.game.world.entity.EntityFactory;
import mocha.game.world.entity.EntityIdFactory;
import mocha.game.world.entity.EntityRegistry;
import mocha.game.world.entity.brain.BrainFactory;
import mocha.game.world.entity.movement.MovementFactory;
import mocha.game.world.entity.movement.collision.CollisionFactory;
import mocha.game.world.tile.TileFactory;
import mocha.net.PacketListenerFactory;
import mocha.net.PacketSenderFactory;
import mocha.net.packet.PacketFactory;

@Configuration
public class ClientConfiguration {

  @Bean
  public PacketListenerFactory packetListenerFactory(EventBus eventBus) {
    return new PacketListenerFactory(eventBus);
  }

  @Bean
  public PacketFactory packetFactory() {
    return new PacketFactory();
  }

  @Bean
  public World world(ChunkFactory chunkFactory) {
    World world = new World();
    Chunk chunk = chunkFactory.newRandomDefault();
    world.put(new Location(0, 0), chunk);
    return world;
  }

  @Bean
  public BrainFactory brainFactory() {
    return new BrainFactory();
  }

  @Bean
  public CollisionFactory collisionFactory(World world) {
    return new CollisionFactory(world);
  }

  @Bean
  public MovementFactory movementFactory(CollisionFactory collisionFactory) {
    return new MovementFactory(collisionFactory);
  }

  @Bean
  public EntityIdFactory entityIdFactory(EntityRegistry entityRegistry) {
    return new EntityIdFactory(entityRegistry);
  }

  @Bean
  public EntityFactory entityFactory(BrainFactory brainFactory, MovementFactory movementFactory, EntityIdFactory entityIdFactory) {
    return new EntityFactory(brainFactory, movementFactory, entityIdFactory);
  }

  @Bean
  public EntityRegistry entityRegistry() {
    return new EntityRegistry();
  }

  @Bean
  public Game game(World world, List<GameRule> gameRules, MochaClientEventBus eventBus, EntityFactory entityFactory, EntityRegistry entityRegistry) {
    Game game = new MochaClientGame(world, gameRules, entityFactory, entityRegistry, eventBus);
    eventBus.register(game);
    return game;
  }

  @Bean
  public GameLoop getGameLoop(Game game) {
    return new GameLoop(game);
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
  public PacketSenderFactory packetSenderFactory(EventBus eventBus) {
    return new PacketSenderFactory(eventBus);
  }

  @Bean
  public ExecutorService executorService() {
    return Executors.newFixedThreadPool(2);
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
  public ClientPacketHandler clientPacketHandler(MochaClientEventBus mochaClientEventBus, ChunkFactory chunkFactory, Game game) {
    ClientPacketHandler clientPacketHandler = new ClientPacketHandler(chunkFactory, game, mochaClientEventBus);
    mochaClientEventBus.register(clientPacketHandler);
    return clientPacketHandler;
  }

  @Bean
  @Scope("prototype")
  Logger logger(InjectionPoint injectionPoint) {
    return LoggerFactory.getLogger(injectionPoint.getMethodParameter().getContainingClass());
  }

}
