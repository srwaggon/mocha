package mocha.game.world.entity;

import mocha.game.world.Location;
import mocha.game.world.collision.Collider;
import mocha.game.world.collision.Collision;
import mocha.game.world.entity.movement.MovementType;
import mocha.game.world.entity.prototype.EntityPrototype;
import mocha.game.world.item.Item;

public class BaseEntity implements Entity {

  private int speed = 2;
  private MovementType movementType = MovementType.STILL;
  private int height = 32;
  private int width = 32;
  private Integer id;
  private Location location = new Location();
  private EntityType entityType = EntityType.MOB;
  private int typeId;
  private boolean isBlocking = true;
  private Item leftHand;
  private Item rightHand;
  private String spriteId = "/mocha/gfx/sprites/slime/slime0.png::0";
  private double scale = 1.0;
  private Collision collision;

  protected BaseEntity() {
  }

  public BaseEntity(Integer id) {
    this.id = id;
  }

  BaseEntity(Integer id, EntityPrototype entityPrototype) {
    this.id = id;
    this.typeId = entityPrototype.getTypeId();
  }

  public BaseEntity(Integer id, Location location) {
    this.id = id;
    this.location = location;
  }

  public String toString() {
    return "{" +
        "\"id\":" + id + ", " +
        "\"location\": " + location +
        "}";
  }

  @Override
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

  @Override
  public Location getLocation() {
    return location;
  }

  @Override
  public String getSpriteId() {
    return spriteId;
  }

  @Override
  public void setSpriteId(String spriteId) {
    this.spriteId = spriteId;
  }

  @Override
  public double getScale() {
    return scale;
  }

  public void setScale(double scale) {
    this.scale = scale;
  }

  @Override
  public MovementType getMovementType() {
    return movementType;
  }

  @Override
  public void setMovementType(MovementType movementType) {
    this.movementType = movementType;
  }

  @Override
  public int getSpeed() {
    return speed;
  }

  @Override
  public void setSpeed(int speed) {
    this.speed = speed;
  }

  @Override
  public int getWidth() {
    return width;
  }

  @Override
  public void setWidth(int width) {
    this.width = width;
  }

  @Override
  public int getHeight() {
    return height;
  }

  @Override
  public void setHeight(int height) {
    this.height = height;
  }

  @Override
  public void setCollision(Collision collision) {
    this.collision = collision;
  }

  @Override
  public Collision getCollision() {
    return this.collision;
  }

  @Override
  public EntityType getEntityType() {
    return entityType;
  }

  @Override
  public boolean isBlocking() {
    return isBlocking;
  }

  public void setBlocking(boolean isBlocking) {
    this.isBlocking = isBlocking;
  }

  @Override
  public void collide(Collider collider) {
  }

  @Override
  public int getTypeId() {
    return typeId;
  }

  @Override
  public boolean isTransient() {
    return false;
  }

  protected void setEntityType(EntityType entityType) {
    this.entityType = entityType;
  }

  protected void setTypeId(int typeId) {
    this.typeId = typeId;
  }

  public Item getLeftHand() {
    return leftHand;
  }

  public void setLeftHand(Item leftHand) {
    this.leftHand = leftHand;
  }

  public Item getRightHand() {
    return rightHand;
  }

  public void setRightHand(Item rightHand) {
    this.rightHand = rightHand;
  }

  @Override
  public void pickup(Entity entity) {
    entity.pickedUpBy(this);
  }

  public void pickedUpBy(Entity entity) {
  }

}
