package mocha.server;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.game.world.Location;
import mocha.game.world.chunk.ChunkService;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.EntityService;
import mocha.game.world.entity.EntityType;
import mocha.game.world.entity.ItemEntity;
import mocha.game.world.entity.prototype.EntityPrototype;
import mocha.game.world.entity.prototype.EntityPrototypeService;
import mocha.game.world.item.Item;
import mocha.game.world.item.ItemService;
import mocha.game.world.item.ItemType;
import mocha.game.world.item.itemprototype.ItemPrototype;
import mocha.game.world.item.itemprototype.ItemPrototypeService;
import mocha.shared.IdFactory;

@Component
public class ServerSetup implements CommandLineRunner {

  private ChunkService chunkService;
  private ItemPrototypeService itemPrototypeService;
  private IdFactory<Entity> entityIdFactory;
  private EntityPrototypeService entityPrototypeService;
  private EntityService entityService;
  private ItemService itemService;

  @Inject
  public ServerSetup(
      ChunkService chunkService,
      ItemPrototypeService itemPrototypeService, IdFactory<Entity> entityIdFactory,
      EntityPrototypeService entityPrototypeService, EntityService entityService,
      ItemService itemService
  ) {
    this.chunkService = chunkService;
    this.itemPrototypeService = itemPrototypeService;
    this.entityIdFactory = entityIdFactory;
    this.entityPrototypeService = entityPrototypeService;
    this.entityService = entityService;
    this.itemService = itemService;
  }

  @Override
  public void run(String... args) {
    createItems();
    createEntities();
    createChunks();
  }

  private void createItems() {
    ItemPrototype itemPrototype = new ItemPrototype(1, "Pickaxe", "/mocha/gfx/items/pickaxe0.png::0", ItemType.TOOL, "cool");
    ItemPrototype pickaxe = itemPrototypeService.save(itemPrototype);
    Item serverPickaxe = new Item(1, pickaxe, 0, 0, 0);
    itemService.save(serverPickaxe);

    Location itemLocation = new Location(128, 128);
    ItemEntity pickaxeEntity = new ItemEntity(entityIdFactory.newId(), itemLocation, serverPickaxe);
    entityService.save(pickaxeEntity);
  }

  private void createEntities() {
    EntityPrototype slime = new EntityPrototype(
        1,
        EntityType.MOB,
        1,
        true,
        null,
        null,
        "/mocha/gfx/sprites/slime0.png::0",
        1.0
    );
    entityPrototypeService.save(slime);
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
