package mocha.game.world.chunk;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;

import mocha.game.world.tile.TileType;

@Entity
public class ServerChunk extends Chunk {

  public ServerChunk() {
  }

  ServerChunk(Integer id, TileType[] tiles) {
    super(id, tiles);
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
}
