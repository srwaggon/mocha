package mocha.game.world.chunk;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import mocha.game.world.tile.TileType;

@Entity
public class ServerChunk extends Chunk {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Convert(converter = TileTypeArrayConverter.class)
  @Column(length = 256)
  private TileType[] tiles;

  public ServerChunk() {
  }

  public Integer getId() {
    return id;
  }

  public ServerChunk(Integer id, Chunk chunk) {
    super(chunk.tiles, chunk.entities);
    this.id = id;
    this.tiles = chunk.tiles;
  }

  public ServerChunk(Integer id, TileType[] tiles, Set<mocha.game.world.entity.Entity> entities) {
    super(tiles, entities);
    this.id = id;
    this.tiles = tiles;
  }

  @Override
  public TileType[] getTiles() {
    return tiles;
  }
}
