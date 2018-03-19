package mocha;

import com.google.common.collect.Lists;
import com.google.common.eventbus.EventBus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.List;

import mocha.client.ClientPacketHandler;
import mocha.client.event.ClientEventBus;
import mocha.game.Game;
import mocha.game.GameLoop;
import mocha.game.IdFactory;
import mocha.game.Registry;
import mocha.game.event.MochaEventBus;
import mocha.game.rule.GameRule;
import mocha.game.world.Location;
import mocha.game.world.World;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.chunk.ChunkFactory;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.EntityFactory;
import mocha.game.world.entity.brain.BrainFactory;
import mocha.game.world.entity.brain.rule.BrainRule;
import mocha.game.world.entity.movement.MovementFactory;
import mocha.game.world.entity.movement.collision.CollisionFactory;
import mocha.game.world.entity.movement.rule.MovementRule;
import mocha.game.world.entity.rule.PickUpItemsRule;
import mocha.game.world.entity.rule.RemoveEntityRule;
import mocha.game.world.tile.TileFactory;
import mocha.net.packet.PacketFactory;
import mocha.net.packet.PacketListenerFactory;
import mocha.net.packet.PacketSenderFactory;
import mocha.shared.task.TaskService;

@Configuration
public class ClientConfiguration {

  @Bean
  public PacketListenerFactory packetListenerFactory(ClientEventBus eventBus) {
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
  public IdFactory<Entity> entityIdFactory(Registry<Entity> registry) {
    return new IdFactory<>(registry);
  }

  @Bean
  public EntityFactory entityFactory(BrainFactory brainFactory, MovementFactory movementFactory, IdFactory<Entity> idFactory) {
    return new EntityFactory(brainFactory, movementFactory, idFactory);
  }

  @Bean
  public Registry<Entity> entityRegistry() {
    return new Registry<>();
  }

  @Bean
  public Game game(MochaEventBus eventBus, World world, List<GameRule> gameRules, Registry<Entity> entityRegistry) {
    return new Game(eventBus, world, gameRules, entityRegistry);
  }

  @Bean
  public GameLoop getGameLoop(Game game) {
    return new GameLoop(game);
  }

  @Bean
  public List<GameRule> getRules(World world, ClientEventBus clientEventBus) {
    MovementRule movementRule = new MovementRule(clientEventBus);
    clientEventBus.register(movementRule);

    PickUpItemsRule pickUpItemsRule = new PickUpItemsRule(clientEventBus);
    clientEventBus.register(pickUpItemsRule);

    RemoveEntityRule removeEntityRule = new RemoveEntityRule(world);
    clientEventBus.register(removeEntityRule);

    return Lists.newArrayList(new BrainRule(), movementRule, pickUpItemsRule, removeEntityRule);
  }

  @Bean
  public PacketSenderFactory packetSenderFactory(EventBus eventBus) {
    return new PacketSenderFactory(eventBus);
  }

  @Bean()
  public TaskService taskService(MochaEventBus eventBus) {
    TaskService taskService = new TaskService();
    eventBus.register(taskService);
    return taskService;
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
  public ClientPacketHandler clientPacketHandler(ClientEventBus clientEventBus, ChunkFactory chunkFactory, Game game, TaskService taskService) {
    ClientPacketHandler clientPacketHandler = new ClientPacketHandler(chunkFactory, game, clientEventBus);
    clientEventBus.register(clientPacketHandler);
    taskService.submit(clientPacketHandler);
    return clientPacketHandler;
  }

  @Bean
  @Scope("prototype")
  Logger logger(InjectionPoint injectionPoint) {
    return LoggerFactory.getLogger(injectionPoint.getMethodParameter().getContainingClass());
  }

}
