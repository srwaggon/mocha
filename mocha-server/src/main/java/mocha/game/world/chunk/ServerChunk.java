package mocha.game.world.chunk;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;

import mocha.game.world.Location;
import mocha.game.world.chunk.tile.TileType;

@Entity
public class ServerChunk extends Chunk {

  public ServerChunk() {
  }

  ServerChunk(Integer id, Location location, TileType[] tiles) {
    super(id, location, tiles);
  }

  @Id
  public Integer getId() {
    return super.getId();
  }

  public void setId(Integer id) {
    super.setId(id);
  }

  @Convert(converter = TileTypeArrayConverter.class)
  @Column(length = 256)
  @Override
  public TileType[] getTiles() {
    return super.getTiles();
  }

  @Override
  public void setTiles(TileType[] tiles) {
    super.setTiles(tiles);
  }

  @Column
  public int getX() {
    return getLocation().getX();
  }

  public void setX(int x) {
    getLocation().setX(x);
  }

  @Column
  public int getY() {
    return getLocation().getY();
  }

  public void setY(int y) {
    getLocation().setY(y);
  }
}
