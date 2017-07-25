package mocha.game.world.entity;

import mocha.gfx.Drawable;
import mocha.game.world.Location;
import mocha.game.world.entity.movement.Movement;
import mocha.game.world.entity.movement.SimpleMovement;
import mocha.gfx.MochaCanvas;

public class Entity implements Drawable {
  private static int lastId = 0;
  private int id;
  private Location location = new Location();
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
    mochaCanvas.drawSprite(2, location);
  }

  public void tick() {
    move();
  }

  public void move() {
    this.getMovementComponent().tick();
  }

  public Location getLocation() {
    return this.location;
  }

  public void setLocation(Location location) {
    this.location = location;
  }

  public Movement getMovementComponent() {
    return movementComponent;
  }

}
