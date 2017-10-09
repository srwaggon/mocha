package mocha.game.world.entity;

import mocha.game.world.entity.movement.Movement;
import mocha.game.world.entity.movement.SimpleMovement;
import mocha.gfx.Drawable;
import mocha.gfx.MochaCanvas;

public class Entity implements Drawable {
  private static int lastId = 0;
  private int id;
  protected Movement movementComponent = new SimpleMovement();

  Entity() {
    this.id = ++lastId;
  }

  Entity(int id) {
    this.id = id;
    lastId = id > lastId ? id : lastId;
  }

  public int getId() {
    return id;
  }

  public void draw(MochaCanvas mochaCanvas, int x, int y) {
    mochaCanvas.drawSprite(2, movementComponent.getLocation());
  }

  public void tick() {
    move();
  }

  private void move() {
    this.getMovementComponent().tick();
  }

  public Movement getMovementComponent() {
    return movementComponent;
  }

}
