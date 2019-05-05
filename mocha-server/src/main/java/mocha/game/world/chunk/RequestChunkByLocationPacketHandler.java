package mocha.game.world.chunk;

import com.google.common.eventbus.Subscribe;

import mocha.game.world.Location;
import mocha.net.packet.MochaConnection;
import mocha.net.packet.PacketHandler;

public class RequestChunkByLocationPacketHandler implements PacketHandler<RequestChunkByLocationPacket> {

  private MochaConnection mochaConnection;
  private ChunkService chunkService;

  public RequestChunkByLocationPacketHandler(
      MochaConnection mochaConnection,
      ChunkService chunkService
  ) {
    this.mochaConnection = mochaConnection;
    this.chunkService = chunkService;
  }

  @Subscribe
  public void handle(RequestChunkByLocationPacket requestChunkByLocationPacket) {
    Location location = requestChunkByLocationPacket.getLocation();
    mochaConnection.sendChunkUpdate(chunkService.getOrCreateChunkAt(location));
  }
}