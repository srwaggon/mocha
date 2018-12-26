package mocha.game;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.game.world.entity.movement.Movement;
import mocha.game.world.entity.prototype.EntityPrototypeService;
import mocha.game.world.item.ItemService;
import mocha.game.world.item.itemprototype.ItemPrototypeService;
import mocha.net.event.NetworkedMochaEventBus;
import mocha.shared.Repository;

@Component
public class LocalClientGameLogic extends ClientGameLogic {

  @Inject
  public LocalClientGameLogic(
      NetworkedMochaEventBus mochaEventBus,
      Repository<Movement, Integer> movementRepository,
      ItemPrototypeService itemPrototypeService,
      ItemService itemService,
      EntityPrototypeService entityPrototypeService
  ) {
    super(mochaEventBus, movementRepository, itemPrototypeService, itemService, entityPrototypeService);
  }

}
