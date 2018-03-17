package mocha.game.world.entity.movement.rule;

import com.google.common.collect.Lists;
import com.google.common.eventbus.Subscribe;

import java.util.List;
import java.util.Queue;

import mocha.game.Game;
import mocha.game.event.MochaEventBus;
import mocha.game.rule.GameRule;
import mocha.game.world.Location;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.movement.command.MoveCommand;

public class MovementRule implements GameRule {

  private Queue<MoveCommand> moveCommands = Lists.newLinkedList();
  private MochaEventBus mochaEventBus;

  public MovementRule(MochaEventBus mochaEventBus) {
    this.mochaEventBus = mochaEventBus;
  }

  @Subscribe
  public void handleMoveCommand(MoveCommand moveCommand) {
    moveCommands.add(moveCommand);
  }

  @Override
  public void apply(Game game) {

    while (!moveCommands.isEmpty()) {
      moveCommands.poll().apply(game);
    }

    List<Entity> activeEntities = game.getActiveEntities();

    activeEntities.forEach((entity) -> {
      Location start = entity.getLocation().copy();
      game.getWorld().getChunkAt(start)
          .ifPresent(chunk -> chunk.remove(entity));

      entity.getMovement().tick(0L);

      Location finish = entity.getLocation();

      game.getWorld().getChunkAt(finish)
          .ifPresent(chunk -> chunk.add(entity));

      if (!start.equals(finish)) {
        mochaEventBus.move(entity.getMovement());
      }
    });
  }
}
