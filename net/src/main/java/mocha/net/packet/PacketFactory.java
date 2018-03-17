package mocha.net.packet;

import mocha.game.world.Location;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.movement.command.EntityMoveCommand;
import mocha.net.packet.world.chunk.ChunkPacket;
import mocha.net.packet.world.chunk.RequestChunkPacket;
import mocha.net.packet.world.entity.EntityPacket;
import mocha.net.packet.world.entity.RequestEntitiesInChunkPacket;
import mocha.net.packet.world.entity.movement.action.MovePacket;

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

  public EntityPacket entityPacket(Entity entity) {
    return new EntityPacket(entity);
  }

  public MovePacket movePacket(EntityMoveCommand entityMoveCommand) {
    return new MovePacket(entityMoveCommand);
  }
}
