package mocha.client;

import com.google.common.collect.Lists;
import com.google.common.eventbus.EventBus;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import mocha.game.Game;
import mocha.game.rule.BrainRule;
import mocha.game.rule.GameRule;
import mocha.game.rule.MovementRule;
import mocha.game.rule.PickUpItemsRule;
import mocha.game.rule.RemoveEntityRule;
import mocha.game.world.Location;
import mocha.game.world.World;
import mocha.game.world.chunk.ChunkFactory;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.EntityFactory;
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
  public EventBus eventBus() {
    return new EventBus();
  }

  @Bean
  public PacketListenerFactory packetListenerFactory(EventBus eventBus) {
    return new PacketListenerFactory(eventBus);
  }

  @Bean
  public PacketFactory packetFactory(World world) {
    return new PacketFactory(world);
  }

  @Bean
  public World world(ChunkFactory chunkFactory) {
    World world = new World();
    world.getChunks().put(new Location(0,0), chunkFactory.newRandomDefault(0,0));
    return world;
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

  @Bean
  public PacketSenderFactory packetSenderFactory(EventBus eventBus) {
    return new PacketSenderFactory(eventBus);
  }

  @Bean
  public ExecutorService executorService() {
    return Executors.newFixedThreadPool(8);
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
  public ClientPacketHandler clientPacketHandler(EventBus eventBus, ChunkFactory chunkFactory, World world) {
    ClientPacketHandler clientPacketHandler = new ClientPacketHandler(chunkFactory, world);
    eventBus.register(clientPacketHandler);
    return clientPacketHandler;
  }

}
