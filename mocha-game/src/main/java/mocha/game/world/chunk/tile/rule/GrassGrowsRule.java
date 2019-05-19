package mocha.game.world.chunk.tile.rule;

import java.util.function.Predicate;

import mocha.game.rule.GameRule;
import mocha.game.world.Location;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.chunk.ChunkService;
import mocha.game.world.chunk.tile.TileType;
import mocha.shared.Repository;

import static mocha.game.world.chunk.tile.TileType.DIRT;
import static mocha.game.world.chunk.tile.TileType.GRASS;
import static mocha.game.world.chunk.tile.TileType.SIZE;

public class GrassGrowsRule implements GameRule {

  private Repository<Chunk, Integer> chunkRepository;
  private ChunkService chunkService;

  public GrassGrowsRule(
      Repository<Chunk, Integer> chunkRepository,
      ChunkService chunkService
  ) {
    this.chunkRepository = chunkRepository;
    this.chunkService = chunkService;
  }

  @Override
  public void apply() {
    chunkRepository.findAll().forEach(this::tryGrowGrass);
  }

  private void tryGrowGrass(Chunk chunk) {
    for (int y = 0; y < Chunk.SIZE; y++) {
      for (int x = 0; x < Chunk.SIZE; x++) {
        if (shouldGrassGrow(chunk, x, y)) {
          chunkService.updateTileAtToType(chunk, x, y, GRASS);
        }
      }
    }
  }

  private boolean shouldGrassGrow(Chunk chunk, int x, int y) {
    return Math.random() < .0001
        && chunk.getTile(x, y) == DIRT
        && hasGrassNeighbor(chunk, x, y);
  }

  private boolean hasGrassNeighbor(Chunk chunk, int x, int y) {
    Location chunkLocation = chunk.getLocation();
    Location tileLocation = chunkLocation.addNew(x * SIZE, y * SIZE);
    Predicate<TileType> isGrass = isGrass();
    return !chunkService.areAnyNeighbors(tileLocation, isGrass).isEmpty();
  }

  private Predicate<TileType> isGrass() {
    return tileType -> tileType.equals(GRASS);
  }
}
