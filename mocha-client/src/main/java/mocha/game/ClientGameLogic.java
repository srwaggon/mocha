package mocha.game;

import java.util.List;

import mocha.client.event.ClientEventBus;
import mocha.game.event.MochaEventHandler;
import mocha.game.world.entity.movement.Movement;
import mocha.game.world.entity.movement.command.EntityMoveCommand;
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

  ClientGameLogic(
      ClientEventBus mochaEventBus,
      Repository<Movement, Integer> movementRepository,
      ItemPrototypeService itemPrototypeService,
      ItemService itemService,
      List<MochaEventHandler> eventHandlers,
      List<CommandHandler> commandHandlers
  ) {
    this.eventBus = mochaEventBus;
    this.movementRepository = movementRepository;
    this.itemPrototypeService = itemPrototypeService;
    this.itemService = itemService;

    eventHandlers.forEach(eventBus::register);
    commandHandlers.forEach(eventBus::register);
  }

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

  public void handle(UpdateItemCommand updateItemCommand) {
    itemService.updateItem(updateItemCommand);
  }

  private void moveEntity(EntityMoveCommand entityMoveCommand, Movement movement) {
    movement.handle(entityMoveCommand);
    eventBus.postMoveEvent(movement);
  }
}
