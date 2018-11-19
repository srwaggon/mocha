package mocha.game;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.game.event.MochaEventBus;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.movement.command.EntityMoveCommand;
import mocha.shared.Repository;

@Component
public class LocalClientGameLogic implements GameLogic {

  @Inject
  private MochaEventBus mochaEventBus;

  @Inject
  private Repository<Entity, Integer> entityRepository;

  @Override
  public void handle(EntityMoveCommand entityMoveCommand) {
    entityRepository
        .findById(entityMoveCommand.getEntityId())
        .map(Entity::getMovement)
        .ifPresent(movement -> {
          movement.handle(entityMoveCommand);
          mochaEventBus.postMoveEvent(movement);
        });
  }

}
