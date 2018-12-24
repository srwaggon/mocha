package mocha.game;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.game.event.MochaEventBus;
import mocha.game.world.entity.movement.Movement;
import mocha.game.world.entity.movement.command.EntityMoveCommand;
import mocha.game.world.item.ItemPrototypeService;
import mocha.game.world.item.command.UpdateItemPrototypeCommand;
import mocha.shared.Repository;

@Component
public class LocalClientGameLogic implements GameLogic {

  private MochaEventBus mochaEventBus;
  private Repository<Movement, Integer> movementRepository;
  private ItemPrototypeService itemPrototypeService;

  @Inject
  public LocalClientGameLogic(
      MochaEventBus mochaEventBus,
      Repository<Movement, Integer> movementRepository,
      ItemPrototypeService itemPrototypeService
  ) {
    this.mochaEventBus = mochaEventBus;
    this.movementRepository = movementRepository;
    this.itemPrototypeService = itemPrototypeService;
  }

  @Override
  public void handle(EntityMoveCommand entityMoveCommand) {
    int entityId = entityMoveCommand.getEntityId();
    movementRepository.findById(entityId)
        .ifPresent(movement ->
            moveEntity(entityMoveCommand, movement));
  }

  @Override
  public void handle(UpdateItemPrototypeCommand updateItemPrototypeCommand) {
    itemPrototypeService.updateItemPrototype(updateItemPrototypeCommand);
  }

  private void moveEntity(EntityMoveCommand entityMoveCommand, Movement movement) {
    movement.handle(entityMoveCommand);
    mochaEventBus.postMoveEvent(movement);
  }

}
