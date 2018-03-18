package mocha.game.world.entity.rule;


import com.google.common.collect.Lists;
import com.google.common.eventbus.Subscribe;

import java.util.List;
import java.util.Queue;

import mocha.game.Game;
import mocha.game.event.MochaEventBus;
import mocha.game.rule.GameRule;
import mocha.game.world.Location;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.brain.PickUpItemCommand;

public class PickUpItemsRule implements GameRule {

  private MochaEventBus eventBus;

  private Queue<PickUpItemCommand> pickUpItemCommands = Lists.newLinkedList();

  public PickUpItemsRule(MochaEventBus eventBus) {
    this.eventBus = eventBus;
  }

  @Subscribe
  public void handlePickUpItemCommand(PickUpItemCommand pickUpItemCommand) {
    pickUpItemCommands.add(pickUpItemCommand);
  }

  @Override
  public void apply(Game game) {
    while (!pickUpItemCommands.isEmpty()) {
      pickUpItem(game, pickUpItemCommands.poll());
    }
  }

  private void pickUpItem(Game game, PickUpItemCommand pickUpItemCommand) {
    Location entityLocation = pickUpItemCommand.getPickingUpEntity().getLocation();

    game.getWorld().getChunkAt(entityLocation)
        .ifPresent(chunk -> {
          List<Entity> entities = chunk.getEntitiesAt(entityLocation);

          entities.remove(pickUpItemCommand.getPickingUpEntity());

          if (entities.isEmpty()) {
            return;
          }

          Entity pickedUpEntity = entities.get(entities.size() - 1);
          removeEntity(game, chunk, pickedUpEntity);
        });

  }

  private void removeEntity(Game game, Chunk chunk, Entity pickedUpEntity) {
    chunk.remove(pickedUpEntity);
    game.getEntityRegistry().remove(pickedUpEntity);
    eventBus.postRemoveEntityEvent(pickedUpEntity);
  }
}
