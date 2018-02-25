package mocha.net.packet.world.entity;

import mocha.net.packet.AbstractPacket;
import mocha.net.packet.PacketType;

public class RequestEntityByIdPacket extends AbstractPacket {
  private String[] data = new String[2];

  public RequestEntityByIdPacket() {

  }

  public RequestEntityByIdPacket(int id) {
    this.data[0] = getType().name();
    this.data[1] = "" + id;
  }

  @Override
  public void build(String[] data) {
    this.data[0] = getType().name();
    this.data[1] = data[1];
  }

  @Override
  public PacketType getType() {
    return PacketType.REQUEST_ENTITY_BY_ID;
  }

  @Override
  public String[] getData() {
    return data;
  }

  public int getId() {
    return Integer.parseInt(data[1]);
  }
}
