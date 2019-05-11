package mocha.game.world.chunk.tile.rule;

import java.util.function.Predicate;

import mocha.game.event.MochaEventBus;
import mocha.game.rule.GameRule;
import mocha.game.world.Location;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.chunk.ChunkService;
import mocha.game.world.chunk.tile.TileType;
import mocha.shared.Repository;

public class GrassGrowsRule implements GameRule {

  private MochaEventBus mochaEventBus;
  private Repository<Chunk, Integer> chunkRepository;
  private ChunkService chunkService;

  public GrassGrowsRule(
      MochaEventBus mochaEventBus, Repository<Chunk, Integer> chunkRepository,
      ChunkService chunkService
  ) {
    this.chunkRepository = chunkRepository;
    this.mochaEventBus = mochaEventBus;
    this.chunkService = chunkService;
  }

  @Override
  public void apply() {
    chunkRepository.findAll().forEach(chunk -> {
      if (tryGrowGrass(chunk)) {
        chunkRepository.save(chunk);
      }
    });
  }

  private boolean tryGrowGrass(Chunk chunk) {
    boolean updated = false;
    for (int y = 0; y < Chunk.SIZE; y++) {
      for (int x = 0; x < Chunk.SIZE; x++) {
        if (shouldGrassGrow(chunk, x, y)) {
          chunk.setTile(x, y, TileType.GRASS);
          mochaEventBus.postTileUpdatedEvent(chunk, x, y);
          updated = true;
        }
      }
    }
    return updated;
  }

  private boolean shouldGrassGrow(Chunk chunk, int x, int y) {
    return Math.random() < .0001
        && chunk.getTile(x, y) == TileType.DIRT
        && hasGrassNeighbor(chunk, x, y);
  }

  private boolean hasGrassNeighbor(Chunk chunk, int x, int y) {
    Location chunkLocation = chunk.getLocation();
    Location tileLocation = chunkLocation.addNew(x * TileType.SIZE, y * TileType.SIZE);
    return chunkService.getTileNeighborsInExistingChunks(tileLocation).values().stream().anyMatch(isGrass());
  }

  private Predicate<TileType> isGrass() {
    return tileType -> tileType.equals(TileType.GRASS);
  }
}
