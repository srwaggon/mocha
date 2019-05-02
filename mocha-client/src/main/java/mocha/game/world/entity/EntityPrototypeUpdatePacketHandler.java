package mocha.game.world.entity;

import com.google.common.eventbus.Subscribe;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.game.GameLogic;
import mocha.game.world.entity.prototype.EntityPrototypeUpdatePacket;
import mocha.game.world.entity.prototype.UpdateEntityPrototypeCommand;
import mocha.net.packet.PacketHandler;

@Component
public class EntityPrototypeUpdatePacketHandler implements PacketHandler {

  private GameLogic gameLogic;

  @Inject
  public EntityPrototypeUpdatePacketHandler(GameLogic gameLogic) {
    this.gameLogic = gameLogic;
  }

  @Subscribe
  public void handle(EntityPrototypeUpdatePacket entityPrototypeUpdatePacket) {
    gameLogic.handle(new UpdateEntityPrototypeCommand(entityPrototypeUpdatePacket.getEntityPrototype()));
  }
}