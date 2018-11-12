package mocha.game.world.entity.rule;


import com.google.common.collect.Lists;
import com.google.common.eventbus.Subscribe;

import java.util.Queue;

import mocha.game.Game;
import mocha.game.event.MochaEventBus;
import mocha.game.rule.GameRule;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.PickUpItemCommand;

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
    game.getWorld()
        .getChunkAt(pickUpItemCommand.getPickingUpEntity().getLocation())
        .ifPresent(chunk -> removeEntity(game, pickUpItemCommand.getPickingUpEntity(), chunk));
  }

  private void removeEntity(Game game, Entity pickingUpEntity, Chunk chunk) {
    chunk.getEntitiesAt(pickingUpEntity.getLocation()).stream()
        .filter(entity -> entity.getId() != pickingUpEntity.getId())
        .findFirst()
        .ifPresent(game::removeEntity);
  }
}
