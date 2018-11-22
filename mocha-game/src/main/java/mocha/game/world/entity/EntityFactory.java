package mocha.game.world.entity;

import mocha.game.world.Location;
import mocha.game.world.entity.movement.MovementFactory;
import mocha.game.world.entity.movement.SimpleMovement;

public class EntityFactory {

  private MovementFactory movementFactory;

  public EntityFactory(MovementFactory movementFactory) {
    this.movementFactory = movementFactory;
  }

  public Entity newSimple() {
    Entity entity = Entity.builder()
        .location(new Location())
        .build();
    entity.setMovement(movementFactory.newSimpleMovement());
    return entity;
  }

  public Entity newSlider() {
    Entity entity = Entity.builder()
        .location(new Location())
        .build();
    entity.setMovement(movementFactory.newSlidingMovement(entity));
    return entity;
  }

  public Entity newRandomSlider() {
    Entity entity = Entity.builder()
        .location(new Location())
        .build();
    entity.setMovement(movementFactory.newSlidingMovement(entity));
    return entity;
  }

  public Pickaxe newPickaxe() {
    SimpleMovement movement = movementFactory.newSimpleMovement();
    Pickaxe pickaxe = new Pickaxe();
    pickaxe.setMovement(movement);
    return pickaxe;
  }
}
