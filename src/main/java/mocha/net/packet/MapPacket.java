package mocha.net.packet;

import mocha.game.world.Map;

public class MapPacket extends AbstractPacket implements Packet {

  private String[] data;

  public MapPacket(Map map) {
    data = new String[4];
    data[0] = getType().name();
    data[1] = String.valueOf(map.getId());
    data[2] = String.valueOf(map.getColumnCount());
    data[3] = String.valueOf(map.getRowCount());
  }

  @Override
  public void build(String[] data) {
    this.data = data;
  }

  @Override
  public PacketType getType() {
    return PacketType.MAP;
  }

  @Override
  public String[] getData() {
    return data;
  }

}
