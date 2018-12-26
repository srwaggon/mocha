package mocha.game.world.entity.prototype;

import mocha.game.world.entity.EntityType;
import mocha.game.world.item.Item;
import mocha.shared.Identified;

public class EntityPrototype implements Identified<Integer> {

  private Integer id;
  private Integer baseEntityPrototypeId;
  private EntityType entityType = EntityType.MOB;
  private int typeId;
  private boolean isBlocking = true;
  private Item leftHand;
  private Item rightHand;
  private String spriteId = "/mocha/gfx/sprites/slime/slime0.png::0";
  private double scale = 1.0;

  public EntityPrototype() {
  }

  public EntityPrototype(
      Integer baseEntityPrototypeId,
      EntityType entityType,
      int typeId,
      boolean isBlocking,
      Item leftHand,
      Item rightHand,
      String spriteId,
      double scale
  ) {
    this.baseEntityPrototypeId = baseEntityPrototypeId;
    this.entityType = entityType;
    this.typeId = typeId;
    this.isBlocking = isBlocking;
    this.leftHand = leftHand;
    this.rightHand = rightHand;
    this.spriteId = spriteId;
    this.scale = scale;
  }

  public EntityPrototype(
      Integer id,
      Integer baseEntityPrototypeId,
      EntityType entityType,
      int typeId,
      boolean isBlocking,
      Item leftHand,
      Item rightHand,
      String spriteId,
      double scale
  ) {
    this.id = id;
    this.baseEntityPrototypeId = baseEntityPrototypeId;
    this.entityType = entityType;
    this.typeId = typeId;
    this.isBlocking = isBlocking;
    this.leftHand = leftHand;
    this.rightHand = rightHand;
    this.spriteId = spriteId;
    this.scale = scale;
  }

  @Override
  public Integer getId() {
    return id;
  }

  @Override
  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getBaseEntityPrototypeId() {
    return baseEntityPrototypeId;
  }

  public void setBaseEntityPrototypeId(Integer baseEntityPrototypeId) {
    this.baseEntityPrototypeId = baseEntityPrototypeId;
  }

  public EntityType getEntityType() {
    return entityType;
  }

  public void setEntityType(EntityType entityType) {
    this.entityType = entityType;
  }

  public int getTypeId() {
    return typeId;
  }

  public void setTypeId(int typeId) {
    this.typeId = typeId;
  }

  public boolean isBlocking() {
    return isBlocking;
  }

  public void setBlocking(boolean blocking) {
    isBlocking = blocking;
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

  public String getSpriteId() {
    return spriteId;
  }

  public void setSpriteId(String spriteId) {
    this.spriteId = spriteId;
  }

  public double getScale() {
    return scale;
  }

  public void setScale(double scale) {
    this.scale = scale;
  }
}
