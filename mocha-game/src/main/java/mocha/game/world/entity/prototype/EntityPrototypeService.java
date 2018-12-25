package mocha.game.world.entity.prototype;

import java.util.Optional;

import mocha.shared.IdFactory;
import mocha.shared.Repository;

public class EntityPrototypeService {

  private EntityPrototypeFactory entityPrototypeFactory;
  private Repository<EntityPrototype, Integer> entityPrototypeRepository;
  private IdFactory<EntityPrototype> entityPrototypeIdFactory;

  public EntityPrototypeService(
      EntityPrototypeFactory entityPrototypeFactory,
      Repository<EntityPrototype, Integer> entityPrototypeRepository,
      IdFactory<EntityPrototype> entityPrototypeIdFactory
  ) {
    this.entityPrototypeFactory = entityPrototypeFactory;
    this.entityPrototypeRepository = entityPrototypeRepository;
    this.entityPrototypeIdFactory = entityPrototypeIdFactory;
  }

  public EntityPrototype save(EntityPrototype entityPrototype) {
    ensureId(entityPrototype);
    return entityPrototypeRepository.save(entityPrototype);
  }

  private void ensureId(EntityPrototype entityPrototype) {
    Integer entityPrototypeId = Optional.ofNullable(entityPrototype.getId())
        .orElse(entityPrototypeIdFactory.newId());
    entityPrototype.setId(entityPrototypeId);
  }

  private EntityPrototype findById(Integer id) {
    return entityPrototypeRepository.findById(id)
        .orElse(entityPrototypeFactory.newDefaultEntityPrototype());
  }
}
