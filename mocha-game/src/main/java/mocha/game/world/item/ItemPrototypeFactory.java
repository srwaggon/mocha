package mocha.game.world.item;

import org.jetbrains.annotations.NotNull;

import mocha.game.world.item.command.UpdateItemPrototypeCommand;

public class ItemPrototypeFactory {

  @NotNull
  ItemPrototype newItemPrototype(UpdateItemPrototypeCommand updateItemPrototypeCommand) {
    return newItemPrototype(
        updateItemPrototypeCommand.getId(),
        updateItemPrototypeCommand.getName(),
        updateItemPrototypeCommand.getSpriteId(),
        updateItemPrototypeCommand.getItemType(),
        updateItemPrototypeCommand.getDescription()
    );
  }

  @NotNull
  ItemPrototype newDefaultItemPrototype() {
    return newItemPrototype(0, "Default", "/mocha/gfx/tiles/dirt.png::0", ItemType.CURRENCY, "Default Item");
  }

  @NotNull
  ItemPrototype newItemPrototype(
      int id,
      String name,
      String spriteId,
      ItemType itemType,
      String description
  ) {
    return new ItemPrototype(
        id,
        name,
        spriteId,
        itemType,
        description
    );
  }
}