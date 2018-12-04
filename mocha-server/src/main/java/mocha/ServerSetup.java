package mocha;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Random;

import javax.inject.Inject;

import mocha.game.PlayerService;
import mocha.game.item.ItemPrototypeRepository;
import mocha.game.item.ItemRepository;
import mocha.game.item.ServerItem;
import mocha.game.item.ServerItemPrototype;
import mocha.game.world.Location;
import mocha.game.world.chunk.ChunkService;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.EntityService;
import mocha.game.world.item.ItemEntity;
import mocha.game.world.item.ItemType;
import mocha.shared.IdFactory;

@Component
public class ServerSetup implements CommandLineRunner {

  private ChunkService chunkService;
  private ItemRepository itemRepository;
  private ItemPrototypeRepository itemPrototypeRepository;
  private IdFactory<Entity> entityIdFactory;
  private EntityService entityService;
  private PlayerService playerService;

  @Inject
  public ServerSetup(
      ChunkService chunkService,
      ItemRepository itemRepository,
      ItemPrototypeRepository itemPrototypeRepository,
      IdFactory<Entity> entityIdFactory,
      EntityService entityService, PlayerService playerService
  ) {
    this.chunkService = chunkService;
    this.itemRepository = itemRepository;
    this.itemPrototypeRepository = itemPrototypeRepository;
    this.entityIdFactory = entityIdFactory;
    this.entityService = entityService;
    this.playerService = playerService;
  }

  @Override
  public void run(String... args) {
    createItems();
    createChunks();
  }

  private void createItems() {
    ServerItemPrototype itemPrototype = new ServerItemPrototype(1, "Pickaxe", 100, ItemType.TOOL, "cool");
    ServerItemPrototype pickaxe = itemPrototypeRepository.save(itemPrototype);
    ServerItem serverPickaxe = new ServerItem(1, itemPrototype, 0, 0, 0);
    itemRepository.save(serverPickaxe);

    Location itemLocation = new Location(128, 128);
    ItemEntity pickaxeEntity = new ItemEntity(entityIdFactory.newId(), itemLocation, serverPickaxe);
    entityService.addEntity(pickaxeEntity);
  }

  private void addEntity() {
    entityService.addEntity(newRandomEntity());
  }

  private Entity newRandomEntity() {
    return new Entity(null, new Location(random(), random()));
  }

  private int random() {
    return new Random().nextInt(16) * 32;
  }

  private void createChunks() {
    int radius = 0;
    for (int y = -radius; y <= radius; y++) {
      for (int x = -radius; x <= radius; x++) {
        chunkService.getOrCreateChunkByIndex(new Location(x, y));
      }
    }
  }
}
