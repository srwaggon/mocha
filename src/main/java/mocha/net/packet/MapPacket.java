package mocha.net.packet;

import java.util.Arrays;
import java.util.function.Consumer;

import mocha.game.world.map.Map;
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
    Consumer<Tile[]> collectRow = row -> Arrays.stream(row)
        .map(tile -> tile.getTileType().getSymbol())
        .forEach(tilesBuilder::append);
    Arrays.stream(map.getTiles()).forEach(collectRow);
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
