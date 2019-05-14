package mocha.server;

import org.springframework.stereotype.Component;

import java.util.List;

import javax.inject.Inject;

import mocha.game.player.PlayerService;
import mocha.game.world.chunk.ChunkService;
import mocha.game.world.entity.EntitiesInChunkService;
import mocha.game.world.entity.EntityService;
import mocha.net.LoginRequestPacketHandlerFactory;
import mocha.net.packet.MochaConnection;
import mocha.net.packet.PacketHandler;
import mocha.server.event.ServerEventBus;

@Component
public class ServerPacketHandlerFactory {

  private ServerEventBus serverEventBus;
  private ChunkService chunkService;
  private EntitiesInChunkService entitiesInChunkService;
  private EntityService entityService;
  private PlayerService playerService;
  private LoginRequestPacketHandlerFactory loginRequestPacketHandlerFactory;
  private List<PacketHandler> packetHandlers;

  @Inject
  public ServerPacketHandlerFactory(
      ServerEventBus serverEventBus,
      ChunkService chunkService,
      EntitiesInChunkService entitiesInChunkService,
      EntityService entityService,
      PlayerService playerService,
      LoginRequestPacketHandlerFactory loginRequestPacketHandlerFactory,
      List<PacketHandler> packetHandlers
  ) {
    this.serverEventBus = serverEventBus;
    this.chunkService = chunkService;
    this.entitiesInChunkService = entitiesInChunkService;
    this.entityService = entityService;
    this.playerService = playerService;
    this.loginRequestPacketHandlerFactory = loginRequestPacketHandlerFactory;
    this.packetHandlers = packetHandlers;
  }

  public ServerPacketResolver newServerPacketHandler(
      MochaConnection mochaConnection,
      int playerId
  ) {
    packetHandlers.add(loginRequestPacketHandlerFactory.newLoginRequestPacketHandler(mochaConnection));
    return new ServerPacketResolver(
        mochaConnection,
        serverEventBus,
        chunkService,
        entitiesInChunkService,
        entityService,
        playerService,
        playerId,
        packetHandlers
    );
  }
}
