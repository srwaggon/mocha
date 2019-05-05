package mocha.game.world.entity.movement;

import com.google.common.eventbus.Subscribe;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.game.CommandHandler;
import mocha.game.world.entity.movement.command.EntityMoveCommand;
import mocha.server.event.ServerEventBus;
import mocha.shared.Repository;

@Component
public class EntityMoveCommandHandler implements CommandHandler<EntityMoveCommand> {

  private ServerEventBus eventBus;
  private Repository<Movement, Integer> movementRepository;

  @Inject
  public EntityMoveCommandHandler(
      ServerEventBus eventBus,
      Repository<Movement, Integer> movementRepository
  ) {
    this.eventBus = eventBus;
    this.movementRepository = movementRepository;
  }

  @Subscribe
  public void handle(EntityMoveCommand entityMoveCommand) {
    movementRepository.findById(entityMoveCommand.getEntityId())
        .ifPresent(movement -> {
          movement.handle(entityMoveCommand);
          eventBus.postMoveEvent(movement);
        });
  }
}