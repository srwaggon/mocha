package mocha.game.world.item;

import lombok.AllArgsConstructor;
import lombok.Value;
import mocha.game.command.Command;
import mocha.game.world.item.itemprototype.ItemPrototype;

@Value
@AllArgsConstructor
public class UpdateItemCommand implements Command {

  private int id;
  private ItemPrototype itemPrototype;
  private int data0;
  private int data1;
  private int data2;

}
