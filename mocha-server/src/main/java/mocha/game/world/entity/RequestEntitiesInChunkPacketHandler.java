package mocha.game.world.entity;

import com.google.common.eventbus.Subscribe;

import mocha.game.world.Location;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.chunk.ChunkService;
import mocha.net.packet.MochaConnection;
import mocha.net.packet.PacketHandler;

public class RequestEntitiesInChunkPacketHandler implements PacketHandler<RequestEntitiesInChunkPacket> {

  private MochaConnection mochaConnection;
  private ChunkService chunkService;
  private EntityService entityService;

  public RequestEntitiesInChunkPacketHandler(MochaConnection mochaConnection, ChunkService chunkService, EntityService entityService) {
    this.mochaConnection = mochaConnection;
    this.chunkService = chunkService;
    this.entityService = entityService;
  }

  @Subscribe
  public void handle(RequestEntitiesInChunkPacket requestEntitiesInChunkPacket) {
    Location location = requestEntitiesInChunkPacket.getLocation();
    Chunk chunk = chunkService.getOrCreateChunkAt(location);
    entityService.getEntitiesInChunk(chunk)
        .forEach(mochaConnection::sendEntityUpdate);
  }
}