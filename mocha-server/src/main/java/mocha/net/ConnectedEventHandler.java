package mocha.net;

import com.google.common.eventbus.Subscribe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import mocha.account.CreateAccountRequestPacket;
import mocha.game.LoginRequestPacket;
import mocha.game.event.MochaEventHandler;
import mocha.game.player.Player;
import mocha.game.player.PlayerService;
import mocha.game.player.ServerPlayer;
import mocha.net.event.ConnectedEvent;
import mocha.net.exception.DisconnectedException;
import mocha.net.packet.MochaConnection;
import mocha.net.packet.Packet;
import mocha.net.packet.PacketListener;
import mocha.net.packet.PacketType;
import mocha.server.ServerPacketHandlerFactory;
import mocha.server.ServerPacketResolver;
import mocha.server.event.ServerEventBus;
import mocha.shared.IdFactory;

import static mocha.net.packet.PacketType.CREATE_ACCOUNT_REQUEST;
import static mocha.net.packet.PacketType.LOGIN_REQUEST;

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

    try {
      Packet packet = playerConnection.readPacket();
      Optional<Packet> inflatedPacketMaybe = getInflatedPacket(packet);

      if (inflatedPacketMaybe.isPresent()) {

        int playerId = playerIdFactory.newId();
        playerService.addPlayer(new ServerPlayer(playerId));
        mochaConnectionsByPlayerId.put(playerId, playerConnection);

        ServerPacketResolver serverPacketHandler = serverPacketHandlerFactory.newServerPacketHandler(playerConnection, playerId);
        serverEventBus.postTaskEvent(serverPacketHandler);

        serverPacketHandler.resolve(inflatedPacketMaybe.get());

        PacketListener packetListener = new PacketListener(serverEventBus, playerConnection, playerId, serverPacketHandler);
        serverEventBus.postTaskEvent(packetListener);
      } else {
        playerConnection.disconnect();
      }
    } catch (DisconnectedException e) {
      e.printStackTrace();
    }
  }

  private Optional<Packet> getInflatedPacket(Packet packet) {
    PacketType type = packet.getType();
    String data = packet.getData();

    if (CREATE_ACCOUNT_REQUEST.equals(type)) {
      CreateAccountRequestPacket createAccountRequestPacket = new CreateAccountRequestPacket();
      createAccountRequestPacket.build(data);
      return Optional.of(createAccountRequestPacket);
    } else if (LOGIN_REQUEST.equals(type)) {
      LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
      loginRequestPacket.build(data);
      return Optional.of(loginRequestPacket);
    } else {
      return Optional.empty();
    }
  }

}