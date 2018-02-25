package mocha.net.packet;

import java.util.Optional;

import mocha.game.world.Location;
import mocha.game.world.World;
import mocha.game.world.chunk.Chunk;
import mocha.net.packet.world.chunk.ChunkPacket;
import mocha.net.packet.world.chunk.ChunkRequestPacket;

public class PacketFactory {

  private final World world;

  public PacketFactory(World world) {
    this.world = world;
  }

  public ChunkPacket newChunkPacket() {
    Location location = new Location(0, 0);
    Optional<Chunk> chunk = world.getChunkAt(location);
    return new ChunkPacket(chunk.get());
  }

  public ChunkRequestPacket newChunkRequestPacket() {
    Location location = new Location(0, 0);
    return new ChunkRequestPacket(location);
  }
}
