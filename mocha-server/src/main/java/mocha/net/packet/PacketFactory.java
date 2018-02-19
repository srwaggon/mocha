package mocha.net.packet;

import org.springframework.stereotype.Component;

import java.util.Optional;

import javax.inject.Inject;

import mocha.MochaWorld;
import mocha.game.world.Location;
import mocha.game.world.chunk.Chunk;

@Component
public class PacketFactory {

  @Inject
  private MochaWorld mochaWorld;

  public ChunkPacket newChunkPacket () {
    Location location = new Location(0, 0);
    Optional<Chunk> chunk = mochaWorld.getChunk(location);
    return new ChunkPacket(chunk.get());
  }
}
