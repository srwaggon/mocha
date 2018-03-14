package mocha.game.rule;

import java.util.List;

import mocha.game.Game;
import mocha.game.world.entity.Entity;

public class MovementRule implements GameRule {


  @Override
  public void apply(Game game) {
    List<Entity> activeEntities = game.getActiveEntities();

    activeEntities.stream()
        .map(Entity::getMovement)
        .forEach(movement -> movement.tick(0L));

    activeEntities.forEach(entity ->
        game.getWorld().getChunkAt(entity.getLocation())
            .ifPresent(chunk -> chunk.remove(entity)));

    activeEntities.forEach(entity ->
        game.getWorld().getChunkAt(entity.getLocation())
            .ifPresent(chunk -> chunk.add(entity)));
  }
}
