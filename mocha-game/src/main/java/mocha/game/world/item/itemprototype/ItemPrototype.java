package mocha.game.world.item.itemprototype;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mocha.game.world.item.ItemType;
import mocha.shared.Identified;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemPrototype implements Identified<Integer> {

  private Integer id;
  private String name;
  private String spriteId;
  private ItemType itemType;
  private String description;

}
