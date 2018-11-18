package mocha.game;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.game.world.entity.EntityFactory;
import mocha.net.packet.MochaConnection;
import mocha.net.packet.PacketListener;
import mocha.server.ServerPacketHandler;
import mocha.server.event.ServerEventBus;
import mocha.shared.IdFactory;

@Component
public class PlayerFactory {

  @Inject
  private ServerEventBus serverEventBus;

  @Inject
  private IdFactory<Player> playerIdFactory;

  @Inject
  private EntityFactory entityFactory;

  @Inject
  private Game game;

  NetworkPlayer newNetworkPlayer(MochaConnection mochaConnection) {
    int playerId = playerIdFactory.newId();

    ServerPacketHandler serverPacketHandler = new ServerPacketHandler(mochaConnection, game, serverEventBus, playerId);
    PacketListener packetListener = new PacketListener(serverEventBus, mochaConnection, playerId, serverPacketHandler);

    serverEventBus.postTaskEvent(packetListener);
    serverEventBus.postTaskEvent(serverPacketHandler);

    return NetworkPlayer.builder()
        .id(playerId)
        .entity(entityFactory.newSlider())
        .packetListener(packetListener)
        .serverPacketHandler(serverPacketHandler)
        .build();
  }
}
