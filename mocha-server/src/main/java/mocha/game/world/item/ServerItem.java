package mocha.game.world.item;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import mocha.game.world.item.itemprototype.ItemPrototype;

@Entity
public class ServerItem extends Item {

  public ServerItem() {
  }

  ServerItem(int id, ServerItemPrototype serverItemPrototype, int data0, int data1, int data2) {
    super(id, serverItemPrototype, data0, data1, data2);
  }

  @Id
  @Override
  public Integer getId() {
    return super.getId();
  }


  @ManyToOne(
      targetEntity = ServerItemPrototype.class,
      cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
  @JoinColumn
  @Override
  public ItemPrototype getItemPrototype() {
    return super.getItemPrototype();
  }

  @Column
  @Override
  public Integer getData0() {
    return super.getData0();
  }

  @Column
  @Override
  public Integer getData1() {
    return super.getData1();
  }

  @Column
  @Override
  public Integer getData2() {
    return super.getData2();
  }
}
