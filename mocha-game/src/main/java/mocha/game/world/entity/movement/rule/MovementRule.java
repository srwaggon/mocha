package mocha.game.world.entity.movement.rule;

import mocha.game.Game;
import mocha.game.rule.GameRule;
import mocha.game.world.Location;
import mocha.game.world.entity.Entity;
import mocha.shared.Repository;

public class MovementRule implements GameRule {

  private Repository<Entity, Integer> entityRepository;

  public MovementRule(Repository<Entity, Integer> entityRepository) {
    this.entityRepository = entityRepository;
  }

  @Override
  public void apply(Game game) {
    processEntityMovement(game);
  }

  private void processEntityMovement(Game game) {
    entityRepository
        .findAll()
        .forEach((entity) -> {
          Location start = entity.getLocation().copy();
          entity.getMovement().tick(0L);
          Location finish = entity.getLocation();

          updateChunkOccupants(game, entity, start, finish);
        });
  }

  private void updateChunkOccupants(Game game, Entity entity, Location start, Location finish) {
    if (!start.equals(finish)) {
      game.getWorld().getChunkAt(start)
          .ifPresent(chunk -> chunk.remove(entity));

      game.getWorld().getChunkAt(finish)
          .ifPresent(chunk -> chunk.add(entity));
    }
  }
}
