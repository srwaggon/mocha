package mocha.game.world.entity.movement.rule;

import mocha.game.rule.GameRule;
import mocha.game.world.Location;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.chunk.ChunkService;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.EntityService;
import mocha.game.world.entity.movement.Movement;
import mocha.shared.Repository;

public class MovementRule implements GameRule {

  private Repository<Movement, Integer> movementRepository;
  private ChunkService chunkService;
  private EntityService entityService;

  public MovementRule(
      Repository<Movement, Integer> movementRepository,
      ChunkService chunkService,
      EntityService entityService
  ) {
    this.movementRepository = movementRepository;
    this.chunkService = chunkService;
    this.entityService = entityService;
  }

  @Override
  public void apply() {
    // todo: accept time;
    processEntityMovement();
  }

  private void processEntityMovement() {
    movementRepository.findAll()
        .forEach((movement) -> entityService.findById(movement.getId()).ifPresent(entity -> {
          Location start = movement.getLocation().copy();
          movement.move(entity);
          Location finish = movement.getLocation();
          updateChunkOccupants(entity, start, finish);
        }));
  }

  private void updateChunkOccupants(Entity entity, Location startLocation, Location finishLocation) {
    if (startLocation.equals(finishLocation)) {
      return;
    }
    Chunk startChunk = chunkService.getOrCreateChunkAt(startLocation);
    Chunk finishChunk = chunkService.getOrCreateChunkAt(finishLocation);
    if (startChunk.getId().equals(finishChunk.getId())) {
      return;
    }
    entityService.remove(startChunk, entity);
    entityService.put(finishChunk, entity);
  }
}
