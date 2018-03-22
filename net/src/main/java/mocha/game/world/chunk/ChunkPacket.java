package mocha.game.world.chunk;

import mocha.game.world.Location;
import mocha.net.packet.AbstractPacket;
import mocha.net.packet.PacketType;

public class ChunkPacket extends AbstractPacket {

  public ChunkPacket() {
  }

  public ChunkPacket(Location location, Chunk chunk) {
    data = new String[4];
    data[0] = getType().name();
    data[1] = "" + location.getX();
    data[2] = "" + location.getY();
    data[3] = chunk.buildTileData();
  }

  @Override
  public PacketType getType() {
    return PacketType.CHUNK;
  }

  public ChunkDescription getChunkDescription() {
    return new ChunkDescription(this.data[3]);
  }

  public Location getLocation() {
    return new Location(getX(), getY());
  }

  private int getX() {
    return getDataAsInt(1);
  }

  private int getY() {
    return getDataAsInt(2);
  }
}
