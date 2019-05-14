package mocha.net;

import com.google.common.eventbus.Subscribe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import mocha.account.Account;
import mocha.account.AccountService;
import mocha.game.LoginRequestPacket;
import mocha.game.player.Player;
import mocha.game.player.PlayerService;
import mocha.game.player.ServerPlayer;
import mocha.net.packet.MochaConnection;
import mocha.net.packet.PacketHandler;
import mocha.server.event.ServerEventBus;
import mocha.shared.IdFactory;

public class LoginRequestPacketHandler implements PacketHandler<LoginRequestPacket> {
  private Logger log = LoggerFactory.getLogger(ConnectedEventHandler.class);

  private ServerEventBus mochaEventBus;
  private MochaConnection mochaConnection;
  private PlayerService playerService;
  private AccountService accountService;
  private IdFactory<Player> playerIdFactory;

  LoginRequestPacketHandler(
      ServerEventBus mochaEventBus,
      MochaConnection mochaConnection,
      PlayerService playerService,
      AccountService accountService,
      IdFactory<Player> playerIdFactory
  ) {
    this.mochaEventBus = mochaEventBus;
    this.mochaConnection = mochaConnection;
    this.playerService = playerService;
    this.accountService = accountService;
    this.playerIdFactory = playerIdFactory;
  }


  @Subscribe
  @Override
  public void handle(LoginRequestPacket loginRequestPacket) {
    log.info(loginRequestPacket.toString());
    // can we wait this long to create the player id?

    String accountName = loginRequestPacket.getAccountName();
    Optional<Account> accountMaybe = accountService.findAccountByName(accountName);

    accountMaybe.ifPresent(account -> {
      Optional<Player> maybePlayer = accountService.getPlayer(account);

      if (!maybePlayer.isPresent()) {
        // todo: replace cast with server side player service
        ServerPlayer player = (ServerPlayer) playerService.addPlayer(new ServerPlayer(playerIdFactory.newId()));
        accountService.addPlayer(account, player);
      }

      Player player = account.getPlayer();
      // todo: test
      mochaEventBus.postPlayerJoinedEvent(mochaConnection, player);
    });
  }
}
