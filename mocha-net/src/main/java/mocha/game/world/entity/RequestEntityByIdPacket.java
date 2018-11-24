package mocha.game.world.entity;

import mocha.net.packet.PacketType;

public class RequestEntityByIdPacket extends AbstractEntityPacket {

  public RequestEntityByIdPacket() {
  }

  public RequestEntityByIdPacket(int entityId) {
    super(entityId);
  }

  @Override
  public PacketType getType() {
    return PacketType.REQUEST_ENTITY_BY_ID;
  }
}
