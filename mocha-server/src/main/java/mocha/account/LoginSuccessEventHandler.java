package mocha.account;

import com.google.common.eventbus.Subscribe;

import org.springframework.stereotype.Component;

import java.util.Map;

import javax.inject.Inject;

import lombok.extern.java.Log;
import mocha.game.event.MochaEventHandler;
import mocha.game.player.Player;
import mocha.game.player.PlayerService;
import mocha.game.player.ServerPlayer;
import mocha.net.packet.MochaConnection;
import mocha.net.packet.PacketListener;
import mocha.server.ServerPacketHandlerFactory;
import mocha.server.ServerPacketResolver;
import mocha.server.event.ServerEventBus;
import mocha.shared.IdFactory;

@Log
@Component
public class LoginSuccessEventHandler implements MochaEventHandler<LoginSuccessEvent> {

  private ServerPacketHandlerFactory serverPacketResolverFactory;
  private ServerEventBus serverEventBus;
  private AccountService accountService;
  private PlayerService playerService;
  private IdFactory<Player> playerIdFactory;
  private Map<Integer, MochaConnection> mochaConnectionsByPlayerId;

  @Inject
  public LoginSuccessEventHandler(
      ServerPacketHandlerFactory serverPacketResolverFactory,
      ServerEventBus serverEventBus,
      AccountService accountService,
      PlayerService playerService, IdFactory<Player> playerIdFactory,
      Map<Integer, MochaConnection> mochaConnectionsByPlayerId
  ) {
    this.serverPacketResolverFactory = serverPacketResolverFactory;
    this.serverEventBus = serverEventBus;
    this.accountService = accountService;
    this.playerService = playerService;
    this.playerIdFactory = playerIdFactory;
    this.mochaConnectionsByPlayerId = mochaConnectionsByPlayerId;
  }

  @Subscribe
  @Override
  public void handle(LoginSuccessEvent loginSuccessEvent) {
    log.info(loginSuccessEvent.toString());
    AccountConnection accountConnection = loginSuccessEvent.getAccountConnection();
    wireUpPacketListening(accountConnection);
    addPlayerToGame(accountConnection);
  }

  private void wireUpPacketListening(AccountConnection accountConnection) {
    ServerPacketResolver serverPacketResolver = serverPacketResolverFactory.newServerPacketResolver(accountConnection);
    PacketListener packetListener = new PacketListener(serverEventBus, accountConnection.getMochaConnection(), serverPacketResolver);
    serverEventBus.postTaskEvent(serverPacketResolver);
    serverEventBus.postTaskEvent(packetListener);
  }

  private void addPlayerToGame(AccountConnection accountConnection) {
    Account account = accountConnection.getAccount();
    if (!accountService.getPlayer(account).isPresent()) {
      accountService.addPlayer(account, newPlayer());
    }
    Player player = account.getPlayer();
    mochaConnectionsByPlayerId.put(player.getId(), accountConnection.getMochaConnection());
    serverEventBus.postPlayerJoinedEvent(accountConnection.getMochaConnection(), player);
  }

  private ServerPlayer newPlayer() {
    // todo: replace cast with server side player service
    return (ServerPlayer) playerService.addPlayer(new ServerPlayer(playerIdFactory.newId()));
  }
}
