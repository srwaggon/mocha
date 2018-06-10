package mocha.game.world.entity;

import mocha.game.world.Location;
import mocha.game.world.entity.movement.Movement;
import mocha.game.world.entity.movement.SimpleMovement;
import mocha.game.world.entity.movement.collision.SimpleCollision;
import mocha.shared.Identified;

public class Entity implements Identified {
  private int id;
  private Location location = new Location();
  private Movement movement = new SimpleMovement(new SimpleCollision());

  public Entity() {
  }

  public Entity(int id, Location location) {
    this.id = id;
    this.location = location;
  }

  public Entity(int id, Location location, Movement movement) {
    this.id = id;
    this.location = location;
    this.movement = movement;
  }

  public String toString() {
    return "{" +
        "\"id\":" + id + ", " +
        "\"location\": " + location +
        "}";
  }

  public Location getLocation() {
    return location;
  }

  public int getSpriteId() {
    return 130;
  }

  public double getScale() {
    return 2.0;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Movement getMovement() {
    return movement;
  }

  public void setMovement(Movement movement) {
    this.movement = movement;
    movement.setEntity(this);
  }

  public static EntityBuilder builder() {
    return new EntityBuilder();
  }

  static class EntityBuilder {
    private int id;
    private Location location;
    private Movement movement;

    private EntityBuilder() {
    }

    public EntityBuilder id(int id) {
      this.id = id;
      return this;
    }

    public EntityBuilder location(Location location) {
      this.location = location;
      return this;
    }

    public EntityBuilder movement(Movement movement) {
      this.movement = movement;
      return this;
    }

    public Entity build() {
      return new Entity(id, location, movement);
    }
  }
}
