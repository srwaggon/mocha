package mocha.net.packet;

import mocha.game.world.Location;
import mocha.game.world.chunk.Chunk;
import mocha.net.packet.world.chunk.ChunkPacket;
import mocha.net.packet.world.chunk.ChunkRequestPacket;

public class PacketFactory {

  public ChunkPacket newChunkPacket(Location location, Chunk chunk) {
    return new ChunkPacket(location, chunk);
  }

  public ChunkRequestPacket newChunkRequestPacket(Location location) {
    return new ChunkRequestPacket(location);
  }
}
