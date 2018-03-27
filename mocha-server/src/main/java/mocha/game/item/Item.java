package mocha.game.item;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Item {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @ManyToOne
  @JoinColumn
  private ItemPrototype itemPrototype;

  private Integer x;
  private Integer y;

  private Integer data0;
  private Integer data1;
  private Integer data2;

  public Item() {
  }

  public Item(Long id, ItemPrototype itemPrototype, Integer x, Integer y, Integer data0, Integer data1, Integer data2) {
    this.id = id;
    this.itemPrototype = itemPrototype;
    this.x = x;
    this.y = y;
    this.data0 = data0;
    this.data1 = data1;
    this.data2 = data2;
  }
}
