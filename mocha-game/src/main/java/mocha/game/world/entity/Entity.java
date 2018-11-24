package mocha.game.world.entity;

import mocha.game.world.Location;
import mocha.game.world.entity.movement.collision.Collider;
import mocha.shared.Identified;

public class Entity implements Collider, Identified<Integer> {

  private Integer id;
  private Location location = new Location();

  public Entity() {
  }

  public Entity(Integer id, Location location) {
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

  @Override
  public void setId(Integer id) {
    this.id = id;
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

  @Override
  public boolean isBlocking() {
    return true;
  }

  @Override
  public void collide(Collider collider) {

  }
}
