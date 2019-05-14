package mocha.net;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.account.AccountService;
import mocha.game.player.Player;
import mocha.game.player.PlayerService;
import mocha.net.packet.MochaConnection;
import mocha.server.event.ServerEventBus;
import mocha.shared.IdFactory;

@Component
public class LoginRequestPacketHandlerFactory {

  private final PlayerService playerService;
  private AccountService accountService;
  private ServerEventBus mochaEventBus;
  private IdFactory<Player> playerIdFactory;

  @Inject
  public LoginRequestPacketHandlerFactory(
      PlayerService playerService,
      AccountService accountService,
      ServerEventBus mochaEventBus,
      IdFactory<Player> playerIdFactory
  ) {
    this.playerService = playerService;
    this.accountService = accountService;
    this.mochaEventBus = mochaEventBus;
    this.playerIdFactory = playerIdFactory;
  }

  public LoginRequestPacketHandler newLoginRequestPacketHandler(MochaConnection mochaConnection) {
    return new LoginRequestPacketHandler(mochaEventBus, mochaConnection, playerService, accountService, playerIdFactory);
  }
}
