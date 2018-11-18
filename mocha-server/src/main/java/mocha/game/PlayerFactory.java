package mocha.game;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.game.world.entity.EntityFactory;
import mocha.net.packet.MochaConnection;
import mocha.net.packet.PacketListener;
import mocha.net.packet.PacketListenerFactory;
import mocha.server.ServerPacketHandler;
import mocha.server.ServerPacketHandlerFactory;
import mocha.server.event.ServerEventBus;
import mocha.shared.IdFactory;

@Component
public class PlayerFactory {

  @Inject
  private ServerEventBus eventBus;

  @Inject
  private IdFactory<Player> playerIdFactory;

  @Inject
  private ServerPacketHandlerFactory serverPacketHandlerFactory;

  @Inject
  private PacketListenerFactory packetListenerFactory;

  @Inject
  private EntityFactory entityFactory;

  public NetworkPlayer newNetworkPlayer(MochaConnection mochaConnection) {
    int playerId = playerIdFactory.newId();

    ServerPacketHandler serverPacketHandler = serverPacketHandlerFactory.newServerPacketHandler(mochaConnection, playerId);
    PacketListener packetListener = packetListenerFactory.newPacketListener(mochaConnection, playerId, serverPacketHandler);

    eventBus.postTaskEvent(packetListener);
    eventBus.postTaskEvent(serverPacketHandler);

    return NetworkPlayer.builder()
        .id(playerId)
        .entity(entityFactory.newSlider())
        .packetListener(packetListener)
        .serverPacketHandler(serverPacketHandler)
        .build();
  }
}
