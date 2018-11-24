package mocha.game;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.game.world.chunk.ChunkService;
import mocha.game.world.entity.EntitiesInChunkService;
import mocha.game.world.entity.Entity;
import mocha.net.packet.MochaConnection;
import mocha.net.packet.PacketListener;
import mocha.server.ServerPacketHandler;
import mocha.server.event.ServerEventBus;
import mocha.shared.Repository;

@Component
class PlayerFactory {

  private ServerEventBus serverEventBus;
  private Repository<Player, Integer> playerRepository;
  private Repository<Entity, Integer> entityRepository;
  private ChunkService chunkService;
  private EntitiesInChunkService entitiesInChunkService;

  @Inject
  public PlayerFactory(
      ServerEventBus serverEventBus,
      Repository<Player, Integer> playerRepository,
      Repository<Entity, Integer> entityRepository,
      ChunkService chunkService,
      EntitiesInChunkService entitiesInChunkService
  ) {
    this.serverEventBus = serverEventBus;
    this.playerRepository = playerRepository;
    this.entityRepository = entityRepository;
    this.chunkService = chunkService;
    this.entitiesInChunkService = entitiesInChunkService;
  }

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
        chunkService,
        entitiesInChunkService
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
