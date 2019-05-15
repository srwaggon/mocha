package mocha.account;

import com.google.common.eventbus.Subscribe;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.game.event.MochaEventHandler;
import mocha.game.player.Player;
import mocha.game.player.PlayerService;
import mocha.game.player.ServerPlayer;
import mocha.net.packet.PacketListener;
import mocha.server.ServerPacketHandlerFactory;
import mocha.server.ServerPacketResolver;
import mocha.server.event.ServerEventBus;
import mocha.shared.IdFactory;

@Component
public class LoginSuccessEventHandler implements MochaEventHandler<LoginSuccessEvent> {

  private ServerPacketHandlerFactory serverPacketHandlerFactory;
  private ServerEventBus serverEventBus;
  private AccountService accountService;
  private PlayerService playerService;
  private IdFactory<Player> playerIdFactory;

  @Inject
  public LoginSuccessEventHandler(
      ServerPacketHandlerFactory serverPacketHandlerFactory,
      ServerEventBus serverEventBus,
      AccountService accountService,
      PlayerService playerService, IdFactory<Player> playerIdFactory
  ) {
    this.serverPacketHandlerFactory = serverPacketHandlerFactory;
    this.serverEventBus = serverEventBus;
    this.accountService = accountService;
    this.playerService = playerService;
    this.playerIdFactory = playerIdFactory;
  }

  @Subscribe
  @Override
  public void handle(LoginSuccessEvent loginSuccessEvent) {
    AccountConnection accountConnection = loginSuccessEvent.getAccountConnection();
    wireUpPacketListening(accountConnection);
    addPlayerToGame(accountConnection);
  }

  private void wireUpPacketListening(AccountConnection accountConnection) {
    ServerPacketResolver serverPacketHandler = serverPacketHandlerFactory.newServerPacketHandler(accountConnection);
    PacketListener packetListener = new PacketListener(serverEventBus, accountConnection.getMochaConnection(), serverPacketHandler);
    serverEventBus.postTaskEvent(serverPacketHandler);
    serverEventBus.postTaskEvent(packetListener);
  }

  private void addPlayerToGame(AccountConnection accountConnection) {
    Account account = accountConnection.getAccount();
    if (!accountService.getPlayer(account).isPresent()) {
      accountService.addPlayer(account, newPlayer());
    }
    Player player = account.getPlayer();
    serverEventBus.postPlayerJoinedEvent(accountConnection.getMochaConnection(), player);
  }

  private ServerPlayer newPlayer() {
    // todo: replace cast with server side player service
    return (ServerPlayer) playerService.addPlayer(new ServerPlayer(playerIdFactory.newId()));
  }
}
