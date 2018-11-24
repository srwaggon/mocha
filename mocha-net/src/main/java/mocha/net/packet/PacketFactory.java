package mocha.net.packet;

import mocha.game.LoginSuccessPacket;
import mocha.game.world.Location;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.chunk.ChunkUpdatePacket;
import mocha.game.world.chunk.RequestChunkByLocationPacket;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.EntityUpdatePacket;
import mocha.game.world.entity.EntityRemovedPacket;
import mocha.game.world.entity.RequestEntitiesByPlayerIdPacket;
import mocha.game.world.entity.RequestEntitiesInChunkPacket;
import mocha.game.world.entity.RequestEntityByIdPacket;
import mocha.game.world.entity.movement.MovePacket;
import mocha.game.world.entity.movement.command.EntityMoveCommand;
import mocha.game.world.item.Item;
import mocha.game.world.item.ItemEntity;
import mocha.game.world.item.ItemEntityUpdatePacket;
import mocha.game.world.item.ItemPrototype;
import mocha.game.world.item.ItemPrototypeUpdatePacket;
import mocha.game.world.item.ItemUpdatePacket;
import mocha.game.world.tile.TileStringBuilder;
import mocha.game.world.tile.TileType;
import mocha.game.world.tile.TileUpdatePacket;

public class PacketFactory {

  private TileStringBuilder tileStringBuilder;

  public PacketFactory(TileStringBuilder tileStringBuilder) {
    this.tileStringBuilder = tileStringBuilder;
  }

  LoginSuccessPacket newLoginSuccessPacket(int playerId) {
    return new LoginSuccessPacket(playerId);
  }

  ChunkUpdatePacket newChunkPacket(Chunk chunk) {
    int chunkId = chunk.getId();
    TileType[] tiles = chunk.getTiles();
    String tileString = tileStringBuilder.build(tiles);
    return new ChunkUpdatePacket(chunkId, tileString);
  }

  RequestChunkByLocationPacket newChunkRequestPacket(Location location) {
    return new RequestChunkByLocationPacket(location);
  }

  RequestEntitiesInChunkPacket newRequestEntitiesInChunkPacket(Location location) {
    return new RequestEntitiesInChunkPacket(location);
  }

  EntityUpdatePacket newEntityPacket(Entity entity) {
    return new EntityUpdatePacket(entity);
  }

  public MovePacket newMovePacket(EntityMoveCommand entityMoveCommand) {
    return new MovePacket(entityMoveCommand);
  }

  EntityRemovedPacket newEntityRemovedPacket(Entity entity) {
    return new EntityRemovedPacket(entity.getId());
  }

  public RequestEntityByIdPacket newRequestEntityByIdPacket(int entityId) {
    return new RequestEntityByIdPacket(entityId);
  }

  RequestEntitiesByPlayerIdPacket newRequestEntitiesByPlayerIdPacket(int playerId, int... entityIds) {
    return new RequestEntitiesByPlayerIdPacket(playerId, entityIds);
  }

  ItemPrototypeUpdatePacket newItemPrototypeUpdatePacket(ItemPrototype itemPrototype) {
    return new ItemPrototypeUpdatePacket(itemPrototype);
  }

  ItemUpdatePacket newItemUpdatePacket(Item item) {
    return new ItemUpdatePacket(item);
  }

  ItemEntityUpdatePacket newItemEntityUpdatePacket(ItemEntity itemEntity) {
    return new ItemEntityUpdatePacket(itemEntity);
  }

  public Packet newTileUpdatePacket(Chunk chunk, int x, int y) {
    return new TileUpdatePacket(chunk, x, y);
  }
}
