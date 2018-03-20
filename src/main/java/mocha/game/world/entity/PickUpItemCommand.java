package mocha.game.world.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import mocha.game.command.Command;

@Data
@AllArgsConstructor
public class PickUpItemCommand implements Command {
  private Entity pickingUpEntity;
}
