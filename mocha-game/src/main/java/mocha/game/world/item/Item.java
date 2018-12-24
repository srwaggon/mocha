package mocha.game.world.item;

import mocha.shared.Identified;

public class Item implements Identified<Integer> {

  private Integer id;
  private ItemPrototype itemPrototype;
  private Integer data0;
  private Integer data1;
  private Integer data2;

  public Item() {
  }

  public Item(Integer id, ItemPrototype itemPrototype, Integer data0, Integer data1, Integer data2) {
    this.id = id;
    this.itemPrototype = itemPrototype;
    this.data0 = data0;
    this.data1 = data1;
    this.data2 = data2;
  }

  @Override
  public Integer getId() {
    return id;
  }

  @Override
  public void setId(Integer id) {
    this.id = id;
  }

  public ItemPrototype getItemPrototype() {
    return itemPrototype;
  }

  public void setItemPrototype(ItemPrototype itemPrototype) {
    this.itemPrototype = itemPrototype;
  }

  public Integer getData0() {
    return data0;
  }

  public void setData0(Integer data0) {
    this.data0 = data0;
  }

  public Integer getData1() {
    return data1;
  }

  public void setData1(Integer data1) {
    this.data1 = data1;
  }

  public Integer getData2() {
    return data2;
  }

  public void setData2(Integer data2) {
    this.data2 = data2;
  }

  public String getSpriteId() {
    return itemPrototype.getSpriteId();
  }
}
