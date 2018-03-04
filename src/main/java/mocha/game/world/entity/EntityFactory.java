package mocha.game.world.entity;

import mocha.game.world.entity.brain.BrainFactory;
import mocha.game.world.entity.movement.MovementFactory;

public class EntityFactory {

  private BrainFactory brainFactory;
  private MovementFactory movementFactory;
  private EntityIdFactory entityIdFactory;

  public EntityFactory(BrainFactory brainFactory, MovementFactory movementFactory, EntityIdFactory entityIdFactory) {
    this.brainFactory = brainFactory;
    this.movementFactory = movementFactory;
    this.entityIdFactory = entityIdFactory;
  }

  public Entity createSimple() {
    return Entity.builder()
        .id(entityIdFactory.newEntityId())
        .movement(movementFactory.newSimpleMovement())
        .brain(brainFactory.newSimpleBrain())
        .build();
  }

  public Entity createRandomSlider() {
    Entity entity = Entity.builder()
        .id(entityIdFactory.newEntityId())
        .movement(movementFactory.newSlidingMovement())
        .build();
    entity.setBrain(brainFactory.newRandomBrain(entity));
    return entity;
  }

  public Entity createRandom() {
    Entity entity = Entity.builder()
        .id(entityIdFactory.newEntityId())
        .movement(movementFactory.newVelocityMovement())
        .build();
    entity.setBrain(brainFactory.newRandomBrain(entity));
    return entity;
  }

  public Entity createRandomAccelerating() {
    Entity entity = Entity.builder()
        .id(entityIdFactory.newEntityId())
        .movement(movementFactory.newAccelerationMovement())
        .build();
    entity.setBrain(brainFactory.newRandomBrain(entity));
    return entity;
  }

  public Pickaxe newPickaxe() {
    Pickaxe pickaxe = new Pickaxe(movementFactory.newSimpleMovement(), brainFactory.newSimpleBrain());
    pickaxe.setId(entityIdFactory.newEntityId());
    return pickaxe;
  }
}
