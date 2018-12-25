package mocha.game.world.entity.prototype;

import mocha.shared.Repository;

public class EntityPrototypeService {

  private Repository<EntityPrototype, Integer> entityPrototypeRepository;

  public EntityPrototypeService(Repository<EntityPrototype, Integer> entityPrototypeRepository) {
    this.entityPrototypeRepository = entityPrototypeRepository;
  }
}
