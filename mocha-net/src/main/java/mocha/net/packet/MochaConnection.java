package mocha.net.packet;

import java.net.Socket;

import mocha.account.RegisterAccountRequestPacket;
import mocha.game.LoginRequestPacket;
import mocha.game.world.Location;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.chunk.ChunkUpdatePacket;
import mocha.game.world.chunk.RequestChunkByIdPacket;
import mocha.game.world.chunk.RequestChunkByLocationPacket;
import mocha.game.world.chunk.tile.TileType;
import mocha.game.world.chunk.tile.TileUpdatePacket;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.EntityRemovedPacket;
import mocha.game.world.entity.EntityUpdatePacket;
import mocha.game.world.entity.RequestEntitiesByPlayerIdPacket;
import mocha.game.world.entity.RequestEntitiesInChunkPacket;
import mocha.game.world.entity.movement.MovePacket;
import mocha.game.world.entity.movement.command.EntityMoveCommand;
import mocha.game.world.entity.prototype.EntityPrototype;
import mocha.game.world.entity.prototype.EntityPrototypeUpdatePacket;
import mocha.game.world.item.Item;
import mocha.game.world.item.ItemPrototypeUpdatePacket;
import mocha.game.world.item.ItemUpdatePacket;
import mocha.game.world.item.itemprototype.ItemPrototype;
import mocha.net.LoginSuccessPacket;

public class MochaConnection extends PacketConnection {

  public MochaConnection(Socket socket) {
    super(socket);
  }

  public void sendLoginRequest(String accountName) {
    send(new LoginRequestPacket(accountName));
  }

  public void sendLoginSuccessful(int playerId) {
    send(new LoginSuccessPacket(playerId));
  }

  public void requestChunkById(int chunkId) {
    send(new RequestChunkByIdPacket(chunkId));
  }

  public void requestChunkAt(Location location) {
    send(new RequestChunkByLocationPacket(location));
  }

  public void requestEntitiesInChunk(Location location) {
    send(new RequestEntitiesInChunkPacket(location));
  }

  public void requestEntitiesByPlayerId(int playerId, int... entityIds) {
    send(new RequestEntitiesByPlayerIdPacket(playerId, entityIds));
  }

  public void sendChunkUpdate(Chunk chunk) {
    send(new ChunkUpdatePacket(chunk));
  }

  public void sendTileUpdate(Location location, TileType tileType) {
    send(new TileUpdatePacket(location, tileType));
  }

  public void sendEntityPrototypeUpdate(EntityPrototype entityPrototype) {
    send(new EntityPrototypeUpdatePacket(entityPrototype));
  }

  public void sendEntityUpdate(Entity entity) {
    send(new EntityUpdatePacket(entity));
  }

  public void sendEntityRemoved(Entity entity) {
    send(new EntityRemovedPacket(entity.getId()));
  }

  public void sendMoveCommand(EntityMoveCommand entityMoveCommand) {
    send(new MovePacket(entityMoveCommand));
  }

  public void sendItemPrototypeUpdate(ItemPrototype itemPrototype) {
    send(new ItemPrototypeUpdatePacket(itemPrototype));
  }

  public void sendItemUpdate(Item item) {
    send(new ItemUpdatePacket(item));
  }

  public void sendRegisterAccountRequest(String accountName, String emailAddress) {
    send(new RegisterAccountRequestPacket(accountName, emailAddress));
  }
}
