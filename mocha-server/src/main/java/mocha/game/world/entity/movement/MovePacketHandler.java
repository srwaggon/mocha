package mocha.game.world.entity.movement;

import com.google.common.eventbus.Subscribe;

import java.util.Optional;

import mocha.game.player.Player;
import mocha.game.player.PlayerService;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.movement.command.EntityMoveCommand;
import mocha.net.packet.PacketHandler;
import mocha.server.event.ServerEventBus;

public class MovePacketHandler implements PacketHandler<MovePacket> {

  private final ServerEventBus serverEventBus;
  private final int playerId;
  private final PlayerService playerService;

  public MovePacketHandler(ServerEventBus serverEventBus, int playerId, PlayerService playerService) {
    this.serverEventBus = serverEventBus;
    this.playerId = playerId;
    this.playerService = playerService;
  }

  @Subscribe
  public void handle(MovePacket movePacket) {
    EntityMoveCommand moveCommand = movePacket.getMoveCommand();
    Optional<Player> maybePlayer = playerService.findById(playerId);
    maybePlayer.ifPresent(player -> {
      Entity entity = playerService.getEntityForPlayer(player);
      moveCommand.setEntityId(entity.getId());
      serverEventBus.post(moveCommand);
    });
  }
}