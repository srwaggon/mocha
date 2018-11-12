package mocha.game.world.entity.movement.rule;

import com.google.common.collect.Lists;
import com.google.common.eventbus.Subscribe;

import java.util.Queue;

import mocha.game.Game;
import mocha.game.event.MochaEventBus;
import mocha.game.rule.GameRule;
import mocha.game.world.Location;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.movement.Movement;
import mocha.game.world.entity.movement.command.EntityMoveCommand;

public class MovementRule implements GameRule {

  private Queue<EntityMoveCommand> moveCommands = Lists.newLinkedList();
  private MochaEventBus mochaEventBus;

  public MovementRule(MochaEventBus mochaEventBus) {
    this.mochaEventBus = mochaEventBus;
  }

  @Subscribe
  public void handleMoveCommand(EntityMoveCommand moveCommand) {
    moveCommands.add(moveCommand);
  }

  @Override
  public void apply(Game game) {
    while (!moveCommands.isEmpty()) {
      postMoveEvent(game, moveCommands.poll());
    }
    processEntityMovement(game);
  }

  private void postMoveEvent(Game game, EntityMoveCommand moveCommand) {
    game.getEntityRegistry().get(moveCommand.getEntityId())
        .ifPresent(entity -> {
          Movement movement = entity.getMovement();
          movement.handle(moveCommand);
          mochaEventBus.postMoveEvent(movement);
        });
  }

  private void processEntityMovement(Game game) {
    game.getEntityRegistry()
        .getMembers()
        .forEach((entity) -> {
          Location start = entity.getLocation().copy();
          entity.getMovement().tick(0L);
          Location finish = entity.getLocation();

          updateChunkOccupants(game, entity, start, finish);
        });
  }

  private void updateChunkOccupants(Game game, Entity entity, Location start, Location finish) {
    if (!start.equals(finish)) {
      game.getWorld().getChunkAt(start)
          .ifPresent(chunk -> chunk.remove(entity));

      game.getWorld().getChunkAt(finish)
          .ifPresent(chunk -> chunk.add(entity));
    }
  }
}
