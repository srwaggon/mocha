package mocha.game;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.game.event.MochaEventBus;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.movement.command.EntityMoveCommand;

@Component
public class LocalClientGameLogic implements GameLogic {

  @Inject
  private Game game;

  @Inject
  private MochaEventBus mochaEventBus;

  @Override
  public void handle(EntityMoveCommand entityMoveCommand) {
    game.getEntityRegistry()
        .get(entityMoveCommand.getEntityId())
        .map(Entity::getMovement)
        .ifPresent(movement -> {
          movement.handle(entityMoveCommand);
          mochaEventBus.postMoveEvent(movement);
        });
  }

}
