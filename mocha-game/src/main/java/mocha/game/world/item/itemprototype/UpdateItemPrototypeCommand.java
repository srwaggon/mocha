package mocha.game.world.item.itemprototype;

import lombok.AllArgsConstructor;
import lombok.Value;
import mocha.game.command.Command;
import mocha.game.world.item.ItemType;

@Value
@AllArgsConstructor
public class UpdateItemPrototypeCommand implements Command {

  private int id;
  private String name;
  private String spriteId;
  private ItemType itemType;
  private String description;

}
