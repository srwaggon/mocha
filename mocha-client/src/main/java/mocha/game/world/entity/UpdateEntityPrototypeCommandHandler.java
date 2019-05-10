package mocha.game.world.entity;

import com.google.common.eventbus.Subscribe;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.game.CommandHandler;
import mocha.game.world.entity.prototype.EntityPrototypeService;
import mocha.game.world.entity.prototype.UpdateEntityPrototypeCommand;

@Component
public class UpdateEntityPrototypeCommandHandler implements CommandHandler<UpdateEntityPrototypeCommand> {

  private EntityPrototypeService entityPrototypeService;

  @Inject
  public UpdateEntityPrototypeCommandHandler(EntityPrototypeService entityPrototypeService) {
    this.entityPrototypeService = entityPrototypeService;
  }

  @Subscribe
  public void handle(UpdateEntityPrototypeCommand updateEntityPrototypeCommand) {
    entityPrototypeService.save(updateEntityPrototypeCommand.getEntityPrototype());
  }
}