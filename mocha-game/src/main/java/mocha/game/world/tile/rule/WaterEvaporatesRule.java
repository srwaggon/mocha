package mocha.game.world.tile.rule;

import java.util.function.Predicate;

import mocha.game.Game;
import mocha.game.event.MochaEventBus;
import mocha.game.rule.GameRule;
import mocha.game.world.Location;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.chunk.ChunkService;
import mocha.game.world.tile.TileType;
import mocha.shared.Repository;

public class WaterEvaporatesRule implements GameRule {

  private MochaEventBus mochaEventBus;
  private Repository<Chunk, Integer> chunkRepository;
  private ChunkService chunkService;


  public WaterEvaporatesRule(
      MochaEventBus mochaEventBus, Repository<Chunk, Integer> chunkRepository,
      ChunkService chunkService
  ) {
    this.chunkRepository = chunkRepository;
    this.mochaEventBus = mochaEventBus;
    this.chunkService = chunkService;
  }

  @Override
  public void apply(Game game) {
    chunkRepository.findAll().forEach(chunk -> {
      if (tryEvaporateWater(chunk)) {
        chunkRepository.save(chunk);
      }
    });
  }

  private boolean tryEvaporateWater(Chunk chunk) {
    boolean updated = false;
    for (int y = 0; y < Chunk.SIZE; y++) {
      for (int x = 0; x < Chunk.SIZE; x++) {
        if (shouldEvaporateWater(chunk, x, y)) {
          chunk.setTile(x, y, TileType.DIRT);
          mochaEventBus.postTileUpdatedEvent(chunk, x, y);
          updated = true;
        }
      }
    }
    return updated;
  }

  private boolean shouldEvaporateWater(Chunk chunk, int x, int y) {
    return Math.random() < .0001
        && chunk.getTile(x, y) == TileType.WATER
        && isLoneWater(chunk, x, y);
  }

  private boolean isLoneWater(Chunk chunk, int x, int y) {
    Location chunkLocation = chunk.getLocation();
    Location tileLocation = chunkLocation.addNew(x * TileType.SIZE, y * TileType.SIZE);
    return chunkService.getTileNeighborsInExistingChunks(tileLocation).values().stream().noneMatch(isWater());
  }

  private Predicate<TileType> isWater() {
    return tileType -> tileType.equals(TileType.WATER);
  }
}
