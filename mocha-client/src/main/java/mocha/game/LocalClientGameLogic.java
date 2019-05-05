package mocha.game;

import org.springframework.stereotype.Component;

import java.util.List;

import javax.inject.Inject;

import mocha.client.event.ClientEventBus;
import mocha.game.event.MochaEventHandler;
import mocha.game.world.entity.movement.Movement;
import mocha.game.world.item.ItemService;
import mocha.game.world.item.itemprototype.ItemPrototypeService;
import mocha.shared.Repository;

@Component
public class LocalClientGameLogic extends ClientGameLogic {

  @Inject
  public LocalClientGameLogic(
      ClientEventBus mochaEventBus,
      Repository<Movement, Integer> movementRepository,
      ItemPrototypeService itemPrototypeService,
      ItemService itemService,
      List<MochaEventHandler> eventHandlers,
      List<CommandHandler> commandHandlers
  ) {
    super(mochaEventBus, movementRepository, itemPrototypeService, itemService, eventHandlers, commandHandlers);
  }

}
