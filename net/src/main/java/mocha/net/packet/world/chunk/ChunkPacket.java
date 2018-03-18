package mocha.net.packet.world.chunk;

import mocha.game.world.Location;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.chunk.ChunkDescription;
import mocha.net.packet.AbstractPacket;
import mocha.net.packet.Packet;
import mocha.net.packet.PacketType;

public class ChunkPacket extends AbstractPacket implements Packet {

  private String[] data = new String[4];

  public ChunkPacket() {
  }

  public ChunkPacket(Location location, Chunk chunk) {
    data[0] = getType().name();
    data[1] = "" + location.getX();
    data[2] = "" + location.getY();
    data[3] = chunk.buildTileData();
  }

  @Override
  public void build(String[] data) {
    this.data[0] = getType().name();
    this.data[1] = data[1];
    this.data[2] = data[2];
    this.data[3] = data[3];
  }

  @Override
  public PacketType getType() {
    return PacketType.CHUNK;
  }

  @Override
  public String[] getData() {
    return data;
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
