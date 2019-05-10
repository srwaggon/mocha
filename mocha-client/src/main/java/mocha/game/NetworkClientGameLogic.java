package mocha.game;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.client.event.ClientEventBus;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.movement.Movement;
import mocha.shared.Repository;

@Component
public class NetworkClientGameLogic {

  private ClientEventBus eventBus;
  private Repository<Movement, Integer> movementRepository;
  private Repository<Entity, Integer> entityRepository;


  @Inject
  public NetworkClientGameLogic(
      ClientEventBus eventBus,
      Repository<Entity, Integer> entityRepository,
      Repository<Movement, Integer> movementRepository
  ) {
    this.eventBus = eventBus;
    this.movementRepository = movementRepository;
    this.entityRepository = entityRepository;
  }

//  @Subscribe
//  public void handle(EntityMoveCommand entityMoveCommand) {
//    entityRepository
//        .findById(entityMoveCommand.getEntityId())
//        .ifPresent(entity -> {
//          entity.getLocation().set(entityMoveCommand.getLocation());
//
//          movementRepository.findById(entity.getId())
//              .ifPresent(movement -> {
//                movement.handle(entityMoveCommand);
//                eventBus.postMoveEvent(movement);
//              });
//        });
//  }
}
