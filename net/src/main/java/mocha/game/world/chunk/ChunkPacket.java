package mocha.game.world.chunk;

import mocha.game.world.Location;
import mocha.net.packet.AbstractPacket;
import mocha.net.packet.PacketType;

public class ChunkPacket extends AbstractPacket {

  private static final int ID_INDEX = 1;
  private static final int x_INDEX = 2;
  private static final int Y_INDEX = 3;
  private static final int TILE_DATA_INDEX = 4;

  public ChunkPacket() {
  }

  public ChunkPacket(int chunkId, Location location, String tileData) {
    data = new String[5];
    data[0] = getType().name();
    data[ID_INDEX] = "" + chunkId;
    data[x_INDEX] = "" + location.getX();
    data[Y_INDEX] = "" + location.getY();
    data[TILE_DATA_INDEX] = tileData;
  }

  @Override
  public PacketType getType() {
    return PacketType.CHUNK;
  }

  public int getChunkId() {
    return getDataAsInt(ID_INDEX);
  }

  public String getTilesString() {
    return this.data[TILE_DATA_INDEX];
  }

  public Location getLocation() {
    return new Location(getX(), getY());
  }

  private int getX() {
    return getDataAsInt(ChunkPacket.x_INDEX);
  }

  private int getY() {
    return getDataAsInt(ChunkPacket.Y_INDEX);
  }
}
