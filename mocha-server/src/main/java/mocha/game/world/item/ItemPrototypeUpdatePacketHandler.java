package mocha.game.world.item;

import com.google.common.eventbus.Subscribe;

import mocha.game.GameLogic;
import mocha.game.world.item.itemprototype.UpdateItemPrototypeCommand;
import mocha.net.packet.PacketHandler;

public class ItemPrototypeUpdatePacketHandler implements PacketHandler {

  private GameLogic gameLogic;

  public ItemPrototypeUpdatePacketHandler(GameLogic gameLogic) {
    this.gameLogic = gameLogic;
  }

  @Subscribe
  public void handle(ItemPrototypeUpdatePacket itemPrototypeUpdatePacket) {
    gameLogic.handle(new UpdateItemPrototypeCommand(
        itemPrototypeUpdatePacket.getId(),
        itemPrototypeUpdatePacket.getName(),
        itemPrototypeUpdatePacket.getSpriteId(),
        itemPrototypeUpdatePacket.getItemType(),
        itemPrototypeUpdatePacket.getDescription()
    ));
  }
}