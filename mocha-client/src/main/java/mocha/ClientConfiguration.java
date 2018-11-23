package mocha;

import com.google.common.collect.Lists;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.List;

import javax.inject.Inject;

import mocha.client.event.ClientEventBus;
import mocha.game.Game;
import mocha.game.GameLogic;
import mocha.game.GameLoop;
import mocha.game.LocalClientGameLogic;
import mocha.game.NetworkClientGameLogic;
import mocha.game.Player;
import mocha.game.event.MochaEventBus;
import mocha.game.rule.GameRule;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.EntityFactory;
import mocha.game.world.entity.movement.MovementFactory;
import mocha.game.world.entity.movement.collision.CollisionFactory;
import mocha.game.world.entity.movement.rule.MovementRule;
import mocha.game.world.entity.rule.PickUpItemsRule;
import mocha.game.world.tile.TileReader;
import mocha.game.world.tile.TileSetFactory;
import mocha.game.world.tile.TileStringBuilder;
import mocha.net.packet.PacketFactory;
import mocha.net.packet.PacketSenderFactory;
import mocha.shared.IdFactory;
import mocha.shared.InMemoryRepository;
import mocha.shared.Repository;
import mocha.shared.task.TaskService;

@Configuration
public class ClientConfiguration {

  @Value("${mocha.client.online}")
  private boolean isOnline;

  @Inject
  private ClientEventBus clientEventBus;

  @Bean
  public GameLogic gameLogic(NetworkClientGameLogic networkClientGameLogic, LocalClientGameLogic localClientGameLogic) {
    GameLogic gameLogic = isOnline ? networkClientGameLogic : localClientGameLogic;
    clientEventBus.register(gameLogic);
    return gameLogic;
  }

  @Bean
  public PacketFactory packetFactory(TileStringBuilder tileStringBuilder) {
    return new PacketFactory(tileStringBuilder);
  }

  @Bean
  public CollisionFactory collisionFactory(Repository<Chunk, Integer> chunkRepository) {
    return new CollisionFactory(chunkRepository);
  }

  @Bean
  public MovementFactory movementFactory(CollisionFactory collisionFactory) {
    return new MovementFactory(collisionFactory);
  }

  @Bean
  public IdFactory<Entity> entityIdFactory(Repository<Entity, Integer> repository) {
    return new IdFactory<>(repository);
  }

  @Bean
  public EntityFactory entityFactory(MovementFactory movementFactory) {
    return new EntityFactory(movementFactory);
  }

  @Bean
  public Repository<Entity, Integer> entityRegistry() {
    return new InMemoryRepository<>();
  }

  @Bean
  public Repository<Player, Integer> playerRegistry() {
    return new InMemoryRepository<>();
  }

  @Bean
  public Repository<Chunk, Integer> chunkRepository() {
    return new InMemoryRepository<>();
  }

  @Bean
  public IdFactory<Player> playerIdFactory(Repository<Player, Integer> playerRepository) {
    return new IdFactory<>(playerRepository);
  }

  @Bean
  public Game game(
      MochaEventBus eventBus,
      List<GameRule> gameRules,
      Repository<Entity, Integer> entityRepository,
      Repository<Player, Integer> playerRepository,
      Repository<Chunk, Integer> chunkRepository

  ) {
    return new Game(eventBus, gameRules, playerRepository, entityRepository, chunkRepository);
  }

  @Bean
  public GameLoop getGameLoop() {
    return new GameLoop();
  }

  @Bean
  public List<GameRule> getRules(
      Repository<Entity, Integer> entityRepository,
      Repository<Chunk, Integer> chunkRepository
  ) {
    MovementRule movementRule = new MovementRule(entityRepository, chunkRepository);
    clientEventBus.register(movementRule);

    PickUpItemsRule pickUpItemsRule = new PickUpItemsRule(chunkRepository);
    clientEventBus.register(pickUpItemsRule);

    return Lists.newArrayList(movementRule, pickUpItemsRule);
  }

  @Bean
  public PacketSenderFactory packetSenderFactory() {
    return new PacketSenderFactory(clientEventBus);
  }

  @Bean()
  public TaskService taskService() {
    TaskService taskService = new TaskService();
    clientEventBus.register(taskService);
    return taskService;
  }

  @Bean
  public TileReader tileReader() {
    return new TileReader();
  }

  @Bean
  public TileStringBuilder tileStringBuilder() {
    return new TileStringBuilder();
  }

  @Bean
  public TileSetFactory tileSetFactory(TileReader tileReader) {
    return new TileSetFactory(tileReader);
  }

  @Bean
  @Scope("prototype")
  Logger logger(InjectionPoint injectionPoint) {
    return LoggerFactory.getLogger(injectionPoint.getMethodParameter().getContainingClass());
  }

}
