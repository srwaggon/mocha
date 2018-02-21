package mocha.game.world.entity;

import mocha.game.world.entity.brain.BrainFactory;
import mocha.game.world.entity.movement.MovementFactory;

public class EntityFactory {

  private BrainFactory brainFactory;
  private MovementFactory movementFactory;

  public EntityFactory(BrainFactory brainFactory, MovementFactory movementFactory) {
    this.brainFactory = brainFactory;
    this.movementFactory = movementFactory;
  }

  public Entity createRandomSlider() {
    Entity entity = Entity.builder()
        .movement(movementFactory.newSlidingMovement())
        .build();
    entity.setBrain(brainFactory.newRandomBrain(entity));
    return entity;
  }

  public Entity createRandom() {
    Entity entity = Entity.builder()
        .movement(movementFactory.newVelocityMovement())
        .build();
    entity.setBrain(brainFactory.newRandomBrain(entity));
    return entity;
  }

  public Entity createRandomAccelerating() {
    Entity entity = Entity.builder()
        .movement(movementFactory.newAccelerationMovement())
        .build();
    entity.setBrain(brainFactory.newRandomBrain(entity));
    return entity;
  }

  public Pickaxe newPickaxe() {
    return new Pickaxe(movementFactory.newSimpleMovement(), brainFactory.newSimpleBrain());
  }
}
