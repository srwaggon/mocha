package mocha.net.packet;

import java.util.Arrays;
import java.util.function.Consumer;

import mocha.game.world.chunk.Chunk;
import mocha.game.world.tile.Tile;

public class ChunkPacket extends AbstractPacket implements Packet {

  private String[] data;

  public ChunkPacket(Chunk chunk) {
    data = new String[5];
    data[0] = getType().name();
//    data[1] = String.valueOf(chunk.getId());
    data[2] = String.valueOf(Chunk.SIZE);
    data[3] = String.valueOf(Chunk.SIZE);
    data[4] = buildTileData(chunk);
  }

  private String buildTileData(Chunk chunk) {
    StringBuilder tilesBuilder = new StringBuilder();
    Consumer<Tile[]> collectRow = row -> Arrays.stream(row)
        .map(tile -> tile.getTileType().getSymbol())
        .forEach(tilesBuilder::append);
    Arrays.stream(chunk.getTiles()).forEach(collectRow);
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
