package mocha.game.rule;


import com.google.common.collect.Lists;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import java.util.List;
import java.util.Optional;
import java.util.Queue;

import mocha.game.Game;
import mocha.game.world.Location;
import mocha.game.world.World;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.RemoveEntityEvent;
import mocha.game.world.entity.brain.PickUpItemEvent;

public class PickUpItemsRule implements GameRule {

  private World world;

  private EventBus eventBus;

  private Queue<PickUpItemEvent> pickUpItemEvents = Lists.newLinkedList();

  public PickUpItemsRule(World world, EventBus eventBus) {
    this.world = world;
    this.eventBus = eventBus;
  }

  @Subscribe
  public void handlePickUpItemEvent(PickUpItemEvent pickUpItemEvent) {
    pickUpItemEvents.add(pickUpItemEvent);
  }

  @Override
  public void apply(Game game) {
    while (!pickUpItemEvents.isEmpty()) {
      pickUpItem(pickUpItemEvents.poll());
    }
  }

  private void pickUpItem(PickUpItemEvent pickUpItemEvent) {
    Location entityLocation = pickUpItemEvent.getPickingUpEntity().getMovement().getLocation();
    Optional<Chunk> chunkMaybe = world.getChunkAt(entityLocation);
    if (!chunkMaybe.isPresent()) {
      return;
    }
    List<Entity> entities = chunkMaybe.get().getEntitiesAt(entityLocation);
    entities.remove(pickUpItemEvent.getPickingUpEntity());
    if (entities.isEmpty()) {
      return;
    }
    Entity pickedUpEntity = entities.get(entities.size() - 1);
    eventBus.post(new RemoveEntityEvent(pickedUpEntity));
  }
}
