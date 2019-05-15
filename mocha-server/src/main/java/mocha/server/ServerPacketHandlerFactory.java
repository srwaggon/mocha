package mocha.server;

import org.springframework.stereotype.Component;

import java.util.List;

import javax.inject.Inject;

import mocha.account.AccountConnection;
import mocha.account.AccountService;
import mocha.game.player.PlayerService;
import mocha.game.world.chunk.ChunkService;
import mocha.game.world.entity.EntitiesInChunkService;
import mocha.game.world.entity.EntityService;
import mocha.net.packet.PacketHandler;
import mocha.server.event.ServerEventBus;

@Component
public class ServerPacketHandlerFactory {

  private ServerEventBus serverEventBus;
  private ChunkService chunkService;
  private EntitiesInChunkService entitiesInChunkService;
  private EntityService entityService;
  private PlayerService playerService;
  private List<PacketHandler> packetHandlers;
  private AccountService accountService;

  @Inject
  public ServerPacketHandlerFactory(
      ServerEventBus serverEventBus,
      ChunkService chunkService,
      EntitiesInChunkService entitiesInChunkService,
      EntityService entityService,
      PlayerService playerService,
      List<PacketHandler> packetHandlers,
      AccountService accountService) {
    this.serverEventBus = serverEventBus;
    this.chunkService = chunkService;
    this.entitiesInChunkService = entitiesInChunkService;
    this.entityService = entityService;
    this.playerService = playerService;
    this.packetHandlers = packetHandlers;
    this.accountService = accountService;
  }

  public ServerPacketResolver newServerPacketHandler(
      AccountConnection accountConnection
  ) {
    return new ServerPacketResolver(
        serverEventBus,
        chunkService,
        entitiesInChunkService,
        entityService,
        playerService,
        packetHandlers,
        accountService,
        accountConnection
    );
  }
}
