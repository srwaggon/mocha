package mocha.game;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.game.world.entity.Entity;
import mocha.net.packet.MochaConnection;
import mocha.net.packet.PacketListener;
import mocha.server.ServerPacketHandlerFactory;
import mocha.server.ServerPacketResolver;
import mocha.server.event.ServerEventBus;

@Component
class PlayerFactory {

  private ServerEventBus serverEventBus;
  private ServerPacketHandlerFactory serverPacketHandlerFactory;

  @Inject
  public PlayerFactory(
      ServerEventBus serverEventBus,
      @Lazy ServerPacketHandlerFactory serverPacketHandlerFactory
  ) {
    this.serverEventBus = serverEventBus;
    this.serverPacketHandlerFactory = serverPacketHandlerFactory;
  }

  NetworkPlayer newNetworkPlayer(MochaConnection mochaConnection, int playerId, Entity entity) {
    ServerPacketResolver serverPacketHandler = serverPacketHandlerFactory.newServerPacketHandler(mochaConnection, entity);
    PacketListener packetListener = newPacketListener(mochaConnection, playerId, serverPacketHandler);
    serverEventBus.postTaskEvent(packetListener);
    serverEventBus.postTaskEvent(serverPacketHandler);

    return NetworkPlayer.builder()
        .id(playerId)
        .entity(entity)
        .packetListener(packetListener)
        .serverPacketHandler(serverPacketHandler)
        .build();
  }

  private PacketListener newPacketListener(
      MochaConnection mochaConnection,
      int playerId,
      ServerPacketResolver serverPacketHandler
  ) {
    return new PacketListener(
        serverEventBus,
        mochaConnection,
        playerId,
        serverPacketHandler
    );
  }
}
