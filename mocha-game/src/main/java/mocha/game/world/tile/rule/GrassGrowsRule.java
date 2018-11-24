package mocha.game.world.tile.rule;

import java.util.function.Predicate;

import mocha.game.Game;
import mocha.game.event.MochaEventBus;
import mocha.game.rule.GameRule;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.tile.TileType;
import mocha.shared.Repository;

public class GrassGrowsRule implements GameRule {

  private Repository<Chunk, Integer> chunkRepository;
  private MochaEventBus mochaEventBus;

  public GrassGrowsRule(
      Repository<Chunk, Integer> chunkRepository,
      MochaEventBus mochaEventBus
  ) {
    this.chunkRepository = chunkRepository;
    this.mochaEventBus = mochaEventBus;
  }

  @Override
  public void apply(Game game) {
    chunkRepository.findAll().forEach(chunk -> {
      if (tryGrowGrass(chunk)) {
        chunkRepository.save(chunk);
        mochaEventBus.postChunkUpdatedEvent(chunk);
      }
    });
  }

  private boolean tryGrowGrass(Chunk chunk) {
    boolean updated = false;
    for (int y = 0; y < Chunk.SIZE; y++) {
      for (int x = 0; x < Chunk.SIZE; x++) {
        if (shouldGrassGrow(chunk, x, y)) {
          chunk.setTile(x, y, TileType.GRASS);
          updated = true;
        }
      }
    }
    return updated;
  }

  private boolean shouldGrassGrow(Chunk chunk, int x, int y) {
    return Math.random() < .0001 && chunk.getTile(x, y) == TileType.DIRT && chunk.getTileNeighbors(x, y).stream().anyMatch(isGrass());
  }

  private Predicate<TileType> isGrass() {
    return tileType -> tileType.equals(TileType.GRASS);
  }
}
