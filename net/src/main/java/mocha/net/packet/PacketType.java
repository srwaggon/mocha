package mocha.net.packet;

import mocha.game.LoginSuccessPacket;
import mocha.game.world.chunk.ChunkPacket;
import mocha.game.world.chunk.RequestChunkPacket;
import mocha.game.world.entity.EntityPacket;
import mocha.game.world.entity.EntityRemovedPacket;
import mocha.game.world.entity.RequestEntitiesInChunkPacket;
import mocha.game.world.entity.RequestEntityByIdPacket;
import mocha.game.world.entity.movement.MovePacket;
import mocha.net.packet.message.GlobalMessagePacket;

public enum PacketType {

  LOGIN_SUCCESS(LoginSuccessPacket.class),

  GLOBAL_MESSAGE(GlobalMessagePacket.class),

  REQUEST_CHUNK(RequestChunkPacket.class),
  CHUNK(ChunkPacket.class),

  REQUEST_ENTITIES_IN_CHUNK(RequestEntitiesInChunkPacket.class),
  REQUEST_ENTITY_BY_ID(RequestEntityByIdPacket.class),
  ENTITY(EntityPacket.class),
  ENTITY_REMOVED(EntityRemovedPacket.class),
  MOVE(MovePacket.class);

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
