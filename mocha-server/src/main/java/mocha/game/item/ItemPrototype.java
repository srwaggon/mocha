package mocha.game.item;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ItemPrototype {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String name;
  private int spriteId;
  private ItemType itemType;
  private String description;

  protected ItemPrototype() {
  }

  public ItemPrototype(Long id, String name, int spriteId, ItemType itemType) {
    this.id = id;
    this.name = name;
    this.spriteId = spriteId;
    this.itemType = itemType;
  }
}
