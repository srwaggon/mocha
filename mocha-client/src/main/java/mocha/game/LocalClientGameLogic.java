package mocha.game;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.game.event.MochaEventBus;
import mocha.game.world.entity.movement.Movement;
import mocha.game.world.entity.movement.command.EntityMoveCommand;
import mocha.shared.Repository;

@Component
public class LocalClientGameLogic implements GameLogic {

  @Inject
  private MochaEventBus mochaEventBus;

  @Inject
  private Repository<Movement, Integer> movementRepository;

  @Override
  public void handle(EntityMoveCommand entityMoveCommand) {
    int entityId = entityMoveCommand.getEntityId();
    movementRepository.findById(entityId)
        .ifPresent(movement ->
            moveEntity(entityMoveCommand, movement));
  }

  private void moveEntity(EntityMoveCommand entityMoveCommand, Movement movement) {
    movement.handle(entityMoveCommand);
    mochaEventBus.postMoveEvent(movement);
  }

}
