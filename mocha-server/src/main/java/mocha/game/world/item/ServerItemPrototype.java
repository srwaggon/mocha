package mocha.game.world.item;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ServerItemPrototype extends ItemPrototype {

  public ServerItemPrototype() {
  }

  public ServerItemPrototype(Integer id, String name, String spriteId, ItemType itemType, String description) {
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
  public String getSpriteId() {
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
