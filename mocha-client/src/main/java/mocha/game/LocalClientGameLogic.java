package mocha.game;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.game.event.MochaEventBus;
import mocha.game.world.entity.movement.Movement;
import mocha.game.world.entity.movement.command.EntityMoveCommand;
import mocha.game.world.entity.prototype.EntityPrototypeService;
import mocha.game.world.entity.prototype.UpdateEntityPrototypeCommand;
import mocha.game.world.item.ItemService;
import mocha.game.world.item.UpdateItemCommand;
import mocha.game.world.item.itemprototype.ItemPrototypeService;
import mocha.game.world.item.itemprototype.UpdateItemPrototypeCommand;
import mocha.shared.Repository;

@Component
public class LocalClientGameLogic implements GameLogic {

  private MochaEventBus mochaEventBus;
  private Repository<Movement, Integer> movementRepository;
  private ItemPrototypeService itemPrototypeService;
  private ItemService itemService;
  private EntityPrototypeService entityPrototypeService;

  @Inject
  public LocalClientGameLogic(
      MochaEventBus mochaEventBus,
      Repository<Movement, Integer> movementRepository,
      ItemPrototypeService itemPrototypeService,
      ItemService itemService,
      EntityPrototypeService entityPrototypeService
  ) {
    this.mochaEventBus = mochaEventBus;
    this.movementRepository = movementRepository;
    this.itemPrototypeService = itemPrototypeService;
    this.itemService = itemService;
    this.entityPrototypeService = entityPrototypeService;
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

  @Override
  public void handle(UpdateItemCommand updateItemCommand) {
    itemService.updateItem(updateItemCommand);
  }

  @Override
  public void handle(UpdateEntityPrototypeCommand updateEntityPrototypeCommand) {
    entityPrototypeService.save(updateEntityPrototypeCommand.getEntityPrototype());
  }

  private void moveEntity(EntityMoveCommand entityMoveCommand, Movement movement) {
    movement.handle(entityMoveCommand);
    mochaEventBus.postMoveEvent(movement);
  }

}
