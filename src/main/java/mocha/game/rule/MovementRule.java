package mocha.game.rule;

import com.google.common.collect.Lists;
import com.google.common.eventbus.Subscribe;

import java.util.List;
import java.util.Queue;

import mocha.game.Game;
import mocha.game.event.world.entity.movement.MovementEvent;
import mocha.game.world.entity.Entity;

public class MovementRule implements GameRule {

  private final Queue<MovementEvent> movementEvents = Lists.newLinkedList();

  @Override
  public void apply(Game game) {

    List<Entity> activeEntities = game.getActiveEntities();

    activeEntities.stream()
        .map(Entity::getMovement)
        .forEach(movement -> movement.tick(0L));

    activeEntities.forEach(entity ->
        game.getWorld().getChunkAt(entity.getLocation())
            .ifPresent(chunk -> chunk.remove(entity)));

    while (!movementEvents.isEmpty()) {
      MovementEvent movementEvent = movementEvents.poll();
      movementEvent.getLocation().set(movementEvent.getLocation());
    }

    activeEntities.forEach(entity ->
        game.getWorld().getChunkAt(entity.getLocation())
            .ifPresent(chunk -> chunk.add(entity)));
  }

  @Subscribe
  public void handleMovementEvent(MovementEvent movementEvent) {
    movementEvents.add(movementEvent);
  }
}
