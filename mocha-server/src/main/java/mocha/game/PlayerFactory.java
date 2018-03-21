package mocha.game;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.game.world.entity.Entity;
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

    PacketListener packetListener = packetListenerFactory.newPacketListener(mochaConnection, playerId);
    eventBus.postTaskEvent(packetListener);

    ServerPacketHandler serverPacketHandler = serverPacketHandlerFactory.newServerPacketHandler(mochaConnection, playerId);
    eventBus.postTaskEvent(serverPacketHandler);
    eventBus.register(serverPacketHandler);

    Entity entity = entityFactory.createSlider();

    return NetworkPlayer.builder()
        .id(playerId)
        .entity(entity)
        .packetListener(packetListener)
        .serverPacketHandler(serverPacketHandler)
        .build();
  }
}
