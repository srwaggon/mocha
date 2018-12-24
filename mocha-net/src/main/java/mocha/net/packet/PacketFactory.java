package mocha.net.packet;

import mocha.game.LoginSuccessPacket;
import mocha.game.world.Location;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.chunk.ChunkUpdatePacket;
import mocha.game.world.chunk.RequestChunkByIdPacket;
import mocha.game.world.chunk.RequestChunkByLocationPacket;
import mocha.game.world.chunk.tile.TileStringBuilder;
import mocha.game.world.chunk.tile.TileType;
import mocha.game.world.chunk.tile.TileUpdatePacket;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.EntityRemovedPacket;
import mocha.game.world.entity.EntityUpdatePacket;
import mocha.game.world.entity.RequestEntitiesByPlayerIdPacket;
import mocha.game.world.entity.RequestEntitiesInChunkPacket;
import mocha.game.world.entity.RequestEntityByIdPacket;
import mocha.game.world.entity.movement.MovePacket;
import mocha.game.world.entity.movement.command.EntityMoveCommand;
import mocha.game.world.item.Item;
import mocha.game.world.item.ItemPrototypeUpdatePacket;
import mocha.game.world.item.ItemUpdatePacket;
import mocha.game.world.item.PickUpItemPacket;
import mocha.game.world.item.itemprototype.ItemPrototype;

public class PacketFactory {

  private TileStringBuilder tileStringBuilder;

  public PacketFactory(TileStringBuilder tileStringBuilder) {
    this.tileStringBuilder = tileStringBuilder;
  }

  public LoginSuccessPacket newLoginSuccessPacket(int playerId) {
    return new LoginSuccessPacket(playerId);
  }

  public ChunkUpdatePacket newChunkPacket(Chunk chunk) {
    int chunkId = chunk.getId();
    Location location = chunk.getLocation();
    int x = location.getX();
    int y = location.getY();
    TileType[] tiles = chunk.getTiles();
    String tileString = tileStringBuilder.build(tiles);
    return new ChunkUpdatePacket(chunkId, x, y, tileString);
  }

  public RequestChunkByIdPacket newRequestChunkById(int chunkId) {
    return new RequestChunkByIdPacket(chunkId);
  }

  public RequestChunkByLocationPacket newRequestChunkByLocationPacket(Location location) {
    return new RequestChunkByLocationPacket(location);
  }

  public RequestEntitiesInChunkPacket newRequestEntitiesInChunkPacket(Location location) {
    return new RequestEntitiesInChunkPacket(location);
  }

  public EntityUpdatePacket newEntityPacket(Entity entity) {
    return new EntityUpdatePacket(entity);
  }

  public MovePacket newMovePacket(EntityMoveCommand entityMoveCommand) {
    return new MovePacket(entityMoveCommand);
  }

  public EntityRemovedPacket newEntityRemovedPacket(Entity entity) {
    return new EntityRemovedPacket(entity.getId());
  }

  public RequestEntityByIdPacket newRequestEntityByIdPacket(int entityId) {
    return new RequestEntityByIdPacket(entityId);
  }

  public RequestEntitiesByPlayerIdPacket newRequestEntitiesByPlayerIdPacket(int playerId, int... entityIds) {
    return new RequestEntitiesByPlayerIdPacket(playerId, entityIds);
  }

  public ItemPrototypeUpdatePacket newItemPrototypeUpdatePacket(ItemPrototype itemPrototype) {
    return new ItemPrototypeUpdatePacket(itemPrototype);
  }

  public ItemUpdatePacket newItemUpdatePacket(Item item) {
    return new ItemUpdatePacket(item);
  }

  public TileUpdatePacket newTileUpdatePacket(Chunk chunk, int x, int y) {
    return new TileUpdatePacket(chunk, x, y);
  }

  public PickUpItemPacket newPickUpItemPacket(Entity entity) {
    return new PickUpItemPacket(entity.getId());
  }
}
