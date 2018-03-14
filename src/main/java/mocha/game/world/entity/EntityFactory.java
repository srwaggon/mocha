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

  public Pickaxe newPickaxe() {
    Pickaxe pickaxe = new Pickaxe(movementFactory.newSimpleMovement(), brainFactory.newSimpleBrain());
    pickaxe.setId(entityIdFactory.newEntityId());
    return pickaxe;
  }
}
