package mocha.game.world.entity;

import mocha.game.world.Location;
import mocha.game.world.entity.movement.collision.Collider;
import mocha.shared.Identified;

public class Entity implements Collider, Identified<Integer> {

  Integer id;
  Location location = new Location();

  public Entity() {
  }

  public Entity(int id, Location location) {
    this.id = id;
    this.location = location;
  }

  public String toString() {
    return "{" +
        "\"id\":" + id + ", " +
        "\"location\": " + location +
        "}";
  }

  public Integer getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Location getLocation() {
    return location;
  }

  public int getSpriteId() {
    return 128;
  }

  public double getScale() {
    return 2.0;
  }

  public Entity at(int x, int y) {
    this.location.set(x, y);
    return this;
  }

  @Override
  public boolean isBlocking() {
    return true;
  }

  @Override
  public void collide(Collider collider) {

  }
}
