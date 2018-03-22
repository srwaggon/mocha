package mocha.net.packet;

import mocha.game.world.Location;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.chunk.ChunkPacket;
import mocha.game.world.chunk.RequestChunkPacket;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.EntityPacket;
import mocha.game.world.entity.EntityRemovedPacket;
import mocha.game.world.entity.RequestEntitiesInChunkPacket;
import mocha.game.world.entity.movement.MovePacket;
import mocha.game.world.entity.movement.command.EntityMoveCommand;

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

  public MovePacket newMovePacket(EntityMoveCommand entityMoveCommand) {
    return new MovePacket(entityMoveCommand);
  }

  public EntityRemovedPacket newEntityRemovedPacket(Entity entity) {
    return new EntityRemovedPacket(entity.getId());
  }
}
