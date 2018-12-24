package mocha.server;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.game.GameLogic;
import mocha.game.player.Player;
import mocha.game.world.chunk.ChunkService;
import mocha.game.world.entity.EntitiesInChunkService;
import mocha.game.world.entity.Entity;
import mocha.net.packet.MochaConnection;
import mocha.server.event.ServerEventBus;
import mocha.shared.Repository;

@Component
public class ServerPacketHandlerFactory {

  private GameLogic gameLogic;
  private ServerEventBus serverEventBus;
  private Repository<Player, Integer> playerRepository;
  private Repository<Entity, Integer> entityRepository;
  private ChunkService chunkService;
  private EntitiesInChunkService entitiesInChunkService;

  @Inject
  public ServerPacketHandlerFactory(
      GameLogic gameLogic,
      ServerEventBus serverEventBus,
      Repository<Player, Integer> playerRepository,
      Repository<Entity, Integer> entityRepository,
      ChunkService chunkService,
      EntitiesInChunkService entitiesInChunkService) {
    this.gameLogic = gameLogic;
    this.serverEventBus = serverEventBus;
    this.playerRepository = playerRepository;
    this.entityRepository = entityRepository;
    this.chunkService = chunkService;
    this.entitiesInChunkService = entitiesInChunkService;
  }

  public ServerPacketHandler newServerPacketHandler(
      MochaConnection mochaConnection,
      int playerId
  ) {
    return new ServerPacketHandler(
        mochaConnection,
        serverEventBus,
        playerId,
        entityRepository,
        playerRepository,
        chunkService,
        entitiesInChunkService,
        gameLogic
    );
  }
}
