package mocha.game.world.entity;

import javafx.scene.canvas.GraphicsContext;
import mocha.game.gfx.Renderable;

public class Entity implements Renderable {
  private static int lastId = 0;
  private int id;
  private Location location = new Location();

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

  @Override
  public void render(GraphicsContext graphics) {
  }

  public void tick() {
    move();
  }

  public void move() {
    this.location.applyDx();
    this.location.applyDy();
  }

  public Location getLocation() {
    return this.location;
  }

  public void setLocation(Location location) {
    this.location = location;
  }

}
