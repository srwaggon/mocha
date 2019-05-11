package mocha.game.world.item;

import com.google.common.eventbus.Subscribe;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.game.CommandHandler;
import mocha.game.world.item.itemprototype.ItemPrototypeService;
import mocha.game.world.item.itemprototype.UpdateItemPrototypeCommand;

@Component
public class UpdateItemPrototypeCommandHandler implements CommandHandler<UpdateItemPrototypeCommand> {

  private ItemPrototypeService itemPrototypeService;

  @Inject
  public UpdateItemPrototypeCommandHandler(ItemPrototypeService itemPrototypeService) {
    this.itemPrototypeService = itemPrototypeService;
  }

  @Subscribe
  public void handle(UpdateItemPrototypeCommand updateItemPrototypeCommand) {
    itemPrototypeService.updateItemPrototype(updateItemPrototypeCommand);
  }
}