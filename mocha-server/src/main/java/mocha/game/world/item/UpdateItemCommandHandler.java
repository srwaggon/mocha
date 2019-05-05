package mocha.game.world.item;

import com.google.common.eventbus.Subscribe;

import org.springframework.stereotype.Component;

import java.util.Map;

import javax.inject.Inject;

import mocha.game.CommandHandler;
import mocha.net.packet.MochaConnection;

@Component
public class UpdateItemCommandHandler implements CommandHandler<UpdateItemCommand> {

  private Map<Integer, MochaConnection> mochaConnectionsByPlayerId;
  private ItemService itemService;

  @Inject
  public UpdateItemCommandHandler(Map<Integer, MochaConnection> mochaConnectionsByPlayerId, ItemService itemService) {
    this.mochaConnectionsByPlayerId = mochaConnectionsByPlayerId;
    this.itemService = itemService;
  }

  @Subscribe
  public void handle(UpdateItemCommand updateItemCommand) {
    Item update = itemService.updateItem(updateItemCommand);
    mochaConnectionsByPlayerId.values().forEach(mochaConnection -> mochaConnection.sendItemUpdate(update));
  }
}