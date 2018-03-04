package mocha.game.rule;

import com.google.common.collect.Lists;
import com.google.common.eventbus.Subscribe;

import java.util.Queue;

import mocha.game.Game;
import mocha.game.world.Location;
import mocha.game.world.World;
import mocha.game.event.world.entity.RemoveEntityEvent;

public class RemoveEntityRule implements GameRule {

  private final Queue<RemoveEntityEvent> removeEntityEvents = Lists.newLinkedList();

  private World world;

  public RemoveEntityRule(World world) {
    this.world = world;
  }

  @Override
  public void apply(Game game) {
    while (!removeEntityEvents.isEmpty()) {
      removeEntity(removeEntityEvents.poll());
    }
  }

  private void removeEntity(RemoveEntityEvent removeEntityEvent) {
    Location entityLocation = removeEntityEvent.getEntity().getMovement().getLocation();
    world.getChunkAt(entityLocation)
        .ifPresent(chunk -> chunk.remove(removeEntityEvent.getEntity()));
  }

  @Subscribe
  public void handleRemoveEntityEvent(RemoveEntityEvent removeEntityEvent) {
    removeEntityEvents.add(removeEntityEvent);
  }
}
