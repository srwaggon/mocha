package mocha.net.packet;

import mocha.game.world.Location;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.entity.Entity;
import mocha.net.packet.world.chunk.ChunkPacket;
import mocha.net.packet.world.chunk.RequestChunkPacket;
import mocha.net.packet.world.entity.EntityPacket;
import mocha.net.packet.world.entity.RequestEntitiesInChunkPacket;

public class PacketFactory {

  public ChunkPacket newChunkPacket(Location location, Chunk chunk) {
    return new ChunkPacket(location, chunk);
  }

  public RequestChunkPacket newChunkRequestPacket(Location location) {
    return new RequestChunkPacket(location);
  }

  public RequestEntitiesInChunkPacket newRequestEntitiesInChunkPacket(Location location) {
    return new RequestEntitiesInChunkPacket(location);
  }

  public EntityPacket newEntityPacket(Entity entity) {
    return new EntityPacket(entity);
  }
}
