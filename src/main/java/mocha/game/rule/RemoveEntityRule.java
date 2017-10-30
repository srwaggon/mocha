package mocha.game.rule;

import com.google.common.collect.Lists;
import com.google.common.eventbus.Subscribe;

import java.util.Optional;
import java.util.Queue;

import mocha.game.Game;
import mocha.game.world.Location;
import mocha.game.world.World;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.RemoveEntityEvent;

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
    Entity entity = removeEntityEvent.getEntity();
    Location entityLocation = entity.getMovement().getLocation();
    Optional<Chunk> chunkMaybe = world.getChunkAt(entityLocation);
    chunkMaybe.ifPresent(chunk -> chunk.remove(entity));
  }

  @Subscribe
  public void handleRemoveEntityEvent(RemoveEntityEvent removeEntityEvent) {
    removeEntityEvents.add(removeEntityEvent);
  }
}
