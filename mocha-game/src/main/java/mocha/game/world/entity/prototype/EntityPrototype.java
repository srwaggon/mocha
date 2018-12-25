package mocha.game.world.entity.prototype;

import mocha.game.world.entity.EntityType;
import mocha.game.world.item.Item;
import mocha.shared.Identified;

public class EntityPrototype implements Identified<Integer> {

  private Integer id;
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
      EntityType entityType,
      int typeId,
      boolean isBlocking,
      Item leftHand,
      Item rightHand,
      String spriteId,
      double scale
  ) {
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
      EntityType entityType,
      int typeId,
      boolean isBlocking,
      Item leftHand,
      Item rightHand,
      String spriteId,
      double scale
  ) {
    this.id = id;
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
}
