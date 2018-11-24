package mocha.game.world.chunk;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import mocha.game.world.tile.TileType;

@Entity
public class ServerChunk extends Chunk {

  public ServerChunk() {
  }

  public ServerChunk(Integer id, TileType[] tiles) {
    super(id, tiles);
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  @Convert(converter = TileTypeArrayConverter.class)
  @Column(length = 256)
  @Override
  public TileType[] getTiles() {
    return tiles;
  }

  @Override
  public void setTiles(TileType[] tiles) {
    this.tiles = tiles;
  }
}
