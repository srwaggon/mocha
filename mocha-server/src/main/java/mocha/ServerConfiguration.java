package mocha;

import com.google.common.collect.Lists;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import javax.inject.Inject;

import mocha.game.Game;
import mocha.game.GameLoop;
import mocha.game.Player;
import mocha.game.PlayerService;
import mocha.game.event.MochaEventBus;
import mocha.game.rule.GameRule;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.chunk.ChunkFactory;
import mocha.game.world.chunk.ChunkService;
import mocha.game.world.entity.EntitiesInChunkService;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.EntityService;
import mocha.game.world.entity.ServerEntityToEntityRepositoryAdapter;
import mocha.game.world.entity.movement.Movement;
import mocha.game.world.entity.movement.MovementFactory;
import mocha.game.world.entity.movement.collision.CollisionFactory;
import mocha.game.world.entity.movement.rule.MovementRule;
import mocha.game.world.entity.rule.PickUpItemsRule;
import mocha.game.world.tile.TileReader;
import mocha.game.world.tile.TileSetFactory;
import mocha.game.world.tile.TileStringBuilder;
import mocha.game.world.tile.rule.GrassGrowsRule;
import mocha.game.world.tile.rule.WaterEvaporatesRule;
import mocha.net.packet.PacketFactory;
import mocha.net.packet.PacketSenderFactory;
import mocha.server.event.ServerEventBus;
import mocha.shared.CachingRepository;
import mocha.shared.IdFactory;
import mocha.shared.InMemoryRepository;
import mocha.shared.Repository;
import mocha.shared.task.TaskService;

@Configuration()
public class ServerConfiguration {

  @Inject
  private ServerEventBus serverEventBus;

  @Bean
  public List<GameRule> getRules(
      Repository<Entity, Integer> entityRepository,
      Repository<Chunk, Integer> chunkRepository,
      Repository<Movement, Integer> movementRepository,
      ChunkService chunkService,
      EntitiesInChunkService entitiesInChunkService, EntityService entityService
  ) {
    MovementRule movementRule = new MovementRule(entityRepository, movementRepository, chunkService, entitiesInChunkService);
    serverEventBus.register(movementRule);

    PickUpItemsRule pickUpItemsRule = new PickUpItemsRule(chunkService, entitiesInChunkService, movementRepository, entityService);
    serverEventBus.register(pickUpItemsRule);

    GrassGrowsRule grassGrowsRule = new GrassGrowsRule(serverEventBus, chunkRepository, chunkService);

    WaterEvaporatesRule waterEvaporatesRule = new WaterEvaporatesRule(serverEventBus, chunkRepository, chunkService);

    return Lists.newArrayList(
        movementRule,
        grassGrowsRule,
        waterEvaporatesRule,
        pickUpItemsRule
    );
  }

  @Bean
  public PacketFactory getPacketFactory(TileStringBuilder tileStringBuilder) {
    return new PacketFactory(tileStringBuilder);
  }

  @Bean
  public PlayerService playerService(
      MochaEventBus eventBus,
      Repository<Player, Integer> playerRepository,
      EntityService entityService
  ) {
    return new PlayerService(eventBus, playerRepository, entityService);
  }

  @Bean
  public EntityService entityService(
      MochaEventBus eventBus, Repository<Entity, Integer> entityRepository,
      EntitiesInChunkService entitiesInChunkService,
      ChunkService chunkService,
      Repository<Movement, Integer> movementRepository
  ) {
    return new EntityService(eventBus, entityRepository, entitiesInChunkService, chunkService, movementRepository);
  }

  @Bean
  public Game game(List<GameRule> gameRules) {
    return new Game(gameRules);
  }

  @Bean
  public GameLoop getGameLoop() {
    return new GameLoop();
  }

  @Bean
  public IdFactory<Entity> entityIdFactory(Repository<Entity, Integer> entityRepository) {
    return new IdFactory<>(entityRepository);
  }

  @Bean
  public Repository<Player, Integer> playerRegistry() {
    return new InMemoryRepository<>();
  }

  @Bean
  public IdFactory<Player> playerIdFactory(Repository<Player, Integer> playerRepository) {
    return new IdFactory<>(playerRepository);
  }

  @Bean
  public MovementFactory movementFactory(CollisionFactory collisionFactory, Repository<Entity, Integer> entityRepository) {
    return new MovementFactory(collisionFactory, entityRepository);
  }

  @Bean
  public Repository<Movement, Integer> movementRepository() {
    return new InMemoryRepository<>();
  }

  @Bean
  public CollisionFactory collisionFactory(ChunkService chunkService, EntitiesInChunkService entitiesInChunkService) {
    return new CollisionFactory(chunkService, entitiesInChunkService);
  }

  @Bean
  public PacketSenderFactory packetSenderFactory() {
    return new PacketSenderFactory(serverEventBus);
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
  public ChunkFactory chunkFactory(TileSetFactory tileSetFactory) {
    return new ChunkFactory(tileSetFactory);
  }

  @Bean()
  public TaskService taskService() {
    return new TaskService();
  }

  @Bean
  public Repository<Entity, Integer> entityRepository(ServerEntityToEntityRepositoryAdapter entityRepository) {
    return new CachingRepository<>(entityRepository);
  }

  @Bean
  public Repository<Chunk, Integer> chunkRepository(Repository<Chunk, Integer> chunkRepository) {
    return new CachingRepository<>(chunkRepository);
  }

  @Bean
  public ChunkService chunkService(ChunkFactory chunkFactory, Repository<Chunk, Integer> chunkRepository) {
    return new ChunkService(chunkFactory, chunkRepository);
  }

  @Bean
  public EntitiesInChunkService entitiesInChunkService(Repository<Entity, Integer> entityRepository) {
    return new EntitiesInChunkService(entityRepository);
  }
}
