package mocha.net.packet;

import mocha.game.LoginRequestPacket;
import mocha.game.PlayerIdentityPacket;
import mocha.game.world.chunk.ChunkUpdatePacket;
import mocha.game.world.chunk.RequestChunkByIdPacket;
import mocha.game.world.chunk.RequestChunkByLocationPacket;
import mocha.game.world.chunk.tile.TileUpdatePacket;
import mocha.game.world.entity.EntityRemovedPacket;
import mocha.game.world.entity.EntityUpdatePacket;
import mocha.game.world.entity.RequestEntitiesByPlayerIdPacket;
import mocha.game.world.entity.RequestEntitiesInChunkPacket;
import mocha.game.world.entity.RequestEntityByIdPacket;
import mocha.game.world.entity.movement.MovePacket;
import mocha.game.world.entity.prototype.EntityPrototypeUpdatePacket;
import mocha.game.world.item.ItemPrototypeUpdatePacket;
import mocha.game.world.item.ItemUpdatePacket;
import mocha.game.world.item.PickUpItemPacket;
import mocha.net.LoginSuccessPacket;
import mocha.net.packet.message.GlobalMessagePacket;

public enum PacketType {

  LOGIN_REQUEST(LoginRequestPacket.class),
  LOGIN_SUCCESS(LoginSuccessPacket.class),
  PLAYER_IDENTITY(PlayerIdentityPacket.class),

  GLOBAL_MESSAGE(GlobalMessagePacket.class),

  REQUEST_CHUNK_BY_ID(RequestChunkByIdPacket.class),
  REQUEST_CHUNK_BY_LOCATION(RequestChunkByLocationPacket.class),
  CHUNK_UPDATE(ChunkUpdatePacket.class),
  TILE_UPDATE(TileUpdatePacket.class),

  REQUEST_ENTITY_BY_ID(RequestEntityByIdPacket.class),
  REQUEST_ENTITIES_BY_PLAYER_ID(RequestEntitiesByPlayerIdPacket.class),
  REQUEST_ENTITIES_IN_CHUNK(RequestEntitiesInChunkPacket.class),

  ENTITY_PROTOTYPE_UPDATE(EntityPrototypeUpdatePacket.class),
  ENTITY_UPDATE(EntityUpdatePacket.class),
  ENTITY_REMOVED(EntityRemovedPacket.class),
  MOVE(MovePacket.class),

  ITEM_PROTOTYPE_UPDATE(ItemPrototypeUpdatePacket.class),
  ITEM_UPDATE(ItemUpdatePacket.class),
  PICKUP_ITEM(PickUpItemPacket.class),
  ;

  public static final String SEPARATOR = " ";
  private final Class<? extends Packet> packetClass;


  PacketType(Class<? extends Packet> packetClass) {
    this.packetClass = packetClass;
  }

  public static Packet resolve(Packet packet) {
    try {
      Packet resolvedPacket = packet.getType().packetClass.newInstance();
      resolvedPacket.build(packet.getData());
      return resolvedPacket;
    } catch (InstantiationException | IllegalAccessException ignored) {
    }
    return packet;
  }
}
