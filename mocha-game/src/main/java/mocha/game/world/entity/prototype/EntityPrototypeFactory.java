package mocha.game.world.entity.prototype;

import mocha.game.world.entity.EntityType;

public class EntityPrototypeFactory {
  public EntityPrototype newDefaultEntityPrototype() {
    return new EntityPrototype(
        0,
        EntityType.MOB,
        0,
        true,
        null,
        null,
        "/mocha/gfx/sprites/slime0.png::0",
        1.0
    );
  }
}
