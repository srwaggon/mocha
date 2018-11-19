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
import mocha.game.world.tile.TileStringBuilder;
import mocha.game.world.tile.TileType;

public class PacketFactory {

  private TileStringBuilder tileStringBuilder;

  public PacketFactory(TileStringBuilder tileStringBuilder) {
    this.tileStringBuilder = tileStringBuilder;
  }

  public ChunkPacket newChunkPacket(Location location, Chunk chunk) {
    TileType[] tiles = chunk.getTiles();
    String tileString = tileStringBuilder.build(tiles);
    return new ChunkPacket(location, tileString);
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
