package mocha.game.world.entity.movement.rule;

import mocha.game.Game;
import mocha.game.rule.GameRule;
import mocha.game.world.Location;
import mocha.game.world.chunk.ChunkService;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.movement.Movement;
import mocha.shared.Repository;

public class MovementRule implements GameRule {

  private Repository<Entity, Integer> entityRepository;
  private Repository<Movement, Integer> movementRepository;
  private ChunkService chunkService;

  public MovementRule(
      Repository<Entity, Integer> entityRepository,
      Repository<Movement, Integer> movementRepository,
      ChunkService chunkService
  ) {
    this.entityRepository = entityRepository;
    this.movementRepository = movementRepository;
    this.chunkService = chunkService;
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

  private void updateChunkOccupants(Entity entity, Location startLocation, Location finishLocation) {
    if (!startLocation.equals(finishLocation)) {
      chunkService.getChunkAt(startLocation).remove(entity);
      chunkService.getChunkAt(finishLocation).add(entity);
    }
  }
}
