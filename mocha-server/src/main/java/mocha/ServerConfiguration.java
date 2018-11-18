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
import mocha.net.packet.PacketFactory;
import mocha.net.packet.PacketSenderFactory;
import mocha.server.event.ServerEventBus;
import mocha.shared.IdFactory;
import mocha.shared.Registry;
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
  public List<GameRule> getRules() {
    MovementRule movementRule = new MovementRule();
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
  public PacketFactory getPacketFactory() {
    return new PacketFactory();
  }

  @Bean
  public Game game(World world, List<GameRule> gameRules, Registry<Entity> entityRegistry, Registry<Player> playerRegistry) {
    return new Game(serverEventBus, world, gameRules, entityRegistry, playerRegistry);
  }

  @Bean
  public GameLoop getGameLoop() {
    return new GameLoop();
  }

  @Bean
  public Registry<Entity> entityRegistry() {
    return new Registry<>();
  }

  @Bean
  public IdFactory<Entity> entityIdFactory(Registry<Entity> entityRegistry) {
    return new IdFactory<>(entityRegistry);
  }

  @Bean
  public Registry<Player> playerRegistry() {
    return new Registry<>();
  }

  @Bean
  public IdFactory<Player> playerIdFactory(Registry<Player> playerRegistry) {
    return new IdFactory<>(playerRegistry);
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
  public ChunkFactory chunkFactory() {
    return new ChunkFactory();
  }

  @Bean()
  public TaskService taskService() {
    return new TaskService();
  }
}
