package mocha.server;

import com.google.common.eventbus.Subscribe;

import java.util.Optional;

import mocha.game.Game;
import mocha.game.world.Location;
import mocha.game.world.chunk.Chunk;
import mocha.net.MochaConnection;
import mocha.net.PacketHandler;
import mocha.net.packet.Packet;
import mocha.net.packet.PacketType;
import mocha.net.packet.world.chunk.ChunkPacket;

public class ClientWorker implements PacketHandler {
  private MochaConnection mochaConnection;
  private Game game;

  ClientWorker(MochaConnection mochaConnection, Game game) {
    this.mochaConnection = mochaConnection;
    this.game = game;
  }

  @Override
  @Subscribe
  public void handlePacket(Packet packet) {
    System.out.println(packet.construct());
    if (packet.getType().equals(PacketType.CHUNK_REQUEST)) {

      String[] data = packet.getData();
      int x = Integer.parseInt(data[1]);
      int y = Integer.parseInt(data[2]);
      Location location = new Location(x, y);
      Optional<Chunk> chunkAt = game.getWorld().getChunkAt(location);
      chunkAt.ifPresent((chunk) ->
          mochaConnection.sendPacket(new ChunkPacket(chunk)));
    }
  }
}
