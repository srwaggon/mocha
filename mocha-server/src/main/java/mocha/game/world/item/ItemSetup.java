package mocha.game.world.item;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.game.GameSetup;
import mocha.game.world.Location;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.EntityService;
import mocha.game.world.entity.ItemEntity;
import mocha.game.world.item.itemprototype.ItemPrototype;
import mocha.game.world.item.itemprototype.ItemPrototypeService;
import mocha.shared.IdFactory;

@Component
@Profile("!test")
public class ItemSetup implements GameSetup {

  private ItemPrototypeService itemPrototypeService;
  private ItemService itemService;
  private IdFactory<Entity> entityIdFactory;
  private EntityService entityService;

  protected ItemSetup() {
  }

  @Inject
  public ItemSetup(
      ItemPrototypeService itemPrototypeService,
      ItemService itemService,
      IdFactory<Entity> entityIdFactory,
      EntityService entityService
  ) {
    this.itemPrototypeService = itemPrototypeService;
    this.itemService = itemService;
    this.entityIdFactory = entityIdFactory;
    this.entityService = entityService;
  }

  @Override
  public void run() {
    ItemPrototype itemPrototype = new ItemPrototype(1, "Pickaxe", "/mocha/gfx/items/pickaxe0.png", ItemType.TOOL, "cool");
    ItemPrototype pickaxe = itemPrototypeService.save(itemPrototype);
    Item serverPickaxe = new Item(1, pickaxe, 0, 0, 0);
    itemService.save(serverPickaxe);

    Location itemLocation = new Location(128, 128);
    ItemEntity pickaxeEntity = new ItemEntity(entityIdFactory.newId(), itemLocation, serverPickaxe);
    entityService.save(pickaxeEntity);
  }
}
