package mocha.net.packet;

import java.util.Arrays;
import java.util.stream.Collectors;

import mocha.game.world.chunk.Chunk;
import mocha.game.world.tile.TileType;

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
    return Arrays.stream(chunk.getTiles())
        .map(TileType::getSymbol)
        .collect(Collectors.joining());
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
