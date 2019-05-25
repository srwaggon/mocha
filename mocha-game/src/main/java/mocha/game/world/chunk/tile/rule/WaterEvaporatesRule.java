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
import static mocha.game.world.chunk.tile.TileType.WATER;

public class WaterEvaporatesRule implements GameRule {

  private Repository<Chunk, Integer> chunkRepository;
  private ChunkService chunkService;


  public WaterEvaporatesRule(
      Repository<Chunk, Integer> chunkRepository,
      ChunkService chunkService
  ) {
    this.chunkRepository = chunkRepository;
    this.chunkService = chunkService;
  }

  @Override
  public void apply() {
    chunkRepository.findAll().forEach(this::tryEvaporateWater);
  }

  private void tryEvaporateWater(Chunk chunk) {
    for (int y = 0; y < Chunk.SIZE; y++) {
      for (int x = 0; x < Chunk.SIZE; x++) {
        if (shouldEvaporateWater(chunk, x, y)) {
          chunkService.updateTileTypeAtIndex(chunk, x, y, DIRT);
        }
      }
    }
  }

  private boolean shouldEvaporateWater(Chunk chunk, int x, int y) {
    return Math.random() < .0001
        && chunk.getTile(x, y) == WATER
        && isLoneWater(chunk, x, y);
  }

  private boolean isLoneWater(Chunk chunk, int x, int y) {
    Location chunkLocation = chunk.getLocation();
    Location tileLocation = chunkLocation.addNew(x * SIZE, y * SIZE);
    long waterNeighbors = chunkService.areAnyNeighbors(tileLocation, isWater()).size();
    long grassNeighbors = chunkService.areAnyNeighbors(tileLocation, isGrass()).size();
    return waterNeighbors < grassNeighbors;
  }

  private Predicate<TileType> isWater() {
    return tileType -> tileType.equals(WATER);
  }

  private Predicate<TileType> isGrass() {
    return tileType -> tileType.equals(GRASS);
  }
}
