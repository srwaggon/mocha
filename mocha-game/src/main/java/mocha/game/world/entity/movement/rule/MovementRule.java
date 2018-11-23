package mocha.game.world.entity.movement.rule;

import java.util.Optional;

import mocha.game.Game;
import mocha.game.rule.GameRule;
import mocha.game.world.Location;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.entity.Entity;
import mocha.shared.Repository;

public class MovementRule implements GameRule {

  private Repository<Entity, Integer> entityRepository;
  private Repository<Chunk, Integer> chunkRepository;

  public MovementRule(
      Repository<Entity, Integer> entityRepository,
      Repository<Chunk, Integer> chunkRepository
  ) {
    this.entityRepository = entityRepository;
    this.chunkRepository = chunkRepository;
  }

  @Override
  public void apply(Game game) {
    processEntityMovement();
  }

  private void processEntityMovement() {
    entityRepository
        .findAll()
        .forEach((entity) -> {
          Location start = entity.getLocation().copy();
          entity.getMovement().tick(0L);
          Location finish = entity.getLocation();
          updateChunkOccupants(entity, start, finish);
        });
  }

  private void updateChunkOccupants(Entity entity, Location start, Location finish) {
    if (!start.equals(finish)) {
      getChunkAt(start).ifPresent(chunk -> chunk.remove(entity));
      getChunkAt(finish).ifPresent(chunk -> chunk.add(entity));
    }
  }

  private Optional<? extends Chunk> getChunkAt(Location location) {
    return chunkRepository.findById(Chunk.getIdForChunkAt(location));
  }
}
