package mocha.game.world.entity.movement;

import com.google.common.eventbus.Subscribe;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.client.event.ClientEventBus;
import mocha.game.CommandHandler;
import mocha.game.world.entity.EntityService;
import mocha.game.world.entity.movement.command.EntityMoveCommand;
import mocha.shared.Repository;

@Component
public class EntityMoveCommandHandler implements CommandHandler<EntityMoveCommand> {

  private Repository<Movement, Integer> movementRepository;
  private ClientEventBus eventBus;
  private EntityService entityService;

  @Inject
  EntityMoveCommandHandler(
      Repository<Movement, Integer> movementRepository,
      ClientEventBus eventBus,
      EntityService entityService
  ) {
    this.movementRepository = movementRepository;
    this.eventBus = eventBus;
    this.entityService = entityService;
  }

  @Subscribe
  public void handle(EntityMoveCommand entityMoveCommand) {
    entityService
        .findById(entityMoveCommand.getEntityId())
        .ifPresent(entity -> {
          entity.getLocation().set(entityMoveCommand.getLocation());
          movementRepository.findById(entity.getId())
              .ifPresent(movement ->
                  moveEntity(entityMoveCommand, movement));
        });
  }

  private void moveEntity(EntityMoveCommand entityMoveCommand, Movement movement) {
    movement.handle(entityMoveCommand);
    eventBus.postMoveEvent(entityMoveCommand);
  }

}