package mocha.game.world.chunk;

import java.util.Arrays;
import java.util.stream.Collectors;

import mocha.game.world.chunk.tile.TileType;
import mocha.net.packet.AbstractPacket;
import mocha.net.packet.PacketType;

public class ChunkUpdatePacket extends AbstractPacket {

  public ChunkUpdatePacket() {
  }

  public ChunkUpdatePacket(Chunk chunk) {
    addToData(chunk.getId());
    addToData(chunk.getLocation().getX());
    addToData(chunk.getLocation().getY());
    addToData(buildTileString(chunk));
  }

  private String buildTileString(Chunk chunk) {
    return Arrays.stream(chunk.getTiles())
        .map(TileType::getSymbol)
        .map(String::valueOf)
        .collect(Collectors.joining());
  }

  @Override
  public PacketType getType() {
    return PacketType.CHUNK_UPDATE;
  }

  public int getChunkId() {
    return getDataAsInt(1);
  }

  public int getX() {
    return getDataAsInt(2);
  }

  public int getY() {
    return getDataAsInt(3);
  }

  public String getTilesString() {
    return this.getSplitData()[4];
  }

}
