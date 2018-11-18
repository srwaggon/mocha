package mocha.game.world.entity;

import mocha.game.world.Location;
import mocha.game.world.entity.movement.MovementFactory;
import mocha.game.world.entity.movement.SimpleMovement;
import mocha.shared.IdFactory;

public class EntityFactory {

  private MovementFactory movementFactory;
  private IdFactory<Entity> entityIdFactory;

  public EntityFactory(MovementFactory movementFactory, IdFactory<Entity> entityIdFactory) {
    this.movementFactory = movementFactory;
    this.entityIdFactory = entityIdFactory;
  }

  public Entity newSimple() {
    Entity entity = Entity.builder()
        .id(entityIdFactory.newId())
        .location(new Location())
        .build();
    entity.setMovement(movementFactory.newSimpleMovement());
    return entity;
  }

  public Entity newSlider() {
    Entity entity = Entity.builder()
        .id(entityIdFactory.newId())
        .location(new Location())
        .build();
    entity.setMovement(movementFactory.newSlidingMovement(entity));
    return entity;
  }

  public Entity newRandomSlider() {
    Entity entity = Entity.builder()
        .id(entityIdFactory.newId())
        .location(new Location())
        .build();
    entity.setMovement(movementFactory.newSlidingMovement(entity));
    return entity;
  }

  public Pickaxe newPickaxe() {
    SimpleMovement movement = movementFactory.newSimpleMovement();
    Pickaxe pickaxe = new Pickaxe();
    pickaxe.setId(entityIdFactory.newId());
    pickaxe.setMovement(movement);
    return pickaxe;
  }
}
