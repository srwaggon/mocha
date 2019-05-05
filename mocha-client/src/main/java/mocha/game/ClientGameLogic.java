package mocha.game;

import java.util.List;

import mocha.client.event.ClientEventBus;
import mocha.game.event.MochaEventHandler;
import mocha.game.world.entity.movement.Movement;
import mocha.game.world.entity.movement.command.EntityMoveCommand;
import mocha.game.world.entity.prototype.EntityPrototypeService;
import mocha.game.world.entity.prototype.UpdateEntityPrototypeCommand;
import mocha.game.world.item.ItemService;
import mocha.game.world.item.UpdateItemCommand;
import mocha.game.world.item.itemprototype.ItemPrototypeService;
import mocha.game.world.item.itemprototype.UpdateItemPrototypeCommand;
import mocha.shared.Repository;

public class ClientGameLogic implements GameLogic {

  ClientEventBus eventBus;
  Repository<Movement, Integer> movementRepository;
  private ItemPrototypeService itemPrototypeService;
  private ItemService itemService;
  private EntityPrototypeService entityPrototypeService;

  ClientGameLogic(
      ClientEventBus mochaEventBus,
      Repository<Movement, Integer> movementRepository,
      ItemPrototypeService itemPrototypeService,
      ItemService itemService,
      EntityPrototypeService entityPrototypeService,
      List<MochaEventHandler> eventHandlers,
      List<CommandHandler> commandHandlers
  ) {
    this.eventBus = mochaEventBus;
    this.movementRepository = movementRepository;
    this.itemPrototypeService = itemPrototypeService;
    this.itemService = itemService;
    this.entityPrototypeService = entityPrototypeService;

    eventHandlers.forEach(eventBus::register);
    commandHandlers.forEach(eventBus::register);
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
