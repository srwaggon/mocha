package mocha.game.world.entity;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.game.GameSetup;
import mocha.game.world.entity.prototype.EntityPrototype;
import mocha.game.world.entity.prototype.EntityPrototypeService;

@Component
class EntitySetup implements GameSetup {

  private EntityPrototypeService entityPrototypeService;
  private EntityFactory entityFactory;
  private EntityService entityService;

  @Inject
  EntitySetup(
      EntityPrototypeService entityPrototypeService,
      EntityFactory entityFactory,
      EntityService entityService
  ) {
    this.entityPrototypeService = entityPrototypeService;
    this.entityFactory = entityFactory;
    this.entityService = entityService;
  }

  @Override
  public void run() {
    EntityPrototype slimePrototype = createSlimeEntityPrototype();

//    Entity slime = entityFactory.newEntity(slimePrototype);
//    entityService.save(slime);
  }

  private EntityPrototype createSlimeEntityPrototype() {
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
    return entityPrototypeService.save(slime);
  }
}
