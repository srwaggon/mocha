package mocha.server;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.game.GameLogic;
import mocha.game.player.PlayerService;
import mocha.game.world.chunk.ChunkService;
import mocha.game.world.entity.EntitiesInChunkService;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.EntityService;
import mocha.net.packet.MochaConnection;
import mocha.server.event.ServerEventBus;

@Component
public class ServerPacketHandlerFactory {

  private GameLogic gameLogic;
  private ServerEventBus serverEventBus;
  private ChunkService chunkService;
  private EntitiesInChunkService entitiesInChunkService;
  private EntityService entityService;
  private PlayerService playerService;

  @Inject
  public ServerPacketHandlerFactory(
      GameLogic gameLogic,
      ServerEventBus serverEventBus,
      ChunkService chunkService,
      EntitiesInChunkService entitiesInChunkService,
      EntityService entityService,
      PlayerService playerService
  ) {
    this.gameLogic = gameLogic;
    this.serverEventBus = serverEventBus;
    this.chunkService = chunkService;
    this.entitiesInChunkService = entitiesInChunkService;
    this.entityService = entityService;
    this.playerService = playerService;
  }

  public ServerPacketResolver newServerPacketHandler(
      MochaConnection mochaConnection,
      Entity entity
  ) {
    return new ServerPacketResolver(
        mochaConnection,
        serverEventBus,
        chunkService,
        entitiesInChunkService,
        gameLogic,
        entityService,
        playerService,
        entity
    );
  }
}
