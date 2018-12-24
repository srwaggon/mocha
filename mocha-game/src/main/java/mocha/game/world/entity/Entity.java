package mocha.game.world.entity;

import mocha.game.world.Location;
import mocha.game.world.collision.Collider;
import mocha.game.world.item.Item;
import mocha.shared.Identified;

public class Entity implements Collider, Identified<Integer> {

  private Integer id;
  private Location location = new Location();
  private EntityType entityType = EntityType.MOB;
  private int typeId;
  private boolean isBlocking = true;
  private Item leftHand;
  private Item rightHand;
  private String spriteId = "/mocha/gfx/sprites/slime/slime0.png::0";

  public Entity() {
  }

  public Entity(Integer id) {
    this.id = id;
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

  public String getSpriteId() {
    return spriteId;
  }

  public void setSpriteId(String spriteId) {
    this.spriteId = spriteId;
  }

  public double getScale() {
    return 2.0;
  }

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

  public int getTypeId() {
    return typeId;
  }

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

  public void pickup(Entity entity) {
    entity.pickedUpBy(this);
  }

  protected void pickedUpBy(Entity entity) {

  }
}
