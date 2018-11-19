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
import mocha.game.rule.ArtificialIntelligenceRule;
import mocha.game.rule.GameRule;
import mocha.game.world.World;
import mocha.game.world.chunk.ChunkFactory;
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
import mocha.server.event.ServerEventBus;
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
  public List<GameRule> getRules(Repository<Entity, Integer> entityRepository) {
    MovementRule movementRule = new MovementRule(entityRepository);
    serverEventBus.register(movementRule);

    PickUpItemsRule pickUpItemsRule = new PickUpItemsRule(serverEventBus);
    serverEventBus.register(pickUpItemsRule);

    ArtificialIntelligenceRule artificialIntelligenceRule = new ArtificialIntelligenceRule(serverEventBus);

    return Lists.newArrayList(
        movementRule,
        pickUpItemsRule,
        artificialIntelligenceRule
    );
  }

  @Bean
  public PacketFactory getPacketFactory(TileStringBuilder tileStringBuilder) {
    return new PacketFactory(tileStringBuilder);
  }

  @Bean
  public Game game(World world, List<GameRule> gameRules, Repository<Entity, Integer> entityRepository, Repository<Player, Integer> playerRepository) {
    return new Game(serverEventBus, world, gameRules, entityRepository, playerRepository);
  }

  @Bean
  public GameLoop getGameLoop() {
    return new GameLoop();
  }

  @Bean
  public Repository<Entity, Integer> entityRegistry() {
    return new InMemoryRepository<>();
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
  public EntityFactory getEntityFactory(MovementFactory movementFactory, IdFactory<Entity> idFactory) {
    return new EntityFactory(movementFactory, idFactory);
  }

  @Bean
  public MovementFactory movementFactory(CollisionFactory collisionFactory) {
    return new MovementFactory(collisionFactory);
  }

  @Bean
  public CollisionFactory collisionFactory(Game game) {
    return new CollisionFactory(game);
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
}
