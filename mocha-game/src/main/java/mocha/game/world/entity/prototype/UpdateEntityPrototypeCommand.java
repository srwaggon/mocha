package mocha.game.world.entity.prototype;

import lombok.AllArgsConstructor;
import lombok.Value;
import mocha.game.command.Command;

@Value
@AllArgsConstructor
public class UpdateEntityPrototypeCommand implements Command {

  private EntityPrototype entityPrototype;

}
