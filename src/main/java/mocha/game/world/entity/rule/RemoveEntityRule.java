package mocha.game.world.entity.rule;

import com.google.common.collect.Lists;
import com.google.common.eventbus.Subscribe;

import java.util.Queue;

import mocha.game.Game;
import mocha.game.rule.GameRule;
import mocha.game.world.Location;
import mocha.game.world.World;
import mocha.game.world.entity.event.EntityRemovedEvent;

public class RemoveEntityRule implements GameRule {

  private final Queue<EntityRemovedEvent> entityRemovedEvents = Lists.newLinkedList();

  private World world;

  public RemoveEntityRule(World world) {
    this.world = world;
  }

  @Override
  public void apply(Game game) {
    while (!entityRemovedEvents.isEmpty()) {
      removeEntity(entityRemovedEvents.poll());
    }
  }

  private void removeEntity(EntityRemovedEvent entityRemovedEvent) {
    Location entityLocation = entityRemovedEvent.getEntity().getLocation();
    world.getChunkAt(entityLocation)
        .ifPresent(chunk -> chunk.remove(entityRemovedEvent.getEntity()));
  }

  @Subscribe
  public void handle(EntityRemovedEvent entityRemovedEvent) {
    entityRemovedEvents.add(entityRemovedEvent);
  }
}
