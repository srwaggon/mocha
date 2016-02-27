package mocha.net.packet;

import mocha.game.world.Map;
import mocha.game.world.tile.Tile;

public class MapPacket extends AbstractPacket implements Packet {

  private String[] data;

  public MapPacket(Map map) {
    data = new String[5];
    data[0] = getType().name();
    data[1] = String.valueOf(map.getId());
    data[2] = String.valueOf(map.getColumnCount());
    data[3] = String.valueOf(map.getRowCount());
    data[4] = buildTileData(map);
  }

  private String buildTileData(Map map) {
    StringBuilder tilesBuilder = new StringBuilder();
    for (Tile[] row : map.getTiles()) {
      for (Tile tile : row) {
        tilesBuilder.append(tile.getSymbol());
      }
    }
    return tilesBuilder.toString();
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
