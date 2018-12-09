package mocha;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.game.item.ServerItemPrototype;
import mocha.game.item.ServerItemPrototypeRepository;
import mocha.game.item.ServerItemRepository;
import mocha.game.world.Location;
import mocha.game.world.chunk.ChunkService;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.EntityService;
import mocha.game.world.item.Item;
import mocha.game.world.item.ItemEntity;
import mocha.game.world.item.ItemService;
import mocha.game.world.item.ItemType;
import mocha.shared.IdFactory;

@Component
public class ServerSetup implements CommandLineRunner {

  private ChunkService chunkService;
  private ServerItemRepository serverItemRepository;
  private ServerItemPrototypeRepository serverItemPrototypeRepository;
  private IdFactory<Entity> entityIdFactory;
  private EntityService entityService;
  private ItemService itemService;

  @Inject
  public ServerSetup(
      ChunkService chunkService,
      ServerItemRepository serverItemRepository,
      ServerItemPrototypeRepository serverItemPrototypeRepository,
      IdFactory<Entity> entityIdFactory,
      EntityService entityService,
      ItemService itemService
  ) {
    this.chunkService = chunkService;
    this.serverItemRepository = serverItemRepository;
    this.serverItemPrototypeRepository = serverItemPrototypeRepository;
    this.entityIdFactory = entityIdFactory;
    this.entityService = entityService;
    this.itemService = itemService;
  }

  @Override
  public void run(String... args) {
    createItems();
    createChunks();
  }

  private void createItems() {
    ServerItemPrototype itemPrototype = new ServerItemPrototype(1, "Pickaxe", 100, ItemType.TOOL, "cool");
    ServerItemPrototype pickaxe = serverItemPrototypeRepository.save(itemPrototype);
    Item serverPickaxe = new Item(1, pickaxe, 0, 0, 0);
    itemService.addItem(serverPickaxe);

    Location itemLocation = new Location(128, 128);
    ItemEntity pickaxeEntity = new ItemEntity(entityIdFactory.newId(), itemLocation, serverPickaxe);
    entityService.addEntity(pickaxeEntity);
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
