package mocha.game;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.game.world.chunk.Chunk;
import mocha.game.world.chunk.ChunkService;
import mocha.game.world.entity.Entity;
import mocha.net.packet.MochaConnection;
import mocha.net.packet.PacketListener;
import mocha.server.ServerPacketHandler;
import mocha.server.event.ServerEventBus;
import mocha.shared.Repository;

@Component
class PlayerFactory {

  @Inject
  private ServerEventBus serverEventBus;

  @Inject
  private Repository<Chunk, Integer> chunkRepository;

  @Inject
  private Repository<Player, Integer> playerRepository;

  @Inject
  private Repository<Entity, Integer> entityRepository;

  @Inject
  private ChunkService chunkService;

  NetworkPlayer newNetworkPlayer(MochaConnection mochaConnection, int playerId, Entity entity) {
    ServerPacketHandler serverPacketHandler = newServerPacketHandler(mochaConnection, playerId, chunkService);
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

  private ServerPacketHandler newServerPacketHandler(
      MochaConnection mochaConnection,
      int playerId,
      ChunkService chunkService) {
    return new ServerPacketHandler(
        mochaConnection,
        serverEventBus,
        playerId,
        entityRepository,
        playerRepository,
        chunkService
    );
  }

  private PacketListener newPacketListener(
      MochaConnection mochaConnection,
      int playerId,
      ServerPacketHandler serverPacketHandler
  ) {
    return new PacketListener(
        serverEventBus,
        mochaConnection,
        playerId,
        serverPacketHandler
    );
  }
}
