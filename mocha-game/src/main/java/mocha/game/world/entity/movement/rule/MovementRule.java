package mocha.game.world.entity.movement.rule;

import java.util.Optional;

import mocha.game.Game;
import mocha.game.rule.GameRule;
import mocha.game.world.Location;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.movement.Movement;
import mocha.shared.Repository;

public class MovementRule implements GameRule {

  private Repository<Entity, Integer> entityRepository;
  private Repository<Chunk, Integer> chunkRepository;
  private Repository<Movement, Integer> movementRepository;

  public MovementRule(
      Repository<Entity, Integer> entityRepository,
      Repository<Chunk, Integer> chunkRepository,
      Repository<Movement, Integer> movementRepository) {
    this.entityRepository = entityRepository;
    this.chunkRepository = chunkRepository;
    this.movementRepository = movementRepository;
  }

  @Override
  public void apply(Game game) {
    // todo: forget game
    // todo: accept time;
    processEntityMovement();
  }

  private void processEntityMovement() {
    movementRepository.findAll()
        .forEach((movement) -> {
          Location start = movement.getLocation().copy();
          movement.tick(0L);
          Location finish = movement.getLocation();
          entityRepository.findById(movement.getId())
              .ifPresent(entity -> updateChunkOccupants(entity, start, finish));
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
