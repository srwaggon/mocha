package mocha.game.world.entity.movement;

import com.google.common.eventbus.Subscribe;

import mocha.game.player.PlayerService;
import mocha.game.world.entity.movement.command.EntityMoveCommand;
import mocha.net.packet.PacketHandler;
import mocha.server.event.ServerEventBus;

public class MovePacketHandler implements PacketHandler {

  private int playerId;
  private ServerEventBus serverEventBus;
  private PlayerService playerService;

  public MovePacketHandler(
      int playerId,
      ServerEventBus serverEventBus,
      PlayerService playerService
  ) {
    this.playerId = playerId;
    this.serverEventBus = serverEventBus;
    this.playerService = playerService;
  }

  @Subscribe
  public void handle(MovePacket movePacket) {
    playerService.findById(playerId)
        .ifPresent(player -> {
          EntityMoveCommand moveCommand = movePacket.getMoveCommand();
          moveCommand.setEntityId(player.getEntity().getId());
          serverEventBus.post(moveCommand);
        });
  }
}