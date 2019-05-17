package mocha.game.world.entity.rule;


import com.google.common.collect.Lists;
import com.google.common.eventbus.Subscribe;

import java.util.Queue;
import java.util.Set;

import mocha.game.rule.GameRule;
import mocha.game.world.Location;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.chunk.ChunkService;
import mocha.game.world.collision.Collider;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.EntityService;
import mocha.game.world.entity.EntityType;
import mocha.game.world.entity.command.PickUpItemCommand;

public class PickUpItemsRule implements GameRule {

  private ChunkService chunkService;
  private EntityService entityService;

  private Queue<PickUpItemCommand> pickUpItemCommands = Lists.newLinkedList();

  public PickUpItemsRule(
      ChunkService chunkService,
      EntityService entityService
  ) {
    this.chunkService = chunkService;
    this.entityService = entityService;
  }

  @Subscribe
  public void handlePickUpItemCommand(PickUpItemCommand pickUpItemCommand) {
    pickUpItemCommands.add(pickUpItemCommand);
  }

  @Override
  public void apply() {
    while (!pickUpItemCommands.isEmpty()) {
      pickUpItem(pickUpItemCommands.poll());
    }
  }

  private void pickUpItem(PickUpItemCommand pickUpItemCommand) {
    Location location = pickUpItemCommand.getPickingUpEntity().getLocation();
    Chunk chunk = chunkService.getOrCreateChunkAt(location);
    removeEntity(pickUpItemCommand.getPickingUpEntity(), chunk);
  }

  private void removeEntity(Entity pickingUpEntity, Chunk chunk) {
    Set<Collider> colliders = pickingUpEntity.getCollision().getColliders(pickingUpEntity.getLocation());
    entityService.getEntitiesInChunk(chunk).stream()
        .filter(entity -> !entity.getId().equals(pickingUpEntity.getId()))
        .filter(PickUpItemsRule::isItem)
        .filter(colliders::contains)
        .findFirst()
        .ifPresent(itemEntity -> pickupItem(pickingUpEntity, itemEntity));
  }

  private void pickupItem(Entity pickingUpEntity, Entity itemEntity) {
    pickingUpEntity.pickup(itemEntity);
    entityService.removeEntity(itemEntity);
  }

  private static boolean isItem(Entity entity) {
    return entity.getEntityType().equals(EntityType.ITEM);
  }
}
