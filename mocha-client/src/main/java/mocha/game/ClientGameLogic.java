package mocha.game;

import mocha.game.world.entity.movement.Movement;
import mocha.game.world.entity.movement.command.EntityMoveCommand;
import mocha.game.world.entity.prototype.EntityPrototypeService;
import mocha.game.world.entity.prototype.UpdateEntityPrototypeCommand;
import mocha.game.world.item.ItemService;
import mocha.game.world.item.UpdateItemCommand;
import mocha.game.world.item.itemprototype.ItemPrototypeService;
import mocha.game.world.item.itemprototype.UpdateItemPrototypeCommand;
import mocha.net.event.NetworkedMochaEventBus;
import mocha.shared.Repository;

public class ClientGameLogic implements GameLogic {

  NetworkedMochaEventBus eventBus;
  Repository<Movement, Integer> movementRepository;
  ItemPrototypeService itemPrototypeService;
  ItemService itemService;
  EntityPrototypeService entityPrototypeService;

  ClientGameLogic(NetworkedMochaEventBus mochaEventBus, Repository<Movement, Integer> movementRepository, ItemPrototypeService itemPrototypeService, ItemService itemService, EntityPrototypeService entityPrototypeService) {
    this.eventBus = mochaEventBus;
    this.movementRepository = movementRepository;
    this.itemPrototypeService = itemPrototypeService;
    this.itemService = itemService;
    this.entityPrototypeService = entityPrototypeService;
  }

  @Override
  public void handle(EntityMoveCommand entityMoveCommand) {

  }

  public void movePlayerEntity(EntityMoveCommand entityMoveCommand) {
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
    eventBus.postMoveEvent(movement);
  }
}
