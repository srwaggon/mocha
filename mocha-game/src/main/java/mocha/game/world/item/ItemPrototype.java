package mocha.game.world.item;

import mocha.shared.Identified;

public class ItemPrototype implements Identified<Integer> {

  private Integer id;
  private String name;
  private String spriteId;
  private ItemType itemType;
  private String description;

  public ItemPrototype() {
  }

  public ItemPrototype(Integer id, String name, String spriteId, ItemType itemType, String description) {
    this.id = id;
    this.name = name;
    this.spriteId = spriteId;
    this.itemType = itemType;
    this.description = description;
  }

  @Override
  public Integer getId() {
    return id;
  }

  @Override
  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSpriteId() {
    return spriteId;
  }

  public void setSpriteId(String spriteId) {
    this.spriteId = spriteId;
  }

  public ItemType getItemType() {
    return itemType;
  }

  public void setItemType(ItemType itemType) {
    this.itemType = itemType;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
