package mocha.net.packet;

import java.net.Socket;

import mocha.game.world.Location;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.movement.command.EntityMoveCommand;
import mocha.game.world.entity.prototype.EntityPrototype;
import mocha.game.world.item.Item;
import mocha.game.world.item.itemprototype.ItemPrototype;

public class MochaConnection extends PacketConnection {

  private PacketFactory packetFactory;

  public MochaConnection(
      Socket socket,
      PacketFactory packetFactory
  ) {
    super(socket);
    this.packetFactory = packetFactory;
  }

  public void sendLoginSuccessful(int playerId) {
    sendPacket(packetFactory.newLoginSuccessPacket(playerId));
  }

  public void requestChunkById(int chunkId) {
    sendPacket(packetFactory.newRequestChunkById(chunkId));
  }

  public void requestChunkAt(Location location) {
    sendPacket(packetFactory.newRequestChunkByLocationPacket(location));
  }

  public void requestEntitiesInChunk(Location location) {
    sendPacket(packetFactory.newRequestEntitiesInChunkPacket(location));
  }

  public void requestEntitiesByPlayerId(int playerId, int... entityIds) {
    sendPacket(packetFactory.newRequestEntitiesByPlayerIdPacket(playerId, entityIds));
  }

  public void sendChunkUpdate(Chunk chunk) {
    sendPacket(packetFactory.newChunkPacket(chunk));
  }

  public void sendTileUpdate(Chunk chunk, int x, int y) {
    sendPacket(packetFactory.newTileUpdatePacket(chunk, x, y));
  }

  public void sendEntityPrototypeUpdate(EntityPrototype entityPrototype) {
    sendPacket(packetFactory.newEntityPrototypeUpdatePacket(entityPrototype));
  }

  public void sendEntityUpdate(Entity entity) {
    sendPacket(packetFactory.newEntityPacket(entity));
  }

  public void sendEntityRemoved(Entity entity) {
    sendPacket(packetFactory.newEntityRemovedPacket(entity));
  }

  public void sendMoveCommand(EntityMoveCommand entityMoveCommand) {
    sendPacket(packetFactory.newMovePacket(entityMoveCommand));
  }

  public void sendItemPrototypeUpdate(ItemPrototype itemPrototype) {
    sendPacket(packetFactory.newItemPrototypeUpdatePacket(itemPrototype));
  }

  public void sendItemUpdate(Item item) {
    sendPacket(packetFactory.newItemUpdatePacket(item));
  }

}
