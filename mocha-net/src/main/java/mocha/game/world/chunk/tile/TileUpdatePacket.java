package mocha.game.world.chunk.tile;

import mocha.game.world.Location;
import mocha.net.packet.AbstractPacket;
import mocha.net.packet.PacketType;

public class TileUpdatePacket extends AbstractPacket {

  public TileUpdatePacket() {
  }

  public TileUpdatePacket(Location location, TileType tileType) {
    addToData(location.getX());
    addToData(location.getY());
    addToData(tileType.getSymbol());
  }

  @Override
  public PacketType getType() {
    return PacketType.TILE_UPDATE;
  }

  private int getTileX() {
    return getDataAsInt(1);
  }

  private int getTileY() {
    return getDataAsInt(2);
  }

  public Location getLocation() {
    return new Location(getTileX(), getTileY());
  }

  public TileType getTileType() {
    return TileType.valueOf(getDataAsChar(3));
  }
}
