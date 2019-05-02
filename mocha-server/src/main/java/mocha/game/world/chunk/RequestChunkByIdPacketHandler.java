package mocha.game.world.chunk;

import com.google.common.eventbus.Subscribe;

import mocha.net.packet.MochaConnection;
import mocha.net.packet.PacketHandler;

public class RequestChunkByIdPacketHandler implements PacketHandler {
  private MochaConnection mochaConnection;
  private ChunkService chunkService;

  public RequestChunkByIdPacketHandler(
      MochaConnection mochaConnection,
      ChunkService chunkService
  ) {
    this.mochaConnection = mochaConnection;
    this.chunkService = chunkService;
  }

  @Subscribe
  public void handle(RequestChunkByIdPacket requestChunkByIdPacket) {
    int chunkId = requestChunkByIdPacket.getChunkId();
    mochaConnection.sendChunkUpdate(chunkService.getOrCreateChunkById(chunkId));
  }
}