package mocha.game.world.entity;

import mocha.game.world.Location;
import mocha.game.world.collision.Collider;
import mocha.game.world.item.Item;

public class BaseEntity implements Entity {

  private Integer id;
  private Location location = new Location();
  private EntityType entityType = EntityType.MOB;
  private int typeId;
  private boolean isBlocking = true;
  private Item leftHand;
  private Item rightHand;
  private String spriteId = "/mocha/gfx/sprites/slime/slime0.png::0";
  private double scale = 1.0;

  protected BaseEntity() {
  }

  public BaseEntity(Integer id) {
    this.id = id;
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
