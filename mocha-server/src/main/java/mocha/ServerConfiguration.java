package mocha;

import com.google.common.collect.Lists;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import javax.inject.Inject;

import mocha.game.Game;
import mocha.game.GameLoop;
import mocha.game.Player;
import mocha.game.item.Item;
import mocha.game.item.ItemPrototype;
import mocha.game.item.ItemPrototypeRepository;
import mocha.game.item.ItemRepository;
import mocha.game.item.ItemType;
import mocha.game.rule.GameRule;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.ServerEntityToEntityRepositoryAdapter;
import mocha.game.world.entity.movement.Movement;
import mocha.game.world.entity.movement.MovementFactory;
import mocha.game.world.entity.movement.collision.CollisionFactory;
import mocha.game.world.entity.movement.rule.MovementRule;
import mocha.game.world.entity.rule.PickUpItemsRule;
import mocha.game.world.tile.TileReader;
import mocha.game.world.tile.TileSetFactory;
import mocha.game.world.tile.TileStringBuilder;
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
  public CommandLineRunner demo(ItemRepository itemRepository, ItemPrototypeRepository itemPrototypeRepository) {
    return (args) -> {
      itemRepository.count();
      ItemPrototype pickaxe = itemPrototypeRepository.save(new ItemPrototype(0L, "Pickaxe", 100, ItemType.TOOL));
      itemRepository.save(new Item(0L, pickaxe, 0, 0, 0, 0, 0));
      itemRepository.count();
      itemPrototypeRepository.count();
    };
  }

  @Bean
  public List<GameRule> getRules(
      Repository<Entity, Integer> entityRepository,
      Repository<Chunk, Integer> chunkRepository,
      Repository<Movement, Integer> movementRepository
  ) {
    MovementRule movementRule = new MovementRule(entityRepository, chunkRepository, movementRepository);
    serverEventBus.register(movementRule);

    PickUpItemsRule pickUpItemsRule = new PickUpItemsRule(chunkRepository);
    serverEventBus.register(pickUpItemsRule);

    return Lists.newArrayList(
        movementRule,
        pickUpItemsRule
    );
  }

  @Bean
  public PacketFactory getPacketFactory(TileStringBuilder tileStringBuilder) {
    return new PacketFactory(tileStringBuilder);
  }

  @Bean
  public Game game(
      Repository<Chunk, Integer> chunkRepository,
      List<GameRule> gameRules,
      Repository<Entity, Integer> entityRepository,
      Repository<Player, Integer> playerRepository,
      Repository<Movement, Integer> movementRepository
  ) {
    return new Game(serverEventBus, gameRules, playerRepository, entityRepository, chunkRepository, movementRepository);
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
  public CollisionFactory collisionFactory(Repository<Chunk, Integer> chunkRepository) {
    return new CollisionFactory(chunkRepository);
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

  @Bean()
  public TaskService taskService() {
    return new TaskService();
  }

  @Bean
  public Repository<Entity, Integer> entityRepository(ServerEntityToEntityRepositoryAdapter entityRepository) {
    return new CachingRepository<>(entityRepository);
//    return new InMemoryRepository<>();
  }

  @Bean
  public Repository<Chunk, Integer> chunkRepository(Repository<Chunk, Integer> chunkRepository) {
    return new CachingRepository<>(chunkRepository);
  }
}
