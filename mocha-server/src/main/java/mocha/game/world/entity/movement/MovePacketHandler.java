package mocha.game.world.entity.movement;

import com.google.common.eventbus.Subscribe;

import mocha.game.world.entity.Entity;
import mocha.game.world.entity.movement.command.EntityMoveCommand;
import mocha.net.packet.PacketHandler;
import mocha.server.event.ServerEventBus;

public class MovePacketHandler implements PacketHandler {

  private ServerEventBus serverEventBus;
  private Entity entity;

  public MovePacketHandler(
      ServerEventBus serverEventBus,
      Entity entity
  ) {
    this.serverEventBus = serverEventBus;
    this.entity = entity;
  }

  @Subscribe
  public void handle(MovePacket movePacket) {
    EntityMoveCommand moveCommand = movePacket.getMoveCommand();
    moveCommand.setEntityId(entity.getId());
    serverEventBus.post(moveCommand);
  }
}