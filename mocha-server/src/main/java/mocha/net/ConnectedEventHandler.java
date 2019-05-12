package mocha.net;

import com.google.common.eventbus.Subscribe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

import javax.inject.Inject;

import mocha.game.player.ServerPlayer;
import mocha.game.event.MochaEventHandler;
import mocha.game.player.Player;
import mocha.game.player.PlayerService;
import mocha.net.event.ConnectedEvent;
import mocha.net.packet.MochaConnection;
import mocha.net.packet.PacketListener;
import mocha.server.ServerPacketHandlerFactory;
import mocha.server.ServerPacketResolver;
import mocha.server.event.ServerEventBus;
import mocha.shared.IdFactory;

@Component
public class ConnectedEventHandler implements MochaEventHandler<ConnectedEvent> {

  private Logger log = LoggerFactory.getLogger(ConnectedEventHandler.class);

  private IdFactory<Player> playerIdFactory;
  private ServerPacketHandlerFactory serverPacketHandlerFactory;
  private ServerEventBus serverEventBus;
  private Map<Integer, MochaConnection> mochaConnectionsByPlayerId;
  private PlayerService playerService;

  @Inject
  public ConnectedEventHandler(
      IdFactory<Player> playerIdFactory,
      ServerPacketHandlerFactory serverPacketHandlerFactory,
      ServerEventBus serverEventBus,
      Map<Integer, MochaConnection> mochaConnectionsByPlayerId,
      PlayerService playerService
  ) {
    this.playerIdFactory = playerIdFactory;
    this.serverPacketHandlerFactory = serverPacketHandlerFactory;
    this.serverEventBus = serverEventBus;
    this.mochaConnectionsByPlayerId = mochaConnectionsByPlayerId;
    this.playerService = playerService;
  }

  @Subscribe
  public void handle(ConnectedEvent connectedEvent) {
    log.info(connectedEvent.toString());
    MochaConnection playerConnection = connectedEvent.getMochaConnection();
    int playerId = playerIdFactory.newId();

    ServerPacketResolver serverPacketHandler = serverPacketHandlerFactory.newServerPacketHandler(playerConnection, playerId);
    PacketListener packetListener = new PacketListener(serverEventBus, playerConnection, playerId, serverPacketHandler);

    playerService.addPlayer(new ServerPlayer(playerId));
    mochaConnectionsByPlayerId.put(playerId, playerConnection);

    serverEventBus.postTaskEvent(packetListener);
    serverEventBus.postTaskEvent(serverPacketHandler);
  }

}