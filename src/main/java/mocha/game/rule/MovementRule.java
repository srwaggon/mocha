package mocha.game.rule;

import com.google.common.collect.Lists;
import com.google.common.eventbus.Subscribe;

import java.util.Queue;

import mocha.game.Game;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.movement.MovementEvent;

public class MovementRule implements GameRule {

  private final Queue<MovementEvent> movementEvents = Lists.newLinkedList();

  @Override
  public void apply(Game game) {
    while(!movementEvents.isEmpty()) {
      move(movementEvents.poll());
    }

    game.getActiveEntities().stream()
        .filter(entity -> !entity.equals(game.getPlayer()))
        .map(Entity::getMovement)
        .forEach(movement -> movement.tick(0L));
    game.getPlayer().getMovement().tick(0L);
  }

  private void move(MovementEvent movementEvent) {
    movementEvent.getMovement().setLocation(movementEvent.getLocation());
  }

  @Subscribe
  public void handleMovementEvent(MovementEvent movementEvent) {
    movementEvents.add(movementEvent);
  }
}
