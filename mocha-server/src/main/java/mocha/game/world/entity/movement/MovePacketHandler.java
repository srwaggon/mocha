package mocha.game.world.entity.movement;

import com.google.common.eventbus.Subscribe;

import java.util.Optional;

import mocha.account.Account;
import mocha.account.AccountService;
import mocha.game.player.Player;
import mocha.game.player.PlayerService;
import mocha.game.world.entity.movement.command.EntityMoveCommand;
import mocha.net.packet.PacketHandler;
import mocha.server.event.ServerEventBus;

public class MovePacketHandler implements PacketHandler<MovePacket> {

  private final ServerEventBus serverEventBus;
  private final PlayerService playerService;
  private AccountService accountService;
  private Account account;

  public MovePacketHandler(
      ServerEventBus serverEventBus,
      PlayerService playerService,
      AccountService accountService,
      Account account
  ) {
    this.serverEventBus = serverEventBus;
    this.playerService = playerService;
    this.accountService = accountService;
    this.account = account;
  }

  @Subscribe
  public void handle(MovePacket movePacket) {
    EntityMoveCommand moveCommand = movePacket.getMoveCommand();
    Optional<Player> maybePlayer = accountService.getPlayer(account);
    maybePlayer.ifPresent(player ->
        playerService.getEntityForPlayer(player).ifPresent(entity -> {
          moveCommand.setEntityId(entity.getId());
          serverEventBus.post(moveCommand);
        }));
  }
}