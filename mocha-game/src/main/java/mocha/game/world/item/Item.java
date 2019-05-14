package mocha.game.world.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mocha.game.world.item.itemprototype.ItemPrototype;
import mocha.shared.Identified;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item implements Identified<Integer> {

  private Integer id;
  private ItemPrototype itemPrototype;
  private Integer data0;
  private Integer data1;
  private Integer data2;

  public String getSpriteId() {
    return itemPrototype.getSpriteId();
  }

}
