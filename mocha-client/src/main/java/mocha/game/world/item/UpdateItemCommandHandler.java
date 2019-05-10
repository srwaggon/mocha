package mocha.game.world.item;

import com.google.common.eventbus.Subscribe;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.game.CommandHandler;

@Component
public class UpdateItemCommandHandler implements CommandHandler<UpdateItemCommand> {

  private ItemService itemService;

  @Inject
  public UpdateItemCommandHandler(ItemService itemService) {
    this.itemService = itemService;
  }

  @Subscribe
  public void handle(UpdateItemCommand updateItemCommand) {
    itemService.updateItem(updateItemCommand);
  }
}