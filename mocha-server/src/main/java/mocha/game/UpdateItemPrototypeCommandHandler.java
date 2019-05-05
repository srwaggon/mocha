package mocha.game;

import com.google.common.eventbus.Subscribe;

import org.springframework.stereotype.Component;

import java.util.Map;

import javax.inject.Inject;

import mocha.game.world.item.itemprototype.ItemPrototype;
import mocha.game.world.item.itemprototype.ItemPrototypeService;
import mocha.game.world.item.itemprototype.UpdateItemPrototypeCommand;
import mocha.net.packet.MochaConnection;

@Component
public class UpdateItemPrototypeCommandHandler implements CommandHandler<UpdateItemPrototypeCommand> {
  private ItemPrototypeService itemPrototypeService;
  private Map<Integer, MochaConnection> connectionsByPlayerId;

  @Inject
  public UpdateItemPrototypeCommandHandler(ItemPrototypeService itemPrototypeService, Map<Integer, MochaConnection> connectionsByPlayerId) {
    this.itemPrototypeService = itemPrototypeService;
    this.connectionsByPlayerId = connectionsByPlayerId;
  }

  @Override
  @Subscribe
  public void handle(UpdateItemPrototypeCommand updateItemPrototypeCommand) {
    ItemPrototype update = itemPrototypeService.updateItemPrototype(updateItemPrototypeCommand);
    connectionsByPlayerId.values().forEach(mochaConnection -> mochaConnection.sendItemPrototypeUpdate(update));
  }
}