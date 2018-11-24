package mocha.game.item;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import mocha.game.world.item.ItemPrototype;
import mocha.game.world.item.ItemType;

@Entity
public class ServerItemPrototype extends ItemPrototype {

  protected ServerItemPrototype() {
  }

  public ServerItemPrototype(Integer id, String name, int spriteId, ItemType itemType, String description) {
    super(id, name, spriteId, itemType, description);
  }

  @Id
  public Integer getId() {
    return super.getId();
  }

  @Column
  @Override
  public String getName() {
    return super.getName();
  }

  @Column
  @Override
  public int getSpriteId() {
    return super.getSpriteId();
  }

  @Column
  @Override
  public ItemType getItemType() {
    return super.getItemType();
  }

  @Column
  @Override
  public String getDescription() {
    return super.getDescription();
  }

}
