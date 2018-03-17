package mocha.game.world.entity;

import mocha.game.IdFactory;
import mocha.game.world.Location;
import mocha.game.world.entity.brain.BrainFactory;
import mocha.game.world.entity.brain.SimpleBrain;
import mocha.game.world.entity.movement.MovementFactory;
import mocha.game.world.entity.movement.SimpleMovement;

public class EntityFactory {

  private BrainFactory brainFactory;
  private MovementFactory movementFactory;
  private IdFactory idFactory;

  public EntityFactory(BrainFactory brainFactory, MovementFactory movementFactory, IdFactory idFactory) {
    this.brainFactory = brainFactory;
    this.movementFactory = movementFactory;
    this.idFactory = idFactory;
  }

  public Entity createSimple() {
    Entity entity = Entity.builder()
        .id(idFactory.newId())
        .location(new Location())
        .build();
    entity.setMovement(movementFactory.newSimpleMovement());
    entity.setBrain(brainFactory.newSimpleBrain());
    return entity;
  }

  public Entity createSlider() {
    Entity entity = Entity.builder()
        .id(idFactory.newId())
        .location(new Location())
        .build();
    entity.setMovement(movementFactory.newSlidingMovement());
    entity.setBrain(brainFactory.newSimpleBrain());
    return entity;
  }

  public Entity createRandomSlider() {
    Entity entity = Entity.builder()
        .id(idFactory.newId())
        .location(new Location())
        .build();
    entity.setMovement(movementFactory.newSlidingMovement());
    entity.setBrain(brainFactory.newRandomBrain());
    return entity;
  }

  public Pickaxe newPickaxe() {
    SimpleMovement movement = movementFactory.newSimpleMovement();
    SimpleBrain brain = brainFactory.newSimpleBrain();
    Pickaxe pickaxe = new Pickaxe();
    pickaxe.setId(idFactory.newId());
    pickaxe.setMovement(movement);
    pickaxe.setBrain(brain);
    return pickaxe;
  }
}
