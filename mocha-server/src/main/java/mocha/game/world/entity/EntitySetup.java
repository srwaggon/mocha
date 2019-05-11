package mocha.game.world.entity;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.game.GameSetup;
import mocha.game.world.entity.prototype.EntityPrototype;
import mocha.game.world.entity.prototype.EntityPrototypeService;

@Component
class EntitySetup implements GameSetup {

  private EntityPrototypeService entityPrototypeService;

  @Inject
  EntitySetup(EntityPrototypeService entityPrototypeService) {
    this.entityPrototypeService = entityPrototypeService;
  }

  @Override
  public void run() {
    EntityPrototype slime = new EntityPrototype(
        1,
        1,
        EntityType.MOB,
        1,
        true,
        null,
        null,
        "/mocha/gfx/sprites/slime0.png::0",
        1.0
    );
    entityPrototypeService.save(slime);
  }
}
